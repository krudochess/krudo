/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.search;

import static org.krudo.tests.debug.Debug.*;
import static org.krudo.Constants.*;
import static org.krudo.Decode.*;
import static org.krudo.Tool.*;

import org.krudo.Engine;
import org.krudo.UCI;

//
public class Search3 {

    //
    public static void main(String[] args) {
        
        //
        final Engine e = new Engine();
            
        //
        //e.n.domove("e2e4 e7e5 f1a6 b7a6".split("\\s"));
        
        //e.n.startpos("1k1r4/pp1b1R2/3q2pp/4p3/2B5/4Q3/PPP2B2/2K5 b");
        
        //    
        /*
        e.s.info = new Runnable(){
            // info action
            @Override 
            public void run() {
                // print out     
                if (e.s.la == _LOG_BM_) {
                    echo(e.s.la,
                        UCI.INFO, 
                        UCI.DEPTH,        e.s.d, 
                        UCI.SCORE_CP,    e.s.lw,
                        UCI.TIME,        e.s.lt,
                        UCI.NODES,        e.s.ln,
                        UCI.PV,            i2m(e.s.pv)
                    );                        
                }
            }
        };    
        *
        /
        //dump(e.n);
        
        //dump(e.n.legals());
        
        /*_* /
        dump(e.n.legals());
        /*/        
        try {
            //e.start();
        } catch (Exception ex) {
            //dump(e.p);
            //dump(e.p.L);    
            ex.printStackTrace();
        }
        /*_*/
        
            
    }
    
}
