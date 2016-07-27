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
    public long delta;
    
    //
    public long start;
    
    //
    public long limit;
    
    //
    public final void start()
    {
        //
        delta = 0;
        
        //
        start = time();    
    }
    
    //
    public final long delta() 
    {
        //
        return time() - start;
    }
    
    //
    public final void pause()
    {
        //
        delta = delta();
    }   
    
    //
    public final void limit(long time)
    {
        //
        limit = start + time;
    }
    
    //
    public final boolean expired()
    {
        return time() > limit; 
    }
    
    //
    public final boolean polling()
    {
        return false && time() > limit; 
    }
}
