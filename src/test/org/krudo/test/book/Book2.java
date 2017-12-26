
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

package org.krudo.test.book;

import org.krudo.*;
import org.junit.jupiter.api.Test;

import static org.krudo.test.debug.Dump.*;
import static org.krudo.test.debug.Debug.*;
import static org.junit.jupiter.api.Assertions.*;

class Book2
{
    @Test
    void testBook()
    {
        debug_book_set("bin/krudo.bin");

        Moves.init();

        Node n = new Node();
        
        n.startpos();
        
        String m = Book.rand(n.phk);

        while (m != null)
        {   
            n.domove(m);

            m = Book.rand(n.phk);
        }
        
        dump(n);
        dump(n.L);

        assertEquals(true, n.L.i > 1);
    }    
}
