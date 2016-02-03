/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

// required static class
import static org.krudo.Constant.*;
import static org.krudo.util.Decode.*;
import static org.krudo.util.Tools.*;
import static org.krudo.util.Debug.*;
import static org.krudo.util.Zobrist.hash;

// search main class
public final class Search {	

	// node-centric search
	private final Node n; 
	
	// best moves of a positions after idrun call
	private final Move find;	// founded moves after abrun
	
	// candidate move after idrun
	private final Move move;	
	
	// collect principal variation
	public Move[] pv;
			
	// searching controls and related 		
	public int stop = 0;
	public int zero = 0;
	public int beep = 0;
	
	// iterative deepiing deep cursor and limit
	public int eval;
	public int deep;
	public int deepStart;
	public int deepLimit;
	
	// iterative deeping time values
	public long time;
	public long	timeStart;
	public long	timeLimit;
		
	// count nodes for ab search
	public int ns;
	
	// count nodes for quiesce search
	public int nq;
		
	// logged info 
	public int logAction;	// log action (used for debug)
	public int logWeight;	// log weight of move founded
	public int logNodes;	// log weight of move founded
	public int logDeep;		// log weight of move founded
	public long logTime;	// log weight of move founded
	public long	logNPS;		// log node per seconds		
	public String logMove;	// log move observed
	public String logLine;	// log variation observed 
	public boolean logEnabled; // log node per seconds		
	
	// temp for root score for recursive call
	private int s;

	//private Table t = Cache.table;
	
	// constructor with node-centric search
	public Search(Node node) {
		
		// fill internal node use in node-centric search
		n = node;
		
		//
		find = new Move();
		
		//
		move = new Move();
		
		//
		logEnabled = true;
	}
		
	// launch alfabeta search to evaluate a position
	public final int eval(int d) {
	
		//
		init();
																	
		//
		//find.empty();
	
		// set time limit for the searcing engine
		timeLimit = time() + TIME_5_MINUTES;
	
		// run alpha-beta routine to evaluate
		return abrun(d,-oo,+oo);		
	}
	
	// public method to start search with large time-limit
	public final void start(int deep) {
		
		// start search time limit 5minutes
		start(deep, TIME_5_MINUTES);
	}
		
	// public method to start search custom time-limit
	public final void start(int deep, long time) {
				
		//
		init();
		
		// deep start value for iterative deeping
		deepStart = 1;
		
		// set deep limit for iterative deeping
		deepLimit = deep;
					
		// set time limit for the searcing engine routine
		timeLimit = timeStart + time;

		// start iterative deeping search
		idrun();
	}
	
	//
	public final String bestmove(int deep) {
		
		// launch iterative deeping search
		start(deep);
		
		// return best move founded by descriptive
		return ""; //desc(n,move,0);
	}
	
	
	// preset for start search or eval
	private void init() {
		
		// reset nodes search counter
		ns = 0;
		
		// reset nodes quiesce counter
		nq = 0;
		
		pv = new Move[20];
		for(int i=0; i<20; i++) {
			pv[i] = new Move();			
		}
		
		// reset stop flag
		stop = not;	
		
		// place offset for search variation
		zero = n.L.i;
		
		// set time start for required log or other
		timeStart = time();
	}
		
	// iterative deeping entry-point
	private void idrun() {						
															
		// iterative deeping deep start value
		deep = deepStart;
		
		// empity moves stack
		move.empty();
			
		// iterative deeping alpha start value
		int alfa = -oo;
		
		// iterative deeping beta start value
		int beta = +oo;
				
		// iterative deeping loop
		while (deep <= deepLimit && stop == not) {			
						
			// empty founded moves
			find.empty();

			// launch alfa-beta for searcing candidates 
			eval = abrun(deep, alfa, beta);		
						
			// if found moves and search not are stopped put into candidates
			if (find.l > 0 && stop == not) {
				
				// log best move is the firt of founded 
				log(SEARCH_LOG_PV, pv, 1, deep, eval);		
								
				//
				move.set(find); 
			}
			
			// increade depth of search
			deep++;						
		}	
		
		// exit from iterative deep sort best moves		
		move.sort();
		
		// log best move is the firt of founded 
		log(SEARCH_LOG_BM, move, 0);		
	}
	
	// alfa-beta entry-point
	private int abrun(int d, int a, int b) {
				
		// generate and sort legal-moves
		Move m = n.legals().sort();
					
		// increase move-stack loop counter
		m.loop();
				
		// loop throut legal-moves
		for (int i=0; i<m.l; i++) {
						
			// make
			n.domove(m,i);
			
			// recursive evaluation search					
			s = abmin(d-1, a, a+1);

			// use null window for correction
			if (s > a) {
				s = abmin(d-1, a, b);				
			}
							
			// log move wight
			//log(SEARCH_LOG_MW, m, i, w);
			
			// soft alfa-cut-off 
			if (s > a) {
				pv[d].set(n.L, zero);
				log(SEARCH_LOG_UP,pv,1,d,s);
				find.put(m,i,s); 		
				//m.w[i] = s;
				a = s;				
			}			

			// undo
			n.unmove();
		}
		
		//
		m.stop();
			
		//
		return a; 
	}
	
	//
	private int abmax(int d , int a, int b) {
	
		// temp var to store evaluation weight orscore
		int w;
		
		// prepare traspoisiont table for current hash
		t.begin(hash(n));
		
		// trasposition table probe
		if (t.probe(d, a, b)) { return t.value(); }
		
		// return quiescence value-search, increase node count and store trasposition table value
		if (d == 0) { ns++; w = qmax(a, b); t.store(d, w, t.EXACT); return w; }
							
		// get legal-moves and sort 
		Move m = n.legals().sort();
		
		// no legal moves check-mate or stale-mate
		if (m.l == 0) { return -mate; }
			
		//
		m.loop();
				
		//
		int p = 0;
		
		//
		for (int i=0; i<m.l; i++) {
			
			// 
			n.domove(m,i);
									
			// 				
			w = abmin(d-1, a, p == 0 ? b : a+1);
			
			//
			if (w > a && p > 0) {
				w = abmin(d-1, a, b);				
			}
			
			// hard cut-off
			if (w >= b) { n.unmove(); m.stop(); t.store(d, a, t.BETA); return b; }
		
			//
			if (w > a) { 						
		
				t.exact();
				
				pv[d].set(n.L, zero);
				
				a = w;
				
				//
				p++;
			}
			
			//
			n.unmove();				
		
		}
		
		//
		m.stop();
		
		//
		t.store(d, a);
		
		//
		return a;		
	}

	// alfa-beta min routine
	private int abmin(int d, int a, int b) {
							
		// at-end quiescence search and increase nodes count
		if (d <= 0) { ns++; return qmin(a, b); }
		
		// generate legal-moves and sort
		Move m = n.legals().sort();

		// no-legals-move exit checkmate
		if (m.l == 0) { return +mate; }
		
		//
		int w, p = 0; 
				
		// 
		m.loop();
		
		// 
		for (int i=0; i<m.l; i++) {
			
			// 
			if (time() > timeLimit) { 
			//	stop = yes;
			}
			
			// make move
			n.domove(m,i);
					
			// 
			w = abmax(d-1, p==0 ? a : b-1, b);
			
			//
			if (w < b && p > 0) {
				w = abmax(d-1, a, b);				
			}
										
			// hard cut-off
			if (w <= a || stop == yes) { n.unmove(); m.stop(); return a; }
												
			//
			if (w < b) { 				
				pv[d].set(n.L, zero);
				b = w; 		
				p++;
			}					
		
			// unmake move
			n.unmove(); 
		}
		
		//
		m.stop();
		
		//
		return b;	
	}

	// quiescence max routine
	private int qmax(int a, int b) {
						
		// eval position
		int w = Eval.eval(n);
		
		// increase nodes quiesce count
		nq++;
		
		//
		if (w >= b) { 
			return b; 
		}
		
		//
		if (w > a) { 
			a = w; 
		}

		// quiescence need sort moves
		Move m = n.legals().sort();
				
		// checkmate exit from 
		if (m.l == 0) {
			return -mate; 
		}
		
		//
		m.loop();
		
		//
		for (int i=0; i<m.l; i++) if (mask(m.k[i],capt)) { 
			
			//
			n.domove(m,i);
						
			//
			w = qmin(a, b);
			
			//
			n.unmove();
			
			//
			if (w >= b) { 
				
				//
				m.stop();
		
				//
				return b; 
			}

			//
			if (w > a) { 
				a = w; 
			}
		}
				
		//
		m.stop();
		
		//
		return a;
	}
 
	// quiescence min search
	private int qmin(int a, int b) {
		
		// eval position 
		int w = -Eval.eval(n);

		// increase nodes count
		nq++;
		
		// return alfa if wrost
		if (w <= a) { 
			return a; 
		}
		
		// set new value for upper limit
		if (w < b) { 
			b = w; 
		}
		
		// quiescenze need sort moves
		Move m = n.legals().sort();

		// exit checkmate or stalemate
		if (m.l == 0) { 
			return +mate; 
		}
		
		//
		m.loop();
				
		// loop throut capturers
		for (int i=0; i<m.l; i++) if ((m.k[i]&quie)==quie) { 
			
			// make move 
			n.domove(m, i);
						
			// iterate qsearch
			w = qmax(a, b);
			
			// redo move
			n.unmove();

			// hard cut-off
			if (w <= a) { m.stop(); return a; }		
				
			// soft cut-off
			if (w < b) { b = w; }
		}
		
		//
		m.stop();
		
		// return new uppel limit
		return b;
	}
			
	// default log callback
	public Runnable log = new Runnable() {
		
		//
		@Override 
		public void run(){
			
			// switch output by logAction
			switch(logAction) {
				
				/**/ 
				case SEARCH_LOG_ID: 
					echo("id:", logDeep); 
					break; /**/
				
				/**/	
				case SEARCH_LOG_MW: 
					echo("mw:", logMove, "("+logWeight+")"); 
					break; /**/
				
				/**/
				case SEARCH_LOG_BM: 
					echo("------------------");
					echo("bm:", logMove, "("+logWeight+")", "\n"); 
					break; /**/
					
				case SEARCH_LOG_PV: 
					echo("pv:",logDeep,"("+logWeight+")",logLine); 
					break;
				
				case SEARCH_LOG_UP: 
					echo("up:",logDeep,"("+logWeight+")",logLine); 
					break;
					
				//case _LOG_BB_: echo("bb",lw,lv,lm); break;
				//case _LOG_EX_: echo("ex"); break;
			}
		}
	};
	
	// log a single move into move-stack with weight
	private void log(int a, Move m, int i, int w) {
		logMove		= i2m(m,i);
		logWeight	= w;
		log(a);
	}
	
	// log a 
	private void log(int a, Move m, int i) {
		logMove		= i2m(m,i);
		logWeight	= m.w[i];
		log(a);
	}

	// log a 
	private void log(int a, Move[] pv, int i, int d, int w) {
		logLine		= i2m(pv[i]);
		logDeep		= d;
		logWeight	= w;
		log(a);
	}

	//
	private void log(int a, int d1, int d2) {
		logAction	= a;
		logDeep		= d1;
		log.run();
	}
	
	//
	private void log(int a, int w) {
		logLine		= i2m(n.L,zero);
		logWeight	= w;
		log(a);
	}

	//
	private void log(int a) {
		logAction	= a;
		logTime		= time() - timeStart;
		logNodes	= ns;
		logNPS		= logTime > 0 ? ns / logTime : 0; 
		if (logEnabled) {
			log.run();
		}
	}
}