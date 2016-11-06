/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// krudo main package
package org.krudo;

// required static class
import static org.krudo.Tool.*;
import static org.krudo.Constant.*;

// engine class aim brain-mentor
public final class Engine
{       
    // search processor-algorythm
    public final Search search = new Search();
        
    // white available time
    public long wtime = 60000;
    
    // black available time
    public long btime = 60000;
    
    // depth limit for search algotimh
    public int depth = 20;
    
    // use opening book
    public boolean book = false;
    
    //
    public String info;
    
    //
    public String bestmove;

    //
    public int bestscore;

    //
    public int lastscore;
        
    //
    public Engine() { }
    
    //
    public Engine(String fen) 
    {
        //
        search.node.startpos(fen);
    }
           
    //
    public final void init()
    {
    
    }
    
    //
    public final void startpos() 
    {
        //
        search.node.startpos();
    }
    
    //
    public final void startpos(String fen)
    {
        //
        search.node.startpos(fen);
    }
    
    //
    public final void domove(String move) 
    {
        //
        search.node.domove(move);
    }
    
    //
    public final void domove(String[] moves) 
    {
        //
        search.node.domove(moves);
    }
    
    //
    public final void domove(String[] moves, int offset) 
    {
        //
        search.node.domove(moves, offset);
    }
    
    //
    public final void unmove() 
    {
        search.node.unmove();
    }
    
    // start thinking process
    public final void go() 
    {   
        //
        if (book) 
        {
            //
            String m = Book.rand(search.node.phk);

            //
            if (m != null)
            {    
                //
                search.sendbestmove(m);

                //
                return;
            }
        } 
        
        //
        long time = search.node.t == w ? (wtime / 50) + 500 : (btime / 50) + 500; 
        
        // call iterative deeping (wait here)
        search.start(5, 6000);
    }
    
    //
    public final int eval() 
    {
        //
        return /*search.eval(2)*/0;
    }
    
    //
    public final boolean isReady()
    {
        //
        search.node.legals();
        
        //
        return true;
    }        
}
