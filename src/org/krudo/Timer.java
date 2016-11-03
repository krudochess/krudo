/**
 * Krudo 0.16a 
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

//
import static org.krudo.Tool.*;

//
public class Timer 
{
    //
    private long start;
    
    //
    private long bound;
    
    //
    private long rerun;
    
    //
    public long stamp;
        
    //
    public long limit;

    //
    public long delay;

    //
    public final void setTimeout(long time)
    {
        //
        limit = time;
                
        //
        bound = start + limit;
    }
    
    //
    public final void setPolling(long time)
    {                
        //
        delay = time;
        
        //
        rerun = start + delay;
    }
    
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
        return stamp > 0 ? size / stamp : 0;
    }
        
    //
    public final boolean expired()
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
}
