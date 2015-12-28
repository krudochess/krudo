package org.krudo;

// required static class
import static org.krudo.util.Tools.*;
import static org.krudo.Const.*;
import static org.krudo.util.Debug.*;

// main class entry point for java application
public final class Krudo {	
	
	// console interface with stdio management
	public static Console c; 
	
	// thinker store chess game status brain interface	 	
	public static Thinker t;
		
	// entry-point for console application
	public static void main(String[] args) {
											
		// start input loop of engine 
		try {
		
			// initialize console
			c = new Console(path("Krudo.log"));

			// initialize thinker
			t = new Thinker();

			// credits message
			c.put("Krudo 0.15a by Francesco Bianco <bianco@javanile.org>");
										
			// do input wait loop
			main: for(;;) {	
		
				// parse and read input from stdin
				UCI i = UCI.parse(c.get());

				// switch based on parsed command
				switch(i.cmd) {
				
					// start uci session
					case UCI.UCI:	
						
						// set search callback function in onDone-event search
						t.setBestMoveCallback(UCI.BEST_MOVE_CALLBACK);									
						t.setSearchLogCallback(UCI.SEARCH_LOG_CALLBACK);									
												
						// uci first message
						c.put(UCI.ID_NAME, "Krudo 0.15a");
						c.put(UCI.ID_AUTHOR, "Francesco Bianco");
												
						// uci is ready to receive command
						c.put(UCI.UCIOK);
						
						// break switch
						break;

					// isready request
					case UCI.ISREADY:
						
						// print out im ready
						if (t.isReady()) {
							c.put(UCI.READYOK);
						}
												
						// break switch
						break;

					// set thinker to start position
					case UCI.POSITION_STARTPOS:
						
						// set to start position
						t.startpos();
						
						// break switch
						break;
					
					// set thinker to start position e do move sequences	
					case UCI.POSITION_STARTPOS_MOVES:
						
						//
						t.startpos();
						
						//
						t.domove(i.arg);
												
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
							t.go(i.arg[UCI.WTIME], i.arg[UCI.BTIME]);
						} 
						
						// call go wihout parameters use default
						else {
							t.go();
						}
						
						// break switch						
						break;
						
					// dogame command
					case CMD.DOGAME:
						t.startpos();						
						break;
					
					// domove commnad
					case CMD.DOMOVE:
						t.domove(i.arg);						
						break;
					
					// unmove command	
					case CMD.UNMOVE:
						t.unmove();		
						break;
					
					// unmove command	
					case CMD.PERFT:
						for(int d=1; d<=toInt(i.arg[0]); d++) {
							echo(perft(t.n,d));
						}	
						break;
					
					// quit from main loop	
					case CMD.QUIT: break main;
				}
			} 
		} 
		
		// exception catched print in console
		catch (Throwable e) {
			c.err(e);			
		} 
				
		// exit and close console
		finally {			
			c.put("exit");
			c.end();			
		}	
	}
}
