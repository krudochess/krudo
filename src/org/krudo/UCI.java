package org.krudo;

// required static class
import static org.krudo.Const.*;

// protocol definition class
public final class UCI {
	
    // gui to engine (700-900)	
    public final static int 
	UCI						= 701,    
    ISREADY					= 704,
    SETOPTION				= 705,
    REGISTER				= 706,
    UCINEWGAME				= 707,
    POSITION				= 750,
    POSITION_FEN			= 751,
    POSITION_STARTPOS		= 752,
    POSITION_STARTPOS_MOVES	= 753,
    GO						= 800,
    WTIME					=   0,
    BTIME					=   1,
    MOVESTOGO				=   2,
   	STOP					= 710,
    PONDERHIT				= 711;
    
    // engine to gui
    public final static String 
	ID_NAME         = "id name",
    ID_AUTHOR       = "id author",
    UCIOK           = "uciok",
    READYOK         = "readyok",
    BESTMOVE        = "bestmove",
    COPYPROTECTION	= "copyprotection",
    REGISTRATION    = "registration",
    INFO            = "info",
	DEPTH           = "depth",
	SCORE_CP        = "score cp",
	TIME			= "time",
	NODES			= "nodes",
	PV				= "pv",
	OPTION          = "option";

	// event log callbacks of UCI interface
	public final static Runnable	
	
	// search log callback
	SEARCH_LOG_CALLBACK = new Runnable() { 
		
		//
		@Override 		
		public void run() {
			
			//
			switch (Krudo.t.s.logAction) {
			
				//
				case SEARCH_LOG_UP:
					
					//
					Krudo.c.put(INFO, 
						DEPTH,		Krudo.t.s.logDeep, 
						SCORE_CP,	Krudo.t.s.logWeight,
						TIME,		Krudo.t.s.logTime,
						NODES,		Krudo.t.s.logNodes,
						PV,			Krudo.t.s.logLine
					);
					
					//
					break;
				
				//	
				case SEARCH_LOG_BM:
					
					//
					Krudo.t.bm = Krudo.t.s.logMove;
					
					//
					Krudo.t.bmc.run();
					
					//
					break;		
			}			
		}
	},
		
	//		
	BEST_MOVE_CALLBACK = new Runnable() {
	
		//
		@Override 
		public void run() {
			
			//
			Krudo.c.put(BESTMOVE, Krudo.t.bm);									
		}	
	};			
		
	// 
	public int cmd;
	
	//
	public String[] arg;
	
	//
    public static final UCI parse(String s) {
		
		//
		UCI i = new UCI();
		
		//
    	if (s.startsWith("ucinewgame")) {
            i.cmd = UCINEWGAME;
		} 
		
		//
		else if (s.startsWith("uci")) {
            i.cmd = UCI;        
		} 
		
		//
		else if (s.startsWith("isready")) {
            i.cmd = ISREADY;        
		} 
		
		//
		else if (s.startsWith("position fen ")) {
            i.cmd = POSITION_FEN;        
		} 
		
		//
		else if (s.startsWith("position startpos moves ")) {
			i.cmd = POSITION_STARTPOS_MOVES;
			i.arg = s.substring(24).split("\\s");		
		} 
				
		//
		else if (s.startsWith("position startpos")) {
			i.cmd = POSITION_STARTPOS;		        
		}
		
		//
		else if (s.startsWith("go ")) {
        	i.cmd = GO;
			i.arg = new String[] {
				"", /*WTIME*/		
				"", /*BTIME*/		
				"", /*MOVESTOGO*/
			};			
			String[] t = s.substring(3).split("\\s+");		
			for(int j=0; j<t.length; j++) {
				switch (t[j]) {
					case "wtime":
						i.arg[WTIME] = t[j+1];
						break;
					case "btime":
						i.arg[BTIME] = t[j+1];
						break;				
					case "movestogo":
						i.arg[MOVESTOGO] = t[j+1];
						break;
				}
			}			        
		} 
		
		//
		else if (s.startsWith("stop")) {
            i.cmd = STOP;        
		} 
		
		//
		else if (s.startsWith("dogame")) {
            i.cmd = CMD.DOGAME;		
		} 
		
		//
		else if (s.startsWith("domove")) {
            i.cmd = CMD.DOMOVE;
			i.arg = s.substring(7).split("\\s+");        
		}
		
		//
		else if (s.startsWith("unmove")) {
            i.cmd = CMD.UNMOVE;        
		} 

		//
		else if (s.startsWith("perft")) {
            i.cmd = CMD.PERFT;      
			i.arg = s.substring(6).split("\\s+");
		} 

		//
		else if (s.startsWith("dump")) {
            i.cmd = CMD.DUMP;
		} 
		
		//
		else if (s.startsWith("quit")) {
            i.cmd = CMD.QUIT;        
		} 
		
		//
		else {
        	i.cmd = CMD.NOPE;
        }
		
		//
		return i;
    }
}