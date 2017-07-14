/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

package org.krudo.tests.winatchess;

import org.krudo.*;

import static org.krudo.Tool.*;
import static org.krudo.Describe.*;
import static org.krudo.tests.debug.Debug.*;

public class Winatchess1 
{
    public static void main(String[] args) 
    {        
        DEBUG_SHOW_MOVE_WEIGHT = true;

        String[][] epd = EPDReader("./positions/Win-at-Chess.epd");

        Krudo.init();

        Timer t = new Timer();
        
        Search s = new Search();        
        s.send_text_info = (search) -> {};
        s.send_best_move = (search) -> {};
                        
        int depth = 5;
        int time = 3500;             
        int epd_offset = 0;
        int count_success = 0;                            
        int success_ratio = 0;                
        int fail_limit = 10;

        for (int i = epd_offset; i < epd.length; i++) {            
            t.start();
                        
            String[] row = epd[i];
            //print(row[1]);
            s.node.startpos(row[1]);
            //dump(n);
                        
            try {
                s.start(depth, time);
            } catch (Exception e) {                
                dump(s.node);
                dump(s.node.L);                
                e.printStackTrace();
            }
            
            String bm = algebric(s.node, s.id_best_move);
            
            if (row[2].equals(bm)) { 
                count_success++;
                success_ratio = 100 * count_success / (i+1);
                t.stamp();
                print(
                    row[0], 
                    "("+success_ratio+"%):", 
                    row[2], 
                    "==", 
                    bm, 
                    "("+(t.stamp/1000)+" sec.)"
                );                                    
            } else {
                success_ratio = 100 * count_success / (i+1);
                print(row[0], "("+success_ratio+"%):", row[2], "!=", bm); 
                fail_limit--;

                if (fail_limit < 0) {
                    dump(s.node);
                    s.node.legals();
                    dump(s.node.legals);
                    exit();
                }
            }
        }        
                
        print("Result:", count_success+"/"+epd.length);
    }    
}
