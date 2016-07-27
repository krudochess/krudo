/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

// required static class
import static org.krudo.Tool.*;
import static org.krudo.Debug.*;
import static org.krudo.Config.*;
import static org.krudo.Decode.*;
import static org.krudo.Describe.*;
import static org.krudo.Constant.*;

// search main class
public final class Search 
{    
    //
    private final static long INFO_MILLISECONDS_POLLING = 2000;
    
    // node-centric search
    public Node n = null; 
                    
    // searching controls and related         
    private int stop = 0;
    
    //
    public int line_start = 0;
      
    // iterative deepiing deep cursor and limit
    public int depth_index; // current depth in iterative dee 
    public int deep_start; // start value of depth by default 1
    public int deep_limit; // depth limit stop iterative deeping
    
    // iterative deeping time values
    public long time_index;
    public long time_start;
    public long time_limit;
        
    //
    private final Timer id_timer = new Timer();
    private final Timer ab_timer = new Timer();
    
    // count nodes for ab search
    public long qs_nodes;
    public long ab_nodes;
    public long id_nodes;
  
    //
    public long nps;
    
   
            
    //
    private final static int NT_NODE = 0;
 
    //
    public String best_move;
       
    //
    public int best_score;

    //
    public PV best_pv = new PV(); 
      
    //
    public String info_event;
    
    //
    public String info_message;
    
    //
    public Runnable sendinfo = () -> 
    {        
        //
        int pad = 18;
        
        //
        switch (info_event)
        {
            //
            case "id-run":
                print("INFO: "+ rpad(info_event, pad) + " deep " + deep_limit);
                break;
            
            //
            case "id-loop-run":
                print("INFO: "+ rpad(info_event, pad) + " step " + depth_index + "/" + deep_limit);
                break;
            
            //
            case "id-loop-break":
                print("INFO: "+ rpad(info_event, pad) + " event break");
                break;
                
            //    
            default:
                print("INFO: "+ rpad(info_event, pad) + " " + info_message);
                break;
        }
    };
    
    //
    public Runnable sendbestmove = () -> 
    {
        print("BESTMOVE: " + best_move);
    };
     
    // constructor with node-centric search
    public Search(Node node) 
    {   
        // fill internal node use in node-centric search
        n = node;
    }
            
    // public method to start search with large time-limit
    public final void start(int deep) 
    {    
        // start search time limit 5minutes
        start(deep, TIME_5_MINUTES);
    }
        
    // public method to start search custom time-limit
    public final void start(int depth, long time) 
    {            
        // reset stop flag
        stop = NOT;    
        
        // place offset for search variation
        line_start = n.L.i;

        // start iterative deeping search
        idrun(depth, time);
    }
         
    // iterative deeping entry-point
    private void idrun(int depth, long time) 
    {                           
        //
        id_timer.start();
                                
        //
        id_timer.limit(time);
        
        // iterative deeping deep start value
        depth_index = 1;
                
        // set deep limit for iterative deeping
        deep_limit = depth;
        
        //
        id_nodes = 0;
                    
        // iterative deeping alpha start value
        int alfa = -oo;
        
        // iterative deeping beta start value
        int beta = +oo;
        
        //
        int score = alfa;
        
        //
        PV new_pv = PVs.pick();
                        
        //
        sendinfo("id-run");
        
        // iterative deeping loop
        while (depth_index <= deep_limit && stop == NOT)
        {                                        
            //
            sendinfo("id-loop-run");
           
            // launch alfa-beta for searcing candidates 
            score = abrun(alfa, beta, new_pv);       
                        
            // if search stopped 
            if (stop == YES) { sendinfo("id-loop-break"); break; }
                
            //
            best_pv.copy(new_pv);
            
            //
            best_move = desc(best_pv, 0);
            
            //
            best_score = score;                    
                                        
            //
            nps = ab_timer.delta > 0 ? ab_nodes / ab_timer.delta : 0;
            
            //
            sendinfo("id-loop-end", depth_index+"/"+deep_limit+" "+desc(new_pv)+" "+ab_timer.delta+"ms "+ab_nodes+"n "+nps+"knps");
              
            // increade depth of search
            depth_index++;                        
        }    
        
        //
        PVs.free(new_pv);
        
        //
        PVs.info();
        
        //
        id_timer.pause();
        
        //
        sendinfo("id-end", id_timer.delta+"ms "+score+" "+desc(best_pv));
                
        //
        sendbestmove();
    }
    
    // alfa-beta entry-point
    private int abrun(int a, int b, final PV pv) 
    {    
        //
        pv.clear();

        // reset nodes quiesce counter
        qs_nodes = 0;

        // reset nodes search counter
        ab_nodes = 0;
                         
        //
        ab_timer.start();
                                       
        // generate legal-moves
        n.legals();
                        
        // no legal moves check-mate or stale-mate
        if (n.legals.i == 0) { return n.incheck() ? -mate + n.L.i : 0; } 
        
        //
        final PV new_pv = PVs.pick();
                
        // 
        Move m = n.legals.sort().duplicate();
        
        //
        final int l = m.i;
        
        //
        for (int i = 0; i < l; i++) 
        {   
            //
            //sendinfo("ab-loop-run", "move " + m2s(m, i));
            
            // make
            n.domove(m, i);
            
            // recursive evaluation search                    
            int s = abmin(depth_index - 1, a, b, new_pv, NT_NODE);

            // undo
            n.unmove();            
                            
            // hard cut-off
            if (s >= b && !SEARCH_BRUTE_FORCE) 
            {  
                //
                sendinfo("ab-hard-cut-off");
                
                //
                if (SEARCH_UPDATE) { n.legals.w[i] = b; }
                
                //
                a = b;
                
                //
                break;
            }
            
            // soft alfa-cut-off 
            if (s > a) 
            {                              
                //
                if (SEARCH_UPDATE) { n.legals.w[i] = s; }
                
                //
                sendinfo("ab-soft-cut-off", m2s(m, i)+"="+s+" ["+a+";"+b+"]");
                
                //
                pv.cat(new_pv, m, i);
                
                //
                a = s;                
            }   
        } 
        
        //
        Moves.free(m);
        
        //
        PVs.free(new_pv);
       
        //
        ab_timer.pause();
        
        //
        return a; 
    }
    
    //
    private int abmax(final int d, int a, int b, final PV pv, final int nt) 
    {   
        // score
        int s;        
                
        // trasposition table probe
        if (TT.probemax(n.phk, d, a, b)) { return TT.score; }
        
        // return quiescence value-search, 
        if (d == 0) 
        { 
            //
            ab_nodes++;
             
            //
            control();
            
            //
            s = qsmax(a, b, pv);
            
            //
            TT.storemax(d, s); 
            
            //
            return s; 
        }
        
        //
        pv.clear();
                
        // get legal-moves  
        n.legals();
        
        // threefold repetition
        if (n.threefold()) { return 0; }
                
        // no legal moves check-mate or stale-mate
        if (n.legals.i == 0) { return n.incheck() ? -mate + n.L.i : 0; }
        
        //
        PV new_pv = PVs.pick();
                
        // sort and clone       
        Move m  = n.legals.sort().duplicate();
                
        //
        for (int i = 0; i < m.i; i++) 
        {            
            // 
            n.domove(m, i);
                                    
            //                 
            //w = abmin(d-1, a, p == 0 ? b : a+1);
            
            //
            //if (w > a && p > 0) {
            s = abmin(d-1, a, b, new_pv, NT_NODE);                
            //}

            //
            n.unmove();                

            //
            if (stop == YES) { break; }
                        
            // hard cut-off
            if (s >= b && !SEARCH_BRUTE_FORCE) 
            {  
                //t.store(d, a, t.BETA); 
                //return b; 
                //
                if (SEARCH_UPDATE) { n.legals.w[i] = b; }
                
                //
                a = b;
                
                //
                break;
            }
        
            // soft cut-off
            if (s > a) 
            {                         
                //
                pv.cat(new_pv, m, i);
                
                //
                if (SEARCH_UPDATE) { n.legals.w[i] = s; }
                
                //
                //sendinfo("ab-soft-cut-off", m2s(m, i)+"="+s+" ["+a+";"+b+"]");
                     
                //
                a = s;             
            }
        }
        
        //
        Moves.free(m);
        
        //
        PVs.free(new_pv);
          
        //
        TT.storemax(d, a); 
        
        //
        return a;        
    }

    // alfa-beta min routine
    private int abmin(final int d, int a, int b, final PV pv, final int nt)
    {                        
        //
        int s; 
        
        // trasposition table probe
        if (TT.probemin(n.phk, d, a, b)) { return TT.score; }
        
        // at-end quiescence search and 
        if (d == 0) 
        {
            // increase nodes count
            ab_nodes++;
            
            //
            control();
            
            //
            s = qsmin(a, b, pv); 
            
            //
            TT.storemin(d, s); 
            
            // return quesence values
            return s;
        }
        
        //
        pv.clear();
                
        // generate legal-moves 
        n.legals();

        // threefold repetition
        if (n.threefold()) { return 0; }
        
        // no-legals-move exit checkmate
        if (n.legals.i == 0) { return n.incheck() ? +mate - n.L.i : 0; }
        
        //
        PV new_pv = PVs.pick();
        
        // and sort
        Move m = n.legals.sort().duplicate();        
            
        //
        for (int i = 0; i < m.i; i++)
        {                 
            // make move
            n.domove(m, i);
                    
            // 
            //w = abmax(d-1, p==0 ? a : b-1, b);
            
            //
            //if (w < b && p > 0) {
            s = abmax(d - 1, a, b, new_pv, NT_NODE);                
            //}

            // unmake move
            n.unmove();         

            // hard cut-off
            if (s <= a && !SEARCH_BRUTE_FORCE) 
            { 
                //
                b = a;
                
                //
                break;
            }
                                                
            // soft cut-off
            if (s < b) 
            {                     
                //
                pv.cat(new_pv, m, i);
                
                //
                if (SEARCH_UPDATE) { n.legals.w[i] = s; }
                
                //
                //sendinfo("ab-soft-cut-off", m2s(m, i)+"="+s+" ["+a+";"+b+"]");
                
                //
                b = s;                       
            }                    
        } 
        
        //
        Moves.free(m);
        
        //
        PVs.free(new_pv);
   
        //
        TT.storemin(d, b); 
        
        //
        return b;    
    }

    // quiescence max routine
    private int qsmax(int a, int b, final PV pv)
    {     
        // clear pv to return a best-valorized 
        pv.clear();
        
        // eval position
        int s = Eval.node(n);
                                
        // hard cut-off
        if (s >= b) { return b; }
        
        // soft cut-off
        if (s > a) { a = s; }        
        
        // 
        if (!SEARCH_QUIESCENCE) { return a; }
                                       
        // quiescence need sort moves
        n.captures();
        
        //
        final int l = n.captures.i;
        
        //
        if (l == 0) { return a; }
        
        //
        PV new_pv = PVs.pick();
        
        // 
        Capture c = n.captures.sort().duplicate();
                                
        //
        for (int i = 0; i < l; i++)  
        {
            //
            n.domove(c, i);
                        
            //
            s = qsmin(a, b, new_pv);
            
            //
            n.unmove();
            
            // hard cut-off
            if (s >= b && !SEARCH_BRUTE_FORCE) 
            { 
                //
                a = b;
                
                //
                break; 
            }

            // soft cut-off
            if (s > a) 
            { 
                //
                pv.cat(new_pv, c, i);
                
                //
                a = s; 
            }
        }
        
        //
        Captures.free(c);
        
        //
        PVs.free(new_pv);
                        
        //
        return a;
    }
 
    // quiescence min search
    private int qsmin(int a, int b, final PV pv) 
    {        
        //
        pv.clear();
                
        // increase nodes count
        qs_nodes++;
                
        // eval position 
        int s = -Eval.node(n);
        
        // return alfa if wrost
        if (s <= a) { return a; }

        // set new value for upper limit
        if (s < b) { b = s; }
        
        //
        if (!SEARCH_QUIESCENCE) { return b; }
                                    
        //
        n.captures();
        
        //
        final int l = n.captures.i;
        
        //
        if (l == 0) { return b; }
        
        //
        final PV new_pv = PVs.pick();
                
        // quiescenze need sort moves
        Capture c = n.captures.sort().duplicate();
                       
        // loop throut capturers
        for (int i = 0; i < l; i++) 
        {
            // make move 
            n.domove(c, i);
                        
            // iterate qsearch
            s = qsmax(a, b, new_pv);
            
            // redo move
            n.unmove();

            // hard cut-off
            if (s <= a && !SEARCH_BRUTE_FORCE)
            { 
                //
                b = a;
                
                //
                break;
            }        
                
            // soft cut-off
            if (s < b) 
            { 
                //
                pv.cat(new_pv, c, i);
                
                //
                b = s; 
            }
        }
        
        //
        PVs.free(new_pv);
        
        // 
        return b;
    }
    
    //
    private void control()
    {       
        //
        if (!SEARCH_CONTROL) { return; }
                        
        // 
        if (id_timer.expired()) { stop = YES; return; }

        //
        if (!id_timer.polling()) { return; } 
                                  
        //
        //id_nodes = time + INFO_MILLISECONDS_POLLING;
        
        //
        nps = ab_nodes / ab_timer.delta();
        
        //        
        sendinfo("ab-speed", "nps "+nps);
    } 
    
    //
    private void sendinfo(String event)
    {
        //
        info_event = event;
        
        //
        info_message = null;

        //
        sendinfo.run();    
    }
    
    //
    private void sendinfo(String event, String message)
    {
        //
        info_event = event;
        
        //
        info_message = message;

        //
        sendinfo.run();
    }
    
    //
    public void sendbestmove()
    {
        //
        sendbestmove.run();
    }
    
    // launch alfabeta search to evaluate a position
    public final int eval(int d)
    {     
        /*
        // set time limit for the searcing engine
        timeLimit = time() + TIME_5_MINUTES;
    
        // run alpha-beta routine to evaluate
        return start(d); 
        */
        return 0;
    }
    
    //
    public final static void walk(final Node n, int deep, int width)
    {
        //
        if (deep == 0) { return; }
        
        //
        n.legals();
        
        //
        Move m = n.legals.sort();
    
        //
        int w = m.i > width ? width : m.i;
        
        //
        for (int i = 0; i < w; i++) 
        {
            //
            n.domove(m, i);
            
            //
            walk(n, deep - 1, width);
            
            //
            n.unmove();
        }
        
        //
        Moves.free(m);
    }
}