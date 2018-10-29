
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

package org.krudo.utilities;

import org.krudo.*;

import static org.krudo.Tool.*;
import static org.krudo.test.debug.Dump.*;

public class EvalUtility
{
    public static void main(String[] args)
    {
        if (args.length > 0) {
            print("Fen:", args[0]);

            Eval.init();
            Eval.send_info = Inspect.EVAL_INFO;

            Node n = new Node(args[0]);

            dump(n);
            Eval.node(n);
        }
    }
}






