
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
}
