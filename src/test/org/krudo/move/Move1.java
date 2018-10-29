
  /*\
 / + \ Krudo 0.20a - the blasphemy chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

package org.krudo.move;

import static org.krudo.debug.Info.*;
import static org.krudo.debug.Reflect.*;
import static org.junit.jupiter.api.Assertions.*;

import org.krudo.*;
import org.junit.jupiter.api.*;

class Move1
{
    @Test
    void testMemory()
    {
        int size = debug_get_declared_field_as_int(Moves.class, "SIZE");

        Moves.init();
        assertEquals(size, debug_moves_get_info()[0]);

        Move m0 = Moves.pick();        
        Move m1 = m0.twin();
        debug_moves_info();
        assertEquals(size - 2, debug_moves_get_info()[0]);

        Moves.free(m0);
        Moves.free(m1);
        debug_moves_info();
        assertEquals(size, debug_moves_get_info()[0]);
    }
}
