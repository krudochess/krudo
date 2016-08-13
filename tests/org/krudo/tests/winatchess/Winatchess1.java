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
import static org.krudo.Debug.*;
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
        Node n = new Node();

        //
        Search s = new Search(n);
          
        //
        Timer t = new Timer();
        
        //
        s.sendinfo = () -> {};
        
        //
        s.sendbestmove = () -> {};
        
        //
        int d = 4;
        
        //
        long a = 3500;
        
        //
        int p = 0;
        
        //
        int h = 0;
        
        //
        int l = epd.length;
        
        //l = 1;
        
        //
        for (int i = 43; i < l; i++)
        {            
            //
            t.start();
                        
            //
            String[] row = epd[i];
            
            //                        
            //print(row[1]);
                                   
            //
            n.startpos(row[1]);
            
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
                dump(n);
                dump(n.L);                
                e.printStackTrace();
            }
            
            //
            String bm = algebric(n, s.best_move);
            
            //
            if (row[2].equals(bm)) 
            { 
                //
                p++;
                
                //
                print(row[0]+":", row[2], "==", bm, "  ("+(t.delta()/1000)+" sec.)");                                    
            }
            
            //
            else
            {
                //
                print(row[0]+":", row[2], "!=", bm); 
                
                //
                h--;
                
                //
                if (h < 0) 
                {
                    //
                    DEBUG_SHOW_MOVE_WEIGHT = true;
                    dump(n);
                    n.legals();
                    dump(n.legals);
                    exit();
                }
            }
        }        
        
        //
        print("Result:", p+"/"+l);
    }    
}
