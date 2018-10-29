
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

package org.krudo.search;

import org.krudo.*;
import org.junit.jupiter.api.Test;

import static org.krudo.Tool.*;

class Search8
{
    @Test
    void testTranspos()
    {
        /*
        Krudo.init();

        Search s = new Search();

        s.startpos();

        s.node.domove("g1f3");

        s.start(7);

        /*
        print(s.id_score);

        dump(s.node);
        print("Score:", Eval.node(s.node));

        dump(s.id_pv);

        s.node.domove(s.id_pv, 0);

        dump(s.node);
        print("Score:", Eval.node(s.node));
        print("Fen:", Fen.node(s.node));

        s=0 t=921 g1f3 b8c6 b1c3 g8f6
        s=0 t=954 g1f3 b8c6 b1c3 g8f6

        s=12 t=10039 d2d4 e7e6 d1d3 b8c6 b1c3 d8f6
        s=14 t=9251 d2d4 d7d5 c1f4 e7e6 d1d3 f8b4

        s=12 t=9787 d2d4 e7e6 d1d3 b8c6 b1c3 d8f6



        --> s=-21 t=12826 e7e5 g1f3 b8c6 b1c3 g8f6 f1c4
            s=-21 t=10502 e7e5 g1f3 b8c6 b1c3 g8f6 f1c4

        --> s=-14 t=44164 d7d5 b1c3 e7e6 d2d4 b8c6 d1d3 d8d6

        s=-14 t=37300 d7d5 b1c3 e7e6 d2d4


        s=-4 t=3324 b8c6 b1c3 g8f6 d2d4 e7e6
        s=-4 t=2946 b8c6 b1c3 g8f6 d2d4 e7e6
        s=-4 t=2570 b8c6 b1c3 g8f6 d2d4 e7e6
        s=-4 t=3905 b8c6 b1c3 g8f6 d2d4 e7e6
        s=-4 t=3564 b8c6 b1c3 g8f6 d2d4 e7e6



        s=-16 t=53551 d7d5 d2d4 d8d6 d1d3 g7g6 c1f4

        */

        //print(TT.success,TT.queries);

    }
}
