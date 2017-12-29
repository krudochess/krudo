
  /*\
 / + \ Krudo 0.20a - the messianic chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

// krudo package
package org.krudo;

// require non-static class
import java.util.function.Consumer;

// require static class methods
import static org.krudo.Tool.*;
import static org.krudo.Describe.*;
import static org.krudo.Constants.*;

// search main class
public final class Search 
{        
    // node-centric search
    public final Node node = new Node();

    // search controls is running
    private boolean start = false;

    // iterative deepiing deep cursor and limit
    public int
    line_offset, // offset on move history when start search
    depth_index, // current depth in iterative deeping 
    depth_limit, // depth-limit stop iterative deeping
    id_score, // iterative deeping score
    sw = 50; // aspiration window width

    // count nodes for ab search
    public long 
    nodes, // count total number of nodes
    nps;   // contain velocity in kNPS

    //
    public String
    id_move,
    info,
    move;

    // timer-cronometers
    public final Timer
    timer = new Timer(); // for iterative deeping

    //
    public PV 
    id_pv = new PV();

    //
    public Consumer<Search> 
    send_info = Inspect.SEARCH_INFO,
    send_move = Inspect.SEARCH_MOVE;

    //
    private final static int NT_NODE = 0;

    // empty constructor
    public Search() { }    
    
    // init constructor
    public Search(String fen) 
    {
        // set node to startpos
        node.startpos(fen); 
    }

    // set internal node to startpos
    public final void startpos()
    {
        // set node to startpos
        startpos(STARTPOS);
    }

    // set initernal node to custom startpos
    public final void startpos(String fen)
    {
        // set node to startpos
        node.startpos(fen);
    }

    // public method to start search with large time-limit
    public final void start()
    {
        // start search time limit 5minutes
        start(10);
    }

    // public method to start search with large time-limit
    public final void start(int depth)
    {    
        // start search time limit 5minutes
        start(depth, TIME_5_MINUTES);
    }
        
    // public method to start search custom time-limit
    public final void start(int depth, long time)
    {            
        // reset stop flags
        start = true;
        
        // place offset for search variation
        line_offset = node.L.i;

        // start iterative deeping search
        idrun(depth, time);
    }
         
    // iterative deeping entry-point
    private void idrun(int depth, long time) 
    {                           
        // reset nodes count
        nodes = 0;

        // start timer
        timer.start();
                                
        // time limit stop search algorythm
        timer.setTimeout(time);
        
        // time polling for print-out running-search-info
        timer.setPolling(TIME_5_SECONDS);
        
        // iterative deeping deep start value
        depth_index = 1;
                
        // set deep limit for iterative deeping
        depth_limit = depth;
                                    
        // initial score
        int score = Eval.node(node);
        
        // used to retrieve pv 
        PV new_pv = PVs.pick();
                        
        // log 
        info("+go");
        
        // iterative deeping loop
        while (start && depth_index <= depth_limit)
        {                                        
            // log
            info("+id");

            // get score of aspiration window
            score = awrun(score, new_pv);
                        
            // if search stopped skip to update best values
            if (!start) { info("id-loop-break"); break; }
                
            // update best pv
            id_pv.copy(new_pv);
            
            // update best move
            id_move = desc(id_pv, 0);
            
            // update best score
            id_score = score;
                                         
            // calculate speed
            nps = timer.ratio(nodes);
            
            // log
            info("-id");
              
            // increade depth of search
            depth_index++;                        
        }    
        
        // free a pv stack
        PVs.free(new_pv);
                
        // calc new speed
        nps = timer.ratio(nodes);
                    
        // log
        info("-go");
                
        // send best move
        move(id_move);
    }
    
    // aspiration window controls
    private int awrun(int s, final PV new_pv)
    {
        // iterative deeping alpha start value
        final int a = s - sw;

        // iterative deeping beta start value
        final int b = s + sw;

        // launch alfa-beta for searcing candidates 
        s = abrun(depth_index, a, b, new_pv);

        // if consider aspiration window fails
        if (s <= a || s >= b) { return abrun(depth_index, -oo, +oo, new_pv); }
            
        // aspiration window not fails redial new score
        return s;
    }
        
    // alfa-beta entry-point root search
    private int abrun(int d, int a, int b, final PV pv)
    {
        //
        pv.clear();
               
        // reset nodes quiesce counter
        nodes = 0;

        // start timer
        timer.start();
                                       
        // generate legal-moves
        node.legals();
                
        // get current number of legal moves
        final int count = node.legals.count;
        
        // no legal moves check-mate or stale-mate
        if (count == 0) { return -mate(); }

        //
        final PV new_pv = PVs.pick();
        
        // assign move weight for ab search with sort
        Eval.legals(node); 
                
        // 
        Move m = node.legals.sort().twin();

        //
        info("+ab");

        //
        int f = TT.E;

        // loop thru the moves
        for (int i = 0; i != count; i++) 
        {            
            // make
            node.domove(m, i);
            
            // recursive evaluation search                    
            int s = abmin(d - 1, a, b, new_pv, NT_NODE);

            // undo
            node.unmove();            
                       
            // hard cut-off
            if (s >= b) { a = b; f = TT.B; break; }
            
            // soft alfa-cut-off 
            if (s > a) { pv.cat(new_pv, m, i); a = s; TT.add(node.phk, d, TT.A, a); }
        } 

        //
        TT.add(node.phk, d, f, a);

        //
        PVs.free(new_pv);
                
        //
        Moves.free(m);
                                     
        //
        nps = timer.ratio(nodes);

        //
        info("-ab");
        
        //
        return a; 
    }
    
    //
    private int abmax(final int d, int a, int b, final PV pv, final int nt) 
    {
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
            nodes++;
                    
            // threefold repetition
            if (node.threefold()) { return stalemate; }
                
            // no legal moves check-mate or stale-mate
            if (l == 0) { return -mate(); }

            //
            return qsmax(a, b, pv);
        }

        //
        if (TT.has(node.phk, d)) {
            return TT.max(node.phk, a, b);
        }

        //
        pv.clear();
                
        //
        PV new_pv = PVs.pick();
        
        // assign move weight for ab search with sort
        Eval.legals(node); 
                
        // sort and clone       
        Move m  = node.legals.sort().twin();

        // tt exit flags
        int f = TT.E;

        // loop throut legal moves
        for (int i = 0; i != l; i++) 
        {            
            // make move
            node.domove(m, i);

            //
            int s = abmin(d - 1, a, b, new_pv, NT_NODE);
                       
            // undo move
            node.unmove();                

            // 
            if (!start) { break; }
                        
            // hard cut-off
            if (s >= b) { a = b; f = TT.B; break; }
        
            // soft cut-off
            if (s > a) { pv.cat(new_pv, m, i); a = s; TT.add(node.phk, d, TT.A, a); }
        }

        //
        TT.add(node.phk, d, f, a);

        //
        Moves.free(m);
        
        //
        PVs.free(new_pv);
        
        //
        return a;        
    }

    // alfa-beta min routine
    private int abmin(final int d, int a, int b, final PV pv, final int nt)
    {   
        // generate legal-moves
        node.legals();
       
        // get current number of legal moves
        final int l = node.legals.count;
        
        // at-mate quiescence search and
        if (d == 0) 
        {            
            //
            control();
                        
            // increase nodes count
            nodes++;
                               
            // threefold repetition
            if (node.threefold()) { return 0; }

            // no-legals-move exit checkmate
            if (l == 0) { return mate(); }

            //
            return qsmin(a, b, pv);
        }

        //
        if (TT.has(node.phk, d)) {
            return TT.min(node.phk, a, b);
        }

        //
        pv.clear();
                                
        //
        PV new_pv = PVs.pick();
        
        // assign move weight for ab search with sort
        Eval.legals(node); 
        
        // and sort
        Move m = node.legals.sort().twin();

        // tt exit flags
        int f = TT.E;

        //
        for (int i = 0; i != l; i++)
        {                 
            // make move
            node.domove(m, i);
                    
            int s = abmax(d - 1, a, b, new_pv, NT_NODE);

            // unmake move
            node.unmove();         

            // hard cut-off
            if (s <= a) { b = a; f = TT.A; break; }
                                                
            // soft cut-off
            if (s < b) { pv.cat(new_pv, m, i); b = s; TT.add(node.phk, d, TT.B, b); }
        } 

        //
        TT.add(node.phk, d, f, b);

        //
        Moves.free(m);
        
        //
        PVs.free(new_pv);

        //
        return b;    
    }

    // quiescence max search routine
    private int qsmax(int a, int b, final PV pv)
    {      
        // eval position
        int s = Eval.node(node);
        
        // quiescence need sort moves
        node.captures();
                       
        //
        final int l = node.captures.count;

        // hard cut-off
        if (s >= b) { return b; }
        
        // soft cut-off
        if (s > a) { a = s; }        
                                                               
        // term leaf quiescence search
        if (l == 0) { return a; }

        //
        nodes++;

        //
        pv.clear();

        //
        PV new_pv = PVs.pick();

        // 
        Capture c = node.captures.sort().twin();
                                
        //
        for (int i = 0; i != l; i++)
        {
            //
            node.domove(c, i);
                        
            //
            s = qsmin(a, b, new_pv);
            
            //
            node.unmove();
            
            // hard cut-off disabled in bruteforce mode
            if (s >= b) { a = b; break; }

            // soft cut-off
            if (s > a) { pv.cat(new_pv, c, i); a = s; }
        }
        
        //
        Captures.free(c);

        //
        PVs.free(new_pv);

        //
        return a;
    }
 
    // quiescence min search routine
    private int qsmin(int a, int b, final PV pv)
    {                        
        // eval position 
        int s = -Eval.node(node);
        
        // generate captures
        node.captures();
        
        // get number of captures
        final int l = node.captures.count;

        // return alfa if wrost
        if (s <= a) { return a; }

        // set new value for upper limit
        if (s < b) { b = s; }
                                                     
        // no capture cut-off
        if (l == 0) { return b; }

        // increase nodes count
        nodes++;

        //
        pv.clear();

        //
        PV new_pv = PVs.pick();

        // quiescenze need sort moves
        Capture c = node.captures.sort().twin();
                       
        // loop throut capturers
        for (int i = 0; i != l; i++) 
        {
            // make move 
            node.domove(c, i);
                        
            // iterate qsearch
            s = qsmax(a, b, new_pv);
            
            // redo move
            node.unmove();

            // hard cut-off disabled in bruteforce mode
            if (s <= a) { b = a; break; }        
                
            // soft cut-off
            if (s < b) { pv.cat(new_pv, c, i); b = s; }
        }
        
        //
        Captures.free(c);

        //
        PVs.free(new_pv);

        // 
        return b;
    }

    // get score for checkmate or stalemate node
    private int mate()
    {
        // in check is mate otherwise stalemate
        return node.legals.check ? checkmate + line_offset - node.L.i : stalemate;
    }
    
    // control search process
    private void control()
    {       
        // stop search if timeout exired
        if (timer.timeout()) { start = false; return; }

        // send speed info every 5 seconds
        if (timer.polling())
        {                                                       
            // calculate speed
            nps = timer.ratio(nodes);

            // send speed
            info("speed");
        }
    } 
    
    // run info callback
    private void info(String info)
    {
        //
        this.info = info;
        
        //
        send_info.accept(this);
    }
    
    // run send-best-move callable
    public void move(String move)
    {
        //
        this.move = move;
        
        //
        send_move.accept(this);
    }   
}
