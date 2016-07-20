/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

// required static class
import static org.krudo.Config.*;
import static org.krudo.Constant.*;
import static org.krudo.Decode.*;
import static org.krudo.Describe.*;
import static org.krudo.Tool.*;
import static org.krudo.Debug.*;
import static org.krudo.Zobrist.hash;

// search main class
public final class Search 
{    
    // node-centric search
    public Node n = null; 
    
    // best moves of a positions after idrun call
    private final Move find = null; // founded moves after abrun
    
    // candidate move after idrun
    private final Move move = null;    
    
    // collect principal variation
    public Move[] pv;
            
    // searching controls and related         
    public int stop = 0;
    public int lineStart = 0;
    public int beep = 0;
    
    // iterative deepiing deep cursor and limit
    public int score;
    public int deep;
    public int deepStart;
    public int deepLimit;
    
    // iterative deeping time values
    public long time;
    public long timeStart;
    public long timeLimit;
        
    // count nodes for ab search
    public long ns;
    public long nsnext;
    public long nstime;
    public long nspoll = 500000;
    
    // count nodes for quiesce search
    public int nq;
        
    // logged info 
    public int logAction;    // log action (used for debug)
    public int logWeight;    // log weight of move founded
    public int logNodes;    // log weight of move founded
    public int logDeep;        // log weight of move founded
    public long logTime;    // log weight of move founded
    public long logNPS;        // log node per seconds        
    public String logMove;    // log move observed
    public String logLine;    // log variation observed 
    public boolean logEnabled; // log node per seconds        
  
    private final static int NT_NODE = 0;
  
    
    
    //
    public String info;
    
    //
    public Runnable sendinfo = () -> 
    {
        print("INFO: "+info);
    };
    
    //
    public String bestmove;
       
    //
    public Runnable sendbestmove = () -> 
    {
        print("BESTMOVE: "+bestmove);
    };
      
    //private Table t = Cache.table;
    
    // constructor with node-centric search
    public Search(Node node) 
    {   
        // fill internal node use in node-centric search
        n = node;
        
        /*
        //
        //find = new Move();
        
        //
        //move = new Move();
        
        //
        logEnabled = true;*/
    }
        
    /*
    // launch alfabeta search to evaluate a position
    public final int eval(int d)
    { 
        //
        //find.empty();
    
        // set time limit for the searcing engine
        timeLimit = time() + TIME_5_MINUTES;
    
        // run alpha-beta routine to evaluate
        return abrun(d,-oo,+oo);        
    }
    */
    
    // public method to start search with large time-limit
    public final void start(int deep) 
    {    
        // start search time limit 5minutes
        start(deep, TIME_5_MINUTES);
    }
        
    // public method to start search custom time-limit
    public final void start(int deep, long time) 
    {            
        
        //
        //pv = new Move[20];
        
        // reset stop flag
        //stop = not;    
        
        // place offset for search variation
        lineStart = n.L.i;
        
        // deep start value for iterative deeping
        deepStart = 1;
        
        // set deep limit for iterative deeping
        deepLimit = deep;
                    
        // set time start for required log or other
        timeStart = time();
        
        // set time limit for the searcing engine routine
        timeLimit = timeStart + time;

        // start iterative deeping search
        idrun();
    }
         
    // iterative deeping entry-point
    private void idrun() 
    {                                                                                
        // iterative deeping deep start value
        deep = deepStart;
        
        // empity moves stack
        //move.empty();
            
        // iterative deeping alpha start value
        int alfa = -oo;
        
        // iterative deeping beta start value
        int beta = +oo;
        
        //
        int score = alfa;
        
        //
        PV pv = new PV();
              
        //
        info("id-run", ""+deepLimit);
        
        // iterative deeping loop
        while (deep <= deepLimit && stop == NOT)
        {                            
            // reset nodes search counter
            ns = 0;

            // reset nodes quiesce counter
            nq = 0;
            
            //
            nstime = time();
            
            nsnext = nspoll;
            
            //
            long t = time();
            
            //
            info("id-loop-run", deep+"/"+deepLimit);

            // empty founded moves
            //find.empty();

            // launch alfa-beta for searcing candidates 
            score = abrun(alfa, beta, pv);        
            
            
            
            /*            
            // if found moves and search not are stopped put into candidates
            if (find.i > 0 && stop == not) 
            {    
                // log best move is the firt of founded 
                //log(SEARCH_LOG_PV, pv, 1, deep, eval);        
                                
                //
                //move.set(find); 
            }
            */
            
            //
            t = time() - t;
            
            //
            long r = t > 0 ? ns / t : 0;
                
            //
            info("id-loop-end", deep+"/"+deepLimit+" "+t+"ms "+ns+"n "+r+"knps");
              
            // increade depth of search
            deep++;                        
        }    
        
        //
        info("id-end", ""+deepLimit+" "+score+" "+desc(pv));
        
        // exit from iterative deep sort best moves        
        //move.sort();
        
        // log best move is the firt of founded 
        //log(SEARCH_LOG_BM, move, 0);    
        
        //
        sendbestmove(desc(pv, 0));
    }
    
    // alfa-beta entry-point
    private int abrun(int a, int b, PV pv) 
    {    
        //
        pv.i = 0;
        
        //
        PV new_pv = new PV();
        
        // generate and sort legal-moves
        Move m = n.legals();
              
        // no legal moves check-mate or stale-mate
        if (m.i == 0) 
        { 
            return -mate; 
        }
                
        // 
        m.sort();
        
        //
        for (int i = 0; i < m.i; i++) 
        {   
            //
            //info("ab-loop-run", m2s(m, i));
            
            // make
            n.domove(m, i);
            
            // recursive evaluation search                    
            int s = abmin(deep - 1, a, b, new_pv, NT_NODE);

            // undo
            n.unmove();            

            /*
            // use null window for correction
            if (s > a) 
            {
                s = abmin(d-1, a, b);                
            }
            */ 
                
            // hard cut-off
            if (s >= b && !SEARCH_BRUTE_FORCE) 
            {  
                info("ab-hard-cut-off","");
                //t.store(d, a, t.BETA); 
                //return b; 
                
                //
                a = b;
                
                //
                break;
            }
            
            // soft alfa-cut-off 
            if (s > a) 
            {
                info("ab-soft-cut-off", m2s(m, i)+"="+s+" ["+a+";"+b+"]");
                //
                pv.cat(new_pv, m, i);
                //dump(pv);
                //pv[d].set(n.L, zero);
                //log(SEARCH_LOG_UP, pv, 1,d,s);
                //find.put(m,j,s);         
                //m.w[i] = s;
                a = s;                
            }   
            
            // log move wight
            //log(SEARCH_LOG_MW, m, i, w);            
        } 
        
        //
        Moves.free(m);
       
        //
        return a; 
    }
    
    //
    private int abmax(final int d, int a, int b, final PV pv, final int nt) 
    {   
        // score
        int s;
        
        // prepare traspoisiont table for current hash
        //t.begin(hash(n));
                
        // trasposition table probe
        //if (Tables.probe(n.h, d, a, b)) {         
        //}
        
        // return quiescence value-search, 
        if (d == 0) 
        { 
            //
            ns++;
             
            //
            abcontrol();
            
            //
            s = qmax(a, b, pv);
            
            //t.store(d, w, t.EXACT); 
            
            //
            return s; 
        }
        
        //
        pv.i = 0;
        
        //
        PV new_pv = new PV();
        
        // get legal-moves  
        Move m = n.legals();
                
        // and sort       
        m.sort();
        
        // no legal moves check-mate or stale-mate
        //if (m.l == 0) { return -mate; }
                                  
        //
        for (int i = 0; i < m.i; i++) 
        {            
            // 
            n.domove(m, i);
                                    
            //                 
            //w = abmin(d-1, a, p == 0 ? b : a+1);
            
            //
            //if (w > a && p > 0) {
            s = abmin(d-1, a, b, new_pv, NT_NODE);                
            //}

            //
            n.unmove();                
            
            // hard cut-off
            if (s >= b && !SEARCH_BRUTE_FORCE) 
            {  
                //t.store(d, a, t.BETA); 
                //return b; 
                
                //
                a = b;
                
                //
                break;
            }
        
            // soft cut-off
            if (s > a) 
            {                         
                // t.exact();
              
                //
                pv.cat(new_pv, m, i);
                
                //
                a = s;             
            }
        }
        
        //
        // t.store(d, a);
        
        //
        Moves.free(m);
        
        //
        return a;        
    }

    // alfa-beta min routine
    private int abmin(final int d, int a, int b, final PV pv, final int nt)
    {                        
        //
        int s; 

        // at-end quiescence search and 
        if (d == 0) 
        {
            // increase nodes count
            ns++;
            
            //
            abcontrol();
            
            // return quesence values
            return qmin(a, b, pv); 
        }
        
        //
        pv.i = 0;
        
        //
        PV new_pv = new PV();
        
        // generate legal-moves 
        Move m = n.legals();

        // no-legals-move exit checkmate
        if (m.i == 0) 
        {
            return +mate + d; 
        }
        
        // and sort
        m.sort();        
            
        //
        for (int i = 0; i < m.i; i++)
        {                 
            // make move
            n.domove(m, i);
                    
            // 
            //w = abmax(d-1, p==0 ? a : b-1, b);
            
            //
            //if (w < b && p > 0) {
            s = abmax(d - 1, a, b, new_pv, NT_NODE);                
            //}

            // unmake move
            n.unmove();         

            // hard cut-off
            if (s <= a && !SEARCH_BRUTE_FORCE) 
            { 
                //
                b = a;
                
                //
                break;
            }
                                                
            // soft cut-off
            if (s < b) 
            {     
                
                //
                pv.cat(new_pv, m, i);
                //dump(new_pv);
                //dump(pv);
                
                //
                b = s;                       
            }                    
        } 
        
        //
        Moves.free(m);
   
        //
        return b;    
    }

    // quiescence max routine
    private int qmax(int a, int b, PV pv)
    {                   
        // eval position
        int s = Eval.node(n);
                                
        // hard cut-off
        if (s >= b) { return b; }
        
        // 
        if (!SEARCH_QUIESCENCE) { return s; }
        
        // soft cut-off
        if (s > a) { a = s; }
        
        //
        pv.i = 0;
        
        //
        PV new_pv = new PV();
                
        // quiescence need sort moves
        Capture c = n.capture();
        
        // 
        c.sort();
                               
        //
        for (int i = 0; i < c.i; i++)  
        {
            //
            n.domove(c, i);
                        
            //
            s = qmin(a, b, new_pv);
            
            //
            n.unmove();
            
            // hard cut-off
            if (s >= b && !SEARCH_BRUTE_FORCE) 
            { 
                //
                a = b;
                
                //
                break; 
            }

            // soft cut-off
            if (s > a) 
            { 
                //
                pv.cat(new_pv, c, i);
                
                //
                a = s; 
            }
        }
        
        //
        return a;
    }
 
    // quiescence min search
    private int qmin(int a, int b, PV pv) 
    {        
        // increase nodes count
        nq++;
                
        // eval position 
        int s = -Eval.node(n);
        
        // return alfa if wrost
        if (s <= a) { return a; }

        //
        if (!SEARCH_QUIESCENCE) { return s; }
        
        // set new value for upper limit
        if (s < b) { b = s; }
              
        //
        pv.i = 0;
        
        //
        PV new_pv = new PV();
        
        // quiescenze need sort moves
        Capture c = n.capture();
        
        //
        c.sort();
                        
        // loop throut capturers
        for (int i = 0; i < c.i; i++) 
        {
            // make move 
            n.domove(c, i);
                        
            // iterate qsearch
            s = qmax(a, b, new_pv);
            
            // redo move
            n.unmove();

            // hard cut-off
            if (s <= a  && !SEARCH_BRUTE_FORCE)
            { 
                //
                b = a;
                
                //
                break;
            }        
                
            // soft cut-off
            if (s < b) 
            { 
                //
                pv.cat(new_pv, c, i);
                
                //
                b = s; 
            }
        }

        // 
        return b;
    }
    
    //
    private void abcontrol()
    {
        // 
        if (time() > timeLimit) 
        { 
        //    stop = yes;
        }
        
        if (ns >= nsnext) 
        {
            //print("- "+ns);
            nsnext = ns + nspoll;

            long t = time() - nstime;

            long r = nspoll / t;

            nstime = time();

            info("ab-speed", deep+"/"+deepLimit+" "+t+"ms "+ns+"n "+r+"knps");
            
            //print(Runtime.getRuntime().freeMemory());
        }
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
        logMove        = i2m(m,i);
        logWeight    = w;
        log(a);
    }
    
    // log a 
    private void log(int a, Move m, int i) {
        logMove        = i2m(m,i);
        logWeight    = m.w[i];
        log(a);
    }

    // log a 
    private void log(int a, Move[] pv, int i, int d, int w) {
        logLine        = i2m(pv[i]);
        logDeep        = d;
        logWeight    = w;
        log(a);
    }

    //
    private void log(int a, int d1, int d2) {
        logAction    = a;
        logDeep        = d1;
        log.run();
    }
    
    //
    private void log(int a, int w) {
        logLine        = i2m(n.L,lineStart);
        logWeight    = w;
        log(a);
    }

    //
    private void log(int a) {
        logAction    = a;
        logTime        = time() - timeStart;
        //logNodes    = ns;
        logNPS        = logTime > 0 ? ns / logTime : 0; 
        if (logEnabled) {
            log.run();
        }
    }
    
    //
    private void info(String event, String message)
    {
        //
        print(rpad(event, 16)+" "+message); 
    }
    
    //
    public void sendbestmove(String m)
    {
        bestmove = m;
    
        sendbestmove.run();
    }
    
    //
    public final static void walk(final Node n, int deep, int width)
    {
        if (deep == 0) {
            //dump(n.L);
            return;
        }
        
        //
        Move m = n.legals().sort();
    
        //
        int w = m.i > width ? width : m.i;
        
        //
        for (int i = 0; i < w; i++) 
        {
            n.domove(m, i);
            
            walk(n, deep - 1, width);
            
            n.unmove();
        }
        
        Moves.free(m);
    }
}