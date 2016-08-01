/**
 * Krudo 0.16a 
 * by Francesco Bianco <bianco@javanile.org>
 */

// testing class "Line" performance
package org.krudo.tests.bench;

//
import org.krudo.Line;

//
import static org.krudo.Tool.*;

//
public class Bench1 
{
    //
    public static void main(String[] args)
    {
        //
        Line l = new Line();

        //
        long c = 0; 
        
        //
        long t = time();
        
        //
        for (int r = 0; r<10; r++) 
        {
            //
            for (int n=0; n<300; n++)
            {
                //
                l.store(           
                    rand(),
                    rand(),
                    rand(),
                    rand(),
                    rand(),
                    rand(),
                    rand(),
                    rand(),
                    rand()         
                );

                //
                for (int i=0; i<300; i++)
                {
                    //
                    for (int j=0; j<300; j++) 
                    {
                        //
                        l.store(                         
                            l.p[i],
                            l.s[i],
                            l.v[i],
                            l.x[i],
                            l.k[i],
                            l.e[i],
                            l.c[i],
                            l.phk[i],
                            l.mhk[i]
                        );

                        //
                        c++;
                    }
                }

                //
                for (int i=0; i<300; i++)
                {
                    //
                    for (int j=0; j<300; j++) 
                    {
                        //
                        l.store(                   
                            l.p[i],
                            l.s[i],
                            l.v[i],
                            l.x[i],
                            l.k[i],
                            l.e[i],
                            l.c[i],
                            l.phk[i],
                            l.mhk[i]
                        );

                        //
                        c++;
                    }
                }
            }
        }
        
        //
        print(time() - t, "ms", c, "loops");
        
        /*
        2424 ms 540000000 loops
        2451 ms 540000000 loops
        */
    }    
}
