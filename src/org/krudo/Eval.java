/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

//
import java.util.Map;
import java.util.LinkedHashMap;

// required static class
import static org.krudo.Config.*;
import static org.krudo.Constant.*;
import static org.krudo.Decode.*;
import static org.krudo.Tool.*;
import static org.krudo.Debug.*;
import static org.krudo.Zobrist.*;
import static org.krudo.Describe.*;

//
public final class Eval 
{    
    //
    private static final int EVAL_POSITION_SIZE = 100000;
    
    //
    private static final int EVAL_MATERIAL_SIZE = 1000;
    
    //
    private final static POSITION_CACHE POSITION = new POSITION_CACHE();
      
    //
    static class POSITION_CACHE extends LinkedHashMap<Long, Integer>    
    {
        //
        public int queries = 0;
        
        //
        public int success = 0;
        
        //
        public POSITION_CACHE()
        {
            super(EVAL_POSITION_SIZE, 0.95f, true);        
        }
        
        //
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, Integer> e) 
        {
            //
            return size() > EVAL_POSITION_SIZE; 
        }
        
        //
        public final void add(long h, int m)
        {           
            //
            if (CACHE_EVAL) 
            { 
                //
                super.put(h, m);                
            }        
        }

        //
        public final boolean has(long h) 
        {    
            //
            queries++;

            //
            if (CACHE_EVAL && super.containsKey(h)) 
            {
                //
                success++;

                //
                return true;
            }

            //
            return false; 
        }

        //
        public final int get(long h)
        {
            //
            if (CACHE_EVAL) 
            {
                //
                return super.get(h);
            }

            //
            return 0;
        }
    };
       
    //
    private final static MATERIAL_CACHE MATERIAL = new MATERIAL_CACHE(); 
    
    //
    static class MATERIAL_CACHE extends LinkedHashMap<Long, Integer> 
    {
        //
        public int queries = 0;
        
        //
        public int success = 0;
        
        //
        public MATERIAL_CACHE() 
        {
            //
            super(EVAL_MATERIAL_SIZE, 0.95f, true);
            
            //
            
        }
        
        //
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, Integer> e) 
        {
            //
            return size() > EVAL_POSITION_SIZE; 
        }
        
        //
        public final void add(long h, int m)
        {           
            //
            if (CACHE_EVAL) 
            { 
                //
                super.put(h, m);                
            }        
        }

        //
        public final boolean has(long h) 
        {    
            //
            queries++;

            //
            if (CACHE_EVAL && super.containsKey(h)) 
            {
                //
                success++;

                //
                return true;
            }

            //
            return false; 
        }

        //
        public final int get(long h)
        {
            //
            if (CACHE_EVAL) 
            {
                //
                return super.get(h);
            }

            //
            return 0;
        }
    };
    
    //
    public final static void info()
    {
        //
        print("Eval position (size:"+POSITION.size()+" q:"+POSITION.queries+" s:"+POSITION.success+")");
        
        //
        print("Eval material (size:"+MATERIAL.size()+" q:"+MATERIAL.queries+" s:"+MATERIAL.success+")");
    }    
    
    // opening piece sqaure weight
    public final static int[][] OPW = {       
        // black pawn from a8 to h1
        {
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,
            +0,    +0,    +0,    -2,    -2,    +0,    +0,    +0,              
            +0,    +0,    +0,    +14,   +15,   +0,    +0,    +0,
            +0,    +0,    +0,    +24,   +25,   +0,    +0,    +0,              
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,              
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,              
        },                
        // white pawn from a8 to h1
        {
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,              
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,
            +0,    +0,    +0,    +24,   +25,   +0,    +0,    +0,              
            +0,    +0,    +0,    +14,   +15,   +0,    +0,    +0,
            +0,    +0,    +0,    -2,    -2,    +0,    +0,    +0,              
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,     
        },                
        // black knight from a8 to h1
        {
            -30,   +0,    +0,    +0,    +0,    +0,    +0,    -30,    
            -20,   +0,    +0,    +0,    +0,    +0,    +0,    -20,    
            -10,   +0,    +12,   +0,    +0,    +11,   +0,    -10,    
            -10,   +0,    +0,    +0,    +0,    +0,    +0,    -10,    
            -10,   +0,    +0,    +14,   +0,    +0,    +0,    -10,    
            -10,   +0,    +0,    +0,    +0,    +0,    +0,    -10,    
            -10,   +0,    +0,    +0,    +0,    +0,    +0,    -20,    
            -30,   +0,    +0,    +0,    +0,    +0,    +0,    -30,    
        },            
        // white knight from a8 to h1
        {
            -30,   +0,    +0,    +0,    +0,    +0,    +0,    -30,    
            -20,   +0,    +0,    +0,    +0,    +0,    +0,    -20,    
            -10,   +0,    +0,    +0,    +0,    +0,    +0,    -10,    
            -10,   +0,    +0,    +0,    +14,   +0,    +0,    -10,    
            -10,   +0,    +0,    +0,    +0,    +0,    +0,    -10,    
            -10,   +0,    +11,   +0,    +0,    +12,   +0,    -10,    
            -20,   +0,    +0,    +0,    +0,    +0,    +0,    -20,    
            -30,   +0,    +0,    +0,    +0,    +0,    +0,    -30,    
        },            
        // black bishop from a8 to h1
        {
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +11,   +0,    +0,    +10,   +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
         },    
        // white bishop from a8 to h1
        {
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +11,   +0,    +0,    +10,   +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
        },    
        // black rook from a8 to h1
        {
            -2,    +0,    +0,    +7,    +7,    -1,    +0,    +0,    
            +0,    +0,    +0,    +10,   +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +10,   +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
        },    
        // white rook from a8 to h1
        {
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +10,   +0,    +0,    +0,    
            +0,    +0,    +0,    +10,   +0,    +0,    +0,    +0,    
            -2,    +0,    +0,    +7,    +7,    -1,    +0,    +0,    
         },    
        // black queen from a8 to h1
        {
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +8,    +4,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
        },            
        // white queen from a8 to h1
        {
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +8,    +4,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
        },            
        // black king from a8 to h1
        {
            +5,    +5,    +10,   -12,   -10,   -15,   +10,   +5,  
            +0,    +0,    -100,  -100,  -100,  -100,  +0,    +0,    
            +0,    +0,    -150,  -150,  -150,  -150,  +0,    +0,  
            +0,    +0,    -150,  -200,  -200,  -150,  +0,    +0,    
            +0,    +0,    -150,  -200,  -200,  -150,  +0,    +0,   
            +0,    +0,    -150,  -150,  -150,  -150,  +0,    +0,     
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,  
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
        },    
        // white king from a8 to h1
        {
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,  
            +0,    +0,    -150,  -150,  -150,  -150,  +0,    +0,  
            +0,    +0,    -150,  -200,  -200,  -150,  +0,    +0,    
            +0,    +0,    -150,  -200,  -200,  -150,  +0,    +0,   
            +0,    +0,    -150,  -150,  -150,  -150,  +0,    +0,    
            +0,    +0,    -100,  -100,  -100,  -100,  +0,    +0,    
            +5,    +5,    +10,   -12,   -10,   -15,   +10,   +5,  
        }
    }; 

    //
    public final static int[][] EPW = {       
        /*bp*/
        {
        +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,
        +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,
        +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
        +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,
        +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
        +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,
        +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
        +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
        },                
        /*wp*/
        {
        +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
        -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    
        +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
        +4,    +5,    +5,    +4,    +4,    +5,    +5,    +4,    
        +5,    +6,    +6,    +5,    +5,    +6,    +6,    +5,    
        +6,    +7,    +7,    +6,    +6,    +7,    +7,    +6,    
        +7,    +8,    +8,    +8,    +8,    +8,    +8,    +7,    
        +5,    +6,    +6,    +8,    +8,    +6,    +6,    +5,    
        },                
        /*wn*/
        {
        -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    
        -9,    +0,    +0,    +0,    +0,    +0,    +0,    -9,            
        -9,    +0,    +4,    +4,    +4,    +4,    +0,    -9,    
        -9,    +0,    +4,    +8,    +8,    +4,    +0,    -9,    
        -9,    +0,    +4,    +8,    +8,    +4,    +0,    -9,    
        -9,    +0,    +4,    +4,    +4,    +4,    +0,    -9,    
        -9,    +0,    +0,    +0,    +0,    +0,    +0,    -9,    
        -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    
        },            
        /*bn*/
        {
        -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    
        -9,    +0,    +0,    +0,    +0,    +0,    +0,    -9,            
        -9,    +0,    +4,    +4,    +4,    +4,    +0,    -9,    
        -9,    +0,    +4,    +8,    +8,    +4,    +0,    -9,    
        -9,    +0,    +4,    +8,    +8,    +4,    +0,    -9,    
        -9,    +0,    +4,    +4,    +4,    +4,    +0,    -9,    
        -9,    +0,    +0,    +0,    +0,    +0,    +0,    -9,    
        -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    
        },            
        /*bb*/
        {
        -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    
        -9,    +0,    +0,    +0,    +0,    +0,    +0,    -9,            
        -9,    +0,    +4,    +4,    +4,    +4,    +0,    -9,    
        -9,    +0,    +4,    +8,    +8,    +4,    +0,    -9,    
        -9,    +0,    +4,    +8,    +8,    +4,    +0,    -9,    
        -9,    +0,    +4,    +4,    +4,    +4,    +0,    -9,    
        -9,    +0,    +0,    +0,    +0,    +0,    +0,    -9,    
        -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    
        },    
        /*wb*/
        {
        -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    
        -9,    +0,    +0,    +0,    +0,    +0,    +0,    -9,            
        -9,    +0,    +4,    +4,    +4,    +4,    +0,    -9,    
        -9,    +0,    +4,    +8,    +8,    +4,    +0,    -9,    
        -9,    +0,    +4,    +8,    +8,    +4,    +0,    -9,    
        -9,    +0,    +4,    +4,    +4,    +4,    +0,    -9,    
        -9,    +0,    +0,    +0,    +0,    +0,    +0,    -9,    
        -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    
        },    
        /*br*/
        {
        -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    
        -9,    +0,    +0,    +0,    +0,    +0,    +0,    -9,            
        -9,    +0,    +4,    +4,    +4,    +4,    +0,    -9,    
        -9,    +0,    +4,    +8,    +8,    +4,    +0,    -9,    
        -9,    +0,    +4,    +8,    +8,    +4,    +0,    -9,    
        -9,    +0,    +4,    +4,    +4,    +4,    +0,    -9,    
        -9,    +0,    +0,    +0,    +0,    +0,    +0,    -9,    
        -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    
        },    
        /*wr*/
        {
        -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    
        -9,    +0,    +0,    +0,    +0,    +0,    +0,    -9,            
        -9,    +0,    +4,    +4,    +4,    +4,    +0,    -9,    
        -9,    +0,    +4,    +8,    +8,    +4,    +0,    -9,    
        -9,    +0,    +4,    +8,    +8,    +4,    +0,    -9,    
        -9,    +0,    +4,    +4,    +4,    +4,    +0,    -9,    
        -9,    +0,    +0,    +0,    +0,    +0,    +0,    -9,    
        -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    
        },    
        /*bq*/
        {
        -2,    -2,    -2,    -2,    -2,    -2,    -2,    -2,    
        -2,    +0,    +0,    +0,    +0,    +0,    +0,    -2,            
        -2,    +0,    +4,    +4,    +4,    +4,    +0,    -2,    
        -2,    +0,    +4,    +8,    +8,    +4,    +0,    -2,    
        -2,    +0,    +4,    +8,    +8,    +4,    +0,    -2,    
        -2,    +0,    +4,    +4,    +4,    +4,    +0,    -2,    
        -2,    +0,    +0,    +0,    +0,    +0,    +0,    -2,    
        -2,    -2,    -2,    -2,    -2,    -2,    -2,    -2,    
        },            
        /*wq*/
        {
        -2,    -2,    -2,    -2,    -2,    -2,    -2,    -2,    
        -2,    +0,    +0,    +0,    +0,    +0,    +0,    -2,            
        -2,    +0,    +4,    +4,    +4,    +4,    +0,    -2,    
        -2,    +0,    +4,    +8,    +8,    +4,    +0,    -2,    
        -2,    +0,    +4,    +8,    +8,    +4,    +0,    -2,    
        -2,    +0,    +4,    +4,    +4,    +4,    +0,    -2,    
        -2,    +0,    +0,    +0,    +0,    +0,    +0,    -2,    
        -2,    -2,    -2,    -2,    -2,    -2,    -2,    -2,    
        },            
        /*bk*/
        {
        -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    
        -9,    +0,    +0,    +2,    +2,    +0,    +0,    -9,            
        -9,    +0,    +4,    +6,    +6,    +4,    +0,    -9,    
        -9,    +0,    +4,    +8,    +8,    +4,    +0,    -9,    
        -9,    +0,    +4,    +8,    +8,    +4,    +0,    -9,    
        -9,    +0,    +4,    +6,    +6,    +4,    +0,    -9,    
        -9,    +0,    +0,    +2,    +2,    +0,    +0,    -9,    
        -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    
        },    
        /*wk*/
        {
        -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    
        -9,    +0,    +0,    +2,    +2,    +0,    +0,    -9,            
        -9,    +0,    +4,    +6,    +6,    +4,    +0,    -9,    
        -9,    +0,    +4,    +8,    +8,    +4,    +0,    -9,    
        -9,    +0,    +4,    +8,    +8,    +4,    +0,    -9,    
        -9,    +0,    +4,    +6,    +6,    +4,    +0,    -9,    
        -9,    +0,    +0,    +2,    +2,    +0,    +0,    -9,    
        -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    
        }
    }; 

    //
    static 
    {
        for (int p = 0; p < 12; p++)
        {
            for (int r = 0; r < 4; r++)
            {
                for (int c = 0; c < 8; c++)
                {
                    //
                    int s0 = r * 8 + c;
                    
                    //
                    int s1 = (7 - r) * 8 + c;
                    
                    //
                    int t0 = OPW[p][s0];
                    
                    //
                    OPW[p][s0] = OPW[p][s1];
                    
                    //
                    OPW[p][s1] = t0;
                    
                    //
                    int t1 = EPW[p][s0];
                    
                    //
                    EPW[p][s0] = EPW[p][s1];
                    
                    //
                    EPW[p][s1] = t1;
                }   
            }
        }    
        
        //
        for (int p = 0; p < 12; p++)
        {
            //
            for (int s = 0; s < 64; s++)
            {
                //
                if (p % 2 == 0) 
                {
                    //
                    OPW[p][s] = -OPW[p][s];

                    //
                    EPW[p][s] = -EPW[p][s];
                }

                //
                EPW[p][s] -= OPW[p][s];
            }
        }
    }
   
    // capture piece weight
    private final static int[] PW = {
        -100,    +100,    -300,   +300,    -305,    +305,    
        -500,    +500,    -900,   +900,    -6090,   +6090
    };
    
    // MVV/LAA 
    private final static int[][] ml = {
             /*bp wp bn wn bb wb br wr bq wq bk wk*/
        /*bp*/{+0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0},    
        /*wp*/{+0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0},    
        /*bn*/{+0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0},    
        /*wn*/{+0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0},    
        /*bb*/{+0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0},    
        /*wb*/{+0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0},    
        /*br*/{+5,    +5,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0},    
        /*wr*/{+5,    +5,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0},    
        /*bq*/{+2,    +2,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0},    
        /*wq*/{+2,    +2,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0},    
        /*bk*/{+0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0},    
        /*wk*/{+0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0}
    };    
    
    // weight of five-pawn hash structure
    private final static int[] psw = {
        /*00000*/+0,    /*00001*/-4,    /*00010*/-8,    /*00011*/+2,    
        /*00100*/-8,    /*00101*/-9,    /*00110*/+2,    /*00111*/+3,    
        /*01000*/-8,    /*01001*/-8,    /*01010*/-9,    /*01011*/-9,    
        /*01100*/+2,    /*01101*/-2,    /*01110*/+3,    /*01111*/+5,    
        /*10000*/-4,    /*10001*/-3,    /*10010*/-8,    /*10011*/-4,    
        /*10100*/-9,    /*10101*/-8,    /*10110*/-2,    /*10111*/-2,    
        /*11000*/+2,    /*11001*/-4,    /*11010*/-9,    /*11011*/+2,    
        /*11100*/+3,    /*11101*/-2,    /*11110*/+5,    /*11111*/+6,    
    };

    //
    public final static int node(final Node n) 
    {
        //
        if (MATERIAL.has(n.mhk)) { return MATERIAL.get(n.mhk); }
        
        //
        if (POSITION.has(n.phk)) { return POSITION.get(n.phk); } 
        
        //
        int w = cache_node(n);

        //
        POSITION.add(n.phk, w);

        //
        return w;
    }
    
    // eval 
    private static int cache_node(final Node n) 
    {
        // if node eval is enabled pass-throu else return zero forever
        if (!EVAL_NODE) { return 0; }
        
        //
        int score = 0;
        
        // index count squares
        int si = 0;
        
        // index count pieces
        int pi = n.cw + n.cb;
        
        // looking for pieces
        do 
        {        
            // next observed square
            final int s = n.bm[si++];          

            // get piece in start square
            final int p = n.B[s];
            
            // square not have a side to move piece skip
            if (p == O) { continue; }
            
             //
            final int i = p & lo;
                
            // decrease piece count
            pi--;
            
            // remap square in wbm 
            if (REMAPS_EVAL) { n.remaps(si, pi, s); }

            // piece value
            score += PW[i];
            
            //
            score += OPW[i][s];
        } 
        
        //
        while (pi != 0);
        
        //
        return n.t == w ? score : -score;
    }        
    
    // score move stack for first time use in search
    public final static void move(final Node n)
    {  
        //
        int b = node(n);
        
        //
        for (int i = 0; i < n.m.i; i++)            
        {
            //
            int score = b;
            
            //            
            final int s = n.m.s[i];         
            
            // get versus square
            final int v = n.m.v[i];
            
            // get moved piece
            final int p = n.B[s] & lo;
                      
            // get captured piece
            final int x = n.B[v] & lo;
                                  
            //
            if (EVAL_POSITIONAL)
            {
                score += OPW[p][v] - OPW[p][s];
            }
            
            /*
            //
            if (EVAL_MVV_LAA) {
                w += x != O ? ml[p][x] : 0;
            }
            
            //
            if (EVAL_CAPTURE) {
                w += cw[x];
            }
            
            //
            if (EVAL_TAPERED_ENDING) {
                w += ew[p][v] >> op[n.cw];
            }
            
            //
            if (EVAL_TAPERED_OPENING) {
                w += ow[p][v] >> ep[n.cw];
            }
            */
            
            //print(s2s(s)+s2s(v)+"="+OPW[p][v]);
            
            // assign tapered value
            n.m.w[i] = n.t == w ? score : -score;
        }
        
    }
      
    //
    public final static void walk(final Node n, int deep, int width)
    {
        if (deep == 0) {
            dump(n);
            return;
        }
        
        //
        Move m = n.legals().sort();
    
        //
        int w = m.i > width ? width : m.i;
        
        //
        for (int i = 0; i < w; i++) 
        {
            n.domove(m, i);
            
            walk(n, deep - 1, width);
            
            n.unmove();
            
            if (deep == 1) {
                dump(m);
                print("\n");
            }
        }
        
        Moves.free(m);
    }
}
