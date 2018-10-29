
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

package org.krudo.bench;

import org.krudo.*;
import org.junit.jupiter.api.*;

import static org.krudo.Tool.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.krudo.debug.Reflect.*;

class Bench1
{
    @Test
    void testBench()
    {
        /*
        int LINE_SIZE = debug_get_declared_field_as_int(Line.class, "SIZE");

        long c = 0;

        Line l = new Line();
        Timer t = new Timer();

        for (int n = 0; n < LINE_SIZE; n++) {
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
        }

        l.i = 0;

        t.start();

        for (int r = 0; r < 10; r++)
        {
            for (int n = 0; n < LINE_SIZE; n++)
            {
                for (int i = 0; i < LINE_SIZE; i++)
                {
                    for (int j = 0; j < LINE_SIZE; j++)
                    {
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

                        c++;
                    }

                    l.i = 0;
                }
            }
        }

        long r = t.ratio(c);

        print(t.stamp, "ms", c, "loops", r, "kNPS");
        */

        /*
        1818 ms 156250000 loops 85946 kNPS
        1737 ms 156250000 loops 89953 kNPS
        */

        /*
        assertEquals(156250000,  c);
        assertEquals(true,  t.stamp < 2000);
        assertEquals(true,  r > 85000);
        */
    }
}
