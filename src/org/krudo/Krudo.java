/**
 * Krudo 0.16a 
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

// required static class
import static org.krudo.Const.*;
import static org.krudo.util.Tools.*;

// main class entry point for java application
public final class Krudo {
	
	// engine store chess game status is chess brain interface	 	
	public final static Engine engine = new Engine();
	
	// console interface with "standard i/o" management
	public final static Console console = new Console(); 
			
	// entry-point for console application
	public static void main(String[] args) {
		
		// start input loop of engine 
		try {
					
			// initialize console
			console.open(path("Krudo.log"));

			// credits message
			console.print("Krudo 0.16a by Francesco Bianco <bianco@javanile.org>");
						
			// do input wait loop
			main: for(;;) {	
		
				// parse and read input from stdin
				UCI i = UCI.parse(console.input());

				// switch based on parsed command
				switch(i.cmd) {
				
					// start uci session
					case UCI.UCI:	

						// initialize thinker
						engine.init();
           	
						// set search callback function in onDone-event search
						engine.setBestMoveCallback(UCI.BEST_MOVE_CALLBACK);									
						engine.setSearchLogCallback(UCI.SEARCH_LOG_CALLBACK);									
												
						// uci first message
						console.print(UCI.ID_NAME, "Krudo 0.15a");
						console.print(UCI.ID_AUTHOR, "Francesco Bianco");
												
						// uci is ready to receive command
						console.print(UCI.UCIOK);
						
						// break switch
						break;

					// isready request
					case UCI.ISREADY:
						
						// print out im ready
						if (engine.isReady()) {
							console.print(UCI.READYOK);
						}
												
						// break switch
						break;

					// set thinker to start position
					case UCI.POSITION_STARTPOS:
						
						// set to start position
						engine.startpos();
						
						// break switch
						break;
					
					// set thinker to start position e do move sequences	
					case UCI.POSITION_STARTPOS_MOVES:
						
						//
						engine.startpos();
						
						//
						engine.domove(i.arg);
												
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
						if (has(i.arg[UCI.WTIME]) && has(i.arg[UCI.BTIME])) {
							engine.go(i.arg[UCI.WTIME], i.arg[UCI.BTIME]);
						} 
						
						// call go wihout parameters use default
						else {
							engine.go();
						}
						
						// break switch						
						break;
											
					// quit from main loop	
					case CMD.QUIT: break main;
				}
			} 
		} 
		
		// exception catched print in console
		catch (Throwable e) {
			console.error(e);			
		} 
				
		// exit and close console
		finally {			
			console.print("exit");
			console.close();			
		}	
	}
}
