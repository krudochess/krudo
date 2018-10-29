
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

package org.krudo.bench;

import org.junit.jupiter.api.*;

import static org.krudo.Tool.*;
import static org.junit.jupiter.api.Assertions.*;

public class Bench2
{
    @Test
    void testMoveSplittedArray()
    {
        /*
        int[] a = new int[100];
        int[] b = new int[100];
        int[] c = new int[100];

        long splitting_array;
        long collected_array;

        int n,m =0,x,y,o = 0;
        
        long t ;

        t = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++)
        {
            x = rand(0,1000) + o;
            y = rand(0,1000);
            n = rand(0,99);
            a[n] = x << 16 | y;
            n = rand(0,99);
            x = a[n]>>16;
            y = a[n]&4;
            o = x + y;
        }
        collected_array = System.currentTimeMillis() - t;

        t = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++)
        {
            x = rand(0,1000) + m;
            y = rand(0,1000);
            n = rand(0,99);
            b[n] = x;
            c[n] = y;
            n = rand(0,99);
            x = b[n];
            y = c[n];
            m = x + y;
        }
        splitting_array = System.currentTimeMillis() - t;

        print(splitting_array, collected_array);

        assertEquals(true, splitting_array < collected_array);
        */
    }
}
