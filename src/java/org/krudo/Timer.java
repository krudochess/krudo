
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

//
package org.krudo;

//
import static org.krudo.Tool.*;

//
public final class Timer 
{
    private long
    start,
    bound,
    rerun;

    public long
    stamp,
    limit,
    delay;

    //
    public final void start()
    {
        //
        stamp = 0;
        
        //
        start = time();    
    }
      
    //
    public final void stamp()
    {
        //
        stamp = time() - start;
    }   
        
    //
    public final long ratio(long size)
    {
        //
        stamp();
        
        //
        return stamp > 0 ? size / stamp : 0;
    }

    //
    public final void setTimeout(long time)
    {
        //
        limit = time;

        //
        bound = start + limit;
    }

    //
    public final boolean timeout()
    {
        return time() > bound;
    }
    
    //
    public final boolean polling()
    {
        //
        if (time() < rerun) { return false; }
        
        //
        rerun += delay; 
        
        //
        return true; 
    }

    //
    public final void setPolling(long time)
    {
        //
        delay = time;

        //
        rerun = start + delay;
    }
}
