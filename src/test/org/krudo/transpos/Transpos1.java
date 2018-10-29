
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

package org.krudo.transpos;

import org.krudo.*;
import org.junit.jupiter.api.Test;

import static org.krudo.Tool.*;

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
