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
    public long stamp;
    
    //
    public long start;
    
    //
    public long limit;

    //
    public final void limit(long time)
    {
        //
        limit = start + time;
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
        return time() > limit; 
    }
    
    //
    public final boolean polling()
    {
        return false && time() > limit; 
    }
}
