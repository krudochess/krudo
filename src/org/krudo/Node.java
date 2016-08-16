/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// 
package org.krudo;
 
// required static classes and methods
import static org.krudo.Fix.*;
import static org.krudo.Tool.*;
import static org.krudo.Parse.*;
import static org.krudo.Debug.*;
import static org.krudo.Config.*;
import static org.krudo.Decode.*;
import static org.krudo.Encode.*;
import static org.krudo.Zobrist.*;
import static org.krudo.Describe.*;
import static org.krudo.Constant.*;

// Spizzy XBoard version of Krudo 
public final class Node 
{    
    // board position internal status
    public final int B[] = new int[64]; 
    
    // board material internal status
    public final int M[] = new int[12]; 
    
    // node status  
    public int t; // turn (side to move)
    public int c; // castling status (negative logic)
    public int e; // en-passant capture square
    
    // moves history line
    public final Line L = new Line();
        
    // other node status
    public int cw;  // count white piece
    public int cb;  // count black piece
    public int wks; // white king square
    public int bks; // black king square
    public int ote; // game phases (opening to endig)
    public int hm;  // half-move after pawn move or calpture
    public int n;   // count moves from the begin
                     
    // hash keys
    public long phk; // current zobrist position hash key
    public long mhk; // current matirial hash key (for draw matirial rule)
   
    // legals moves-stack internal
    public Move legals;
    
    // captures moves-stack internal
    public Capture captures;
        
    // white+black boardmap improve piece lookup on board
    public final int[] bm = new int[]
    {
        c3, f3, d3, e3, c4, d4, e4, f4, 
        a7, b7, c7, d7, e7, f7, g7, h7, 
        a8, b8, c8, d8, e8, f8, g8, h8, 
        c5, d5, e5, f5, b1, g1, c1, d1, 
        f1, e1, h1, a1, e2, d2, c2, f2,
        h2, b2, g2, a2, a3, b3, h3, g3, 
        a4, b4, g4, h4, a5, b5, g5, h5,
        a6, b6, c6, f6, g6, h6, d6, e6
    };
    
    // white boardmap improve white piece lookup on board
    public final int[] wbm = new int[]
    {
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
    public final int[] bbm = new int[]
    {
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

    // contructor
    public Node() 
    { 
        //
        startpos();
    }
    
    // restore node to start position
    public final void startpos() 
    {     
        //
        Fen.parse(this, STARTPOS); 
        
        //
        hash0(this);
    }
    
    // restore node to position passed in FEN
    public final void startpos(final String fen) 
    {
        //
        Fen.parse(this, fen); 
        
        //
        hash0(this);
    }
    
    // do-play a moves sequence passed by array
    public final void domove(
        final String[] moves
    ) {        
        domove(moves, 0);
    }
    
    // do-play a moves sequence passed by array
    public final void domove(
        final String[] moves,
        final int offset
    ) {        
        // loop throu moves
        for (int i = offset; i < moves.length; i++) 
        { 
            domove(moves[i]); 
        }
    }
    
    // do-play a move represented as coordinates (es. "e2e4", "d2d1q")
    public final void domove(final String move)
    {        
        // parse move parts and retrieve s,v,k
        int s = parse_square(move.substring(0, 2));
        int v = parse_square(move.substring(2, 4));
        int k = parse_kind_of_move(move, B[s], s, v, B[v], c, e, t);  
        
        //
        v = fix_castling_versus_square(s, v, k);
        
        // do-play move apply status changes
        domove(s, v, k);
    }
    
    // do a move placed into internal "m" select by index
    public final void domove(        
        final int index
    ) {            
        // call direct s-v-k domove
        domove(
            legals.s[index],
            legals.v[index],
            legals.k[index]
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
    
    // do a move placed into moves-stack select by index
    public final void domove(
        final Capture captures, 
        final int index
    ) {                
        // call direct s-v-k domove
        domove(
            captures.s[index],
            captures.v[index],
            captures.k[index]
        );
    }
    
    // domove and change node internal status
    public final void domove(final int s, final int v, final int k)
    {   
        //Debug.assertPieceCount(this);
        
        // get moved piece
        final int p = B[s];
                
        // get captured piece
        final int x = B[v];        
        
        // store status into history line
        L.store(p, s, v, x, k, c, e, phk, mhk);
        
        // hash base movement and clear precent enpassant
        hash_move(this, p, s, v);
        
        // set zero leaved square
        B[s] = O; 

        // place moved piece into versus square
        B[v] = p;
                
        // clear en-passant square
        e = xx;
                
        //
        t ^= T;
        
        // decrease piece counter
        if (x != O) 
        {
            // hash captured piece
            hash_capt(this, v, x);
            
            // decrease opponet material 
            M[x & lo]--;
            
            // 
            ote += Eval.OTE[x & lo];
                        
            // 
            if (t == b) { cb--; } else { cw--; }
        }
                                                  
        // turn already swapped inversion of color consider
        if (k != MOVE) if (t == b) { white_domove(s, v, k); } else { black_domove(s, v, k); }  
        
        //
        //Debug.assertPieceCount(this);

        //if (hex(phk).equals("2978b967ac2f9be4")) {
        //    dump(this);
        //    legals();
        //    dump(legals);
        //    exit("foud");
        //}
        
    }
    
    // domove and change node internal status
    private void white_domove(final int s, final int v, final int k)
    {                                           
        // fix specific status
        switch (k) 
        {                   
            // set enpassant square and possible pawn square that caputre if have one
            case PDMO: if (white_domove_enpassant(s + 8)) { hash_pdmo(this); } return;            
            
            // perform enpassant capture
            case ECAP: final int u = v - 8; cb--; B[u] = O; M[bp & lo]--; hash_ecap(this, u, bp); return;                  
            
            // update white king square and castling    
            case KMOV: wks = v; hash_kmov_w(this); c |= KQ__; return;                    
            
            // handle castling status and rook bonus movement    
            case KSCA: B[f1] = wr; B[h1] = O; wks = g1; hash_ksca_w(this); c |= KQ__; break;
                
            // perform queen side castling    
            case QSCA: B[d1] = wr; B[a1] = O; wks = c1; hash_qsca_w(this); c |= KQ__; break;
               
            // disable opportunity of castling ability    
            case RKMO: hash_rkmo_w(this); c |= K___; return;                             
            
            // disable opportunity of castling ability    
            case RQMO: hash_rqmo_w(this); c |= _Q__; return;                             
            
            // by default promote piece
            default: final int p = k & pi; B[v] = p; M[wp & lo]--; M[p & lo]++; hash_prom(this, v, wp, p);
        }                                    
    }
    
    // domove and change node internal status
    private void black_domove(final int s, final int v, final int k)
    {                                                        
        // fix specific status
        switch (k) 
        {      
            // set en-passant square
            case PDMO: if (black_domove_enpassant(s - 8)) { hash_pdmo(this); } return;            
            
            // performe en-passant capture
            case ECAP: final int u = v + 8; cw--; B[u] = O; M[wp & lo]--; hash_ecap(this, u, wp); return;          
            
            // set new king square and lose castling ability
            case KMOV: bks = v; hash_kmov_b(this); c |= __kq; return;                    
            
            // performe king-side castling
            case KSCA: B[f8] = br; B[h8] = O; bks = g8; hash_ksca_b(this); c |= __kq; return;
            
            //
            case QSCA: B[d8] = br; B[a8] = O; bks = c8; hash_qsca_b(this); c |= __kq; return;
                                          
            // lose king-side castling ability    
            case RKMO: hash_rkmo_b(this); c |= __k_; return;                         
            
            // lose king-side castling ability    
            case RQMO: hash_rqmo_b(this); c |= ___q; return;                         
            
            // by default promote piece
            default: final int p = k & pi; B[v] = p; M[bp & lo]--; M[p & lo]++; hash_prom(this, v, bp, p);                
        }                                    
    }
    
    //
    public final boolean white_domove_enpassant(final int v) 
    {
        //
		int u = SPAN[v][NE];
        
        //
        if (u != xx && B[u] == bp) { e = v; return true; } 
            
        //
        u = SPAN[v][NW];
            
        //
        if (u != xx && B[u] == bp) { e = v; return true; }
		       
        //
        return false;    
    }
    
    //
    public final boolean black_domove_enpassant(final int v) 
    {
        //
		int	u = SPAN[v][SE];
            
        //
        if (u != xx && B[u] == wp) { e = v; return true; }
            
        //
        u = SPAN[v][SW];
            
        //
        if (u != xx && B[u] == wp) { e = v; return true; }
		        
        //
        return false;    
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
        final int i = --L.i;
                        
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
        
        // restore position hash key
        phk = L.phk[i];
        
        // restore matirial hash key
        mhk = L.mhk[i];
        
        // retrieve previsour castling status
        c = L.c[i];
        
        // retrieve previsour en-passant square
        e = L.e[i];        
        
        // restore piece in start square
        B[s] = p; 

        // restore versus square with captured piece
        B[v] = x;
                   
        // swap side-to-move
        t ^= T;
        
        // decrease piece counter
        if (x != O) 
        {
            //
            M[x & lo]++;
            
            //
            ote -= Eval.OTE[x & lo];
            
            //
            if (t == b) { cw++; } else { cb++; }
        }
        
        //
        if (k != MOVE) if (t == w) { white_unmove(s, v, k); } else { black_unmove(s, v, k); }
    }
    
    //
    private void white_unmove(
        final int s,
        final int v,
        final int k
    ) {
        //
        switch (k)
        {
            // set en-passant square
            case PDMO: return; 
            
            //
            case RKMO: return; 
            
            //
            case RQMO: return; 
            
            //
            case KMOV: wks = s; break;
            
            //
            case ECAP: cb++; B[v - 8] = bp; M[bp & lo]++; break;
            
            //
            case KSCA: B[h1] = wr; B[f1] = O; break;
            
            //
            case QSCA: B[a1] = wr; B[d1] = O; break;
                
            // undo promotion    
            default: M[wp & lo]++; M[k & pi & lo]--;    
        }
    }
    
    //
    private void black_unmove(    
        final int s,
        final int v,
        final int k
    ) {
        //
        switch (k)
        {
            // set en-passant square
            case PDMO: return; 
            
            //
            case RKMO: return; 
            
            //
            case RQMO: return; 
            
            //
            case KMOV: bks = s; break;
       
            //
            case ECAP: cw++; B[v + 8] = wp; M[wp & lo]++; break;
        
            //
            case KSCA: B[h8] = br; B[f8] = O; bks = g8; break;
            
            //
            case QSCA: B[a8] = br; B[d8] = O; bks = c8; break;
              
            // by default undo promotion move
            default: M[bp & lo]++; M[k & pi & lo]--;    
        }               
    }
            
    // generate moves-stack with legal-moves
    public void legals() 
    {                  
        //
        if (Legals.has(phk)) 
        {
            //
            legals = Legals.get(phk);
            
            //
            return;
        } 
                
        // move-container get move from move-stack pre-created
        legals = Moves.pick();

        // generate-fill "m" with white or black legal moves
        if (t == w) { white_legals(); } else { black_legals(); }

        // assign move weight for ab search with sort
        if (EVAL_LEGALS) { Eval.legals(this); }

        // add move to legals cached
        Legals.add(phk, legals);
    }
    
    // generate moves-stack with legal-moves
    private void white_legals() 
    {        
        // generate white pseudo-moves
        white_pseudo();       
        
        // test if white current in check
        legals.c = black_attack(wks);
        
        // skip legals test for moves 
        if (!MOVE_LEGALS) { return; }
         
        // legal move index
        int j = 0;
        
        // pseudo move count            
        final int l = legals.i;
                        
        // loop throut pseudo-legal
        for (int i = 0; i != l; i++) 
        {   
            // start square
            final int s = legals.s[i];
            
            // versus square
            final int v = legals.v[i];
                
            //
            final int k = legals.k[i];
            
            // test king side castling possibility 
            if (k == KSCA) 
            {
                //
                if (!legals.c && !black_attack(f1) && !black_attack(g1)) 
                {
                    //
                    legals.copy(i, j); 

                    //
                    j++; 
                }             

                //
                continue;
            }

            // test queen castling possibility 
            if (k == QSCA) 
            {
                //
                if (!legals.c && !black_attack(d1) && !black_attack(c1)) 
                { 
                    //
                    legals.copy(i, j); 

                    //
                    j++; 
                }             

                //
                continue;
            }
            
            // king is safe move piece that not unsafe the king secure legal
            if (!legals.c &&s != wks && LINK[s][wks] == 0) 
            {
                //
                legals.copy(i, j); 
                
                //
                j++; 
                
                //
                continue;
            }                                
            
            // king under attack try to move a piece that non protect king
            if (legals.c && v != wks && LINK[v][wks] == 0) { continue; }                         
                                                                                
            // make move
            domove(i);

            // test attacked king
            if (!black_attack(wks)) 
            { 
                //
                legals.copy(i, j); 
                
                //
                j++; 
            }

            // undo move
            unmove();
        }
        
        //
        legals.i = j;
    } 
            
    // generate moves-stack with legal-moves
    private void black_legals() 
    {            
        // generate pseudo-moves into "m"
        black_pseudo();         
        
        //
        legals.c = white_attack(bks);
        
        // skip legals test for moves 
        if (!MOVE_LEGALS) { return; }
    
        // legal move index
        int j = 0;
        
        // prepare "j" loop cursor
        final int l = legals.i; 
            
        // loop throut pseudo-legal moves
        for (int i = 0; i != l; i++) 
        {     
            //
            final int s = legals.s[i];
            
            //
            final int v = legals.v[i];
            
            //
            final int k = legals.k[i];
            
            //
            if (k == KSCA) 
            {
                // 
                if (!legals.c && !white_attack(f8) && !white_attack(g8)) 
                {
                    //
                    legals.copy(i, j); 
                    
                    //
                    j++;
                }

                //
                continue;
            }
            
            //
            if (k == QSCA) 
            {
                // 
                if (!legals.c && !white_attack(d8) && !white_attack(c8)) 
                { 
                    //
                    legals.copy(i, j); 
                    
                    //
                    j++; 
                }

                //
                continue;
            }
                                              
            // king safety move piece that not unsafe the king secure legal
            if (!legals.c && s != bks && LINK[s][bks] == 0) 
            {
                // confirm and next
                legals.copy(i, j); 
                
                //
                j++; 
                
                //
                continue;
            }
                         
            // king under attack try to move a piece that non protect king
            if (legals.c && s != bks && LINK[v][bks] == 0) { continue; }                         
                        
            //
            domove(i);

            //
            if (!white_attack(bks)) 
            { 
                //
                legals.copy(i, j); 
                
                //
                j++; 
            }

            //
            unmove();            
        }
        
        //
        legals.i = j;
    }                        
            
    //
    private boolean black_castling(final int v)
    {        
        //
        boolean castling = v == g8
             ?! white_attack(g8)
            &&! white_attack(f8)
             :! white_attack(c8)
            &&! white_attack(d8); 
        
        //        
        return castling;    
    }

    // populate move-stack with pseudo-legal moves
    private void white_pseudo()
    {
        // index count squares
        int si = 0;
        
        // index count pieces
        int pi = cw;
        
        // looking for white pieces
        do 
        {        
            // next observed square
            final int s = wbm[si++];          

            // get piece in start square
            final int p = B[s];
            
            // square not have a side to move piece skip
            if ((p & w) != w) { continue; }
                
            // decrease piece count
            pi--;
            
            // remap square in wbm 
            if (REMAPS_PSEUDO) { white_remaps(si, pi, s); }

            // switch to specific piece 
            switch (p) 
            {
                // white pawn
                case wp: white_pawn_pseudo(s); break;                                        
                
                // white rook
                case wr: sliding_pseudo(s, 0, 4, white_pseudo_rook(s)); break;                        
                
                // white knight    
                case wn: knight_pseudo(s); break;                        
                
                // white bishop    
                case wb: sliding_pseudo(s, 4, 8, MOVE); break;                                                                
                
                // white queen
                case wq: sliding_pseudo(s, 0, 8, MOVE); break;
                
                // white king    
                case wk: white_king_pseudo(s); break;
                
                // default exit
                default: exit("default: white_pseudo()");                                            
            }            
        } 
        
        //
        while (pi != 0);
    }

    // populate move-stack with pseudo-legal moves
    private void black_pseudo() 
    {    
        // count squares
        int si = 0;

        // count pieces
        int pi = cb;
                
        // loop throut black piece
        do 
        {        
            // next start square
            final int s = bbm[si++];

            // get piece in start square
            final int p = B[s];

            // not is a black piece continue
            if ((p & b) != b) { continue; }    
                
            // apply boars search square remaps
            if (REMAPS_PSEUDO) { black_remaps(si, pi, s); }
            
            // switch by piece
            switch (p) 
            {
                // add black pawn moves
                case bp: black_pawn_pseudo(s); break;                                             
                
                // add sliding piece rook moves
                case br: sliding_pseudo(s, 0, 4, black_pseudo_rook(s)); break;
                
                // add kngiht moves
                case bn: knight_pseudo(s); break;                                
                
                // add sliding piece bishop moves
                case bb: sliding_pseudo(s, 4, 8, MOVE); break;
                
                // add sliding piece queen moves     
                case bq: sliding_pseudo(s, 0, 8, MOVE); break;
                
                // add kings moves and castling
                case bk: black_king_pseudo(s); break;    
                
                // unrecognized piece fault stop
                default: exit("default: black_pseudo()");                        
            }

            // count founded piece
            pi--;            
        }
        
        //
        while (pi != 0);
    }

    // return true if white can attack square "s"
    public boolean white_attack(final int s)
    {    
        //
        int v;
        
        // attacked from white pawn         
        if (M[wp & lo] != 0) 
        {
            //
            v = SPAN[s][SE];

            // test 
            if (v != xx && B[v] == wp) { return true; }

            //
            v = SPAN[s][SW];

            //
            if (v != xx && B[v] == wp) { return true; }
        }
        
        // if white have a knights test attacking
        if (M[wn & lo] != 0) for (int i = 0; i < 8; i++) 
        {            
            // get versus square
            v = HOPE[s][i];            
            
            // skip found out-of-board
            if (v == xx) { break; }
            
            // add move to stack if found empty square
            if (B[v] == wn) { return true; } 
        }
            
        // if white have a bishop test attacking
        if (M[wb & lo] != 0) for (int i = 4; i < 8; i++) 
        {        
            // versus square
            v = SPAN[s][i];
        
            //
            while (v != xx) 
            {       
                //
                switch (B[v]) 
                {
                    //
                    case O: v = SPAN[v][i]; continue;
                    
                    //
                    case wb: return true;
                }
                
                //
                break;
            }
        }
         
        // attacked from rook
        if (M[wr & lo] != 0) for (int i = 0; i < 4; i++) 
        {        
            // versus square
            v = SPAN[s][i];
        
            //
            while (v != xx) 
            {     
                //
                switch (B[v]) 
                {
                    //
                    case O: v = SPAN[v][i]; continue;
                    
                    //
                    case wr: return true;              
                }   
                
                //
                break;
            }
        }
        
        // attacked from queen
        if (M[wq & lo] != 0) for (int i = 0; i < 8; i++) 
        {        
            // versus square
            v = SPAN[s][i];
        
            //
            while (v != xx) 
            {     
                //
                switch (B[v]) 
                {
                    //
                    case O: v = SPAN[v][i]; continue;
                    
                    //
                    case wq: return true;              
                }   
                
                //
                break;
            }
        }               
        
        // white king attack
        for (int i = 0; i < 8; i++) 
        {            
            // get versus square
            v = SPAN[s][i];            
            
            // skip found out-of-board
            if (v == xx) { continue; }
            
            // add move to stack if found empty square
            if (B[v] == wk) { return true; } 
        }
            
        // no attaccants founds
        return false;
    }

    // return true if black-side-player can attack square "a"
    public boolean black_attack(int a) 
    {             
        //
        int v;
        
        // attacked from white pawn
        if (M[bp & lo] != 0) 
        { 
            //
            v = SPAN[a][NE];

            //
            if (v != xx && B[v] == bp) { return true; }

            //
            v = SPAN[a][NW];

            //
            if (v != xx && B[v] == bp) { return true; }
        }
        
        //
        if (M[bn & lo] != 0) for (int i = 0; i < 8; i++) 
        {            
            // get versus square
            v = HOPE[a][i];            
            
            // skip found out-of-board
            if (v == xx) { continue; }
            
            // add move to stack if found empty square
            if (B[v] == bn) { return true; } 
        }
                
                
        // attacked from queen or bishop
        if (M[bb & lo] != 0) for (int i = 4; i < 8; i++) 
        {        
            // versus square
            v = SPAN[a][i];
        
            //
            while (v != xx) 
            {      
                //
                switch (B[v]) 
                {
                    //
                    case O: v = SPAN[v][i]; continue;
                    
                    //
                    case bb: return true;                    
                }            
                
                //
                break;
            }
        }

        // attacked from queen or rook
        if (M[br & lo] != 0) for (int i = 0; i < 4; i++) 
        {        
            // versus square
            v = SPAN[a][i];
        
            //
            while (v != xx) 
            {    
                //
                switch (B[v]) 
                {
                    //
                    case O: v = SPAN[v][i]; continue;
                    
                    //
                    case br: return true;
                }   
                
                //
                break;
            }
        }
        
        // attacked from queen 
        if (M[bq & lo] != 0) for (int i = 0; i < 8; i++) 
        {        
            // versus square
            v = SPAN[a][i];
        
            //
            while (v != xx) 
            {    
                //
                switch (B[v]) 
                {
                    //
                    case O: v = SPAN[v][i]; continue;
                    
                    //
                    case bq: return true;
                }   
                
                //
                break;
            }
        }
                
        //
        for (int i = 0; i < 8; i++) 
        {            
            // get versus square
            v = SPAN[a][i];            
            
            // skip found out-of-board
            if (v == xx) { continue; }
            
            // add move to stack if found empty square
            if (B[v] == bk) { return true; } 
        }
        
        //
        return false;
    }
        
    //
    public final boolean threefold()
    {     
        //
        int d = 0;
        
        //
        for (int i = 0; i < L.i; i++) 
        {
            //
            if (phk == L.phk[i]) { d++; }
        }
        
        //
        return d >= 2;
    }
    
    //
    public final void captures()
    {
        // if have captures cached retrieve it else compute it
        if (Captures.has(phk)) { captures = Captures.get(phk); return; }
        
        // new empty capture-stack
        captures = Captures.pick();
        
        // legal move index
        int j = 0;
            
        //
        if (t == w) 
        {       
            //
            white_captures(); 
                            
            //
            final int l = captures.i;
            
            // loop throut pseudo-legal moves
            for (int i = 0; i < l; i++) 
            {                                
                //
                domove(captures, i);

                //
                if (!black_attack(wks)) { captures.copy(i, j); j++; }

                //
                unmove();            
            }
        } 
        
        //
        else 
        { 
            //
            black_captures(); 
            
            //
            final int l = captures.i;
                    
            // loop throut pseudo-legal moves
            for (int i = 0; i < l; i++) 
            {                                
                //
                domove(captures, i);

                //
                if (!white_attack(bks)) { captures.copy(i, j); j++; }

                //
                unmove();            
            }
        }
                
        //
        captures.i = j;     
        
        //
        Captures.add(phk, captures);
    }
    
    //
    private void white_captures()
    {
        // count squares
        int si = 0;

        // count pieces
        int pi = cw;
                
        // loop throut black piece
        do 
        {        
            // next start square
            final int s = wbm[si++];

            // get piece in start square
            final int p = B[s];

            // not is a black piece continue
            if ((p & w) != w) { continue; }    
                
            // apply boars search square remaps
            //if (PSEUDO_REMAPS) { black_remaps(si, pi, s); }
            
            //
            int v;
            
            // switch by piece
            switch (p) 
            {
                // add black pawn capture
                case wp:
                    
                    //
                    int r = s >> 3;
                                                                                      
                    //
                    if (r != 6) 
                    {
                        //
                        v = SPAN[s][NE]; 
                        
                        //
                        if (v != xx && (B[v] & b) == b) { captures.add(s, v, MOVE); }                    

                        //
                        v = SPAN[s][NW]; 
                        
                        //
                        if (v != xx && (B[v] & b) == b) 
                        {
                            //
                            captures.add(s, v, MOVE); 
                        } 
                    }
                    
                    //
                    else
                    {                    
                        //
                        v = SPAN[s][NE]; 
                        
                        //
                        if (v != xx && (B[v] & b) == b) 
                        { 
                            captures.add(s, v, WQPM); 
                            captures.add(s, v, WRPM); 
                            captures.add(s, v, WBPM); 
                            captures.add(s, v, WNPM); 
                        }                    

                        //
                        v = SPAN[s][NW]; 
                        
                        //
                        if (v != xx && (B[v] & b) == b) 
                        { 
                            captures.add(s, v, WQPM); 
                            captures.add(s, v, WRPM); 
                            captures.add(s, v, WBPM); 
                            captures.add(s, v, WNPM);
                        } 
                    }
                    
                    //
                    break;
                                        
                // add sliding piece rook captures
                case wr: sliding_captures(s, 0, 4); break;
                
                // add sliding piece bishop captures             
                case wb: sliding_captures(s, 4, 8); break;
                
                // add sliding piece queen captures     
                case wq: sliding_captures(s, 0, 8); break;
                
                // add kngiht captures
                case wn: 
                    for (int i = 0; i < 8; i++) 
                    {
                        v = HOPE[s][i];            
                        if (v != xx && (B[v] & b) == b) { captures.add(s, v, MOVE); }
                    }
                    break;
                
                // add kings captures
                case wk: 
                    for (int i = 0; i < 8; i++) 
                    {
                        v = SPAN[s][i];            
                        if (v != xx && (B[v] & b) == b) { captures.add(s, v, KMOV); }
                    }
                    break;    

                // unrecognized piece fault stop
                default: exit("WHITE CAPTURE FAULT");                        
            }

            // count founded piece
            pi--;            
        }
        
        //
        while (pi != 0);        
    }
    
    //
    private void black_captures()
    {
        // count squares
        int si = 0;

        // count pieces
        int pi = cb;
                
        // loop throut black piece
        do 
        {        
            // next start square
            final int s = bbm[si++];

            // get piece in start square
            final int p = B[s];

            // not is a black piece continue
            if ((p & b) != b) { continue; }    
                
            // apply boars search square remaps
            //if (PSEUDO_REMAPS) { black_remaps(si, pi, s); }
            
            int v;
            
            // switch by piece
            switch (p) 
            {
                // add sliding piece rook moves
                case br: sliding_captures(s, 0, 4); break;
                
                // add sliding piece bishop moves               
                case bb: sliding_captures(s, 4, 8); break;
                
                // add sliding piece queen moves                     
                case bq: sliding_captures(s, 0, 8); break;

                // add black pawn capture
                case bp:
                    
                    //
                    int r = s >> 3;
                    
                    //
                    if (r != 1) 
                    {
                        //
                        v = SPAN[s][SE]; 
                     
                        //
                        if (v != xx && (B[v] & w) == w)
                        {
                            //
                            captures.add(s, v, MOVE); 
                        }                    

                        //
                        v = SPAN[s][SW];
                        
                        //
                        if (v != xx && (B[v] & w) == w) 
                        { 
                            //
                            captures.add(s, v, MOVE);
                        }                                        
                    }
                    
                    //
                    else
                    {
                        //
                        v = SPAN[s][SE]; 
                        
                        //
                        if (v != xx && (B[v] & w) == w) 
                        { 
                            captures.add(s, v, BQPM); 
                            captures.add(s, v, BRPM); 
                            captures.add(s, v, BBPM); 
                            captures.add(s, v, BNPM); 
                        }                    

                        //
                        v = SPAN[s][SW]; 
                        
                        //
                        if (v != xx && (B[v] & w) == w) 
                        { 
                            captures.add(s, v, BQPM); 
                            captures.add(s, v, BRPM); 
                            captures.add(s, v, BBPM); 
                            captures.add(s, v, BNPM);
                        } 
                    }
                    
                    //
                    break;
                
                // add kngiht moves
                case bn: 
                    for (int i = 0; i < 8; i++) 
                    {
                        //
                        v = HOPE[s][i];            
                        if (v != xx && (B[v] & w) == w) { captures.add(s, v, MOVE); }
                    }
                    
                    //
                    break;
                
                // add kings moves and castling
                case bk:
                    
                    //
                    for (int i = 0; i < 8; i++) 
                    {
                        //
                        v = SPAN[s][i];            
                        
                        //
                        if (v != xx && (B[v] & w) == w) { captures.add(s, v, KMOV); }
                    }
                    
                    //
                    break;    
                
                // unrecognized piece fault stop
                default: exit("BLACK CAPTURE FAULT");                        
            }

            // count founded piece
            pi--;            
        }
        
        //
        while (pi != 0);    
    }
    
    //
    public void remaps(final int si, final int pi, final int s)
    {                              
        //
        bm[si] = bm[pi];

        //
        bm[pi] = s;
    }
    
    //
    private void white_remaps(
        final int si, 
        final int pi,
        final int s
    ) {                             
        //
        wbm[si] = wbm[pi];

        //
        wbm[pi] = s;
    }
    
    //
    private void black_remaps(
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
    private void sliding_pseudo(
        final int s,
        final int x0,    // start direction
        final int x1,    // final direction
        final int x2    // k-value for normal move
    ) {                        
        // loop throut directions
        for (int j = x0; j < x1; j++) 
        {        
            // versus square
            int v = SPAN[s][j];
            
            // while not found out-of-board
            while (v != xx)
            {    
                // 
                if (B[v] == O) { legals.add(s, v, x2); } 
                
                //
                else if ((B[v] & T) != t) { legals.add(s, v, x2); break; } 
                
                //
                else { break; }                
                
                // next versus-square in same direction
                v = SPAN[v][j];
            }
        }
    }

    //
    private int white_pseudo_rook(final int s)
    {
        //
        if (((c & K___) == 0 && s == h1)) { return RKMO; }
        
        //
        if (((c & _Q__) == 0 && s == a1)) { return RQMO; } 
                    
        //            
        return MOVE;    
    }
    
    //
    private int black_pseudo_rook(final int s)
    {
        //
        if (((c & __k_) == 0 && s == h8)) { return RKMO; } 
        
        //
        if (((c & ___q) == 0 && s == a8)) { return RQMO; }
        
        //
        return MOVE;    
    }
    
    // handle knight normal move
    private void knight_pseudo(final int s)
    {    
        //
        for (int i = 0; i != 8; i++)
        {            
            // get versus square
            final int v = HOPE[s][i];            
            
            // skip found out-of-board
            if (v == xx) { return; }
            
            // captured piece
            final int x = B[v];
            
            // if square is empty add to moves
            if (x == O) { legals.add(s, v, MOVE); } 
            
            // if empty is occupay by opponent piece add capture
            else if ((x & T) != t) { legals.add(s, v, MOVE); }
        }
    }
      
    // white pawn moves
    private void white_pawn_pseudo(final int s) 
    {                                        
        //
        int v = SPAN[s][NN];                 
            
        // rank of start square
        final int r = s >> 3;
    
        // is not in promotion rank
        if (r != 6) 
        {            
            //
            if (B[v] == O) 
            {
                //
                final int u = SPAN[v][NN];                 

                //
                if (r == 1 && B[u] == O)
                {
                    legals.add(s, u, PDMO);
                }

                //
                legals.add(s, v, MOVE);                
            }    
            
            //
            v = SPAN[s][NE]; 
            
            //
            if (v != xx)
            {
                //
                if ((B[v] & b) == b) { legals.add(s, v, MOVE); }

                //
                if (r == 4 && v == e) { legals.add(s, v, ECAP); }                    
            }            
            
            //
            v = SPAN[s][NW];
            
            // 
            if (v != xx)  
            {
                //
                if ((B[v] & b) == b) { legals.add(s, v, MOVE); }
                
                //
                if (r == 4 && v == e) { legals.add(s, v, ECAP); }    
            }                                    
        } 
        
        // yes is in promotion rank
        else 
        {                        
            //
            if (B[v] == O)
            {
                legals.add(s, v, WQPM);
                legals.add(s, v, WRPM);
                legals.add(s, v, WBPM);
                legals.add(s, v, WNPM);
            }
                    
            //
            v = SPAN[s][NE];             
            
            //
            if (v != xx && (B[v] & b) == b)
            {
                legals.add(s, v, WQPM);
                legals.add(s, v, WRPM);
                legals.add(s, v, WBPM);
                legals.add(s, v, WNPM);
            }
            
            //
            v = SPAN[s][NW];
            
            //
            if (v != xx && (B[v] & b) == b) 
            {
                legals.add(s, v, WQPM);
                legals.add(s, v, WRPM);
                legals.add(s, v, WBPM);
                legals.add(s, v, WNPM);
            }                            
        }    
    }
    
    // untouched black pawn moves 
    private void black_pawn_pseudo(final int s) 
    {                    
        //
        int v = SPAN[s][SS];                 
        
        // get start square rank 
        final int r = s >> 3;
        
        // not is in promotion rank
        if (r != 1) 
        {            
            //
            if (B[v] == 0) 
            {
                //
                final int u = SPAN[v][SS];                 

                //
                if (r == 6 && B[u] == 0) {                    
                    legals.add(s, u, PDMO);
                }

                //
                legals.add(s, v, MOVE);                
            }    
            
            //
            v = SPAN[s][SE]; 
            
            //
            if (v != xx) 
            { 
                //
                if ((B[v] & w) == w) { legals.add(s, v, MOVE); }

                //
                if (r == 3 && v == e) { legals.add(s, v, ECAP); }    
            } 
                        
            //
            v = SPAN[s][SW];
            
            //
            if (v != xx)
            {
                //
                if ((B[v] & w) == w) { legals.add(s, v, MOVE); }
                
                //
                if (r == 3 && v == e) { legals.add(s, v, ECAP); }    
            }                    
        } 
        
        // else pawn is in promotion rank
        else 
        {                
            // if square is empty add 4 promotion moves
            if (B[v] == 0) 
            {
                legals.add(s, v, BQPM);
                legals.add(s, v, BRPM);
                legals.add(s, v, BBPM);
                legals.add(s, v, BNPM);
            }
            
            // promotion est-capture square
            v = SPAN[s][SE];             
            
            //
            if (v != xx && (B[v] & w) == w)
            {
                legals.add(s, v, BQPM);
                legals.add(s, v, BRPM);
                legals.add(s, v, BBPM);
                legals.add(s, v, BNPM);
            }
            
            //
            v = SPAN[s][SW];
            
            //
            if (v != xx && (B[v] & w) == w) 
            {
                legals.add(s, v, BQPM);
                legals.add(s, v, BRPM);
                legals.add(s, v, BBPM);
                legals.add(s, v, BNPM);
            }                                        
        }    
    }
   
    // handle white king pseudo-moves
    private void white_king_pseudo(final int s)
    {            
        //
        for (int i = 0; i < 8; i++)
        {   
            // get versus square
            final int v = SPAN[s][i];            
            
            // skip found out-of-board
            if (v == xx) { continue; }
            
            // look square for captured piece
            final int x = B[v];

            // add if found empty square            
            if (x == O) { legals.add(s, v, KMOV); } 
            
            // add if captured is black piece            
            else if ((x & b) == b) { legals.add(s, v, KMOV); }
        }        
        
        //
        if (s != e1) { return; }

        // king-side white castling
        if (wksc()) { legals.add(e1, g1, KSCA); } 

        // queen-side white castling        
        if (wqsc()) { legals.add(e1, c1, QSCA); }   
    } 
    
    // look there are condition for white king-side castling
    private boolean wksc() 
    {    
        //
        return 0 == (c & K___) && B[h1] == wr  && B[g1] == O && B[f1] == O;
    }
    
    // look there are condition for white queen-side castling
    private boolean wqsc() 
    {       
        //
        return 0 == (c & _Q__) && B[a1] == wr && B[d1] == O && B[c1] == O && B[b1] == O;
    }
    
    // handle black king pseudo-moves
    private void black_king_pseudo(
        final int s // start square
    ) {    
        //
        for (int i = 0; i < 8; i++) 
        {
            // get versus square
            final int v = SPAN[s][i];            
            
            // skip found out-of-board
            if (v == xx) { continue; }
            
            // add move to stack if found empty square
            if (B[v] == O) { legals.add(s, v, KMOV); } 
            
            // or add capture move if found opponnet piece
            else if ((B[v] & w) == w) { legals.add(s, v, KMOV); }
        }
        
        // no possible castling
        if (s != e8) { return; }
        
        // test for valid king-side castling and add to move stack
        if (bksc()) { legals.add(e8, g8, KSCA); } 

        // or queen-side castling and add to move stack
        if (bqsc()) { legals.add(e8, c8, QSCA); }                    
    }    
    
    //
    private boolean bksc() 
    {    
        //
        return (c & __k_) == 0 && B[h8] == br && B[f8] == O && B[g8] == O;
    }
    
    //
    private boolean bqsc() 
    {    
        //
        return (c & ___q) == 0 && B[a8] == br && B[d8] == O && B[c8] == O && B[b8] == O;
    }
    
    // add captures for sliding pieces
    private void sliding_captures(
        final int s,
        final int x0, 
        final int x1
    ) {
        // loop throu directions
        for (int i = x0; i < x1; i++) 
        {        
            // first square for direction
            int v = SPAN[s][i];
            
            // loop to next square on direction
            while (v != xx)
            { 
                // 
                if (B[v] == O) { v = SPAN[v][i]; continue; } 
                
                //
                if ((B[v] & T) != t) { captures.add(s, v, MOVE); } 
                
                //
                break; 
            }
        } 
    }
}
