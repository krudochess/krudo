/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// 
package org.krudo;
 
// required non-static classes
import org.krudo.util.Fen;

// required static classes and methods
import static org.krudo.Config.*;
import static org.krudo.Constant.*;
import static org.krudo.util.Tool.*;
import static org.krudo.util.Decode.*;
import static org.krudo.util.Encode.*;
import static org.krudo.util.Describe.*;

// Spizzy XBoard version of Krudo 
public final class Node 
{    
    // board internal status
    public final int B[] = new int[64]; 
    
    // node status  
    public int t; // turn (side to move)
    public int e; // en-passant
    public int c; // castling status
    
    // moves history line
    public final Line L = new Line();
        
    // other node status
    public int cw;  // count white piece
    public int cb;  // count black piece
    public int wks; // white king square
    public int bks; // black king square
    public int hm;  // half-move after pawn move or calpture
    public int n;   // count moves from the begin
    public int i;   // count half-move from the begin
                
    // legals moves-stack internal
    public Move m;
    
    // current zobrist hash key
    public long h;
    
    // white boardmap improve white piece lookup on board
    private final int[] wbm = new int[] {
        // just center
        c3, f3, d3, e3, 
        c4, d4, e4, f4, 
        c5, d5, e5, f5,
        // development
        b1, g1, c1, d1, 
        f1, e1, h1, a1,
        // first pawns
        e2, d2, c2, f2,
        // second pawn
        h2, b2, g2, a2,
        // white zones             
        a3, b3, h3, g3, 
        a4, b4, g4, h4, 
        a5, b5, g5, h5,
        // far squares
        a6, b6, c6, f6, 
        g6, h6, d6, e6,   
        a7, b7, c7, d7, 
        e7, f7, g7, h7, 
        a8, b8, c8, d8, 
        e8, f8, g8, h8, 
    };
    
    // black boardmap improve black piece lookup on board
    private final int[] bbm = new int[] {
        // just center
        c6, d6, e6, f6, 
        c5, d5, e5, f5, 
        c4, d4, e4, f4,
        // development
        d8, b8, c8, f8, 
        g8, e8, a8, h8, 
        // first pawns
        c7, d7, e7, f7,         
        // second pawn
        a7, b7, g7, h7,         
        // black zones
        a6, b6, g6, h6, 
        a5, b5, g5, h5, 
        g4, h4, a4, b4,                              
        // far squares
        a3, b3, c3, f3, 
        g3, h3, d3, e3, 
        e2, d2, c2, f2,
        b2, g2, h2, a2, 
        b1, g1, a1, c1, 
        d1, e1, h1, f1,                 
    };

    // empty contructor
    public void Node() {}
    
    // restore node to start position
    public final void startpos() { Fen.parse(this, STARTPOS); }
    
    // restore node to position passed in FEN
    public final void startpos(final String fen) { Fen.parse(this, fen); }
    
    // do-play a moves sequence passed by array
    public final void domove(
        final String[] moves
    ) {        
        // loop throu moves
        for (String move: moves) { domove(move); }
    }
    
    // do-play a move represented as coordinates (es. "e2e4")
    public final void domove(
        final String move
    ) {        
        // parse move parts and retrieve s,v,k
        int s = s2i(move.substring(0, 2));
        int v = s2i(move.substring(2, 4));
        int k = k2i(move, B[s], s, v, B[v], t);                            
        
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
        final int p = B[s];
                
        // get captured piece
        final int x = B[v];        
        
        // store status into history line
        L.store(i, p, s, v, x, k, e, c);
                
        // set zero leaved square
        B[s] = 0; 

        // place moved piece into versus square
        B[v] = p;
                
        // set zero en-passant square
        e = 0;
                                
        // for special moves handle move rules
        if (k != move) if (t == w) {  
            white_domove(s, v, x, k); 
        } else { 
            black_domove(s, v, x, k); 
        }        
        
        // swap turn side
        t ^= T;
        
        // increase half-move count
        i++;        
    }
    
    // domove and change node internal status
    private void white_domove(
        final int s,
        final int v,
        final int x,
        final int k
    ) {                
        // decrease black piece counter
        if (x != O) { cb--; }
                                                
        // fix specific status
        switch (k) {            
            // fast exit if capture
            case capt: return;            
            // 
            case pdmo: e = s + 8; return;            
            //
            case ecap: cb--; B[s + 8] = 0; return;                  
            // update white king square and castling    
            case kmov: wks = v; c |= wca; return;                    
            // handle castling status and rook bonus movement    
            case cast: 
                if (v == g1) {
                    B[f1] = wr; B[h1] = O; c |= wca; wks = g1;
                } else {
                    B[d1] = wr; B[a1] = O; c |= wca; wks = c1;
                }                  
                return;                                         
            // disable opportune castling ability    
            case rmov: c |= s == h1 ? wkc : wqc; return;                             
            // by default promote piece
            default: B[v] = k & pi;    
        }                                    
    }
    
    // domove and change node internal status
    private void black_domove(
        final int s,
        final int v,
        final int x,
        final int k
    ) {        
        // decreate white piece counter
        if (x != O) { cw--; }
                                                
        // fix specific status
        switch (k) {
            // fast exit if capture
            case capt: return;            
            // set en-passant square
            case pdmo: e = s - 8; return;            
            // performe en-passant capture
            case ecap: cw--; B[s - 8] = 0; return;          
            // set new king square and lose castling ability
            case kmov: c |= bca; bks = v; return;                    
            // performe king-side castling
            case cast: 
                if (v == g8) {
                    B[f8] = br; B[h8] = O; c |= bca; bks = g8;
                } else {
                    B[d8] = br; B[a8] = O; c |= wca; bks = c8;
                }                  
                return;                     
            // lose king-side castling ability    
            case rmov: c |= s == h8 ? bkc : bqc; return;                         
            // by default promote piece
            default: B[v] = k & pi;
        }                                    
    }
    
    // undo move every times
    public final void unmove(final int times) 
    {
        // repeat unmove n-times
        for(int j = times; j != 0; j--) { unmove(); }
    }
    
    // undo last move 
    public final void unmove() 
    {    
        // decrease half-move index
        i--;
        
        // swap side-to-move
        t ^= T;
                
        // get moved piece
        final int p = L.p[i];
        
        // get start square
        final int s = L.s[i];
        
        // get versus square
        final int v = L.v[i];
        
        // get captured piece
        final int x = L.x[i]; 
        
        // get kind-of-move
        final int k = L.k[i];
        
        // restore piece in start square
        B[s] = p; 

        // restore versus square with captured piece
        B[v] = x;
                
        // retrieve previsour en-passant square
        e = L.e[i];
        
        // retrieve previsour castling status
        c = L.c[i];
                    
        //
        if (k != move) if (t == w) {
            white_unmove(p, s, v, x, k);
        } else {
            black_unmove(p, s, v, x, k);        
        }                
    }
    
    //
    private void white_unmove(
        final int p,
        final int s,
        final int v,
        final int x,
        final int k
    ) {
        // decreate black piece counter
        if (x != O) { cb++; }
        
        //
        if (p == wk) { wks = s; }
        
        //
        if (k == ecap) { cb++; B[s - 8] = bp; }
        
        //
        if (k == cast) if (v == g1) {
            B[h1] = wr; B[f1] = O;
        } else {
            B[a1] = wr; B[d1] = O;        
        }                
    }
    
    //
    private void black_unmove(    
        final int p,
        final int s,
        final int v,
        final int x,
        final int k
    ) {
        // decreate black piece counter
        if (x != O) { cw++; }

        //
        if (p == bk) { bks = s; }
        
        //
        if (k == ecap) { cw++; B[s + 8] = wp; }
        
        //
        if (k == cast) if (v == g8) {
            B[h8] = br; B[f8] = O;
        } else {
            B[a8] = br; B[d8] = O;        
        }                
    }
        
    // generate moves-stack with legal-moves
    public final Move legals() 
    {            
        // move-container get move from move-stack pre-created
        m = Moves.pick();
                        
        // generate-fill "m" with white or black legal moves
        if (t == w) { white_legals(); } else { black_legals(); }    
        
        // evaluate every move in stack
        if (EVAL_LEGALS) { Eval.move(m, this); }
        
        // retur move-stack reference
        return m;
    }
    
    // generate moves-stack with legal-moves
    private final Move cache_legals() 
    {    
        // use zobrist cached 
        if (MOVE_CACHE) { 
            
            // compute hash of position
            // h = hash(this);
                        
			// 
			//if (Cache.legals.has(h)) {
				//return Cache.legals.get(h); 		
			//}			
		}
		
		//
		legals();
		
		// retur move-stack reference
		return m; 
	}
	
	// generate moves-stack with legal-moves
	private void white_legals() 
    {		
		// generate white pseudo-moves
		white_pseudo(); 		

		// skip legals test for moves 
		if (!MOVE_LEGALS) { return; }
		
		// legal move index
		int l = 0;
		
		// loop coursor			
		final int p = m.i;
				
		// loop throut pseudo-legal
		for (int j = 0; j != p; j++) 
        {							
			//
			if (m.k[j] == cast) if (white_castling(m.v[j])) 
            {
				 m.copy(j, l); l++; continue;
			} 			
											
			//
			domove(j);

			//
			if (!black_attack(wks)) { m.copy(j, l); l++; }

			//
			unmove();			
		}
		
		//
		m.i = l;
	} 
			
	// generate moves-stack with legal-moves
	private void black_legals() 
    {			
		// generate pseudo-moves into "m"
		black_pseudo(); 		
		
		// skip legals test for moves 
		if (!MOVE_LEGALS) { return; }
	
		// legal move index
		int l = 0;
		
		// prepare "j" loop cursor
		final int p = m.i; 
			
		// loop throut pseudo-legal moves
		for (int j = 0; j != p; j++) {						
						
			//
			if (m.k[j] == cast) if (black_castling(m.v[j])) {
				 m.copy(j, l); l++; continue;
			} 			
						
			//
			domove(j);

			//
			if (!white_attack(bks)) { m.copy(j, l); l++; }

			//
			unmove();			
		}
		
		//
		m.i = l;
	}						
		
	//
	private boolean white_castling(
		final int v
	) {		
		//		
		return v == g1
			?! black_attack(e1)
		   &&! black_attack(f1)
		   &&! black_attack(g1)				
			:! black_attack(e1)
		   &&! black_attack(d1)
		   &&! black_attack(c1);	
	}

	//
	private boolean black_castling(
		final int v
	) {		
		//		
		return v == g8
			?! white_attack(e8)
		   &&! white_attack(f8)
		   &&! white_attack(g8)				
			:! white_attack(e8)
		   &&! white_attack(d8)
		   &&! white_attack(c8);	
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
			final int s = wbm[si++]; 

			// get piece in start square
			final int p = B[s];
			
			// square not have a side to move piece skip
			if ((p & w) != w) { continue; }
				
			// decrease piece count
			pi--;
			
			// remap square in wbm 
			if (PSEUDO_REMAPS) { white_remaps(si, pi, s); }

			// switch to specific piece 
			switch (p) {
				// white pawn
				case wp: pawn(s); break;										
				// white rook
				case wr: span(s, 0, 4, s == a1 || s == a8 ? rmov : move); break;						
				// white knight	
				case wn: hope(s); break;						
				// white bishop	
				case wb: span(s, 4, 8, move); break;																
				// white queen
				case wq: span(s, 0, 8, move); break;
				// white king	
				case wk: king(s); break;
				// default exit
				default: exit("default: white_pseudo()");											
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
			final int s = bbm[si++];

			// get piece in start square
			final int p = B[s];

			// not is a black piece continue
			if ((p & b) != b) { continue; }	
				
			// apply boars search square remaps
			if (PSEUDO_REMAPS) { black_remaps(si, pi, s); }
			
			// switch by piece
			switch (p) {
				// add black pawn moves
				case bp: down(s); break; 											
				// add sliding piece rook moves
				case br: span(s, 0, 4, s == a8 || s == h8 ? rmov : move); break;
				// add kngiht moves
				case bn: hope(s); break;								
				// add sliding piece bishop moves
				case bb: span(s, 4, 8, move); break;
				// add sliding piece queen moves 	
				case bq: span(s, 0, 8, move); break;
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
	private boolean white_attack(
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
			if ((p & w) != w) { continue; }	
			
			// 
			switch (p) {
				//
				case wp: if (pawn(s, a)) { return true; } break;															
				//
				case wr: if (orto(s, a) && spak(s, a, 0, 4)) { return true; } break;																
				//
				case wn: if (hipe(s, a)) { return true; } break;						
				//
				case wb: if (diag(s, a) && spak(s, a, 4, 8)) { return true; } break;						
				//
				case wq: if (star(s, a) && spak(s, a, 0, 8)) { return true; } break;
				//
				case wk: if (near(s, a) && spon(s, a)) { return true; } break;	
				//
				default: exit("default: white_attack()");	
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
	private final boolean black_attack(int a) {
		
		// cuont squares
		int si = 0;
		
		// count black pieces
		int pi = cb;
		
		// board squares loop to find black pieces
		do {		
			
			//
			final int s = bbm[si++]; 
			
			//
			final int p = B[s];
				
			// piece not is black			
			if ((p & b) != b) { continue; }
			
			//
			pi--;
			
			//
			if (PSEUDO_REMAPS) { black_remaps(si, pi, s); }
			
			//
			switch (p) {				
				// handle black pawn attack
				case bp: if (down(s, a)) { return true; } break; 																
				//
				case br: if (orto(s, a) && spak(s, a, 0, 4)) { return true; } break;					
				// handle black knight attack
				case bn: if (hipe(s, a)) { return true; } break;								
				// 
				case bb: if (diag(s, a) && spak(s, a, 4, 8)) { return true; } break;
				//
				case bq: if (star(s, a) && spak(s, a, 0, 8)) { return true; } break;
				//
				case bk: if (near(s, a) && spon(s, a)) { return true; } break;
				//
				default: exit("default: black_attack()");	
			}			
		}
		
		//
		while (pi != 0);
		
		//
		return false;
	}
	
	//
	private void white_remaps (
		final int si, 
		final int pi,
		final int s
	) {							
		//
		if (si >> 4 != 0) {
			
			//
			wbm[si] = wbm[pi];

			//
			wbm[pi] = s;
		}
	}
	
	//
	private void black_remaps (
		final int si, 
		final int pi,
		final int s
	) {		
		
		//
		bbm[si] = bbm[pi];

		//
		bbm[pi] = s;
	}
		
	// handle queen/bishop/rook moves
	private void span (
		final int s,
		final int x0,	// start direction
		final int x1,	// final direction
		final int x2	// k-value for normal move
	) {						
		// loop throut directions
		for (int j = x0; j < x1; j++) {
		
			// versus square
			int v = span[s][j];
			
			// while not found out-of-board
			while (v != xx) {
				
				// 
				if (B[v] == 0) { m.add(s, v, x2); } 
				
				//
				else if ((B[v] & T) != t) { m.add(s, v, x2); break; } 
				
				//
				else { break; }				
				
				// next versus-square in same direction
				v = span[v][j];
			}
		}
	}

	// handle queen/bishop/rook moves attack-test
	private boolean spak (		
		final int s,	// start square
		final int a,	// aimed target square		
		final int x1,	// direction start window 
		final int x2	// direction end window  	
	) {					
		//
		for (int j = x1; j != x2; j++) {
		
			//
			int v = span[s][j];
			
			// 
			while (v != xx) {
				
				// 
				if (B[v] == O) { v = span[v][j]; }
				
				//
				else if (v == a) { return true; }
				
				//
				else { break; }
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
		for (int j = 0; j < 8; j++) {
			
			// get versus square
			final int v = hope[s][j];			
			
			// skip found out-of-board
			if (v == xx) { return; }
			
			//
			final int x = B[v];
			
			// if square is empty add to moves
			if (x == 0) { m.add(s, v, move); } 
			
			// if empty is occupay by opponent piece add capture
			else if ((x & T) != t) { m.add(s, v, capt);	}
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
		final int s, //		
		final int a  //			
	) {			
		// 
		for (int j = 7; j == 0; j--) {
			
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
		//
		int v =	span[s][nn]; 				
			
		// rank of start square
		final int r = s >> 3;
	
		// is not in promotion rank
		if (r != 6) {
					
			//
			if (B[v] == O) {

				//
				final int u = span[v][nn]; 				

				//
				if (r == 1 && B[u] == O) {
					m.add(s, u, pdmo);
				}

				//
				m.add(s, v, move);				
			}	
			
			//
			v = span[s][ne]; 
			
			//
			if (v != xx) if ((B[v] & b) == b) {
				m.add(s, v, capt);				
			} else if (r == 4 && v == e) {
				m.add(s, v, ecap);
			}	
			
			//
			v = span[s][nw];
			
			// 
			if (v != xx) if ((B[v] & b) == b) {
				m.add(s, v, capt);
			} else if (r == 4 && v == e) {
				m.add(s, v, ecap);			
			}							
		} 
		
		// yes is in promotion rank
		else {	
						
			//
			if (B[v] == O) {
				m.add(s, v, wqpm);
				m.add(s, v, wrpm);
				m.add(s, v, wbpm);
				m.add(s, v, wnpm);
			}
					
			//
			v = span[s][ne]; 			
			
			//
			if (v != xx && (B[v] & b) == b) {
				m.add(s, v, wqpm);
				m.add(s, v, wrpm);
				m.add(s, v, wbpm);
				m.add(s, v, wnpm);
			}
			
			//
			v = span[s][nw];
			
			//
			if (v != xx && (B[v] & b) == b) {
				m.add(s, v, wqpm);
				m.add(s, v, wrpm);
				m.add(s, v, wbpm);
				m.add(s, v, wnpm);
			}							
		}	
	}
	
	// untouched black pawn moves 
	private void down(
		final int s
	) {					
		//
		int v =	span[s][ss]; 				
		
		// get start square rank 
		final int r = s >> 3;
		
		// not is in promotion rank
		if (r != 1) {
					
			//
			if (B[v] == 0) {

				//
				final int u = span[v][ss]; 				

				//
				if (r == 6 && B[u] == 0) {					
					m.add(s, u, pdmo);
				}

				//
				m.add(s, v, move);				
			}	
			
			//
			v = span[s][se]; 
			
			//
			if (v != xx && mask(B[v],w)) {
				m.add(s, v, capt);				
			} 
			
			//
			else if (r == 3 && v == e) {
				m.add(s, v, ecap);
			}	
			
			//
			v = span[s][sw];
			
			//
			if (v != xx && mask(B[v],w)) {
				m.add(s, v, capt);
			} 
			
			//
			else if (r == 3 && v == e) {
				m.add(s, v, ecap);			
			}			
		} 
		
		// else pawn is in promotion rank
		else {
						
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
			if (v != xx && (B[v] & w) == w) {
				m.add(s, v, bqpm);
				m.add(s, v, brpm);
				m.add(s, v, bbpm);
				m.add(s, v, bnpm);
			}
			
			//
			v = span[s][sw];
			
			//
			if (v != xx && (B[v] & w) == w) {
				m.add(s, v, bqpm);
				m.add(s, v, brpm);
				m.add(s, v, bbpm);
				m.add(s, v, bnpm);
			}										
		}	
	}
	
	// return true if white pawn in "s" can capture in "a"
	private static boolean pawn(
		final int s, // start square		
		final int a	 // aimed attack square				
	) {				
		//
		return span[s][ne] != a ? span[s][nw] == a : true;
	}

	// return true if black pawn in "s" can capture in "a"
	private static boolean down(
		final int s, // start square		
		final int a	 // aimed attack square					
	) {				
		//
		return span[s][sw] != a ? span[s][se] == a : true;
	}

	// handle white king pseudo-moves
	private void king(
		final int s	// start square
	){			
		// loop throut array-direction "d"
		int j = 8;
		
		//
		while (j != 0) {
			
			//
			j--;
			
			// get versus square
			final int v = span[s][j];			
			
			// skip found out-of-board
			if (v == xx) { continue; }
			
			// look square for captured piece
			final int x = B[v];

			// add if found empty square			
			if (x == 0) { m.add(s, v, kmov); } 
			
			// add if captured is black piece			
			else if ((x & b) == b) { m.add(s, v, kmov);	}
		}		
		
		// king-side white castling
		if (s == e1) if (wksc()) { 
			m.add(e1, g1, cast); 
		} 
		
		// queen-side white castling		
		else if (wqsc()) {			
			m.add(e1, c1, cast);
		}			
	} 
	
	// look there are condition for white king-side castling
	private boolean wksc() {
		
		//
		return 0 == (c & wkc)
			&& B[h1] == wr 
			&& B[g1] == O 
			&& B[f1] == O;
	}
	
	// look there are condition for white queen-side castling
	private boolean wqsc() {
		
		//
		return 0 == (c & wqc) 
			&& B[a1] == wr 
			&& B[d1] == O 
			&& B[c1] == O 
			&& B[b1] == O;
	}
	
	// handle black king pseudo-moves
	private void kong(
		final int s // start square
	) {				
		// loop throut array-direction "d"
		int j = 8;
		
		//
		while (j != 0) {
			
			//
			j--;
			
			// get versus square
			final int v = span[s][j];			
			
			// skip found out-of-board
			if (v == xx) { continue; }
			
			// add move to stack if found empty square
			if (B[v] == O) { m.add(s, v, kmov); } 
			
			// or add capture move if found opponnet piece
			else if ((B[v] & w) == w) { m.add(s, v, kmov); }
		}
		
		// test for valid king-side castling and add to move stack
		if (s == e8) if (bksc()) {
			m.add(e8, g8, cast);
		} 
		
		// or queen-side castling and add to move stack
		else if (bqsc()) {
			m.add(e8, c8, cast);
		}
	}	
	
	//
	private boolean bksc() {
		
		//
		return 0 == (c & bkc) 
			&& B[h8] == br 			 
			&& B[g8] == O 
			&& B[g8] == O;
	}
	
	//
	private boolean bqsc() {
		
		//
		return 0 == (c & bqc) 
			&& B[a8] == br 
			&& B[d8] == O 
			&& B[c8] == O 
			&& B[b8] == O;
	}
		
	//
	private static boolean diag(
		final int a, 
		final int s
	) {
		//
		return (diag & lookup[a][s]) == 0;
	} 

	//
	private static boolean orto(
		final int a, 
		final int s
	) {
		//
		return (orto & lookup[a][s]) == 0;	
	} 
	
	//
	private static boolean star(
		final int a, 
		final int s
	) {		
		//
		return (diag & lookup[a][s]) == 0 
			|| (orto & lookup[a][s]) == 0;
	}

	//
	private static boolean near(
		final int a,
		final int s
	) {			
		//
		return (near & lookup[a][s]) == 0;
	}
	
	//
	private static boolean hipe(
		final int a, 
		final int s
	) {			
		//
		return (hipe & lookup[a][s]) == 0;
	}
}