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
    public int depth = 10;
    
    //
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
    public Runnable sendinfo = () -> 
    {
        print("INFO: "+info);
    };
    
    //
    public Runnable sendbestmove = () -> 
    {
        print("BESTMOVE: "+bestmove);
    };
    
    //
    public Engine()
    {
        //
        SEARCH.sendinfo = () -> {
            sendinfo(SEARCH.best_move);
        };
        
        //
        SEARCH.sendbestmove = () -> {
            sendbestmove(SEARCH.best_move, SEARCH.best_score);
        };
    }
    
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
        if (book) 
        {
            //
            String m = Book.rand(NODE.phk);

            //
            if (m != null)
            {    
                //
                sendbestmove(m, 0);

                //
                return;
            }
        } 
        
        //
        long time = NODE.t == w ? (wtime / 80) + 1000 : (btime / 80) + 1000; 
        
        // call iterative deeping (wait here)
        SEARCH.start(depth, 100000);
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
        NODE.legals();
        
        //
        return true;
    }
    
    //
    public final void sendinfo(String i) 
    {
        //
        info = i;
        
        //
        sendinfo.run();
    }
    
    //
    public final void sendbestmove(String move, int score) 
    {       
        //
        if (lastscore - score > 150) {
            Krudo.CONSOLE.error("Engine score stop: "+(lastscore - score)+" "+lastscore+" "+score);
            Krudo.CONSOLE.close();
            exit();
        }
        
        //
        bestmove = move;
                    
        //
        lastscore = bestscore;
         
        //
        bestscore = score;
        
        //
        sendbestmove.run();
    }
}
