/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.engine;

//

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
        e.depth = 4;
    
        //
        e.go();
    }    
}
