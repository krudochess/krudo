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
    public long stamp;
        
    //
    public long limit;

    //
    public final void limit(long time)
    {
        //
        limit = time;
                
        //
        bound = start + limit;
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
        return false && time() > bound; 
    }
}
