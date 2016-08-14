package org.krudo.tests.winatchess;

//
import static org.krudo.Eval.*;
import static org.krudo.Debug.*;
import static org.krudo.Tool.*;

//
import org.krudo.Node;
import org.krudo.Search;

// 
public class Winatchess2 {

    //
    public static void main(String[] args) {
        
        /*
        String[][] epd = EPDReader("./demo/org/krudo/test/winatchess/WinAtChess.epd");
        
        Node n = new Node();
        
        Search s = new Search(n);
        
        s.logEnabled = false;
         
        int o = 13, l = 3;
        
        for (int i=o-1; i<epd.length; i++) {            
            
            String[] row = epd[i];
                        
            n.startpos(row[1]);
            
            String bm = s.bestmove(4);
            
            if (!row[2].equals(bm) && i>l) {        
                s.logEnabled = true;
                echo(row[1]);
                echo(row[2],"!=",bm);
                
                //n.domove("b3b2 d2b2 c4c3 b2b4 c3c2 b4c4 d3d2 c4c6".split("\\s+"));
                
                eval(n,true);
                desc(n,n.legals().sort());
                s.start(2);
                
                dump(n);
                exit("STOP!");            
            }
            
            echo(row[0], row[2],"==",bm);                        
        }        
*/
    }    
}
