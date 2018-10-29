
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

package org.krudo.test.bench;

import org.junit.jupiter.api.*;

import static org.krudo.Tool.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.krudo.test.debug.Debug.*;

class Bench3
{
    @Test
    void testSlower()
    {
        long t = time();
        
        slower(100);

        assertEquals(true, time() - t > 100);
    }
}
