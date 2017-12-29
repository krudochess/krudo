
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

package org.krudo.test.search;

import org.krudo.*;
import org.junit.jupiter.api.Test;

import static org.krudo.Tool.*;

class Search8
{
    @Test
    void testTranspos()
    {
        Krudo.init();

        Search s = new Search();

        s.startpos();

        s.start(6);

        print(s.id_score);
    }
}
