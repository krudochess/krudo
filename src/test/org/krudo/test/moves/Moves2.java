
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

package org.krudo.test.moves;

import org.krudo.*;
import org.junit.jupiter.api.Test;

import static org.krudo.test.debug.Info.*;

class Moves2
{
    @Test
    void testMemoryFactor0()
    {
        Config.MEMORY_FACTOR = 1;

        Moves.init();
        Legals.init();
        Captures.init();

        debug_moves_info();
        debug_legals_info();
        debug_captures_info();
    }
}
