
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

package org.krudo.captures;

import org.krudo.*;
import org.junit.jupiter.api.Test;

import static org.krudo.test.debug.Info.*;

class Captures1
{
    @Test
    void testCaptures()
    {
        Captures.init();

        int[] info = debug_captures_get_info();

        debug_captures_info();

        for (int i = 0; i < info[1]; i++)
        {
            Captures.add(i, Captures.pick());
        }

        Captures.pick();

        debug_captures_info();
    }
}
