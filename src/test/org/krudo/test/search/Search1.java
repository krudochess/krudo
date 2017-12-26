
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

package org.krudo.test.search;

import org.krudo.*;
import org.junit.jupiter.api.Test;

class Search1
{
    @Test
    void testSearch()
    {
        Krudo.init();
                
        Search s = new Search();

        s.startpos();

        /** /
        s.event_filter.add("ab-control-speed");
        s.event_filter.add("ab-routine-end");
        s.event_filter.add("id-loop-break");
        /**/
        
        s.start(1);
        
        //
        //PVs.info();
        //Moves.info();
        //Captures.info();
    }    
}
