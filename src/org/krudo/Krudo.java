/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// root package
package org.krudo;

// required static class
import static org.krudo.Constant.*;
import static org.krudo.Tool.*;

// main class entry point for java application
public final class Krudo 
{   
    // engine store chess game status is chess brain interface         
    public final static Engine ENGINE = new Engine();
    
    // console interface with "standard i/o" management
    public final static Console CONSOLE = new Console(); 
                        
    // entry-point for console application
    public static void main(String[] args) 
    {        
        // prepare and start input loop of engine 
        try 
        {    
            // initialize console
            CONSOLE.start(path("Krudo.log"));

            // credits message
            CONSOLE.print("Krudo 0.16a by Francesco <bianco@javanile.org>");

            //
            init();
            
            //
            loop();                    
        } 
        
        // exception catched print in console
        catch (Throwable e) 
        {            
            //
            CONSOLE.error(e);            
        } 
                
        // exit and close console
        finally 
        {            
            //
            CONSOLE.close();            
        }    
    }
    
    //
    private static void init() 
    {
        // prepare move-stacks
        Moves.init();
    }
    
    //
    private static void loop() 
    {                
        // do input wait loop
        for (;;) 
        {    
            // parse and read input from stdin
            UCI i = UCI.parse(CONSOLE.input());

            // switch based on parsed command
            switch (i.cmd)
            {
                // start uci session
                case UCI.UCI:    

                    // initialize thinker
                    ENGINE.init();

                    // set search callback function in onDone-event search
                    ENGINE.setBestMoveCallback(UCI.BEST_MOVE_CALLBACK);                                    
                    ENGINE.setSearchLogCallback(UCI.SEARCH_INFO_CALLBACK);                                    

                    // uci first message
                    CONSOLE.print(UCI.ID_NAME, "Krudo 0.16a");
                    CONSOLE.print(UCI.ID_AUTHOR, "Francesco Bianco");

                    // uci is ready to receive command
                    CONSOLE.print(UCI.UCIOK);

                    // break switch
                    break;

                // isready request
                case UCI.ISREADY:

                    // print out im ready
                    if (ENGINE.isReady()) {
                        CONSOLE.print(UCI.READYOK);
                    }

                    // break switch
                    break;

                // set thinker to start position
                case UCI.POSITION_STARTPOS:

                    // set to start position
                    ENGINE.startpos();

                    // break switch
                    break;

                // set thinker to start position e do move sequences    
                case UCI.POSITION_STARTPOS_MOVES:

                    //
                    ENGINE.startpos();

                    //
                    ENGINE.domove(i.arg);

                    /*_* /
                    int w = t.eval();
                    if (w < 0) {
                        c.put("exit-before-lose:", w);
                        c.end();
                        exit();
                    }
                    /*_*/

                    // break switch
                    break;

                // start thinking based on args passed    
                case UCI.GO:                        

                    // call go with black and white time attentions
                    if (has(i.arg[UCI.WTIME]) && has(i.arg[UCI.BTIME])) 
                    {
                        ENGINE.go(i.arg[UCI.WTIME], i.arg[UCI.BTIME]);
                    } 

                    // call go wihout parameters use default
                    else 
                    {
                        ENGINE.go();
                    }

                    // break switch                        
                    break;

                // quit from main loop    
                case UCI.QUIT: return;
            }
        } 
    }
}
