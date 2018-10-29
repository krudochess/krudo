/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// root package
package org.krudo;

// required static functions
import static org.krudo.Describe.*;

// required non-static class
import java.util.function.Consumer;

// protocol definition class
public final class UCI 
{    
    // gui to engine (700-900)    
    public final static int 
    UCI                     = 701,    
    ISREADY                 = 704,
    SETOPTION               = 705,
    REGISTER                = 706,
    UCINEWGAME              = 707,
    POSITION                = 750,
    POSITION_FEN            = 751,
    POSITION_STARTPOS       = 752,
    POSITION_STARTPOS_MOVES = 753,
    GO                      = 800,
    WTIME                   =   0,
    BTIME                   =   1,
    MOVESTOGO               =   2,
    STOP                    = 710,
    PONDERHIT               = 711,
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
    NODES          = "nodes",
    NPS            = "nps",
    PV             = "pv",
    OPTION         = "option";

    //
    public static final Consumer<Search> SEND_INFO = (search) ->
    {                                      
        //
        if (search.info.equals("id-loop-end"))
        {
            //
            Krudo.CONSOLE.print(INFO, 
                DEPTH,    search.depth_index, 
                SCORE_CP, search.id_score,
                TIME,     search.timer.stamp,
                NODES,    search.nodes,
                //NPS,    s.nps,
                PV,       desc(search.id_pv),
                "info",   search.info
            );
        }
    };
        
    //
    public static final Consumer<Search> SEND_MOVE = (search) ->
    {                
        //
        Krudo.CONSOLE.print(desc(search.node));
        
        //
        search.node.legals();
        
        //
        Krudo.CONSOLE.print(desc(search.node.legals));
        
        //
        Krudo.CONSOLE.print(BESTMOVE, search.move);
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
            //
            i.cmd = POSITION_FEN;
            
            //
            if (s.contains("moves")) 
            {
                String[] a = s.split(" moves ");
                String[] m = a[1].split("\\s");
                i.arg = new String[m.length + 1];
                i.arg[0] = a[0].substring(13);
                for(int j=1; j<i.arg.length; j++) {
                    i.arg[j] = m[j-1];
                }
                Krudo.CONSOLE.print(i.arg[1]);
                
            } 
            
            //
            else 
            {
                i.arg = new String[] { s.substring(13) };                                    
            }
        } 
                
        // 
        else if (s.equals("position startpos")) { i.cmd = POSITION_STARTPOS; }
        
        //
        else if (s.startsWith("position startpos moves ")) 
        {
            //
            i.cmd = POSITION_STARTPOS_MOVES;
            
            //
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
                            i.arg[WTIME] = args[j + 1];
                            break;
                        case "btime":
                            i.arg[BTIME] = args[j + 1];
                            break;                
                        case "movestogo":
                            i.arg[MOVESTOGO] = args[j + 1];
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