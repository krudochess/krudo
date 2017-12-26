
  /*\
 / + \ Krudo 0.20a - the blasphemy chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

package org.krudo.test.move;

import static org.krudo.test.debug.Info.*;
import static org.krudo.test.debug.Reflect.*;
import static org.junit.jupiter.api.Assertions.*;

import org.krudo.*;
import org.junit.jupiter.api.*;

class Move1
{
    @Test
    void testMemory()
    {
        int STACK_SIZE = debug_get_declared_field_as_int(Moves.class, "STACK_SIZE");

        Moves.init();
        assertEquals(STACK_SIZE, debug_get_moves_count());

        Move m0 = Moves.pick();        
        Move m1 = m0.twin();
        debug_print_moves_info();
        assertEquals(STACK_SIZE - 2, debug_get_moves_count());

        Moves.free(m0);
        Moves.free(m1);
        debug_print_moves_info();
        assertEquals(STACK_SIZE, debug_get_moves_count());
    }
}
