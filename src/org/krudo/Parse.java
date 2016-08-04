/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

//
import static org.krudo.Constant.*;

// class 
public final class Parse
{    
    //
    public static final int parse_square(final String s) 
    {
        //
        return parse_square(s.charAt(0), s.charAt(1));
    }
      
    //
    public static final int parse_square(final String s, final int k) 
    {
        //
        return parse_square(s.charAt(0), s.charAt(1));
    }
    
	// row and column to integer
	public static final int parse_square(final char c, final char r) 
    {
		//
		return (int) (r - '1') * 8 + c - 'a';
	}
    
    // row and column to integer
	public static final int parse_square(final int c, final int r)
    {		
		//
		return r * 8 + c;
	}

    // king-of-move to integer
    public static final int parse_kind_of_move(
        final String m,
        final int p, 
        final int s, 
        final int v,
        final int x,
        final int t
    ) {
        //
        final int r = s >> 3;
        
        // pronotion move
        if (m.length() > 4)
        {
            switch (m.charAt(4))
            {
                case 'q': return t == w ? WQPM : BQPM;
                case 'r': return t == w ? wrpm : brpm;
                case 'b': return t == w ? wbpm : bbpm;
                case 'n': return t == w ? wnpm : bnpm;
                default: return MOVE;
            }
        } 
        
        //
        else if (p == wk && s == e1 && (v == g1 || v == h1))
        {
            return KSCA;
        } 
        
        //
        else if (p == wk && s == e1 && (v == c1 || v == a1)) 
        {
            return QSCA;
        }
        
        //
        else if (p == bk && s == e8 && (v == g8 || v == h8))
        {
            return KSCA;
        } 
        
        //
        else if (p == bk && s == e8 && (v == c8 || v == a8))
        {
            return QSCA;
        }
        
        //
        else if (p == wk || p == bk) 
        {
            return KMOV;
        } 
        
        //
        else if (p == wr && (s == h1 || s == a1))
        {
            return rmov;
        }
        
        //
        else if (p == br && (s == h8 || s == a8)) 
        {
            return rmov;
        }
        
        //
        else if (p == wp && r == 1 && (v >> 3) == 3) 
        {
            return PDMO;
        } 
        
        //
        else if (p == wp && r == 4 && x == 0 && ((v - s) == 9 || (v - s) == 7))
        {
            return ecap;
        } 
        
        //
        else if (p == bp && r == 6 && (v >> 3) == 4) 
        {
            return PDMO;
        } 
        
        //
        else if (p == bp && r == 3 && x == 0 && ((s - v) == 9 || (s - v) == 7)) 
        {
            return ecap;
        } 
        
        //
        else 
        {
            return MOVE;
        }
    }
}

