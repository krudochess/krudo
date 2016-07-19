/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.console;

// required static class
import static org.krudo.Debug.*;
import static org.krudo.Tool.*;
import static org.krudo.Constant.*;

// required non-static class
import org.krudo.Book;
import org.krudo.Eval;
import org.krudo.Node;

// 
public class Eval1    
{
    //
    public static void main(String[] args) 
    {    
        echo(a1);
        
        //
        //String p = "1k1r4/pp1b1R2/3q2pp/4p3/2B5/4Q3/PPP2B2/2K5 b";
        //String p = "k7/8/8/8/8/8/p6P/7K w";
        //String p = "1kr5/3n4/q3p2p/p2n2p1/PppB1P2/5BP1/1P2Q2P/3R2K1 w";
        //String p = "8/pppppppp/8/8/8/8/PPPPPPPP/8 w";
        String p = "8/8/8/8/8/8/1PPPPPP1/8 w";
        
    
        // 
        Node n = new Node();
        
        n.startpos(p);
        
        //n.domove("e2e4");
        //n.domove("a7a6");
        //n.domove("d2d4");
        
        //
        dump(n);
        
        //
        Eval.node(n);
    }    
}
