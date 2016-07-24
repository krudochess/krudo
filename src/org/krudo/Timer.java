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
    public long time;
    
    //
    public long temp;
    
    //
    public final void start()
    {
        time = 0;
        temp = time();    
    }
    
    //
    public final void pause()
    {
        time = time() - temp;
    }   
}
