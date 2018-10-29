/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.engine;

//
import org.krudo.Engine;

// 
public class Engine1 {

    //
    public static void main(String[] args) {
                
        // 
        //Engine engine = new Engine();
        
        /*\
        +----+
        | GO |
        +----+
        
        start calculating on the current position set up with the "position" command.
        There are a number of commands that can follow this command, all will be sent in the same string.
        If one command is not send its value should be interpreted as it would not influence the search.
        * searchmoves  .... 
            restrict search to this moves only
            Example: After "position startpos" and "go infinite searchmoves e2e4 d2d4"
            the engine should only search the two moves e2e4 and d2d4 in the initial position.
        * ponder
            start searching in pondering mode.
            Do not exit the search in ponder mode, even if it's mate!
            This means that the last move sent in in the position string is the ponder move.
            The engine can do what it wants to do, but after a "ponderhit" command
            it should execute the suggested move to ponder on. This means that the ponder move sent by
            the GUI can be interpreted as a recommendation about which move to ponder. However, if the
            engine decides to ponder on a different move, it should not display any mainlines as they are
            likely to be misinterpreted by the GUI because the GUI expects the engine to ponder
           on the suggested move.
        * wtime 
            white has x msec left on the clock
        * btime 
            black has x msec left on the clock
        * winc 
            white increment per move in mseconds if x > 0
        * binc 
            black increment per move in mseconds if x > 0
        * movestogo 
          there are x moves to the next time control,
            this will only be sent if x > 0,
            if you don't get this and get the wtime and btime it's sudden death
        * depth 
            search x plies only.
        * nodes 
           search x nodes only,
        * mate 
            search for a mate in x moves
        * movetime 
            search exactly x mseconds
        * infinite
            search until the "stop" command. Do not exit the search without being told so in this mode!        
        \*/
                
        //
        //long wtime = 1000;
        
        //
        //long btime = 1000;
        
        //
        //engine.go();
    }    
}
