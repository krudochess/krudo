/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

// required static class
import static org.krudo.Config.*;
import static org.krudo.Constant.*;
import static org.krudo.Decode.*;
import static org.krudo.Describe.*;
import static org.krudo.Tool.*;
import static org.krudo.Debug.*;
import static org.krudo.Zobrist.hash;

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
    public int lineStart = 0;
    
    //
    public int score;
        
    // iterative deepiing deep cursor and limit
    public int deep_index; // current depth in iterative dee 
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
    public long ns;
    public long nt;
    public long node_bound;
    
    //
    public long nps;
    
    // count nodes for quiesce search
    public int nq;
            
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
        //if (info_event.equals(becs))
        
        //
        print("INFO: " + info_event + " " + info_message);
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
    public final void start(int deep, long time) 
    {            
        // reset stop flag
        stop = NOT;    
        
        // place offset for search variation
        lineStart = n.L.i;
        
        // deep start value for iterative deeping
        deep_start = 1;
        
        // set deep limit for iterative deeping
        deep_limit = deep;
                    
        // set time start for required log or other
        time_start = time();
        
        // set time limit for the searcing engine routine
        time_limit = time_start + time;

        // start iterative deeping search
        idrun();
    }
         
    // iterative deeping entry-point
    private void idrun() 
    {                           
        //
        id_timer.start();
                
        // iterative deeping deep start value
        deep_index = deep_start;
        
        // empity moves stack
        //move.empty();
            
        // iterative deeping alpha start value
        int alfa = -oo;
        
        // iterative deeping beta start value
        int beta = +oo;
        
        //
        int score = alfa;
        
        //
        PV pv = new PV();
                        
        //
        sendinfo("id-run", ""+deep_limit);
        
        // iterative deeping loop
        while (deep_index <= deep_limit && stop == NOT)
        {                                        
            //
            long t = time();
            
            //
            sendinfo("id-loop-run", deep_index+"/"+deep_limit);
           
            // launch alfa-beta for searcing candidates 
            score = abrun(alfa, beta, pv);        

            //
            if (stop == YES) 
            {                 
                //
                sendinfo("id-loop-break", "");
            
                //
                break; 
            }
                
            //
            best_pv.copy(pv);
            
            //
            best_move = desc(best_pv, 0);
            
            //
            best_score = score;                    
                                        
            //
            nps = ab_timer.delta > 0 ? ns / ab_timer.delta : 0;
            
            //
            sendinfo("id-loop-end", deep_index+"/"+deep_limit+" "+desc(pv)+" "+ab_timer.delta+"ms "+ns+"n "+nps+"knps");
              
            // increade depth of search
            deep_index++;                        
        }    
        
        //
        id_timer.pause();
        
        //
        sendinfo("id-end", id_timer.delta+"ms "+score+" "+desc(best_pv));
                
        //
        sendbestmove();
    }
    
    // alfa-beta entry-point
    private int abrun(int a, int b, PV pv) 
    {    
        // reset nodes search counter
        ns = 0;

        // reset nodes quiesce counter
        nq = 0;
            
        //
        nt = time() + 4000;
        
        //
        ab_timer.start();
                       
        //
        pv.i = 0;
        
        //
        PV new_pv = new PV();
        
        // generate and sort legal-moves
        Move m = n.legals();
              
        // no legal moves check-mate or stale-mate
        if (m.i == 0) 
        { 
            return -mate; 
        }
                
        // 
        m.sort();
        
        //
        for (int i = 0; i < m.i; i++) 
        {   
            //
            //info("ab-loop-run", m2s(m, i));
            
            // make
            n.domove(m, i);
            
            // recursive evaluation search                    
            int s = abmin(deep_index - 1, a, b, new_pv, NT_NODE);

            // undo
            n.unmove();            
            
            /*
            // use null window for correction
            if (s > a) 
            {
                s = abmin(d-1, a, b);                
            }
            */ 
                
            // hard cut-off
            if (s >= b && !SEARCH_BRUTE_FORCE) 
            {  
                sendinfo("ab-hard-cut-off","");
                //t.store(d, a, t.BETA); 
                //return b; 
                
                //
                a = b;
                
                //
                break;
            }
            
            // soft alfa-cut-off 
            if (s > a) 
            {
                sendinfo("ab-soft-cut-off", m2s(m, i)+"="+s+" ["+a+";"+b+"]");
                //
                pv.cat(new_pv, m, i);
                //dump(pv);
                //pv[d].set(n.L, zero);
                //log(SEARCH_LOG_UP, pv, 1,d,s);
                //find.put(m,j,s);         
                //m.w[i] = s;
                               
                a = s;                
            }   
            
            // log move wight
            //log(SEARCH_LOG_MW, m, i, w);            
        } 
        
        //
        Moves.free(m);
       
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
        
        // prepare traspoisiont table for current hash
        //t.begin(hash(n));
                
        // trasposition table probe
        //if (Tables.probe(n.h, d, a, b)) {         
        //}
        
        // return quiescence value-search, 
        if (d == 0) 
        { 
            //
            ns++;
             
            //
            abcontrol();
            
            //
            s = qmax(a, b, pv);
            
            //t.store(d, w, t.EXACT); 
            
            //
            return s; 
        }
        
        //
        pv.i = 0;
        
        //
        PV new_pv = new PV();
        
        // get legal-moves  
        Move m = n.legals();
                
        // and sort       
        m.sort();
        
        // no legal moves check-mate or stale-mate
        //if (m.l == 0) { return -mate; }
                                  
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
                a = b;
                
                //
                break;
            }
        
            // soft cut-off
            if (s > a) 
            {                         
                // t.exact();
              
                //
                pv.cat(new_pv, m, i);
                
                //
                a = s;             
            }
        }
        
        //
        // t.store(d, a);
        
        //
        Moves.free(m);
        
        //
        return a;        
    }

    // alfa-beta min routine
    private int abmin(final int d, int a, int b, final PV pv, final int nt)
    {                        
        //
        int s; 

        // at-end quiescence search and 
        if (d == 0) 
        {
            // increase nodes count
            ns++;
            
            //
            abcontrol();
            
            // return quesence values
            return qmin(a, b, pv); 
        }
        
        //
        pv.i = 0;
        
        //
        PV new_pv = new PV();
        
        // generate legal-moves 
        Move m = n.legals();

        // no-legals-move exit checkmate
        if (m.i == 0) { return +mate - d; }
        
        // and sort
        m.sort();        
            
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
                //dump(new_pv);
                //dump(pv);
                
                //
                b = s;                       
            }                    
        } 
        
        //
        Moves.free(m);
   
        //
        return b;    
    }

    // quiescence max routine
    private int qmax(int a, int b, PV pv)
    {                   
        // eval position
        int s = Eval.node(n);
                                
        // hard cut-off
        if (s >= b) { return b; }
        
        // 
        if (!SEARCH_QUIESCENCE) { return s; }
        
        // soft cut-off
        if (s > a) { a = s; }
        
        //
        pv.i = 0;
        
        //
        PV new_pv = new PV();
                
        // quiescence need sort moves
        Capture c = n.capture();
        
        // 
        c.sort();
                               
        //
        for (int i = 0; i < c.i; i++)  
        {
            //
            n.domove(c, i);
                        
            //
            s = qmin(a, b, new_pv);
            
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
        return a;
    }
 
    // quiescence min search
    private int qmin(int a, int b, PV pv) 
    {        
        // increase nodes count
        nq++;
                
        // eval position 
        int s = -Eval.node(n);
        
        // return alfa if wrost
        if (s <= a) { return a; }

        //
        if (!SEARCH_QUIESCENCE) { return s; }
        
        // set new value for upper limit
        if (s < b) { b = s; }
              
        //
        pv.i = 0;
        
        //
        PV new_pv = new PV();
        
        // quiescenze need sort moves
        Capture c = n.capture();
        
        //
        c.sort();
                        
        // loop throut capturers
        for (int i = 0; i < c.i; i++) 
        {
            // make move 
            n.domove(c, i);
                        
            // iterate qsearch
            s = qmax(a, b, new_pv);
            
            // redo move
            n.unmove();

            // hard cut-off
            if (s <= a  && !SEARCH_BRUTE_FORCE)
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
        return b;
    }
    
    //
    private void abcontrol()
    {       
        //
        long time = time();
        
        // 
        if (time > time_limit) { stop = YES; return; }

        //
        if (time < nt) { return; } 
                                  
        //
        nt = time + INFO_MILLISECONDS_POLLING;
        
        //
        nps = ns / (time - ab_timer.start);
        
        //        
        sendinfo("ab-speed", "nps "+nps);
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
        Move m = n.legals().sort();
    
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