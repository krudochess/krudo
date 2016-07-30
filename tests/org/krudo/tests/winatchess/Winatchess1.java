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
        int d = 3;
        
        //
        int l = epd.length;
        l= 1;
        
        //
        for (int i = 0; i < l; i++)
        {                 
            
            //
            String[] row = epd[i];
            
            //                        
            print(row[1]);
                                   
            //
            n.startpos(row[1]);
            
            //
            dump(n);
            
            //
            s.start(d);
                            
            //
            print(row[0], row[2],"==","\n");                        
        }        

    }    
}
