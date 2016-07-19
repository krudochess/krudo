/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

//
import static org.krudo.Tool.*;
import static org.krudo.Constant.*;

//
public final class Engine
{       
    //
    public final Node NODE = new Node();
    
    //
    public final Search SEARCH = new Search(NODE);
        
    //
    public long wtime = 60000;
    
    //
    public long btime = 60000;
    
    //
    public int depth = 3;
    
    //
    public String bestmove;
    
    //
    public Runnable sendbestmove = () -> 
    {
        print("BESTMOVE: "+bestmove);
    };
        
    //
    public final void init()
    {
    
    }
    
    //
    public final void startpos() 
    {
        NODE.startpos();
    }
    
    //
    public final void startpos(String fen)
    {
        NODE.startpos(fen);
    }
    
    //
    public final void domove(String move) 
    {
        NODE.domove(move);
    }
    
    //
    public final void domove(String[] moves) 
    {
        NODE.domove(moves);
    }
    
    //
    public final void unmove() 
    {
        NODE.unmove();
    }
    
    // start thinking process
    public final void go() 
    {    
        //
        String m = Book.rand(NODE.h);
        
        //
        if (m != null)
        {    
            //
            sendbestmove(m);
            
            //
            return;
        }
                
        //
        long time = NODE.t == w ? (wtime / 80) + 1000 : (btime / 80) + 1000; 
        
        // call iterative deeping (wait here)
        SEARCH.start(depth);
    }
    
    //
    public final int eval() 
    {
        //
        return /*search.eval(2)*/0;
    }
    
    //
    public final void setBestMoveCallback(Runnable callback)
    {
        //bmc = callback;
    }    

    //
    public final void setSearchLogCallback(Runnable callback)
    {
        SEARCH.log = callback;
    }    
    
    //
    public final boolean isReady()
    {
        //
        NODE.legals();
        
        //
        return true;
    }
    
    //
    public final void sendbestmove(String move) 
    {
        //
        bestmove = move;
        
        //
        sendbestmove.run();
    }
}
