
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

// krudo package
package org.krudo;

// require non-static class
import java.util.function.Consumer;

// require static class methods
import static org.krudo.Tool.*;
import static org.krudo.Describe.*;

// inspect interl value for debug and development
public final class Inspect
{
    //
    public static final Strings
    SEARCH_INFO_SHOW = new Strings(),
    SEARCH_INFO_HIDE = new Strings();

    //
    public static final
    Consumer<Search> SEARCH_INFO = (s) ->
    {
        // skip hidden info
        if (SEARCH_INFO_HIDE.contains(s.info)) { return; }

        // skip not selected info
        if (SEARCH_INFO_SHOW.size() > 0 && !SEARCH_INFO_SHOW.contains(s.info)) { return; }

        //
        switch (s.info)
        {
            //
            case "+go":
                print(
                    "info:",
                    lpad(s.info, 10),
                    "d=" + s.depth_limit,
                    "t=" + s.timer.limit
                );
                break;

            //
            case "-go":
                print(
                    "info:",
                    lpad(s.info, 10),
                    "s=" + s.id_score,
                    "t=" + s.timer.stamp,
                    desc(s.id_pv)
                );
                break;

            //
            case "+id":
                print(
                    "info:",
                    lpad("  " + s.info, 10),
                    "d=" + s.depth_index + "/" + s.depth_limit
                );
                break;

            //
            case "#id":
                print(
                    "info:",
                    lpad("  " + s.info, 10),
                    "event break"
                );
                break;

            //
            case "-id":
                print(
                    "info:",
                    lpad("  " + s.info, 10),
                    "d="+s.depth_index+"/"+s.depth_limit,
                    s.id_score,
                    desc(s.id_pv),
                    s.timer.stamp+"ms",
                    s.nodes+"n",
                    s.nps+"knps"
                );
                break;

            //
            case "+ab":
                print(
                    "info:",
                    lpad("    " + s.info, 10)
                );
                break;

            //
            case "-ab":
                print(
                    "info:",
                    lpad("    " + s.info, 10),
                    "d=" + s.depth_index + "/" + s.depth_limit,
                    "n=" + s.nodes,
                    "t=" + s.timer.stamp,
                    "knps=" + s.nps
                );
                break;

            //
            case "!ct":
                print(
                    "info:",
                    lpad("    " + s.info, 10),
                    s.nps + "knps"
                );
                break;

            //
            default:
                print(
                    "info:",
                    lpad(s.info, 10)
                );
                break;
        }
    };

    //
    public static final
    Consumer<Search> SEARCH_MOVE = (search) ->
    {
        //
        print("move:", search.move);
    };

    //
    public static final
    Consumer<int[]> EVAL_INFO = (scores) ->
    {
        //
        print("                ", "white        black");
        print("   pawn struct: ", bin(scores[1], 8), "   ", bin(scores[0], 8));
        print("--");
        print("  opening pawn: ", scores[17],     "      ", scores[16]);
        print("opening knight: ", scores[19],     "      ", scores[18]);
        print("opening bishop: ", scores[21],     "      ", scores[20]);
        print("  opening rook: ", scores[23],     "      ", scores[22]);
        print(" opening queen: ", scores[25],     "      ", scores[24]);
        print("  opening king: ", scores[27],     "      ", scores[26]);
        print("--");
        print("   ending pawn: ", scores[29],     "      ", scores[28]);
        print(" ending knight: ", scores[31],     "      ", scores[30]);
        print(" ending bishop: ", scores[33],     "      ", scores[32]);
        print("   ending rook: ", scores[35],     "      ", scores[34]);
        print("  ending queen: ", scores[37],     "      ", scores[36]);
        print("   ending king: ", scores[39],     "      ", scores[38]);
        print("--");
        print(" opening score: ", scores[41],     "      ", scores[40]);
        print("  ending score: ", scores[43],     "      ", scores[42]);
        print(" tamered score: ", scores[45],     "      ", scores[44]);
        print("material score: ", scores[15],     "      ", scores[14]);
        print("absolute score: ", scores[47],     "      ", scores[46]);
        print("   total score: ", scores[49],     "      ", scores[48]);
    };
}
