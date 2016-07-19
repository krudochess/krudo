/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.engine;

//
import static org.krudo.Debug.*;

//
import org.krudo.Engine;
import org.krudo.Moves;

// 
public class Engine3
{
    //
    public static void main(String[] args) 
    {
        //
        Moves.init();
        
        // 
        Engine e = new Engine();
        
        //
        e.startpos();
        
        //
        e.depth = 3;
    
        //
        e.go();
    }    
}
