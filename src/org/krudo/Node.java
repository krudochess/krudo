package org.krudo;

// required static class
import static org.krudo.Const.*;
import static org.krudo.util.Debug.*;
import static org.krudo.util.Trans.*;
import static org.krudo.util.Tools.*;
import static org.krudo.Zobrist.hash;

// Spizzy main class
public final class Node {

	// internal status
	public int B[];		// board
	public int T;		// turn (side to move)
	public int e;		// en-passant
	public int c;		// castling status
	public int cw;		// count white piece
	public int cb;		// count black piece
	public int wks;		// white king square
	public int bks;		// black king square
	public int hm;		// half-move after pawn move or calpture
	public int n;		// count moves from the begin
	public int i;		// count half-move from the begin
			
	// moves history line
	public Line L = new Line();
	
	// legals moves-stack internal
	private Move m;
	
	// zobrist hash key temp
	private long h;
	
	// globals first order
	private int v;	// versus square in move generation loops
	private int x;	// captured piece
	private int k;	// kind-of-move
	private int u;	// alternative versus square
	private int r;	// square rank
	
	// white boardmap improve white piece lookup on board
	public final int[] wbm = new int[] {
		// board center
		c3, f3, d3, e3,
		c4, d4, e4, f4,
		c5, d5, e5, f5,
		// piece development
		b1, g1, c1, d1, f1, e1, h1, a1,
		// first pawn
		e2, d2, c2, f2,
		// second pawn
		h2, b2, g2, a2,
		// white zone     		
		a3, b3, h3, g3, 
		a4, b4, g4, h4, 	
		a5, b5, g5, h5,
		// far squares
		a6, b6, c6, f6, g6, h6, d6, e6,		
		a7, b7, c7, d7, e7, f7, g7, h7,
		a8, b8, c8, d8, e8, f8, g8, h8, 
		// last pawn
	};
	
	// black boardmap improve black piece lookup on board
	public final int[] bbm = new int[] {
		// center
		c6, d6, e6, f6,
		c5, d5, e5, f5,
		c4, d4, e4, f4,
		// development
		d8, b8, c8, f8, g8, e8, a8, h8, 
		// first pawn
		c7, d7, e7, f7, 		
		// seconds pawn
		a7, b7, g7, h7, 		
		// black zone
		a6, b6, g6, h6,
		a5, b5, g5, h5, 
		g4, h4, a4, b4,			  				
		// far squares
		a3, b3, c3, f3, g3, h3, d3, e3,
		e2, d2, c2, f2, b2, g2, h2, a2, 		
		b1, g1, a1, c1, d1, e1, h1, f1, 				
	};
	
	// constructor create a ready to use node with standard chess start position set 
	public Node() {
		
		//
		startpos();
	}
	
	// constructor with FEN position create a ready to use node with custom start position
	public Node(String fen) {
		
		//
		startpos(fen);
	}
	
	// restore node to start position
	public final void startpos() {
		
		//
		Fen.parse(this, STARTPOS);
	}
	
	// restore node to position passed in FEN
	public final void startpos(
		final String fen
	) {
		//
		Fen.parse(this,fen);
	}
	
	// do-play a moves sequence passed by array
	public final void domove(
		final String[] moves
	) {
		// loop throu moves
		for (String move: moves) {
			domove(move);
		}
	}
	
	// do-play a move represented as coordinates (es. "e2e4")
	public final void domove(
		final String move
	) {		
		// parse move parts and retrieve s,v,k
		int s = s2i(move.substring(0, 2));
		int v = s2i(move.substring(2, 4));
		int k = k2i(move, B[s], s, v, B[v], T);							
		
		// do-play move apply status changes
		domove(s, v, k);
	}
	
	// do a move placed into internal "m" select by index
	public final void domove(		
		final int index
	) {		
		// call direct s-v-k domove
		domove(
			m.s[index],
			m.v[index],
			m.k[index]
		);
	}
	
	// do a move placed into moves-stack select by index
	public final void domove(
		final Move moves, 
		final int index
	) {		
		// call direct s-v-k domove
		domove(
			moves.s[index],
			moves.v[index],
			moves.k[index]
		);
	}
	
	// do a move placed into moves-stack current index
	public final void domove(
		final Move moves
	) {		
		// call direct s-v-k domove
		domove(
			moves.s[moves.i],
			moves.v[moves.i],
			moves.k[moves.i]
		);
	}
	
	// domove and change node internal status
	public final void domove(
		final int s,
		final int v, 
		final int k
	) {		
		// get moved piece
		int p = B[s];
				
		// get captured piece
		int x = B[v];		
		
		// store status into history line
		L.store(i, p, s, v, x, k, e, c);
				
		// set zero leaved square
		B[s] = 0; 

		// place moved piece into versus square
		B[v] = p;
				
		// set zero en-passant square
		e = 0;
				
		// swat turn side
		T ^= t;
		
		// increase half-move count
		i++;
				
		//
		if (k != move) {
			domove_switch_k(s, v, k);
		}				
	}
	
	//
	private void domove_switch_k(int s, int v, int k) {
	
		// ending operation based on kind-move
		switch (k) {
																
			// capture move			
			case wcap: cb--; return;
			case bcap: cw--; return;
			
			// pawn doublepass	
			case wpdm: e = span[s][nn]; return;	
			case bpdm: e = span[s][ss]; return;	
						
			// en-passant capture
			case weca: B[span[v][ss]] = 0; cb--; return;
			case beca: B[span[v][nn]] = 0; cw--; return;
			
			// king move
			case wkmo: wks = v; c |= WKC | WQC; return;
			case bkmo: bks = v; c |= BKC | BQC; return;
			
			// king capture
			case wkca: wks = v; cb--; c |= WKC | WQC; return;
			case bkca: bks = v; cw--; c |= BKC | BQC; return;				
			
			// rook move
			case wrmo: c |= s == a1 ? WQC : (s == h1 ? WKC : 0); return;
			case brmo: c |= s == a8 ? BQC : (s == h8 ? BKC : 0); return;
			
			// rook capture
			case wrca: cb--; c |= s == a1 ? WQC : (s == h1 ? WKC : 0); return;
			case brca: cw--; c |= s == a8 ? BQC : (s == h8 ? BKC : 0); return;				
			
			// castling
			case wksc: B[h1] = 0; wks = g1; B[f1] = wr; c |= WKC | WQC; return;
			case wqsc: B[a1] = 0; wks = c1; B[d1] = wr; c |= WKC | WQC; return;
			case bksc: B[h8] = 0; bks = g8; B[f8] = br; c |= BKC | BQC; return;
			case bqsc: B[a8] = 0; bks = c8; B[d8] = br; c |= BKC | BQC; return;
			
			// promotion to queen
 			case wqpm: B[v] = wq; return;
			case bqpm: B[v] = bq; return;
			case wqpc: B[v] = wq; cb--; return;
			case bqpc: B[v] = bq; cw--; return;
													
			// promotion to other	
			case wrpm: B[v] = wr; return;
			case wbpm: B[v] = wb; return; 
			case wnpm: B[v] = wn; return;
			case brpm: B[v] = br; return;
			case bbpm: B[v] = bb; return; 
			case bnpm: B[v] = bn; return;
			case wrpc: B[v] = wr; cb--; return;
			case wbpc: B[v] = wb; cb--; return; 
			case wnpc: B[v] = wn; cb--; return;
			case brpc: B[v] = br; cw--; return;
			case bbpc: B[v] = bb; cw--; return; 
			case bnpc: B[v] = bn; cw--; return;
								
			// fault!
			default: exit("DOMOVE FAULT!");				
		}
	}
		
	// undo last move 
	public final void unmove() {
		
		// decrease half-move index
		i--;
				
		// get moved piece
		int p = L.p[i];
		
		// get start square
		int s = L.s[i];
		
		// get versus square
		int v = L.v[i]; 
		
		// get versus square
		int x = L.x[i]; 
		
		//
		int k = L.k[i];
		
		// restore piece in start square
		B[s] = p; 

		// restore versus square
		B[v] = 0;
				
		// retrieve previsour en-passant square
		e = L.e[i];
		
		// retrieve previsour castling status
		c = L.c[i];
			
		// swap side-to-move
		T ^= t;
				
		//	
		if (k != move) {
			unmove_switch_k();
		}
	}
	
	//
	private void unmove_switch_k() {
	
		//
		switch(L.k[i]) {
		
			// capture move
			case wcap: B[v] = x; cb++; return;				
			case bcap: B[v] = x; cw++; return;				
										
			// pawn doublepass 
			case wpdm: return;		
			case bpdm: return;	

			// en-passant capture
			case weca: B[span[v][ss]] = bp; cb++; return;
			case beca: B[span[v][nn]] = wp; cw++; return;
		
			// king move	
			case wkmo: wks = 0; return;
			case bkmo: bks = 0; return;
			
			// king capture
			case wkca: B[v] = x; wks = 0; cb++; return;
			case bkca: B[v] = x; bks = 0; cw++; return;
			
			// rook move	
			case wrmo: return;
			case brmo: return;
			
			// rook capture
			case wrca: B[v] = x; cb++; return;
			case brca: B[v] = x; cw++; return;
			
			// castling	
			case wksc: B[f1] = 0; B[h1] = wr; wks = e1; return;
			case wqsc: B[d1] = 0; B[a1] = wr; wks = e1; return;
			case bksc: B[f8] = 0; B[h8] = br; bks = e8; return;
			case bqsc: B[d8] = 0; B[a8] = br; bks = e8; return;
						
			// promotion to queen
			case wqpm: return;
			case bqpm: return;
			case wqpc: B[v] = x; cb++; return;
			case bqpc: B[v] = x; cw++; return;
															
			// promotion to other
			case wrpm: return;
			case wbpm: return; 
			case wnpm: return;
			case brpm: return;
			case bbpm: return; 
			case bnpm: return;
			case wrpc: B[v] = x; cb++; return;
			case wbpc: B[v] = x; cb++; return; 
			case wnpc: B[v] = x; cb++; return;
			case brpc: B[v] = x; cw++; return;
			case bbpc: B[v] = x; cw++; return; 
			case bnpc: B[v] = x; cw++; return;
							
			// fault!
			default: dump(L); exit("UNMOVE FAULT!"); 			
		}	
	}
		
	// generate moves-stack with legal-moves
	public final Move legals() {
		
		// use zobrist cached 
		if (MOVE_CACHE) {
			
			// compute hash of position
			h = hash(this);
						
			// 
			if (Cache.legals.has(h)) {
				return Cache.legals.get(h); 		
			}			
		}
		
		// move-stack get move stack from move buffer pre-created
		// to avoid creating this sta
		m = Move.pop();
		
		//
		m.h = h;
		
		// store in cache bu hash
		if (MOVE_CACHE) { 
						
			//
			Cache.legals.add(h, m); 
		}
		
		// generate-fill m with legal moves
		if (T == w) {
			white_legals();
		}
		
		//
		else {
			black_legals();
		}	

		// evaluate every move in stack
		if (MOVE_EVAL) {
			Eval.eval(m,this);
		}

		// retur move-stack reference
		return m; 
	}
	
	// generate moves-stack with legal-moves
	public final void white_legals() {
				
		// generate pseudo-moves
		white_pseudo(); 		

		// increase loop counter 
		m.loop();

		// loop throut pseudo-legal moves ("i" cursor)			
		int i = m.i;
		
		while (i != 0) {						

			//
			i--;
			
			//
			domove(m,i);

			//
			if (MOVE_LEGALS) {
				if (!black_attack(wks)) {
					switch(m.k[i]) {
						case wksc: if (!black_attack(e1) && !black_attack(f1)) {m.fix(i);} break; 	
						case wqsc: if (!black_attack(e1) && !black_attack(d1)) {m.fix(i);} break; 	
						default: m.fix(i); 	
					}
				}
			} 

			//
			else {
				m.fix(i);
			}

			//
			unmove();			
		}

		// decrase loop counter
		m.stop();

	} 
			
	// generate moves-stack with legal-moves
	public final void black_legals() {	
		
		// generate pseudo-moves into "m"
		black_pseudo(); 		
				
		// prepare "j" loop cursor
		int j = m.i; 
		
		// loop throut pseudo-legal moves
		while (j != 0) {						

			//
			j--;
			
			//
			domove(j);

			//
			if (MOVE_LEGALS) {
				if (!white_attack(bks)) {
					switch(m.k[j]) {						
						case bksc: if (!white_attack(e8) && !white_attack(f8)) {m.fix(j);} break; 	
						case bqsc: if (!white_attack(e8) && !white_attack(d8)) {m.fix(j);} break; 	
						default: m.fix(j); 	
					}
				}
			} 

			//
			else {
				m.fix(j);
			}

			//
			unmove();			
		}
	}						
				
	// populate move-stack with pseudo-legal moves
	private void white_pseudo() {

		// index count squares
		int si = 0;
		
		// index count pieces
		int pi = cw;
		
		// looking for white pieces
		do {
				
			// next observed square
			int s = wbm[si++]; 

			// get piece in start square
			int p = B[s];
			
			// square have a side to move piece
			if ((p & w) != w) {
				continue;
			}
			
			// decrease piece count
			pi--;
			
			// remap square in wbm 
			if (PSEUDO_AUTOMAP) {
				white_remaps(si, pi, s);
			}

			// switch to specific piece 
			switch (p) {

				// white pawn
				case wp: pawn(s); break;										

				// white rook
				case wr: span(s, 0, 4, wrmo, wrca); break;						

				// white knight	
				case wn: hope(s); break;						

				// white bishop	
				case wb: span(s, 4, 8, move, wcap); break;																

				// white queen
				case wq: span(s, 0, 8, move, wcap); break;

				// white king	
				case wk: king(s); break;

				// default exit
				default: exit("WHITE PSEUDO FAULT!");											
			}			
		} 
		
		//
		while (pi != 0);
	}

	// populate move-stack with pseudo-legal moves
	private void black_pseudo() {
		
		// count squares
		int si = 0;

		// count pieces
		int pi = cb;
				
		// loop throut black piece
		do {
		
			// next start square
			int s = bbm[si++];

			// get piece in start square
			int p = B[s];

			// not is a black piece continue
			if ((p & b) != b) {
				continue;
			}	
				
			//
			if (PSEUDO_AUTOMAP) {
				black_remaps(si, pi, s);
			}
			
			// switch by piece
			switch (p) {

				// add black pawn moves
				case bp: down(s); break; 											

				// add sliding piece rook moves
				case br: span(s, 0, 4, brmo, brca); break;

				// add kngiht moves
				case bn: hope(s); break;								

				// add sliding piece bishop moves
				case bb: span(s, 4, 8, move, bcap); break;

				// add sliding piece queen moves 	
				case bq: span(s, 0, 8, move, bcap); break;

				// add kings moves and castling
				case bk: kong(s); break;	

				// unrecognized piece fault stop
				default: exit("BLACK PSEUDO FAULT");						
			}

			// count founded piece
			pi--;			
		}
		
		//
		while (pi != 0);
	}

	// return true if side-player can attack square "a"
	public final boolean white_attack(
		final int a
	) {				
		// cuont squares
		int si = 0;
		
		// count pieces
		int pi = cw;
				
		// it is white turn loop throut white piece
		do {
			
			// get next start square
			int s = wbm[si++];
			
			// get piece in start square
			int p = B[s];
			
			// if is white piece
			if ((p & w) != w) {
				continue;
			}	
			
			// 
			switch (p) {

				//
				case wp: if (pawn(s, a)) { return true; } break;															

				//
				case wr: if (orto(s, a) && span(s, a, 0, 4)) { return true; } break;																

				//
				case wn: if (hipe(s, a)) { return true; } break;						

				//
				case wb: if (diag(s, a) && span(s, a, 4, 8)) { return true; } break;						

				//
				case wq: if (star(s, a) && span(s, a, 0, 8)) { return true; } break;

				//
				case wk: if (near(s, a) && spon(s, a)) { return true; } break;	

				//
				default: exit("WHITE ATTACK FAULT!");	
			}	

			// decrease founded piece counter
			pi--;									
		} 
		
		//
		while (pi != 0);
		
		//
		return false;
	}

	// return true if black-side-player can attack square "a"
	public final boolean black_attack(int a) {
		
		// cuont squares
		int si = 0;
		
		// count black pieces
		int pi = cb;
		
		// board squares loop to find black pieces
		do {		
			
			//
			int s = bbm[si++]; 
			
			//
			int p = B[s];
				
			// piece not is black			
			if ((p & b) == b) {
				continue;
			}
			
			//
			pi--;
			
			//
			if (PSEUDO_AUTOMAP) {
				black_remaps(si, pi, s);
			}
			
			//
			switch (p) {				

				// handle black pawn attack
				case bp: if (down(s, a)) { return true; } break; 																

				//
				case br: if (orto(s, a) && span(s, a, 0, 4)) { return true; } break;					

				// handle black knight attack
				case bn: if (hipe(s, a)) { return true; } break;								

				// 
				case bb: if (diag(s, a) && span(s, a, 4, 8)) { return true; } break;

				//
				case bq: if (star(s, a) && span(s, a, 0, 8)) { return true; } break;

				//
				case bk: if (near(s, a) && spon(s, a)) { return true; } break;

				//
				default: exit("BLACK ATTACK FAULT!");	
			}			
		}
		
		//
		while (pi != 0);
		
		//
		return false;
	}
	
	//
	private void white_remaps(
		int si, 
		int pi,
		int s
	) {							
		//
		if (si >> 4 != 0) {
			
			//
			wbm[si] = wbm[16];
			
			//
			wbm[16] = wbm[pi];

			//
			wbm[pi] = s;
		}
	}
	
	//
	private void black_remaps(
		int si, 
		int pi,
		int s
	) {					
		//
		bbm[si] = bbm[pi];

		//
		bbm[pi] = s;
	}
	
	
	
	// handle queen/bishop/rook moves
	private void span(
		final int s,
		final int x0,	// start direction
		final int x1,	// final direction
		final int x2,	// k-value for normal move
		final int x3	// k-value for capture move
	){						
		// loop throut directions
		for (int j = x0; j < x1; j++) {
		
			// versus square
			int v = span[s][j];
			
			// while not found out-of-board
			while (v != xx) {
				
				// 
				if (B[v] == 0) {
					m.add(s, v, x2);
				} 
				
				//
				else if ((B[v] & t) != T) {
					m.add(s, v, x3);
					break;
				} 
				
				//
				else { break; }				
				
				// next versus-square in same direction
				v = span[v][j];
			}
		}
	}

	// handle queen/bishop/rook moves attack-test
	private boolean span(		
		final int s,	// start square
		final int a,	// aimed target square		
		final int x1,	// direction start window 
		final int x2	// direction end window  	
	){					
		//
		for (int j = x1; j < x2; j++) {
		
			//
			int v = span[s][j];
			
			// 
			while (v != xx) {
				
				// 
				if (B[v] == 0) {
					v = span[v][j]; 
				}
				
				//
				else if (v == a) { 
					return true; 
				}
				
				//
				else { 
					break; 
				}
			}
		}
		
		//
		return false;
	}

	// handle knight normal move
	private void hope(
		final int s //
	) {	
		
		//
		for (int i3 = 0; i3 < 8; i3++) {
			
			// get versus square
			int v = hope[s][i3];			
			
			// skip found out-of-board
			if (v == xx) { 
				return; 
			}
			
			//
			int x = B[v];
			
			// if square is empty add to moves
			if (x == 0) { 				
				m.add(s, v, move);
			} 
			
			// if empty is occupay by opponent piece add capture
			else if ((x & t) != T) {
				m.add(s, v, capt|T);			
			}
		}
	}
	
	// 
	private static boolean hope(		
		final int a,	//			
		final int s		//
	) {			
		
		// 
		for (int j = 0; j < 8; j++) {
			
			//
			if (hope[s][j] == a) { 
				return true;
			}
		}
		
		//
		return false;		
	}
	
	// 
	private static boolean spon(		
		final int a,	//			
		final int s		//		
	) {			
		// 
		for (int j = 0; j < 8; j++) {
			
			//
			if (span[s][j] == a) { return true; }			
		}
		
		//
		return false;		
	}
	
	// white pawn moves
	private void pawn(
		final int s
	) {										
		// rank of start square
		int r = s >> 3;
	
		//
		int v =	span[s][nn]; 				
			
		// is not in promotion rank
		if (r != 6) {
					
			//
			if (B[v] == 0) {

				//
				u =	span[v][nn]; 				

				//
				if (r == 1 && B[u] == 0) {
					m.add(s, u, wpdm);
				}

				//
				m.add(s, v, move);				
			}	
			
			//
			v = span[s][ne]; 
			
			//
			if (v != xx && (B[v] & b) == b) {
				m.add(s, v, wcap);				
			} 
			
			//
			else if (r == 4 && v == e) {
				m.add(s, v, weca);
			}	
			
			//
			v = span[s][nw];
			
			//
			if (v != xx && (B[v] & b) == b) {
				m.add(s, v, wcap);
			} 
			
			//
			else if (r == 4 && v == e) {
				m.add(s, v, weca);			
			}			
		} 
		
		// yes is in promotion rank
		else {	
						
			//
			if (B[v] == 0) {
				m.add(s, v, wqpm);
				m.add(s, v, wrpm);
				m.add(s, v, wbpm);
				m.add(s, v, wnpm);
			}
					
			//
			v = span[s][ne]; 			
			
			//
			if (v != xx && mask(B[v],b)) {
				m.add(s, v, wqpc);
				m.add(s, v, wrpc);
				m.add(s, v, wbpc);
				m.add(s, v, wnpc);
			}
			
			//
			v = span[s][nw];
			
			//
			if (v != xx && mask(B[v],b)) {
				m.add(s, v, wqpc);
				m.add(s, v, wrpc);
				m.add(s, v, wbpc);
				m.add(s, v, wnpc);
			}							
		}	
	}
	
	// untouched black pawn moves 
	private void down(
		final int s
	) {			
		// get start square rank 
		int r = s >> 3;
		
		// not is in promotion rank
		if (r != 1) {
		
			// 
			int v =	span[s][ss]; 				
			
			//
			if (B[v] == 0) {

				//
				int u =	span[v][ss]; 				

				//
				if (r == 6 && B[u] == 0) {					
					m.add(s, u, bpdm);
				}

				//
				m.add(s, v, move);				
			}	
			
			//
			v = span[s][se]; 
			
			//
			if (v != xx && mask(B[v],w)) {
				m.add(s, v, bcap);				
			} 
			
			//
			else if (r == 3 && v == e) {
				m.add(s, v, beca);
			}	
			
			//
			v = span[s][sw];
			
			//
			if (v != xx && mask(B[v],w)) {
				m.add(s, v, bcap);
			} 
			
			//
			else if (r == 3 && v == e) {
				m.add(s, v, beca);			
			}			
		} 
		
		// else pawn is in promotion rank
		else {
			
			// protion square
			v =	span[s][ss];
			
			// if square is empty add 4 promotion moves
			if (B[v] == 0) {
				m.add(s, v, bqpm);
				m.add(s, v, brpm);
				m.add(s, v, bbpm);
				m.add(s, v, bnpm);
			}
			
			// promotion est-capture square
			v = span[s][se]; 			
			
			//
			if (v != xx && mask(B[v],w)) {
				m.add(s, v, bqpc);
				m.add(s, v, brpc);
				m.add(s, v, bbpc);
				m.add(s, v, bnpc);
			}
			
			//
			v = span[s][sw];
			
			//
			if (v != xx && mask(B[v],w)) {
				m.add(s, v, bqpc);
				m.add(s, v, brpc);
				m.add(s, v, bbpc);
				m.add(s, v, bnpc);
			}										
		}	
	}
	
	// return true if pawn in "s" can capture in "a"
	private static boolean pawn(
		final int s,	// start square		
		final int a		// aimed attack square				
	){				
		//
		return span[s][ne] != a ? span[s][nw] == a : true;
	}

	// return true if pawn in "s" can capture in "a"
	private static boolean down(
		final int s,	// start square		
		final int a		// aimed attack square					
	){				
		//
		return span[s][sw] != a ? span[s][se] == a : true;
	}

	// handle white king pseudo-moves
	private void king(
		final int s		// start square
	){			
		//
		for (int j = 0; j < 8; j++) {
			
			// get versus square
			int v = span[s][j];			
			
			// skip found out-of-board
			if (v == xx) { 
				continue; 
			}
			
			// look square for captured piece
			int x = B[v];
			
			// add if found empty square
			if (x == 0) { 				
				m.add(s, v, wkmo);
			} 
			
			// add if captured is black piece
			else if ((x & b) == b) {
				m.add(s, v, wkca);			
			}
		}		
		
		// king-side white castling
		if (s==e1 && B[h1]==wr && mask(c,WKC,0) && B[f1]==0 && B[g1]==0) {
			m.add(e1, g1, wksc);
		}
				
		// queen-side white castling
		if (s==e1 && B[a1]==wr && mask(c,WQC,0) && B[d1]==0 && B[c1]==0 && B[b1]==0) {			
			m.add(e1, c1, wqsc);
		}			
	} 
	
	// handle black king pseudo-moves
	private void kong(
		int s
	) {		
		
		// loop throut array-direction "d"
		for (int j = 0; j < 8; j++) {
			
			// get versus square
			int v = span[s][j];			
			
			// skip found out-of-board
			if (v == xx) { continue; }
			
			// add move to stack if found empty square
			if (B[v] == 0) { 				
				m.add(s, v, bkmo);
			} 
			
			// or add capture move if found opponnet piece
			else if (mask(B[v],w)) {
				m.add(s, v, bkca);			
			}
		}
		
		// test for valid king-side castling and add to move stack
		if (s == e8 && mask(c,BKC,0) && B[h8] == br  && B[f8] == 0 && B[g8] == 0) {
			m.add(e8, g8, bksc);
		}
		
		// test for valid queen-side castling adn add to move stack
		if (s == e8 && mask(c,BQC,0) && B[a8] == br  && B[d8] == 0 && B[c8] == 0 && B[b8] == 0) {
			m.add(e8, c8, bqsc);
		}
	}	
	
	//
	private static boolean diag(
		final int a, 
		final int s
	) {
	
		//
		return (diag&lookup[a][s]) == 0;
	} 

	//
	private static boolean orto(
		final int a, 
		final int s
	) {
		
		//
		return (orto&lookup[a][s]) == 0;	
	} 
	
	//
	private static boolean star(
		final int a, 
		final int s
	) {
		
		//
		return (diag&lookup[a][s]) == 0 || (orto&lookup[a][s]) == 0;
	}

	//
	private static boolean near(
		final int a,
		final int s
	) {
			
		//
		return (near&lookup[a][s]) == 0;
	}
	
	//
	private static boolean hipe(
		final int a, 
		final int s
	) {			
		//
		return (hipe&lookup[a][s]) == 0;
	}
}