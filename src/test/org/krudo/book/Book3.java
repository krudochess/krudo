
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

package org.krudo.book;

import org.junit.jupiter.api.Test;
import org.krudo.*;

import java.io.IOException;

import static org.krudo.debug.Debug.*;
import static org.junit.jupiter.api.Assertions.*;

public class Book3
{
    @Test
    void main()
    {
        debug_book_set("bin/krudo.bin");

        Node n = new Node();

        Strings m0 = Book.list(n.phk);

        assertTrue(m0.size() == 0);

        n.startpos();

        Strings m1 = Book.list(n.phk);

        assertTrue(m1.size() > 0);
    }
}
