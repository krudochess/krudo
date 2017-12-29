
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

package org.krudo.test.moves;

import org.krudo.*;
import org.junit.jupiter.api.*;

import static org.krudo.test.debug.Info.*;
import static org.krudo.test.debug.Reflect.*;
import static org.junit.jupiter.api.Assertions.*;

class Moves1
{
    @Test
    void testMemory()
    {        
        Moves.init();

        int STACK_SIZE = debug_get_declared_field_as_int(Moves.class, "STACK_SIZE");
        Move[] temp = new Move[STACK_SIZE];
        
        for (int i = 0; i < STACK_SIZE; i++) {
            temp[i] = Moves.pick();
        }   
        debug_moves_info();
        assertEquals(0, debug_moves_get_info()[0]);

        for (int i = 0; i < STACK_SIZE; i++) {
            Moves.free(temp[i]);
        }        
        debug_moves_info();
        assertEquals(STACK_SIZE, debug_moves_get_info()[0]);

        try {
            for (int i = 0; i < STACK_SIZE + 1; i++) {
                temp[i] = Moves.pick();
            }
        } catch (java.lang.IndexOutOfBoundsException e) {
            assertEquals("-1", e.getMessage());
        }
    }
}
