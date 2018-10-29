
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

// krudo package
package org.krudo;

import static org.krudo.Decode.m2s;
import static org.krudo.Tool.*;

// transpos is a TT element
public final class Transpos
{
    public static final int
    MAX = 1,
    MIN = 2,
    HARD = 3,
    SOFT = 4,
    EVAL = 5;

    public int
    mm,
    cut_loop,
    cut_save,
    score,
    d,
    a,
    b,
    s,
    v,
    k;

    public final void hard(int s0)
    {
        cut_loop = HARD;
        score = s0;
    }

    public final void soft(int s0, Move m, int i)
    {
        cut_loop = SOFT;
        s = m.s[i];
        v = m.v[i];
        k = m.k[i];
        score = s0;
    }
}
