/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.console;

//
import static org.krudo.tests.debug.Debug.*;
import static org.krudo.Tool.*;

//
import org.krudo.Console;

// 
public class Console1 
{
    //
    public static void main(String[] args) 
    {     
        //
        Console c = new Console();

        c.print("start");
            
        for(int i=0;i<3;i++) {
            try {        
                c.input();
                c.print("start divion");
                int a = 5 / 0;
            } catch (Exception e) {
                c.error(e);
            }
        }
        
        c.error(new Exception("Ciao"));
        
        c.print("exit");
        
        c.close();    
    
        echo ("----");    
        //echo (c.log());
    }    
}
