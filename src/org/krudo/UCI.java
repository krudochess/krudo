/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// root package
package org.krudo;

// protocol definition class
public final class UCI 
{    
    // gui to engine (700-900)    
    public final static int 
    UCI                        = 701,    
    ISREADY                    = 704,
    SETOPTION                = 705,
    REGISTER                = 706,
    UCINEWGAME                = 707,
    POSITION                = 750,
    POSITION_FEN            = 751,
    POSITION_STARTPOS        = 752,
    POSITION_STARTPOS_MOVES    = 753,
    GO                        = 800,
    WTIME                    =   0,
    BTIME                    =   1,
    MOVESTOGO                =   2,
       STOP                    = 710,
    PONDERHIT                = 711,
    QUIT                    = 910;
    
    // engine to gui
    public final static String 
    ID_NAME        = "id name",
    ID_AUTHOR      = "id author",
    UCIOK          = "uciok",
    READYOK        = "readyok",
    BESTMOVE       = "bestmove",
    COPYPROTECTION = "copyprotection",
    REGISTRATION   = "registration",
    INFO           = "info",
    DEPTH          = "depth",
    SCORE_CP       = "score cp",
    TIME           = "time",
    NODES           = "nodes",
    PV               = "pv",
    OPTION         = "option";

    // search log callback
    public final static Runnable SENDINFO = () -> 
    {                    
        /*
        //
        switch (Krudo.ENGINE.search.logAction) 
        {
            //
            case SEARCH_LOG_UP:

                //
                Krudo.CONSOLE.print(INFO, 
                    DEPTH,        Krudo.ENGINE.search.logDeep, 
                    SCORE_CP,    Krudo.ENGINE.search.logWeight,
                    TIME,        Krudo.ENGINE.search.logTime,
                    NODES,        Krudo.ENGINE.search.logNodes,
                    PV,            Krudo.ENGINE.search.logLine
                );

                //
                break;

            //    
            case SEARCH_LOG_BM:

                //
                Krudo.ENGINE.bm = Krudo.ENGINE.search.logMove;

                //
                Krudo.ENGINE.bmc.run();

                //
                break;        
        }    */        
    };
        
    //        
    public final static Runnable SENDBESTMOVE = () -> 
    {        
        //
        Krudo.CONSOLE.print(BESTMOVE, Krudo.ENGINE.bestmove);                                                
    };            
        
    // 
    public int cmd;
    
    //
    public String[] arg;
    
    //
    public static final UCI parse(String s) 
    {     
        //
        UCI i = new UCI();
        
        //
        if (s.equals("uci")) { i.cmd = UCI; } 
              
        //
        else if (s.equals("ucinewgame")) { i.cmd = UCINEWGAME; } 
                
        //
        else if (s.equals("isready")) { i.cmd = ISREADY; } 
        
        //
        else if (s.startsWith("position fen "))
        {
            i.cmd = POSITION_FEN;        
        } 
                
        //
        else if (s.equals("position startpos"))
        {
            i.cmd = POSITION_STARTPOS;                
        }
        
        //
        else if (s.startsWith("position startpos moves ")) 
        {
            i.cmd = POSITION_STARTPOS_MOVES;
            i.arg = s.substring(24).split("\\s");        
        } 
        
        //
        else if (s.startsWith("go")) 
        {    
            //
            i.cmd = GO;
            
            //
            i.arg = new String[]
            {
                "", /*WTIME*/        
                "", /*BTIME*/        
                "", /*MOVESTOGO*/
            };    
            
            //
            if (s.length() > 3) 
            {
                //
                String[] args = s.substring(3).split("\\s+");        
                
                //
                for(int j = 0; j < args.length; j++) 
                {
                    switch (args[j]) 
                    {
                        case "wtime":
                            i.arg[WTIME] = args[j+1];
                            break;
                        case "btime":
                            i.arg[BTIME] = args[j+1];
                            break;                
                        case "movestogo":
                            i.arg[MOVESTOGO] = args[j+1];
                            break;
                    }
                }                    
            }
        } 
        
        //
        else if (s.equals("stop")) { i.cmd = STOP; } 
            
        //
        else if (s.equals("quit")) { i.cmd = QUIT; }         
        
        //
        return i;
    }
}