/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// krudo package
package org.krudo;

// required static class

import java.util.function.Consumer;

import static org.krudo.Describe.desc;
import static org.krudo.Tool.print;
import static org.krudo.Tool.rpad;

//
public final class Inspect
{
    //
    public static final Strings
    SEARCH_INFO_SHOW = new Strings(),
    SEARCH_INFO_HIDE = new Strings();

    //
    public static final
    Consumer<Search> SEARCH_INFO = (search) ->
    {
        //
        int pad = 18;

        // skip hidden info
        if (SEARCH_INFO_HIDE.contains(search.info)) { return; }

        // skip not selected info
        if (SEARCH_INFO_SHOW.size() > 0 && !SEARCH_INFO_SHOW.contains(search.info)) { return; }

        //
        String info = "INFO: "+ rpad(search.info, pad);

        //
        switch (search.info)
        {
            //
            case "id-run":
                print(
                        info,
                        "d(" + search.depth_limit + ")",
                        "t(" + search.timer.limit + ")"
                );
                break;

            //
            case "id-loop-run":
                print(
                        info,
                        "d(" + search.depth_index + "/" + search.depth_limit+")"
                );
                break;

            //
            case "id-loop-break":
                print(info, "event break");
                break;

            //
            case "id-loop-end":
                print(info,
                        search.depth_index+"/"+search.depth_limit,
                        search.id_best_score,
                        desc(search.id_best_pv),
                        search.timer.stamp+"ms",
                        search.nodes+"n",
                        search.nps+"knps"
                );
                break;

            //
            case "id-end":
                print(info,
                        search.timer.stamp+"ms",
                        search.id_best_score,
                        desc(search.id_best_pv)
                );
                break;

            //
            case "ab-routine-end":
                print(info,
                        search.depth_index + "/" + search.depth_limit,
                        rpad(search.nodes, 10) + "n",
                        rpad(search.nodes, 8) + "n",
                        rpad(search.timer.stamp / 1000, 6) + "s",
                        rpad(search.nps, 5) + "knps"
                );
                break;

            //
            case "ab-control-speed":
                print(info, search.nps + "knps");
                break;

            //
            default:
                print(info);
                break;
        }
    };

    //
    public static final
    Consumer<Search> SEARCH_MOVE = (search) ->
    {
        //
        print("bestmove:", search.move);
    };
}
