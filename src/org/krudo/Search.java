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

//
import java.util.function.Consumer;

// search main class
public final class Search 
{        
    // node-centric search
    public final Node node = new Node(); 
      
    // iterative deepiing deep cursor and limit
    public int 
    ab_depth,
    line_offset, // offset on move history when start search
    depth_index, // current depth in iterative deeping 
    depth_limit; // depth-limit stop iterative deeping
            
    // timer-cronometers
    public final Timer 
    id_timer = new Timer(), // for iterative deeping 
    ab_timer = new Timer(); // for alfa-beta
    
    // count nodes for ab search
    public long 
    qs_nodes, 
    ab_nodes,
    id_nodes,
    nps;      // contain velocity in kNPS
                  
    // aspiration window width
    public int sw = 50;
    
    //
    private final static int NT_NODE = 0;
 
    //
    public String 
    id_best_move ;
       
    //
    public int 
    id_best_score;

    //
    public PV 
    id_best_pv = new PV(); 
           
    //
    public String best_move;
    
    //
    public String 
    event,
    event_message;

    //
    public Consumer<Search> 
    send_text_info = SEARCH_SEND_TEXT_INFO, 
    send_best_move = SEARCH_SEND_BEST_MOVE;
                 
    // searching controls and related         
    private boolean stop = false;    
    
    //
    public Search() { }    
    
    //
    public Search(String fen) 
    {
        //
        node.startpos(fen); 
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
        stop = false;    
        
        // place offset for search variation
        line_offset = node.L.i;

        // start iterative deeping search
        idrun(depth, time);
    }
         
    // iterative deeping entry-point
    private void idrun(int depth, long time) 
    {                           
        // reset nodes count
        id_nodes = 0;

        // start timer
        id_timer.start();
                                
        // time limit stop search algorythm
        id_timer.setTimeout(time);
        
        // time polling for print-out running-search-info
        id_timer.setPolling(TIME_5_SECONDS);
        
        // iterative deeping deep start value
        depth_index = 1;
                
        // set deep limit for iterative deeping
        depth_limit = depth;
                                    
        // initial score
        int score = Eval.node(node);
        
        // used to retrieve pv 
        PV new_pv = PVs.pick();
                        
        // log 
        info("id-run");
        
        // iterative deeping loop
        while (depth_index != depth_limit && !stop)
        {                                        
            // log
            info("id-loop-run");

            //
            score = awrun(score, new_pv);
                        
            // if search stopped 
            if (stop) { info("id-loop-break"); break; }
                
            //
            id_best_pv.copy(new_pv);
            
            //
            id_best_move = desc(id_best_pv, 0);
            
            //
            id_best_score = score;                    
                                         
            // calculate speed
            nps = id_timer.ratio(id_nodes);
            
            // log
            info("id-loop-end");
              
            // increade depth of search
            depth_index++;                        
        }    
        
        //
        PVs.free(new_pv);
                
        //
        nps = id_timer.ratio(id_nodes);
                    
        //
        info("id-end");
                
        //
        sendbestmove(id_best_move);
    }
    
    // aspiration window controls
    private int awrun(int s, final PV new_pv)
    {
        // by-pass aspiration window
        if (SEARCH_BRUTE_FORCE || !SEARCH_ASPIRATION) 
        {
            // 
            return abrun(-oo, +oo, new_pv);
        }
        
        // iterative deeping alpha start value
        final int a = s - sw;

        // iterative deeping beta start value
        final int b = s + sw;

        // launch alfa-beta for searcing candidates 
        s = abrun(a, b, new_pv); 

        // if consider aspiration window fails
        if (s <= a || s >= b) { return abrun(-oo, +oo, new_pv); } 
            
        // aspiration window not fails redial new score
        return s;
    }
        
    // alfa-beta entry-point root search
    private int abrun(int a, int b, final PV pv) 
    {    
        //
        info("ab-routine-run");        
                               
        //
        pv.clear();
               
        // reset nodes quiesce counter
        qs_nodes = 0;

        //
        ab_depth = 0;
        
        // reset nodes search counter
        ab_nodes = 0;
                         
        // start timer
        ab_timer.start();
                                       
        // generate legal-moves
        node.legals();
                
        // get current number of legal moves
        final int count = node.legals.count;
        
        // no legal moves check-mate or stale-mate
        if (count == 0) { return node.legals.check ? -checkmate + node.L.i : 0; } 
        
        //
        final PV new_pv = PVs.pick();
        
        // assign move weight for ab search with sort
        Eval.legals(node); 
                
        // 
        Move m = node.legals.sort().twin();
                
        // tansposition tables flag and values
        int f = TT.E, bm_s = xx, bm_v = xx;
                
        // loop thru the moves
        for (int i = 0; i != count; i++) 
        {            
            // make
            node.domove(m, i);
            
            // recursive evaluation search                    
            int s = abmin(depth_index - 1, a, b, new_pv, NT_NODE);

            // undo
            node.unmove();            
                       
            // hard cut-off
            if (s >= b) { f = TT.B; a = b; break; }
            
            // soft alfa-cut-off 
            if (s > a) 
            {   
                //
                bm_s = m.s[i];
                
                //
                bm_v = m.v[i];
                
                //
                TT.store(node.phk, TT.A, depth_index, s, bm_s, bm_v);
                                                
                //
                pv.cat(new_pv, m, i);
                
                //
                a = s;                
            }   
        } 
        
        // trasposition store EXACT or BETA cut-off
        TT.store(node.phk, f, depth_index, a, bm_s, bm_v);
        
        //
        PVs.free(new_pv);
                
        //
        Moves.free(m);
                                     
        //
        nps = ab_timer.ratio(ab_nodes);
        
        //
        id_nodes += ab_nodes + qs_nodes;
                
        //
        info("ab-routine-end");
        
        //
        return a; 
    }
    
    //
    private int abmax(final int d, int a, int b, final PV pv, final int nt) 
    {   
        //
        TT.probemax(node.phk);
        
        // get legal-moves  
        node.legals();
        
        // get current number of legal moves
        final int l = node.legals.count;
                
        // return quiescence value-search, 
        if (d == 0) 
        { 
            //
            control();

            //
            ab_nodes++;
                    
            // threefold repetition
            if (node.threefold()) { return stalemate; }
                
            // no legal moves check-mate or stale-mate
            if (l == 0) { return node.legals.check ? -checkmate + node.L.i : stalemate; }
        
            //
            return qsmax(a, b); 
        }
        
        // score
        int s;  
        
        //
        pv.clear();
                
        //
        PV new_pv = PVs.pick();
        
        // assign move weight for ab search with sort
        Eval.legals(node); 
                
        // sort and clone       
        Move m  = node.legals.sort().twin();
                
        // loop throut legal moves
        for (int i = 0; i != l; i++) 
        {            
            // 
            node.domove(m, i);

            //
            s = abmin(d-1, a, b, new_pv, NT_NODE);                
                       
            //
            node.unmove();                

            // 
            if (stop) { break; }
                        
            // hard cut-off
            if (s >= b && !SEARCH_BRUTE_FORCE) 
            {  
                //
                //if (SEARCH_UPDATE) { node.legals.w[i] = b; }
                
                //
                a = b;
                
                //
                break;
            }
        
            // soft cut-off
            if (s > a) 
            {                         
                //
                //if (SEARCH_UPDATE) { node.legals.w[i] = s; }

                //
                pv.cat(new_pv, m, i);                
                
                //
                //sendinfo("ab-soft-cut-off", m2s(m, i)+"="+s+" ["+a+";"+b+"]");
                     
                //
                a = s;             
            }
        }
        
        //
        if (MOVE_TWIN) { Moves.free(m); }
        
        //
        PVs.free(new_pv);
        
        //
        return a;        
    }

    // alfa-beta min routine
    private int abmin(final int d, int a, int b, final PV pv, final int nt)
    {   
        //
        TT.probemax(node.phk);

        // generate legal-moves 
        node.legals();
       
        // get current number of legal moves
        final int l = node.legals.count;
        
        // at-end quiescence search and 
        if (d == 0) 
        {            
            //
            control();
                        
            // increase nodes count
            ab_nodes++;
                               
            // threefold repetition
            if (node.threefold()) { return 0; }

            // no-legals-move exit checkmate
            if (l == 0) { return node.legals.check ? +checkmate - node.L.i : stalemate; }
            
            //
            return qsmin(a, b);
        }

        //
        int s; 
        
        //
        pv.clear();
                                
        //
        PV new_pv = PVs.pick();
        
        // assign move weight for ab search with sort
        Eval.legals(node); 
        
        // and sort
        Move m = node.legals.sort().twin();        
            
        //
        for (int i = 0; i != l; i++)
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
                //if (SEARCH_UPDATE) { node.legals.w[i] = s; }
                
                //
                pv.cat(new_pv, m, i);
                
                //
                //sendinfo("ab-soft-cut-off", m2s(m, i)+"="+s+" ["+a+";"+b+"]");
                
                //
                b = s;                       
            }                    
        } 
        
        //
        if (MOVE_TWIN) { Moves.free(m); }
        
        //
        PVs.free(new_pv);
          
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
        final int l = node.captures.count;
        
        //
        if (l == 0)
        {                
            // generate legal-moves 
            //node.legals();

            // no-legals-move exit checkmate
            //if (node.legals.i == 0) { return node.legals.c ? -checkmate + node.L.i : 0; }
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
        Capture c = node.captures.sort().twin();
                                
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
        final int l = node.captures.count;
        
        //
        if (l == 0) 
        {
            // generate legal-moves 
            //node.legals();

            // no-legals-move exit checkmate
            //if (node.legals.i == 0) { return node.legals.c ? +checkmate - node.L.i : 0; }                       
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
        Capture c = node.captures.sort().twin();
                       
        // loop throut capturers
        for (int i = 0; i != l; i++) 
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
            nps = id_timer.ratio(id_nodes + ab_nodes + qs_nodes);

            //        
            info("speed");
        }
    } 
    
    //
    private void info(String event)
    {
        //
        this.event = event;
        
        //
        send_text_info.accept(this);    
    }
    
    //
    private void info(String event, String message)
    {
        //
        this.event = event;
        
        //
        this.event_message = message;

        //
        send_text_info.accept(this);
    }
    
    // run send_bast_move callable
    public void sendbestmove(String bm)
    {
        //
        best_move = bm;
        
        //
        send_best_move.accept(this);
    }   
}
