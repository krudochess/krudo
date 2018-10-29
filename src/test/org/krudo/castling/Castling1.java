
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

package org.krudo.castling;

import org.krudo.*;
import org.junit.jupiter.api.Test;

import static org.krudo.Tool.*;
import static org.krudo.test.debug.Dump.*;
import static org.junit.jupiter.api.Assertions.*;

class Castling1
{
    @Test
    void testCastling()
    {
        Moves.init();

        Node n = new Node();

        n.startpos();

        n.domove(split("e2e4 g8f6 g1f3 e7e6 f1c4 f8c4"));

        n.legals();

        dump(n, n.legals);

        //assertTrue(contains(n.legals, "e1g1"));
    }    
}
