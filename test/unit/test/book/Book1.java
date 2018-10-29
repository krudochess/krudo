
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

package org.krudo.test.book;

import org.krudo.*;
import org.junit.jupiter.api.Test;

import static org.krudo.Tool.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.krudo.test.debug.Debug.*;

class Book1
{
    @Test
    void testBook()
    {
        long[] nodes = new long[] 
        {
            // starting position
            0x463b96181691fc9cL,

            // position after e2e4
            0x823c9b50fd114196L,

            // position after e2e4 d7d5
            0x0756b94461c50fb0L,

            // position after e2e4 d7d5 e4e5
            0x662fafb965db29d4L,

            // position after e2e4 d7d5 e4e5 f7f5
            0x22a48b5a8e47ff78L,
        };

        debug_book_set("bin/krudo.bin");

        print(debug_book_get());

        Book.open();

        for (int i = 0; i < nodes.length; i++)
        {
            long node = nodes[i];

            if (i < 3) {
                assertTrue(Book.list(node).size() > 0);
            } else {
                assertTrue(Book.list(node).size() == 0);
            }
        }

        Book.exit();
    }
}
