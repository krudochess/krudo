/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// 
package org.krudo.tests.winatchess;

//
import org.krudo.*;

//
import static org.krudo.Tool.*;
import static org.krudo.tests.debug.Debug.*;
import static org.krudo.Describe.*;

// 
public class Winatchess1 
{
    //
    public static void main(String[] args) 
    {        
        //
        String[][] epd = EPDReader("./tests/org/krudo/tests/winatchess/WinAtChess.epd");

        //
        Krudo.init();
        
        //
        Search s = new Search();
          
        //
        Timer t = new Timer();
        
        //
        s.send_text_info = (search) -> {};
        
        //
        s.send_best_move = (search) -> {};
        
        //
        int d = 5;
        
        //
        long a = 3500;
        
        //
        int p = 0;
        
        //
        int o = 0;
        
        //
        int r = 0;
        
        //
        int h = 400;
        
        //
        int l = epd.length;
        
        //l = 1;
        
        //
        for (int i = 0; i < (l-o); i++)
        {            
            //
            t.start();
                        
            //
            String[] row = epd[i + o];
            
            //                        
            //print(row[1]);
                                   
            //
            s.node.startpos(row[1]);
            
            //
            //dump(n);
            
            //
            try 
            {
                s.start(d, a);
            } 
            
            //
            catch (Exception e)
            {                
                dump(s.node);
                dump(s.node.L);                
                e.printStackTrace();
            }
            
            //
            String bm = algebric(s.node, s.id_best_move);
            
            //
            if (row[2].equals(bm)) 
            { 
                //
                p++;
                
                //
                r = 100 * p / (i+1);
                
                //
                t.stamp();
                
                //
                print(row[0]+" ("+r+"%):", row[2], "==", bm, "  ("+(t.stamp/1000)+" sec.)");                                    
            }
            
            //
            else
            {
                //
                r = 100 * p / (i+1);
                
                //
                print(row[0]+" ("+r+"%):", row[2], "!=", bm); 
                
                //
                h--;
                
                //
                if (h < 0) 
                {
                    //
                    DEBUG_SHOW_MOVE_WEIGHT = true;
                    dump(s.node);
                    s.node.legals();
                    dump(s.node.legals);
                    exit();
                }
            }
        }        
        
        //
        print("Result:", p+"/"+l);
    }    
}
