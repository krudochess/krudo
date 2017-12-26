
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

package org.krudo.test.capture;

import org.junit.jupiter.api.Test;
import org.krudo.Capture;
import org.krudo.Captures;

import static org.krudo.test.debug.Info.*;
import static org.krudo.test.debug.Reflect.*;
import static org.krudo.Tool.*;

class Capture1
{
    @Test
    void testCaptures()
    {
        //
        Captures.init();
        debug_captures_info();
        
        //
        int l = 2 * debug_get_declared_field_as_int(Captures.class, "STACK_SIZE");
        long[] h = new long[l];
        for (int i = 0; i < l; i++) {
            Capture c = Captures.pick();
            h[i] = uuid();
            Captures.add(h[i], c);
        }
        debug_captures_info();
        
        //
        for (int i = 0; i < l; i++) {
            Captures.del(h[i]);
        }
        debug_captures_info();
    }
}
