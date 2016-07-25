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
    public final void start()
    {
        delta = 0;
        start = time();    
    }
    
    //
    public final void pause()
    {
        delta = time() - start;
    }   
}
