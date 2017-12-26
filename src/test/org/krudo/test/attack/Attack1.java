
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

package org.krudo.test.attack;

import org.krudo.*;
import org.junit.jupiter.api.*;

import static org.krudo.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

class Attack1
{
    @Test
    void testAttack()
    {
        Node n = new Node();

        n.startpos();
        n.domove("d2d3");

        assertEquals(true, n.white_attack(e4));
        assertEquals(true, n.black_attack(f6));
        assertEquals(false, n.white_attack(e8));
        assertEquals(false, n.black_attack(e1));
    }
}
