/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// 
package org.krudo;
 
// required static classes and methods
import static org.krudo.Tool.*;
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
    public int e; // possible en-passant capture square
    public int c; // castling status (negative logic)
    
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
        hash(this);
    }
    
    // restore node to position passed in FEN
    public final void startpos(final String fen) 
    {
        //
        Fen.parse(this, fen); 
        
        //
        hash(this);
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
    
    // do-play a move represented as coordinates (es. "e2e4")
    public final void domove(
        final String move
    ) {        
        // parse move parts and retrieve s,v,k
        int s = s2i(move.substring(0, 2));
        int v = s2i(move.substring(2, 4));
        int k = k2i(move, B[s], s, v, B[v], t);  
        
        // fix book castling move
        if (k == cast) switch (v)
        {
            case a1: v = c1; break;  
            case a8: v = c8; break;  
            case h1: v = g1; break;  
            case h8: v = g8; break;  
        }
        
        // do-play move apply status changes
        domove(s, v, k);
    }
    
    // do a move placed into internal "m" select by index
    public final void domove(        
        final int index
    ) {            
        // call direct s-v-k domove
        domove(legals.s[index],
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
        //
        Debug.assertPieceCount(this);

        // get moved piece
        final int p = B[s];
                
        // get captured piece
        final int x = B[v];        
        
        // store status into history line
        L.store(p, s, v, x, k, e, c, phk);
        
        //
        hash_step1(this);
        
        // set zero leaved square
        B[s] = 0; 

        // place moved piece into versus square
        B[v] = p;
                
        // set zero en-passant square
        e = xx;
        
        // decrease piece counter
        if (x != O) 
        {
            //
            M[x & lo]--;
            
            //
            ote += Eval.OTEW[x & lo];
            
            //
            if (t == w) { cb--; } else { cw--; }
        }
                                        
        // for special moves handle move rules
        if (k != move) if (t == w) 
        {  
            white_domove(s, v, k); 
        }
        
        //
        else 
        { 
            black_domove(s, v, k); 
        }        
        
        // swap turn side
        t ^= T;
        
        //
        hash_step2(this, p, s, v, x, k);
        
        //
        Debug.assertPieceCount(this);
    }
    
    // domove and change node internal status
    private void white_domove(
        final int s,
        final int v,
        final int k
    ) {                                           
        // fix specific status
        switch (k) 
        {                   
            // 
            case pdmo: e = s + 8; return;            
            //
            case ecap: cb--; B[v - 8] = O; return;                  
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
        final int k
    ) {                                                        
        // fix specific status
        switch (k) 
        {      
            // set en-passant square
            case pdmo: e = s - 8; return;            
            // performe en-passant capture
            case ecap: cw--; B[v + 8] = 0; return;          
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
        //
        //Debug.assertPieceCount(this);
        
        // decrease half-move index
        L.i--;
        
        // swap side-to-move
        t ^= T;
                
        // get moved piece
        final int p = L.p[L.i];
        
        // get start square
        final int s = L.s[L.i];
        
        // get versus square
        final int v = L.v[L.i];
        
        // get captured piece
        final int x = L.x[L.i]; 
        
        // get kind-of-move
        final int k = L.k[L.i];
        
        // restore piece in start square
        B[s] = p; 

        // restore versus square with captured piece
        B[v] = x;
                
        // retrieve previsour en-passant square
        e = L.e[L.i];
        
        // retrieve previsour castling status
        c = L.c[L.i];
        
        //
        phk = L.h[L.i];
        
        // decrease piece counter
        if (x != O) 
        {
            //
            M[x & lo]++;
            
            //
            ote -= Eval.OTEW[x & lo];
            
            //
            if (t == w) { cb++; } else { cw++; }
        }
        
        //
        if (k != move && k != rmov) if (t == w) 
        {
            white_unmove(p, s, v, k);
        } 
        
        //
        else 
        {
            black_unmove(p, s, v, k);        
        }
        
        //
        //Debug.assertPieceCount(this);
    }
    
    //
    private void white_unmove(
        final int p,
        final int s,
        final int v,
        final int k
    ) {
        //
        if (p == wk) { wks = s; }
        
        //
        if (k == ecap) { cb++; B[v - 8] = bp; }
        
        //
        if (k == cast) if (v == g1)
        {
            B[h1] = wr; 
            B[f1] = O;
        } 
        
        //
        else         
        {
            B[a1] = wr; 
            B[d1] = O;        
        }                
    }
    
    //
    private void black_unmove(    
        final int p,
        final int s,
        final int v,
        final int k
    ) {
        //
        if (p == bk) { bks = s; }
        
        //
        if (k == ecap) { cw++; B[v + 8] = wp; }
        
        //
        if (k == cast) if (v == g8) { B[h8] = br; B[f8] = O; } 
        
        //
        else { B[a8] = br; B[d8] = O; }                
    }
            
    // generate moves-stack with legal-moves
    public void legals() 
    {   
        //
        if (Legals.has(phk)) 
        {
            legals = Legals.get(phk);
        } 
        
        //
        else
        {
            // move-container get move from move-stack pre-created
            legals = Moves.pick();

            // generate-fill "m" with white or black legal moves
            if (t == w) { white_legals(); } else { black_legals(); }
            
            //
            if (EVAL_MOVE) { Eval.move(this); }
            
            //
            Legals.add(phk, legals);
        }        
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
        final int p = legals.i;
                
        // loop throut pseudo-legal
        for (int i = 0; i != p; i++) 
        {                            
            //
            if (legals.k[i] == cast) 
            {
                //
                if (white_castling(legals.v[i])) { legals.copy(i, l); l++; }             
                
                //
                continue;
            }
            
            //
            domove(i);

            //
            if (!black_attack(wks)) { legals.copy(i, l); l++; }

            //
            unmove();            
        }
        
        //
        legals.i = l;
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
        final int p = legals.i; 
            
        // loop throut pseudo-legal moves
        for (int j = 0; j != p; j++) 
        {                                
            //
            if (legals.k[j] == cast) 
            {
                //
                if (black_castling(legals.v[j])) { legals.copy(j, l); l++; }
                
                //
                continue;
            }             
                        
            //
            domove(j);

            //
            if (!white_attack(bks)) { legals.copy(j, l); l++; }

            //
            unmove();            
        }
        
        //
        legals.i = l;
    }                        
        
    //
    private boolean white_castling(final int v) 
    {   
        //
        boolean castling = v == g1
             ?! black_attack(e1)
            &&! black_attack(f1)
            &&! black_attack(g1)                
             :! black_attack(e1)
            &&! black_attack(d1)
            &&! black_attack(c1);    
                
        //        
        return castling;
    }

    //
    private boolean black_castling(
        final int v
    ) {        
        //
        boolean castling = v == g8
             ?! white_attack(e8)
            &&! white_attack(f8)
            &&! white_attack(g8)                
             :! white_attack(e8)
            &&! white_attack(d8)
            &&! white_attack(c8); 
        
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
            v = span[s][se];

            // test 
            if (v != xx && B[v] == wp) { return true; }

            //
            v = span[s][sw];

            //
            if (v != xx && B[v] == wp) { return true; }
        }
        
        // if white have a knights test attacking
        if (M[wn & lo] != 0) for (int i = 0; i < 8; i++) 
        {            
            // get versus square
            v = hope[s][i];            
            
            // skip found out-of-board
            if (v == xx) { break; }
            
            // add move to stack if found empty square
            if (B[v] == wn) { return true; } 
        }
            
        // if white have a bishop test attacking
        if (M[wb & lo] != 0) for (int i = 4; i < 8; i++) 
        {        
            // versus square
            v = span[s][i];
        
            //
            while (v != xx) 
            {       
                //
                switch (B[v]) 
                {
                    //
                    case O: v = span[v][i]; continue;
                    
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
            v = span[s][i];
        
            //
            while (v != xx) 
            {     
                //
                switch (B[v]) 
                {
                    //
                    case O: v = span[v][i]; continue;
                    
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
            v = span[s][i];
        
            //
            while (v != xx) 
            {     
                //
                switch (B[v]) 
                {
                    //
                    case O: v = span[v][i]; continue;
                    
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
            v = span[s][i];            
            
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
            v = span[a][ne];

            //
            if (v != xx && B[v] == bp) { return true; }

            //
            v = span[a][nw];

            //
            if (v != xx && B[v] == bp) { return true; }
        }
        
        //
        if (M[bn & lo] != 0) for (int i = 0; i < 8; i++) 
        {            
            // get versus square
            v = hope[a][i];            
            
            // skip found out-of-board
            if (v == xx) { continue; }
            
            // add move to stack if found empty square
            if (B[v] == bn) { return true; } 
        }
                
                
        // attacked from queen or bishop
        if (M[bb & lo] != 0) for (int i = 4; i < 8; i++) 
        {        
            // versus square
            v = span[a][i];
        
            //
            while (v != xx) 
            {      
                //
                switch (B[v]) 
                {
                    //
                    case O: v = span[v][i]; continue;
                    
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
            v = span[a][i];
        
            //
            while (v != xx) 
            {    
                //
                switch (B[v]) 
                {
                    //
                    case O: v = span[v][i]; continue;
                    
                    //
                    case br: return true;
                }   
                
                //
                break;
            }
        }
        
        // attacked from queen 
        if (M[bq & lo] != 0) for (int i = 0; i < 4; i++) 
        {        
            // versus square
            v = span[a][i];
        
            //
            while (v != xx) 
            {    
                //
                switch (B[v]) 
                {
                    //
                    case O: v = span[v][i]; continue;
                    
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
            v = span[a][i];            
            
            // skip found out-of-board
            if (v == xx) { continue; }
            
            // add move to stack if found empty square
            if (B[v] == bk) { return true; } 
        }
        
        //
        return false;
    }
    
    //
    public final boolean incheck()
    {
        //
        return t == w ? black_attack(wks) : white_attack(bks);        
    }
    
    //
    public final boolean threefold()
    {     
        //
        for (int i = 0; i < L.i; i++) 
        {
            if (phk == L.h[i]) 
            {
            }
        }
        
        //
        return false;
    }
    
    //
    public final void captures()
    {
        // if have captures cached retrieve it else compute it
        if (Captures.has(phk)) { captures = Captures.get(phk); return; }
        
        // new empty capture-stack
        captures = Captures.pick();
        
        // legal move index
        int l = 0;
            
        //
        if (t == w) 
        {       
            //
            white_capture(); 
                            
            // loop throut pseudo-legal moves
            for (int i = 0; i < captures.i; i++) 
            {                                
                //
                domove(captures, i);

                //
                if (!black_attack(wks)) { captures.copy(i, l); l++; }

                //
                unmove();            
            }
        } 
        
        //
        else 
        { 
            //
            black_capture(); 
        
            // loop throut pseudo-legal moves
            for (int i = 0; i < captures.i; i++) 
            {                                
                //
                domove(captures, i);

                //
                if (!white_attack(bks)) { captures.copy(i, l); l++; }

                //
                unmove();            
            }
        }
                
        //
        captures.i = l;       
    }
    
    //
    private void white_capture()
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
                    v = span[s][ne]; 
                    if (v != xx && (B[v] & b) == b) { captures.add(s, v, move); }                    
                    
                    //
                    v = span[s][nw]; 
                    if (v != xx && (B[v] & b) == b) { captures.add(s, v, move); }                    
                    
                    //
                    if (r == 1) 
                    {
                        //
                        v = span[s][nn]; 
                        
                        //
                        if (B[v] == O) 
                        { 
                            captures.add(s, v, move); 
                        }
                    }
                    
                    //
                    break;
                                        
                // add sliding piece rook captures
                case wr: spac(captures, s, 0, 4); break;
                
                // add sliding piece bishop captures             
                case wb: spac(captures, s, 4, 8); break;
                
                // add sliding piece queen captures     
                case wq: spac(captures, s, 0, 8); break;
                
                // add kngiht captures
                case wn: 
                    for (int i = 0; i < 8; i++) 
                    {
                        v = hope[s][i];            
                        if (v != xx && (B[v] & b) == b) { captures.add(s, v, move); }
                    }
                    break;
                
                // add kings captures
                case wk: 
                    for (int i = 0; i < 8; i++) 
                    {
                        v = span[s][i];            
                        if (v != xx && (B[v] & b) == b) { captures.add(s, v, kmov); }
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
    private void black_capture()
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
                case br: spac(captures, s, 0, 4); break;
                
                // add sliding piece bishop moves               
                case bb: spac(captures, s, 4, 8); break;
                
                // add sliding piece queen moves                     
                case bq: spac(captures, s, 0, 8); break;

                // add black pawn capture
                case bp:
                    
                    //
                    int r = s >> 3;
                    
                    //
                    v = span[s][se]; 
                    if (v != xx && (B[v] & w) == w) { captures.add(s, v, move); }                    
                    
                    //
                    v = span[s][sw]; 
                    if (v != xx && (B[v] & w) == w) { captures.add(s, v, move); }                    
                    
                    //
                    break;
                
                // add kngiht moves
                case bn: 
                    for (int i = 0; i < 8; i++) 
                    {
                        //
                        v = hope[s][i];            
                        if (v != xx && (B[v] & w) == w) { captures.add(s, v, move); }
                    }
                    
                    //
                    break;
                
                // add kings moves and castling
                case bk:
                    
                    //
                    for (int i = 0; i < 8; i++) 
                    {
                        //
                        v = span[s][i];            
                        
                        //
                        if (v != xx && (B[v] & w) == w) { captures.add(s, v, kmov); }
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
    private void span(
        final int s,
        final int x0,    // start direction
        final int x1,    // final direction
        final int x2    // k-value for normal move
    ) {                        
        // loop throut directions
        for (int j = x0; j < x1; j++) 
        {        
            // versus square
            int v = span[s][j];
            
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
                v = span[v][j];
            }
        }
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
            if (x == 0) { legals.add(s, v, move); } 
            
            // if empty is occupay by opponent piece add capture
            else if ((x & T) != t) { legals.add(s, v, move); }
        }
    }
      
    // white pawn moves
    private void pawn(
        final int s
    ) {                                        
        //
        int v = span[s][nn];                 
            
        // rank of start square
        final int r = s >> 3;
    
        // is not in promotion rank
        if (r != 6) 
        {            
            //
            if (B[v] == O) 
            {
                //
                final int u = span[v][nn];                 

                //
                if (r == 1 && B[u] == O) {
                    legals.add(s, u, pdmo);
                }

                //
                legals.add(s, v, move);                
            }    
            
            //
            v = span[s][ne]; 
            
            //
            if (v != xx)
            {
                //
                if ((B[v] & b) == b) { legals.add(s, v, move); }

                //
                if (r == 4 && v == e) { legals.add(s, v, ecap); }                    
            }            
            
            //
            v = span[s][nw];
            
            // 
            if (v != xx)  
            {
                //
                if ((B[v] & b) == b) { legals.add(s, v, move); }
                
                //
                if (r == 4 && v == e) { legals.add(s, v, ecap); }    
            }                                    
        } 
        
        // yes is in promotion rank
        else 
        {                        
            //
            if (B[v] == O)
            {
                legals.add(s, v, wqpm);
                legals.add(s, v, wrpm);
                legals.add(s, v, wbpm);
                legals.add(s, v, wnpm);
            }
                    
            //
            v = span[s][ne];             
            
            //
            if (v != xx && (B[v] & b) == b)
            {
                legals.add(s, v, wqpm);
                legals.add(s, v, wrpm);
                legals.add(s, v, wbpm);
                legals.add(s, v, wnpm);
            }
            
            //
            v = span[s][nw];
            
            //
            if (v != xx && (B[v] & b) == b) 
            {
                legals.add(s, v, wqpm);
                legals.add(s, v, wrpm);
                legals.add(s, v, wbpm);
                legals.add(s, v, wnpm);
            }                            
        }    
    }
    
    // untouched black pawn moves 
    private void down(
        final int s
    ) {                    
        //
        int v = span[s][ss];                 
        
        // get start square rank 
        final int r = s >> 3;
        
        // not is in promotion rank
        if (r != 1) 
        {            
            //
            if (B[v] == 0) 
            {
                //
                final int u = span[v][ss];                 

                //
                if (r == 6 && B[u] == 0) {                    
                    legals.add(s, u, pdmo);
                }

                //
                legals.add(s, v, move);                
            }    
            
            //
            v = span[s][se]; 
            
            //
            if (v != xx) 
            { 
                //
                if ((B[v] & w) == w) { legals.add(s, v, move); }

                //
                if (r == 3 && v == e) { legals.add(s, v, ecap); }    
            } 
                        
            //
            v = span[s][sw];
            
            //
            if (v != xx)
            {
                //
                if ((B[v] & w) == w) { legals.add(s, v, move); }
                
                //
                if (r == 3 && v == e) { legals.add(s, v, ecap); }    
            }                    
        } 
        
        // else pawn is in promotion rank
        else 
        {                
            // if square is empty add 4 promotion moves
            if (B[v] == 0) 
            {
                legals.add(s, v, bqpm);
                legals.add(s, v, brpm);
                legals.add(s, v, bbpm);
                legals.add(s, v, bnpm);
            }
            
            // promotion est-capture square
            v = span[s][se];             
            
            //
            if (v != xx && (B[v] & w) == w)
            {
                legals.add(s, v, bqpm);
                legals.add(s, v, brpm);
                legals.add(s, v, bbpm);
                legals.add(s, v, bnpm);
            }
            
            //
            v = span[s][sw];
            
            //
            if (v != xx && (B[v] & w) == w) 
            {
                legals.add(s, v, bqpm);
                legals.add(s, v, brpm);
                legals.add(s, v, bbpm);
                legals.add(s, v, bnpm);
            }                                        
        }    
    }
   
    // handle white king pseudo-moves
    private void king(
        final int s // start square
    ) {            
        //
        for (int j = 0; j < 8; j++)
        {   
            // get versus square
            final int v = span[s][j];            
            
            // skip found out-of-board
            if (v == xx) { continue; }
            
            // look square for captured piece
            final int x = B[v];

            // add if found empty square            
            if (x == 0) { legals.add(s, v, kmov); } 
            
            // add if captured is black piece            
            else if ((x & b) == b) { legals.add(s, v, kmov); }
        }        
        
        //
        if (s == e1) 
        {
            // king-side white castling
            if (wksc())
            { 
                legals.add(e1, g1, cast); 
            } 
        
            // queen-side white castling        
            if (wqsc()) 
            {            
                legals.add(e1, c1, cast);
            }   
        }
    } 
    
    // look there are condition for white king-side castling
    private boolean wksc() 
    {    
        //
        return 0 == (c & wkc)
            && B[h1] == wr 
            && B[g1] == O 
            && B[f1] == O;
    }
    
    // look there are condition for white queen-side castling
    private boolean wqsc() 
    {       
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
        //
        for (int j = 0; j < 8; j++) 
        {
            // get versus square
            final int v = span[s][j];            
            
            // skip found out-of-board
            if (v == xx) { continue; }
            
            // add move to stack if found empty square
            if (B[v] == O) { legals.add(s, v, kmov); } 
            
            // or add capture move if found opponnet piece
            else if ((B[v] & w) == w) { legals.add(s, v, kmov); }
        }
        
        //
        if (s == e8) 
        {
            // test for valid king-side castling and add to move stack
            if (bksc()) 
            {
                legals.add(e8, g8, cast);
            } 
        
            // or queen-side castling and add to move stack
            if (bqsc()) {
                legals.add(e8, c8, cast);
            }
        }
    }    
    
    //
    private boolean bksc() 
    {    
        //
        return (c & bkc) == 0
            && B[h8] == br              
            && B[f8] == O 
            && B[g8] == O;
    }
    
    //
    private boolean bqsc() 
    {    
        //
        return (c & bqc) == 0 
            && B[a8] == br 
            && B[d8] == O 
            && B[c8] == O 
            && B[b8] == O;
    }
    
    //
    private void spac(
        final Capture c, 
        final int s,
        final int x0, 
        final int x1
    ) {
        //
        for (int i = x0; i < x1; i++) 
        {        
            int v = span[s][i];
            while (v != xx)
            {    
                if (B[v] == O) { v = span[v][i]; continue; } 
                if ((B[v] & T) != t) { c.add(s, v, move); } 
                break; 
            }
        } 
    }
}
