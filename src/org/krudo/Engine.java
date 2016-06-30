/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

//
import org.krudo.util.Book;

//
import static org.krudo.Constant.*;
import static org.krudo.util.Tool.*;

//
public final class Engine {    
    
    //
    public final Node node = new Node();
    
    //
    public final Search search = new Search(node);
        
    //
    private long wtime = 60000;
    
    //
    private long btime = 60000;
    
    //
    public String bm;
    
    // best move default callback
    public Runnable bmc = new Runnable() {
        @Override
        public void run() {
            
        }
    };
    
    //
    public final void init() {
    
    }
    
    //
    public final void startpos() {
        node.startpos();
    }
    
    //
    public final void startpos(String fen) {
        node.startpos(fen);
    }
    
    //
    public final void domove(String move) {
        node.domove(move);
    }
    
    //
    public final void domove(String[] moves) {
        node.domove(moves);
    }
    
    //
    public final void unmove() {
        node.unmove();
    }
    
    //
    public final void go(String wtime0, String btime0) {
    
        //
        go(toLong(wtime0), toLong(btime0));        
    }
    
    //
    public final void go(long wtime0, long btime0) {
    
        //
        wtime = wtime0;
        
        //
        btime = btime0;
        
        //
        go();
    }
    
    // start thinking process
    public final void go() {
        
        //
        String m = Book.rand(node);
        
        //
        if (m != null) {
            
            //
            bm = m;        
            
            //
            bmc.run();
            
            //
            return;
        }
        
        //
        Cache.legals.clr();
        
        //
        long time = node.t == w ? (wtime / 80) + 1000 : (btime / 80) + 1000; 
        
        // call iterative deeping (wait here)
        search.start(4);
    }
    
    //
    public final int eval() {

        //
        return search.eval(2);
    }
    
    //
    public final void setBestMoveCallback(Runnable callback) {
        bmc = callback;
    }    

    //
    public final void setSearchLogCallback(Runnable callback) {
        search.log = callback;
    }    
    
    //
    public final boolean isReady() {
    
        //
        node.legals();
        
        //
        return true;
    }
}
