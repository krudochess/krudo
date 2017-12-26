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
    public static final Strings SEARCH_SHOW_INFO = new Strings();

    //
    public static final Strings SEARCH_HIDE_INFO = new Strings();

    //
    public static final Consumer<Search> SEARCH_SEND_INFO = (search) ->
    {
        //
        int pad = 18;

        // skip hidden info
        if (SEARCH_HIDE_INFO.contains(search.event)) { return; }

        // skip not selected info
        if (SEARCH_SHOW_INFO.size() > 0 && !SEARCH_SHOW_INFO.contains(search.event)) { return; }

        //
        String info = "INFO: "+ rpad(search.event, pad);

        //
        switch (search.event)
        {
            //
            case "id-run":
                print(
                        info,
                        "d(" + search.depth_limit + ")",
                        "t(" + search.id_timer.limit + ")"
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
                        search.ab_timer.stamp+"ms",
                        search.ab_nodes+"n",
                        search.nps+"knps"
                );
                break;

            //
            case "id-end":
                print(info,
                        search.id_timer.stamp+"ms",
                        search.id_best_score,
                        desc(search.id_best_pv)
                );
                break;

            //
            case "ab-routine-end":
                print(info,
                        search.depth_index + "/" + search.depth_limit,
                        rpad(search.ab_nodes, 10) + "n",
                        rpad(search.qs_nodes, 8) + "n",
                        rpad(search.ab_timer.stamp / 1000, 6) + "s",
                        rpad(search.nps, 5) + "knps"
                );
                break;

            //
            case "ab-control-speed":
                print(info, search.nps + "knps");
                break;

            //
            default:
                print(info, search.event_message);
                break;
        }
    };

    //
    public static final Consumer<Search> SEARCH_BEST_MOVE = (search) ->
    {
        //
        print("bestmove:", search.best_move);
    };
}
