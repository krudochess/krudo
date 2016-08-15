/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

// required static class
import java.util.ArrayList;
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
    public final Node node = new Node(); 
                    
    // searching controls and related         
    private boolean stop = false;
    
    //
    public int line_start = 0;
      
    // iterative deepiing deep cursor and limit
    public int depth_index; // current depth in iterative deeping 
    public int depth_limit; // depth-limit stop iterative deeping
            
    //
    private final Timer id_timer = new Timer();
    private final Timer ab_timer = new Timer();
    
    // count nodes for ab search
    public long qs_nodes;
    public long ab_nodes;
    public long id_nodes;
  
    //
    public long nps;
               
    // aspiration window width
    public int sw = 50;
    
    //
    private final static int NT_NODE = 0;
 
    //
    public String best_move;
       
    //
    public int best_score;

    //
    public PV best_pv = new PV(); 
      
    //
    public String event;
    
    //
    public String event_message;

    //
    public ArrayList<String> event_filter = new ArrayList<>();
    
    //
    public ArrayList<String> event_exclude = new ArrayList<>();
            
    //
    public Runnable sendinfo = () -> 
    {        
        //
        int pad = 18;
        
        //
        if (event_filter.size() > 0 && !event_filter.contains(event)) { return; }
        
        //
        if (event_exclude.contains(event)) { return; }
         
        //
        String info = "INFO: "+ rpad(event, pad);
        
        //
        switch (event)
        {
            //
            case "id-run":
                print(info + "d(" + depth_limit + ") t("+id_timer.limit+")");
                break;
            
            //
            case "id-loop-run":
                print(info + "d(" + depth_index + "/" + depth_limit+")");
                break;
            
            //
            case "id-loop-break":
                print(info + " event break");
                break;
            
            //    
            case "id-loop-end":    
                print(info + depth_index+"/"+depth_limit+" "+desc(best_pv)+" "+ab_timer.stamp+"ms "+ab_nodes+"n "+nps+"knps event break");                
                break;
              
            //    
            case "id-end":
                print(info + id_timer.stamp+"ms "+best_score+" "+desc(best_pv)+"knps event break");                
                break;
        
            //    
            case "ab-routine-end":
                print(
                    info, 
                    depth_index + "/" + depth_limit, 
                    rpad(ab_nodes, 10) + "n",
                    rpad(qs_nodes, 5) + "n",
                    rpad(ab_timer.stamp / 1000, 6) + "s",
                    rpad(nps, 5) + "knps"
                );                
                break;
                                        
            //    
            default:
                print(info + " " + event_message);
                break;
        }
    };
    
    //
    public Runnable sendbestmove = () -> 
    {
        print("BESTMOVE: " + best_move);
    };
                 
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
        stop = false;    
        
        // place offset for search variation
        line_start = node.L.i;

        // start iterative deeping search
        idrun(depth, time);
    }
         
    // iterative deeping entry-point
    private void idrun(int depth, long time) 
    {                           
        //
        id_nodes = 0;

        //
        id_timer.start();
                                
        //
        id_timer.limit(time);
        
        // iterative deeping deep start value
        depth_index = 1;
                
        // set deep limit for iterative deeping
        depth_limit = depth;
                                    
        //
        int score = Eval.node(node);
        
        //
        PV new_pv = PVs.pick();
                        
        //
        info("id-run");
        
        // iterative deeping loop
        while (depth_index <= depth_limit && !stop)
        {                                        
            //
            info("id-loop-run");
           
            // bydefault use aspiration window
            if (!SEARCH_BRUTE_FORCE) {
                score = awrun(score, new_pv);
            } else {
                score = abrun(-oo, +oo, new_pv);             
            }
                        
            // if search stopped 
            if (stop) { info("id-loop-break"); break; }
                
            //
            best_pv.copy(new_pv);
            
            //
            best_move = desc(best_pv, 0);
            
            //
            best_score = score;                    
                             
            //
            id_timer.stamp();
            
            //
            nps = id_timer.ratio(id_nodes);
            
            //
            info("id-loop-end");
              
            // increade depth of search
            depth_index++;                        
        }    
        
        //
        PVs.free(new_pv);
                
        //
        id_timer.stamp();
        
        //
        nps = id_timer.ratio(id_nodes);
                    
        //
        info("id-end");
                
        //
        sendbestmove();
    }
    
    //
    private int awrun(int score, final PV new_pv)
    {
        // iterative deeping alpha start value
        final int alfa = score - sw;

        // iterative deeping beta start value
        final int beta = score + sw;

        // launch alfa-beta for searcing candidates 
        final int eval = abrun(alfa, beta, new_pv); 

        //
        id_nodes += ab_nodes + qs_nodes;

        // if consider aspiration window fails
        if (eval <= alfa || eval >= beta)             
        {
            //
            score = abrun(-oo, +oo, new_pv); 

            //
            id_nodes += ab_nodes + qs_nodes;

            //
            return score;
        } 
            
        // aspiration window not fails redial new score
        return eval;
    }
        
    // alfa-beta entry-point
    private int abrun(int a, int b, final PV pv) 
    {    
        //
        info("ab-routine-run");        

        //
        pv.clear();

        // reset nodes quiesce counter
        qs_nodes = 0;

        // reset nodes search counter
        ab_nodes = 0;
                         
        //
        ab_timer.start();
                                       
        // generate legal-moves
        node.legals();
                        
        // no legal moves check-mate or stale-mate
        if (node.legals.i == 0) { return node.legals.c ? -mate + node.L.i : 0; } 
        
        //
        final PV new_pv = PVs.pick();
                
        // 
        Move m = node.legals.sort().duplicate();
        
        //
        final int l = m.i;
        
        //
        for (int i = 0; i < l; i++) 
        {   
            
            // make
            node.domove(m, i);
            
            // recursive evaluation search                    
            int s = abmin(depth_index - 1, a, b, new_pv, NT_NODE);

            // undo
            node.unmove();            
            
            //
            //sendinfo("ab-loop-run", "move " + m2s(m, i)+"="+s);
            
            // hard cut-off
            if (s >= b && !SEARCH_BRUTE_FORCE) 
            {  
                //
                info("ab-hard-cut-off");
                
                //
                if (SEARCH_UPDATE) { node.legals.w[i] = b; }
                
                //
                a = b;
                
                //
                break;
            }
            
            // soft alfa-cut-off 
            if (s > a) 
            {                              
                //
                if (SEARCH_UPDATE) { node.legals.w[i] = s; }
                
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
        ab_timer.stamp();

        //
        nps = ab_timer.ratio(ab_nodes);
        
        //
        info("ab-routine-end");
        
        //
        return a; 
    }
    
    //
    private int abmax(final int d, int a, int b, final PV pv, final int nt) 
    {   
        // score
        int s;  
                        
        // trasposition table probe
        if (TT.probemax(node.phk, d, a, b)) { return TT.score; }
        
        // return quiescence value-search, 
        if (d == 0) 
        { 
            //
            ab_nodes++;
             
            //
            control();
            
            //
            s = qsmax(a, b);
            
            //
            TT.storemax(d, s); 
            
            //
            return s; 
        }
        
        //
        pv.clear();
                
        // get legal-moves  
        node.legals();
        
        // threefold repetition
        if (node.threefold()) { return 0; }
                
        // no legal moves check-mate or stale-mate
        if (node.legals.i == 0) { return node.legals.c ? -mate + node.L.i : 0; }
        
        //
        PV new_pv = PVs.pick();
                
        // sort and clone       
        Move m  = node.legals.sort().duplicate();
                
        //
        for (int i = 0; i < m.i; i++) 
        {            
            // 
            node.domove(m, i);
                                    
            //                 
            //w = abmin(d-1, a, p == 0 ? b : a+1);
            
            //
            //if (w > a && p > 0) {
            s = abmin(d-1, a, b, new_pv, NT_NODE);                
            //}

            
            
            //
            node.unmove();                

            //
            if (stop) { break; }
                        
            // hard cut-off
            if (s >= b && !SEARCH_BRUTE_FORCE) 
            {  
                //t.store(d, a, t.BETA); 
                //return b; 
                //
                if (SEARCH_UPDATE) { node.legals.w[i] = b; }
                
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
                if (SEARCH_UPDATE) { node.legals.w[i] = s; }
                
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
        if (TT.probemin(node.phk, d, a, b)) { return TT.score; }
                       
        // at-end quiescence search and 
        if (d == 0) 
        {            
            // increase nodes count
            ab_nodes++;
            
            //
            control();
                                    
            //
            s = qsmin(a, b); 
                       
            //
            TT.storemin(d, s); 
            
            // return quesence values
            return s;
        }
        
        //
        pv.clear();
                
        // generate legal-moves 
        node.legals();

        // threefold repetition
        if (node.threefold()) { return 0; }
        
        // no-legals-move exit checkmate
        if (node.legals.i == 0) { return node.legals.c ? +mate - node.L.i : 0; }
        
        //
        PV new_pv = PVs.pick();
        
        // and sort
        Move m = node.legals.sort().duplicate();        
            
        //
        for (int i = 0; i < m.i; i++)
        {                 
            // make move
            node.domove(m, i);
                    
            // 
            //w = abmax(d-1, p==0 ? a : b-1, b);
            
            //
            //if (w < b && p > 0) {
            s = abmax(d - 1, a, b, new_pv, NT_NODE);                
            //}

            // unmake move
            node.unmove();         

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
                if (SEARCH_UPDATE) { node.legals.w[i] = s; }
                
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

    // quiescence max search routine
    private int qsmax(int a, int b)
    {      
        // eval position
        int s = Eval.node(node);
        
        // quiescence need sort moves
        node.captures();
                       
        //
        final int l = node.captures.i;
        
        //
        if (l == 0)
        {                
            // generate legal-moves 
            node.legals();

            // no-legals-move exit checkmate
            if (node.legals.i == 0) { return node.legals.c ? -mate + node.L.i : 0; }
        }
                       
        // hard cut-off
        if (s >= b) { return b; }
        
        // soft cut-off
        if (s > a) { a = s; }        
                                                               
        // term leaf quiescence search
        if (l == 0) { return a; }
          
        // 
        if (!SEARCH_QUIESCENCE) { return a; }
        
        //
        qs_nodes++;            
        
        // 
        Capture c = node.captures.sort().duplicate();
                                
        //
        for (int i = 0; i < l; i++)  
        {
            //
            node.domove(c, i);
                        
            //
            s = qsmin(a, b);
            
            //
            node.unmove();
            
            // hard cut-off disabled in bruteforce mode
            if (s >= b) { a = b; break; }

            // soft cut-off
            if (s > a) { a = s; }
        }
        
        //
        Captures.free(c);
                                     
        //
        return a;
    }
 
    // quiescence min search routine
    private int qsmin(int a, int b) 
    {                        
        // eval position 
        int s = -Eval.node(node);
        
        //
        node.captures();
        
        //
        final int l = node.captures.i;
        
        //
        if (l == 0) 
        {
            // generate legal-moves 
            node.legals();

            // no-legals-move exit checkmate
            if (node.legals.i == 0) { return node.legals.c ? +mate - node.L.i : 0; }                       
        }
                       
        // return alfa if wrost
        if (s <= a) { return a; }

        // set new value for upper limit
        if (s < b) { b = s; }
                                                     
        //
        if (l == 0) { return b; }
        
        //
        if (!SEARCH_QUIESCENCE) { return b; }
        
        // increase nodes count
        qs_nodes++;
                             
        // quiescenze need sort moves
        Capture c = node.captures.sort().duplicate();
                       
        // loop throut capturers
        for (int i = 0; i < l; i++) 
        {
            // make move 
            node.domove(c, i);
                        
            // iterate qsearch
            s = qsmax(a, b);
            
            // redo move
            node.unmove();

            // hard cut-off disabled in bruteforce mode
            if (s <= a) { b = a; break; }        
                
            // soft cut-off
            if (s < b) { b = s; }
        }
        
        //
        Captures.free(c);
                                              
        // 
        return b;
    }
    
    //
    private void control()
    {       
        //
        if (!SEARCH_CONTROL) { return; }
                        
        // 
        if (id_timer.expired()) { stop = true; return; }

        //
        if (id_timer.polling()) 
        {                                           
            //
            ab_timer.stamp();

            //
            nps = ab_timer.ratio(ab_nodes);

            //        
            sendinfo("ab-speed", "nps "+nps);
        }
    } 
    
    //
    private void info(String event)
    {
        //
        this.event = event;
        
        //
        event_message = null;

        //
        sendinfo.run();    
    }
    
    //
    private void sendinfo(String event, String message)
    {
        //
        this.event = event;
        
        //
        this.event_message = message;

        //
        sendinfo.run();
    }
    
    //
    public void sendbestmove()
    {
        //
        sendbestmove.run();
    }
   
    //
    public final static void walk(final Node n, int depth, int width)
    {
        //
        if (depth == 0) { return; }
        
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
            walk(n, depth - 1, width);
            
            //
            n.unmove();
        }
        
        //
        Moves.free(m);
    }
}