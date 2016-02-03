/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

// required static class
import static org.krudo.Constant.*;
import static org.krudo.Config.*;
import static org.krudo.util.Tools.*;
import static org.krudo.util.Zobrist.*;

//
public final class Eval {
	
	// white partials evalued weight
	private static int wma; // white matirial weight
	private static int wow; // white opening weight
	private static int wew; // white ending weight
	private static int wmw; // white mixed opening ending weight
	private static int wkd;	// white king defended value
	private static int wph; // white pawn structure hash
	private static int wps; // white pawn structure value
	private static int wtt; // white total weight
	
	// black partials evaluad weight
	private static int bma; // black matirial weight
	private static int bow; // black opening weight
	private static int bew; // black ending weigt
	private static int bmw; // black mixed opening ending weight
	private static int bkd;	// black king defended value 
	private static int bph; // black pawn structure hash
	private static int bps; // black pawn structure value
	private static int btt; // black total weight

	// temps and cursors 
	private static int p; // focused piece
	private static int s; // start square
	private static int v; // versus square
	private static int x; // captured piece
	private static int i; // piece index
	private static int u; // piece index
	private static int g; 
	private static int b; // eval bias for move 
	private static int j; // a simple cursor in for 	
	
	// hash temp global variable
	private static long h;
		
	// opening piece sqaure weight
	private final static int[][] ow = {	   
		/*bp*/
		{
		+3,+3,+3,+3,+3,+3,+3,+3,
		+3,+3,+3,+3,+3,+3,+3,+3,
		+3,+3,+3,+3,+3,+3,+3,+3,
		+0,+0,+4,+9,+9,+4,+0,+0,
		+0,+0,+4,+8,+8,+1,+0,+0,
		+0,+0,+3,+2,+2,-4,+0,+0,
		+6,+6,+6,+0,+0,+6,+6,+6,
		+0,+0,+0,+0,+0,+0,+0,+0,
		},			
		/*wp*/
		{
		+0,+0,+0,+0,+0,+0,+0,+0,
		+6,+6,+6,+0,+0,+6,+6,+6,
		+0,+0,+3,+2,+2,-4,+0,+0,
		+0,+0,+4,+8,+8,+1,+0,+0,
		+0,+0,+4,+9,+9,+4,+0,+0,
		+3,+3,+3,+3,+3,+3,+3,+3,
		+3,+3,+3,+3,+3,+3,+3,+3,
		+3,+3,+3,+3,+3,+3,+3,+3,
		},			
		/*bn*/
		{
		-9,-8,-8,-8,-8,-8,-8,-9,
		-9,+2,+9,+2,+2,+9,+2,-9,		
		-9,+5,+6,+9,+9,+6,+5,-9,
		-9,+4,+5,+5,+5,+5,+4,-9,
		-9,+3,+4,+5,+5,+4,+3,-9,
		-9,+2,+3,+4,+4,+3,+2,-9,
		-9,-6,-6,-6,-6,-6,-6,-9,
		-9,-8,-8,-8,-8,-8,-8,-9,
		},		
		/*wn*/
		{
		-9,-8,-8,-8,-8,-8,-8,-9,
		-9,-6,-6,-6,-6,-6,-6,-9,
		-9,+2,+3,+4,+4,+3,+2,-9,
		-9,+3,+4,+5,+5,+4,+3,-9,
		-9,+4,+5,+5,+5,+5,+4,-9,
		-9,+5,+6,+9,+9,+6,+5,-9,
		-9,+2,+9,+2,+2,+9,+2,-9,		
		-9,-8,-8,-8,-8,-8,-8,-9,
		},		
		/*bb*/
		{
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+4,+0,+0,+0,+0,+4,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+4,+2,+0,+0,+2,+4,+0,
		+0,+2,+8,+2,+2,+8,+2,+0,
		+0,+0,+0,+4,+4,+0,+0,+0,
		+0,+4,+0,+0,+0,+0,+4,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		},
		/*wb*/
		{
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+4,+0,+0,+0,+0,+4,+0,
		+0,+0,+0,+4,+4,+0,+0,+0,
		+0,+2,+8,+2,+2,+8,+2,+0,
		+0,+4,+2,+0,+0,+2,+4,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+4,+0,+0,+0,+0,+4,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		},
		/*br*/
		{
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		},
		/*wr*/
		{
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		},
		/*bq*/
		{
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		},		
		/*wq*/
		{
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		},		
		/*bk*/
		{
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		-2,-3,-4,-4,-4,-4,-3,-2,		
		-2,-3,-4,-9,-9,-4,-3,-2,
		-2,-3,-4,-9,-9,-4,-3,-2,
		-2,-3,-4,-4,-4,-4,-3,-2,
		-3,-3,-3,+0,+0,-3,-3,-3,
		+8,+9,-4,-2,-2,-4,+9,+8,
		},
		/*wk*/
		{
		+8,+9,-4,-2,-2,-4,+9,+8,
		-3,-3,-3,+0,+0,-3,-3,-3,
		-2,-3,-4,-4,-4,-4,-3,-2,		
		-2,-3,-4,-9,-9,-4,-3,-2,
		-2,-3,-4,-9,-9,-4,-3,-2,
		-2,-3,-4,-4,-4,-4,-3,-2,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+0,+0,+0,+0,+0,+0,+0,+0,
		}
	}; 

	//
	private final static int[][] ew = {	   
		/*bp*/
		{
		+5,+6,+6,+8,+8,+6,+6,+5,
		+7,+8,+8,+8,+8,+8,+8,+7,
		+6,+7,+7,+6,+6,+7,+7,+6,
		+5,+6,+6,+5,+5,+6,+6,+5,
		+4,+5,+5,+4,+4,+5,+5,+4,
		+0,+0,+0,+0,+0,+0,+0,+0,
		-9,-9,-9,-9,-9,-9,-9,-9,
		+0,+0,+0,+0,+0,+0,+0,+0,
		},			
		/*wp*/
		{
		+0,+0,+0,+0,+0,+0,+0,+0,
		-9,-9,-9,-9,-9,-9,-9,-9,
		+0,+0,+0,+0,+0,+0,+0,+0,
		+4,+5,+5,+4,+4,+5,+5,+4,
		+5,+6,+6,+5,+5,+6,+6,+5,
		+6,+7,+7,+6,+6,+7,+7,+6,
		+7,+8,+8,+8,+8,+8,+8,+7,
		+5,+6,+6,+8,+8,+6,+6,+5,
		},			
		/*wn*/
		{
		-9,-9,-9,-9,-9,-9,-9,-9,
		-9,+0,+0,+0,+0,+0,+0,-9,		
		-9,+0,+4,+4,+4,+4,+0,-9,
		-9,+0,+4,+8,+8,+4,+0,-9,
		-9,+0,+4,+8,+8,+4,+0,-9,
		-9,+0,+4,+4,+4,+4,+0,-9,
		-9,+0,+0,+0,+0,+0,+0,-9,
		-9,-9,-9,-9,-9,-9,-9,-9,
		},		
		/*bn*/
		{
		-9,-9,-9,-9,-9,-9,-9,-9,
		-9,+0,+0,+0,+0,+0,+0,-9,		
		-9,+0,+4,+4,+4,+4,+0,-9,
		-9,+0,+4,+8,+8,+4,+0,-9,
		-9,+0,+4,+8,+8,+4,+0,-9,
		-9,+0,+4,+4,+4,+4,+0,-9,
		-9,+0,+0,+0,+0,+0,+0,-9,
		-9,-9,-9,-9,-9,-9,-9,-9,
		},		
		/*bb*/
		{
		-9,-9,-9,-9,-9,-9,-9,-9,
		-9,+0,+0,+0,+0,+0,+0,-9,		
		-9,+0,+4,+4,+4,+4,+0,-9,
		-9,+0,+4,+8,+8,+4,+0,-9,
		-9,+0,+4,+8,+8,+4,+0,-9,
		-9,+0,+4,+4,+4,+4,+0,-9,
		-9,+0,+0,+0,+0,+0,+0,-9,
		-9,-9,-9,-9,-9,-9,-9,-9,
		},
		/*wb*/
		{
		-9,-9,-9,-9,-9,-9,-9,-9,
		-9,+0,+0,+0,+0,+0,+0,-9,		
		-9,+0,+4,+4,+4,+4,+0,-9,
		-9,+0,+4,+8,+8,+4,+0,-9,
		-9,+0,+4,+8,+8,+4,+0,-9,
		-9,+0,+4,+4,+4,+4,+0,-9,
		-9,+0,+0,+0,+0,+0,+0,-9,
		-9,-9,-9,-9,-9,-9,-9,-9,
		},
		/*br*/
		{
		-9,-9,-9,-9,-9,-9,-9,-9,
		-9,+0,+0,+0,+0,+0,+0,-9,		
		-9,+0,+4,+4,+4,+4,+0,-9,
		-9,+0,+4,+8,+8,+4,+0,-9,
		-9,+0,+4,+8,+8,+4,+0,-9,
		-9,+0,+4,+4,+4,+4,+0,-9,
		-9,+0,+0,+0,+0,+0,+0,-9,
		-9,-9,-9,-9,-9,-9,-9,-9,
		},
		/*wr*/
		{
		-9,-9,-9,-9,-9,-9,-9,-9,
		-9,+0,+0,+0,+0,+0,+0,-9,		
		-9,+0,+4,+4,+4,+4,+0,-9,
		-9,+0,+4,+8,+8,+4,+0,-9,
		-9,+0,+4,+8,+8,+4,+0,-9,
		-9,+0,+4,+4,+4,+4,+0,-9,
		-9,+0,+0,+0,+0,+0,+0,-9,
		-9,-9,-9,-9,-9,-9,-9,-9,
		},
		/*bq*/
		{
		-2,-2,-2,-2,-2,-2,-2,-2,
		-2,+0,+0,+0,+0,+0,+0,-2,		
		-2,+0,+4,+4,+4,+4,+0,-2,
		-2,+0,+4,+8,+8,+4,+0,-2,
		-2,+0,+4,+8,+8,+4,+0,-2,
		-2,+0,+4,+4,+4,+4,+0,-2,
		-2,+0,+0,+0,+0,+0,+0,-2,
		-2,-2,-2,-2,-2,-2,-2,-2,
		},		
		/*wq*/
		{
		-2,-2,-2,-2,-2,-2,-2,-2,
		-2,+0,+0,+0,+0,+0,+0,-2,		
		-2,+0,+4,+4,+4,+4,+0,-2,
		-2,+0,+4,+8,+8,+4,+0,-2,
		-2,+0,+4,+8,+8,+4,+0,-2,
		-2,+0,+4,+4,+4,+4,+0,-2,
		-2,+0,+0,+0,+0,+0,+0,-2,
		-2,-2,-2,-2,-2,-2,-2,-2,
		},		
		/*bk*/
		{
		-9,-9,-9,-9,-9,-9,-9,-9,
		-9,+0,+0,+2,+2,+0,+0,-9,		
		-9,+0,+4,+6,+6,+4,+0,-9,
		-9,+0,+4,+8,+8,+4,+0,-9,
		-9,+0,+4,+8,+8,+4,+0,-9,
		-9,+0,+4,+6,+6,+4,+0,-9,
		-9,+0,+0,+2,+2,+0,+0,-9,
		-9,-9,-9,-9,-9,-9,-9,-9,
		},
		/*wk*/
		{
		-9,-9,-9,-9,-9,-9,-9,-9,
		-9,+0,+0,+2,+2,+0,+0,-9,		
		-9,+0,+4,+6,+6,+4,+0,-9,
		-9,+0,+4,+8,+8,+4,+0,-9,
		-9,+0,+4,+8,+8,+4,+0,-9,
		-9,+0,+4,+6,+6,+4,+0,-9,
		-9,+0,+0,+2,+2,+0,+0,-9,
		-9,-9,-9,-9,-9,-9,-9,-9,
		}
	}; 

	// fading weight for to transito throt opening
	private final static int[] op = {
		//	ending phase		opending phase
		0,	0,0,0,0,1,1,2,2,	8,8,8,8,8,8,8,8
	};
	
	// fading weight for to transit throt ending
	private final static int[] ep = {
		//	ending phase		opening phase
		0,	8,8,8,8,8,8,8,8,	1,1,0,0,0,0,0,0
	};

	// absolute piece weight
	private final static int[] pw = {
		100,100,300,300,305,305,500,500,900,900,6090,6090
	};
	
	// MVV/LAA 
	private final static int[][] cw = {
		     /*bp wp bn wn bb wb br wr bq wq bk wk*/
		/*bp*/{+0,+0,+0,+0,+0,+0,+0,+0,+0,+0,+0,+0},
		/*wp*/{+0,+0,+0,+0,+0,+0,+0,+0,+0,+0,+0,+0},
		/*bn*/{+0,+0,+0,+0,+0,+0,+0,+0,+0,+0,+0,+0},
		/*wn*/{+0,+0,+0,+0,+0,+0,+0,+0,+0,+0,+0,+0},
		/*bb*/{+0,+0,+0,+0,+0,+0,+0,+0,+0,+0,+0,+0},
		/*wb*/{+0,+0,+0,+0,+0,+0,+0,+0,+0,+0,+0,+0},
		/*br*/{+5,+5,+0,+0,+0,+0,+0,+0,+0,+0,+0,+0},
		/*wr*/{+5,+5,+0,+0,+0,+0,+0,+0,+0,+0,+0,+0},
		/*bq*/{+2,+2,+0,+0,+0,+0,+0,+0,+0,+0,+0,+0},
		/*wq*/{+2,+2,+0,+0,+0,+0,+0,+0,+0,+0,+0,+0},
		/*bk*/{+0,+0,+0,+0,+0,+0,+0,+0,+0,+0,+0,+0},
		/*wk*/{+0,+0,+0,+0,+0,+0,+0,+0,+0,+0,+0,+0}
	};	
	
	// weight of five-pawn hash structure
	private final static int[] psw = {
		/*00000*/+0,/*00001*/-4,/*00010*/-8,/*00011*/+2,
		/*00100*/-8,/*00101*/-9,/*00110*/+2,/*00111*/+3,
		/*01000*/-8,/*01001*/-8,/*01010*/-9,/*01011*/-9,
		/*01100*/+2,/*01101*/-2,/*01110*/+3,/*01111*/+5,
		/*10000*/-4,/*10001*/-3,/*10010*/-8,/*10011*/-4,
		/*10100*/-9,/*10101*/-8,/*10110*/-2,/*10111*/-2,
		/*11000*/+2,/*11001*/-4,/*11010*/-9,/*11011*/+2,
		/*11100*/+3,/*11101*/-2,/*11110*/+5,/*11111*/+6,
	};
	
	// assign values to legal moves-stack based on node
	public final static void eval(
		final Move m,
		final Node n
	) {
		/**
		//
		b = eval(n);
		
		// loop throu moves and assgin values
		for (j = m.l - 1; j == 0; j--) {			
			
			//
			g = b;
			
			// get piece
			p = n.B[m.s[j]];
			
			//
			i = p&lo;
			
			// get versus square
			v = m.v[j];
			
			// get versus square
			x = n.B[v];
					
			// 
			g += x != 0 ? cw[i][x&lo] : 0;
			
			//
			u = span[v][ne]; if (u != xx && n.B[u] != 0) { g+=1; }
			u = span[v][nw]; if (u != xx && n.B[u] != 0) { g+=1; }
			u = span[v][se]; if (u != xx && n.B[u] != 0) { g+=1; }
			u = span[v][sw]; if (u != xx && n.B[u] != 0) { g+=1; }
						
			// assign tapered value
			m.w[j] = (ow[i][v] >> ep[n.cw]) + (ew[i][v] >> op[n.cw]) + g;
		}
		*/ 
	}
	
	// eval no verbose output 
	public final static int eval(
		final Node n
	) {
		
		// return verbose off
		return eval(n,false);
	}
	
	// eval with verbose output for debug
	public final static int eval(
		final Node n, // node to eval
		final boolean debug // verbose flag true for debug
	) {
		/**
		// if node eval is enabled pass-throu else return zero forever
		if (!NODE_EVAL) { return 0; }
		
		// hash node for get position hash-key
		h = hash(n);
		
		//
		if (EVAL_CACHE && Cache.eval.has(h)) { return Cache.eval.get(h); }
		
		// empty white matirial weight
		wma = 0;
				
		//
		wow = 0;
		
		//
		wew = 0;
		
		//
		bma = 0;
		
		//
		bow = 0;
			
		//
		bew = 0;
	
		//
		wph = 0;
		
		//
		bph = 0;
						
		//
		wps = 0;
		
		//
		bps = 0;
		
		//
		for (s=0; s < 64; s++) {
			
			//
			p = n.B[s];
			
			//
			if (p == 0) { continue; }
			
			//
			i = p&lo;
			
			//
			if (mask(p,t,w)) {
				
				//
				wma += pw[i];			
				wow += ow[i][s];
				wew += ew[i][s];

				//
				switch(p) {
							
					//
					case wp:  							
						// pedone affiancati
						v = span[s][ww]; if (v != xx && n.B[v] == wp) { wps += 10; }
						v = span[s][ee]; if (v != xx && n.B[v] == wp) { wps += 10; }
						// pedone incatenati pedone o difende e attacca
						v = span[s][se]; if (v != xx) { / *w += pad[n.B[v]&pi];* / }						
						// pedone incatenati pedone o difende e attacca
						v = span[s][sw]; if (v != xx) { / *w += pad[n.B[v]&pi];* / }
						//
						wph |= 1 << (s & 7);
						break;

					//
					case wr:
						break;

					//
					case wn:
						//v = d[s][se]; if (v != xx && n.B[v]==wp) { o += 30; }
						//v = d[s][sw]; if (v != xx && n.B[v]==wp) { o += 30; }
						break;

					//
					case wb:
						break;

					//
					case wq:
						break;

					//
					case wk:						
						/ * add king's pawn shield score and evaluate part of piece blockage score
					    (the rest of the latter will be done via piece eval) * / 
						v = span[s][nn]; if (v != xx && n.B[v] == wp) wkd += 5;
						v = span[s][ne]; if (v != xx && n.B[v] == wp) wkd += 5;
						v = span[s][nw]; if (v != xx && n.B[v] == wp) wkd += 5;						
						break;
				}					
			} 
			
			//
			else {

				//
				bma += pw[i];
				bow += ow[i][s];
				bew += ew[i][s];
						
				//
				switch(p) {

					//
					case bp: 
						// pedone affiancati
						v = span[s][ww]; if (v != xx && n.B[v] == bp) { bps += 10; }
						v = span[s][ee]; if (v != xx && n.B[v] == bp) { bps += 10; }
						// pedone incatenati pedone o difende e attacca
						v = span[s][se]; if (v != xx) { /*w += pad[n.B[v]&pi];* / }
						// pedone incatenati pedone o difende e attacca
						v = span[s][sw]; if (v != xx) { /*w += pad[n.B[v]&pi];* / }
						//
						bph |= 1 << (s & 7);
						
						break;

					//
					case br:
						break;

					//
					case bn:
						//v = d[s][ne]; if (v != xx && n.B[v]==bp) { o -= 30; }
						//v = d[s][nw]; if (v != xx && n.B[v]==bp) { o -= 30; }
						break;
						
					//
					case bb:
						break;


					//
					case bq:
						break;


					//
					case bk:
						/* add king's pawn shield score and evaluate part of piece blockage score
					   (the rest of the latter will be done via piece eval) * / 
						v = span[s][ss]; if (v != xx && n.B[v] == bp) bkd += 5;
						v = span[s][se]; if (v != xx && n.B[v] == bp) bkd += 5;
						v = span[s][sw]; if (v != xx && n.B[v] == bp) bkd += 5;						
						break;					
				}				
			}										
		}
		
		//
		//w = o+(e>>i);	
		
		//
		//return n.T==wt ? w : -w;

		// merge midgame and endgame position score
		wmw = (wow >> ep[n.cw]) + (wew >> op[n.cw]);
		bmw = (bow >> ep[n.cw]) + (bew >> op[n.cw]);
		
		// to matirial add positional
		
		// pawn structure weight
		//wps = psw[wph>>3] + psw[wph&31] ;
		//bps = psw[bph>>3] + psw[bph&31];
		
		//
		wtt = wma + wps; 
		btt = bma + bps; 
		
		/* tempo bonus 
			player hava turn have 5point bonus
		* / 
		int tot = n.t==w ? wtt-btt : btt-wtt; 
		
		if (debug) {
			echo("");
			echo(lpad("",14),				rpad("White",9), rpad("Black",9));
			
			echo(lpad("Matirial:",14),		rpad(wma,9),	rpad(bma,9));
			echo(lpad("King defence:",14),	rpad(wkd,9),rpad(bkd,9));
			echo(lpad("Pawn hasing:",14),	rpad(bin(wph),9),rpad(bin(bph),9));
			echo(lpad("Pawn struct:",14),	rpad(wps,9),rpad(bps,9));
			echo(lpad("Opening:",14),		rpad(wow,9),	rpad(bow,9));
			echo(lpad("Opening k:",14),		rpad(op[n.cw],9),rpad(op[n.cb],9));
			echo(lpad("Ending:",14),		rpad(wew,9),	rpad(bew,9));
			echo(lpad("Ending k:",14),		rpad(ep[n.cw],9),rpad(ep[n.cb],9));
			echo(lpad("Position:",14),		rpad(wmw,9),rpad(bmw,9));
			echo(lpad("Partial:",14),		rpad(wtt,9),	rpad(btt,9));
			echo("---------------------------------------");
			echo(lpad("Total:",14),	rpad(tot,9), n.t==w?"for white":"for black");
		}
		
		//
		if (EVAL_CACHE) { Cache.eval.add(h, tot); }
		
		//
		return tot;*/
		return 0;
	}		
	
	public final static void move(Node n) {
	
	
	}
}
