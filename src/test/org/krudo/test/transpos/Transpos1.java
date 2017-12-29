
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

package org.krudo.test.transpos;

import org.junit.jupiter.api.Test;
import org.krudo.Eval;
import org.krudo.Inspect;
import org.krudo.Krudo;
import org.krudo.Search;

import static org.krudo.Tool.print;
import static org.krudo.test.debug.Debug.DEBUG_SHOW_ALGEBRIC;
import static org.krudo.test.debug.Debug.DEBUG_SHOW_MOVE_WEIGHT;

class Transpos1
{
    @Test
    void testTranspos()
    {
        Krudo.init();

        Search s = new Search();

        s.startpos();

        s.start(6);
    }
}
