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
    private static final int EVAL_POSITION_SIZE = 50000;
    
    //
    private static final int EVAL_MATERIAL_SIZE = 1000;
    
    //
    private final static POSITION_CACHE POSITION = new POSITION_CACHE();
    //
    public static final int[] OTEW = new int[] {0, 0, 10, 10, 12, 12, 21, 21, 42, 42, 0, 0};
      
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
            +0,    +0,    +0,    -2,    -2,    +10,   +0,    +0,              
            +0,    +0,    +10,   +24,   +25,   +0,    +0,    +0,
            +0,    +0,    +20,   +34,   +35,   +0,    +0,    +0,              
            +0,    +0,    +30,   +44,   +45,   +0,    +0,    +0,
            +0,    +0,    +50,   +64,   +65,   +50,   +0,    +0,              
            +0,    +0,    +60,   +84,   +85,   +60,   +0,    +0,
            +0,    +0,    +100,  +200,  +200,  +100,  +0,    +0,              
        },                
        // white pawn from a8 to h1
        {
            +0,    +0,    +100,  +200,  +200,  +100,  +0,    +0,
            +0,    +0,    +70,   +84,   +85,   +60,   +0,    +0,              
            +0,    +0,    +50,   +64,   +65,   +50,   +0,    +0,
            +0,    +0,    +30,   +44,   +45,   +0,    +0,    +0,
            +0,    +0,    +20,   +34,   +25,   +0,    +0,    +0,              
            +0,    +0,    +10,   +24,   +15,   +0,    +0,    +0,
            +0,    +0,    +0,    -2,    -2,    +10,   +0,    +0,              
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,     
        },                
        // opening black knight from a8 to h1
        {
            -30,   -10,   -20,   -20,   -20,   -20,   -10,   -30,    
            -20,   +0,    +0,    +0,    +0,    +0,    +0,    -20,    
            -10,   +0,    +12,   +0,    +0,    +11,   +0,    -10,    
            -10,   +0,    +5,    +10,   +10,   +5,    +0,    -10,    
            -10,   +0,    +5,    +24,   +20,   +5,    +0,    -10,    
            -10,   +0,    +5,    +10,   +10,   +5,    +0,    -10,    
            -20,   +0,    +50,   +0,    +0,    +50,   +0,    -20,    
            -30,   +0,    +0,    +0,    +0,    +0,    +0,    -30,    
        },            
        // opening white knight from a8 to h1
        {
            -30,   +0,    +0,    +0,    +0,    +0,    +0,    -30,    
            -20,   +0,    +50,   +0,    +0,    +50,   +0,    -20,    
            -10,   +0,    +5,    +10,   +10,   +5,    +0,    -10,    
            -10,   +0,    +5,    +20,   +24,   +5,    +0,    -10,    
            -10,   +0,    +5,    +10,   +10,   +5,    +0,    -10,    
            -10,   +0,    +11,   +0,    +0,    +12,   +0,    -10,    
            -20,   +0,    +0,    +0,    +0,    +0,    +0,    -20,    
            -30,   -10,   -20,   -20,   -20,   -20,   -10,   -30,    
        },            
        // black bishop from a8 to h1
        {
            +0,    +0,    -10,   +0,    +0,    -10,   +0,    +0,    
            +0,    +5,    +0,    +0,    +0,    +0,    +20,   +0,    
            +0,    +0,    +0,    +5,    +5,    +0,    +0,    +5,    
            +0,    +0,    +11,   +0,    +0,    +10,   +0,    +0,    
            +0,    +10,   +0,    +0,    +0,    +0,    +11,   +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +8,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
         },    
        // white bishop from a8 to h1
        {
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +8,    
            +0,    +10,   +0,    +0,    +0,    +0,    +10,   +0,    
            +0,    +0,    +11,   +0,    +0,    +10,   +0,    +0,    
            +0,    +0,    +0,    +5,    +5,    +0,    +0,    +5,    
            +0,    +5,    +0,    +0,    +0,    +0,    +20,   +0,    
            +0,    +0,    -10,   +0,    +0,    -10,   +0,    +0,    
        },    
        // black rook from a8 to h1
        {
            -2,    +0,    +0,    +7,    +7,    -1,    +0,    +0,    
            +0,    +0,    +0,    +10,   +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +10,   +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +30,   +30,   +30,   +30,   +30,   +30,   +30,   +30,    
            +40,   +40,   +40,   +40,   +40,   +40,   +40,   +40,    
        },    
        // white rook from a8 to h1
        {
            +40,   +40,   +40,   +40,   +40,   +40,   +40,   +40,    
            +30,   +30,   +30,   +30,   +30,   +30,   +30,   +30,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    +0,    +10,   +0,    +0,    +0,    
            +0,    +0,    +0,    +10,   +0,    +0,    +0,    +0,    
            -2,    +0,    +0,    +7,    +7,    -1,    +0,    +0,    
         },    
        // opening black queen from a8 to h1
        {
            +0,    +0,    +0,    -2,    +0,    +0,    +0,    +0,    
            +0,    +0,    +10,   +0,    +0,    +0,    +0,    +0,    
            +0,    +10,   +0,    +18,   +4,    +10,   +0,    +0,    
            +6,    +12,   +14,   +16,   +14,   +12,   +10,   +5,    
            +6,    +12,   +14,   +16,   +14,   +12,   +10,   +5,    
            +20,   +30,   +32,   +14,   +12,   +30,   +30,   +20,    
            +30,   +40,   +40,   +40,   +40,   +40,   +40,   +30,    
            +5,    +10,   +10,   +10,   +10,   +10,   +10,   +5,    
        },            
        // opening white queen from a8 to h1
        {
            +5,    +10,   +10,   +10,   +10,   +20,   +10,   +5,    
            +30,   +40,   +40,   +40,   +40,   +40,   +40,   +30,    
            +20,   +30,   +32,   +14,   +12,   +30,   +30,   +20,    
            +6,    +12,   +14,   +16,   +14,   +12,   +10,   +5,    
            +6,    +12,   +14,   +16,   +14,   +12,   +10,   +5,    
            +0,    +10,   +0,    +18,   +4,    +10,   +0,    +0,    
            +0,    +0,    +10,   +0,    +0,    +0,    +0,    +0,    
            +0,    +0,    +0,    -2,    +0,    +0,    +0,    +0,    
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
        +0,    +0,    +0,    +40,   +40,   +0,    +0,    +0,
        +10,   +10,   +20,   +60,   +60,   +20,   +10,   +10,
        +30,   +40,   +50,   +80,   +80,   +50,   +40,   +30,
        +50,   +80,   +90,   +100,  +100,  +90,   +80,   +50,    
        +100,  +180,  +190,  +200,  +200,  +190,  +180,  +100,    
        },                
        /*wp*/
        {
        +100,  +180,  +190,  +200,  +200,  +190,  +180,  +100,    
        +50,   +80,   +90,   +100,  +100,  +90,   +80,   +50,    
        +30,   +40,   +50,   +80,   +80,   +50,   +40,   +30,
        +10,   +10,   +20,   +60,   +60,   +20,   +10,   +10,
        +0,    +0,    +0,    +40,   +40,   +0,    +0,    +0,    
        +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,
        +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
        +0,    +0,    +0,    +0,    +0,    +0,    +0,    +0,    
        },                
        /*wn*/
        {
        -9,    -9,    -9,    -9,    -9,    -9,    -9,    -9,    
        -9,    +0,    +0,    +0,    +0,    +0,    +0,    -9,            
        -9,    +0,    +4,    +14,   +14,   +4,    +0,    -9,    
        -9,    +0,    +4,    +28,   +28,   +4,    +0,    -9,    
        -9,    +0,    +4,    +28,   +28,   +4,    +0,    -9,    
        -9,    +0,    +4,    +14,   +14,   +4,    +0,    -9,    
        -9,    +0,    +0,    +0,    +0,    +0,    +0,    -9,    
        -29,   -29,   -29,   -29,   -29,   -29,   -29,   -29,    
        },            
        /*bn*/
        {
        -29,   -29,   -29,   -29,   -29,   -29,   -29,   -29,    
        -9,    +0,    +0,    +0,    +0,    +0,    +0,    -9,            
        -9,    +0,    +4,    +14,   +14,   +4,    +0,    -9,    
        -9,    +0,    +4,    +28,   +28,   +4,    +0,    -9,    
        -9,    +0,    +4,    +28,   +28,   +4,    +0,    -9,    
        -9,    +0,    +4,    +14,   +14,   +4,    +0,    -9,    
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
        -99,   -99,   -59,   -59,   -59,   -59,   -99,   -99,    
        -99,   -59,   -59,   -59,   -59,   -59,   -59,   -99,    
        -90,   -20,   +40,   +60,   +60,   +40,   -20,   -90,    
        -90,   -20,   +40,   +80,   +80,   +40,   -20,   -90,    
        -90,   -20,   +40,   +80,   +80,   +40,   -20,   -90,    
        -90,   -20,   +40,   +60,   +60,   +40,   -20,   -90,    
        -99,   -59,   -59,   -59,   -59,   -59,   -59,   -59,    
        -99,   -99,   -59,   -59,   -59,   -59,   -59,   -59,    
        },    
        /*wk*/
        {
        -99,   -99,   -59,   -59,   -59,   -59,   -99,   -99,    
        -99,   -59,   -59,   -59,   -59,   -59,   -59,   -99,    
        -90,   -20,   +40,   +60,   +60,   +40,   -20,   -90,    
        -90,   -20,   +40,   +80,   +80,   +40,   -20,   -90,    
        -90,   -20,   +40,   +80,   +80,   +40,   -20,   -90,    
        -90,   -20,   +40,   +60,   +60,   +40,   -20,   -90,    
        -99,   -59,   -59,   -59,   -59,   -59,   -59,   -99,    
        -99,   -99,   -59,   -59,   -59,   -59,   -99,   -99,    
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

    //
    private final static int[][] MAW = {
        //bp wp bn wn bb wb br wr bq wq bk wk score
        { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1,  +0 }  
    };
    
    // capture piece weight
    private final static int[] PW = {
        -100,    +100,    -285,   +285,    -305,    +305,    
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
    private final static int[] QPSW = {
        /*00000*/+0,    /*00001*/-0,    /*00010*/-0,    /*00011*/+18,    
        /*00100*/+0,    /*00101*/-0,    /*00110*/+18,   /*00111*/+0,    
        /*01000*/-0,    /*01001*/-0,    /*01010*/-0,    /*01011*/-0,    
        /*01100*/+15,   /*01101*/-0,    /*01110*/+0,    /*01111*/+0,    
        /*10000*/-0,    /*10001*/-0,    /*10010*/-0,    /*10011*/-0,    
        /*10100*/-0,    /*10101*/-0,    /*10110*/-0,    /*10111*/-0,    
        /*11000*/+12,   /*11001*/-0,    /*11010*/-0,    /*11011*/+0,    
        /*11100*/+0,    /*11101*/-0,    /*11110*/+0,    /*11111*/+0,    
    };
    
    // weight of five-pawn hash structure
    private final static int[] KPSW = {
        /*00000*/+0,    /*00001*/-0,    /*00010*/-0,    /*00011*/+12,    
        /*00100*/+0,    /*00101*/-0,    /*00110*/+15,   /*00111*/+0,    
        /*01000*/+0,    /*01001*/-0,    /*01010*/-0,    /*01011*/-0,    
        /*01100*/+18,   /*01101*/-0,    /*01110*/+0,    /*01111*/+0,    
        /*10000*/-0,    /*10001*/-0,    /*10010*/-0,    /*10011*/-0,    
        /*10100*/-0,    /*10101*/-0,    /*10110*/-0,    /*10111*/-0,    
        /*11000*/+18,   /*11001*/-0,    /*11010*/-0,    /*11011*/+0,    
        /*11100*/+0,    /*11101*/-0,    /*11110*/+0,    /*11111*/+0,    
    };
    
    // weight of five-pawn hash structure
    private final static int[] QPPW = {
        /*00000*/+0,    /*00001*/+30,   /*00010*/+10,   /*00011*/+0,    
        /*00100*/+26,   /*00101*/-0,    /*00110*/+0,    /*00111*/+0,    
        /*01000*/+24,   /*01001*/-0,    /*01010*/-0,    /*01011*/-0,    
        /*01100*/+45,   /*01101*/-0,    /*01110*/+0,    /*01111*/+0,    
        /*10000*/+20,   /*10001*/-0,    /*10010*/-0,    /*10011*/-0,    
        /*10100*/+10,   /*10101*/-0,    /*10110*/-0,    /*10111*/-0,    
        /*11000*/+20,   /*11001*/-0,    /*11010*/-0,    /*11011*/+0,    
        /*11100*/+0,    /*11101*/-0,    /*11110*/+0,    /*11111*/+0,    
    };
    
    // weight of five-pawn hash structure
    private final static int[] KPPW = {
        /*00000*/+0,    /*00001*/+20,   /*00010*/+24,   /*00011*/+20,    
        /*00100*/+26,   /*00101*/+10,   /*00110*/+45,   /*00111*/+0,    
        /*01000*/+10,   /*01001*/-0,    /*01010*/-0,    /*01011*/-0,    
        /*01100*/+0,    /*01101*/-0,    /*01110*/+0,    /*01111*/+0,    
        /*10000*/+30,   /*10001*/-0,    /*10010*/-0,    /*10011*/-0,    
        /*10100*/-0,    /*10101*/-0,    /*10110*/-0,    /*10111*/-0,    
        /*11000*/+0,    /*11001*/-0,    /*11010*/-0,    /*11011*/+0,    
        /*11100*/+0,    /*11101*/-0,    /*11110*/+0,    /*11111*/+0,    
    };
    
    //
    public final static void init()
    {
        //
        for (int[] material : MAW) {
            MATERIAL.add(hash_material(material), material[12]);
        }
    }
    
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
        if (EVAL_MOBILITY) 
        { 
           
        }
        
        // if node eval is enabled pass-throu else return zero forever
        if (!EVAL_NODE) { return 0; }
        
        //
        //n.legals();
        
        // eval checkmate or stalemate conditions
        //if (n.legals.i == 0) { return n.legals.c ? -mate + n.L.i : 0; }
                
        //
        int wps = 0;
        
        //
        int bps = 0;
        
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
            score += OPW[i][s] + ((EPW[i][s] * n.ote) >> 8);    
        } 
        
        //
        while (pi != 0);
        
        //
        score += pawn_structure(wps, bps);
        
        //
        return n.t == w ? score : -score;
    }        
    
    // score move stack for first time use in search
    public final static void move(final Node n)
    {  
        //
        int b = node(n);
        
        //
        for (int i = 0; i < n.legals.i; i++)            
        {
            //
            int score = b;
            
            //            
            final int s = n.legals.s[i];         
            
            // get versus square
            final int v = n.legals.v[i];
            
            // get moved piece
            final int p = n.B[s] & lo;
                      
            // get captured piece
            final int x = n.B[v] & lo;
                                  
            //
            if (EVAL_POSITIONAL)
            {
                //
                score += OPW[p][v] + ((EPW[p][v] * n.ote) >> 8); 
                
                //
                score -= OPW[p][s] + ((EPW[p][s] * n.ote) >> 8);
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
            n.legals.w[i] = n.t == w ? score : -score;
        }   
    }
      
    //
    public final static void walk(final Node n, int deep, int width)
    {
        //
        if (deep == 0) {
            dump(n);
            return;
        }
       
        //
        n.legals();
        
        //
        Move m = n.legals.sort().duplicate();
    
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
    
    //
    public final static int pawn_structure(int wps, int bps)
    {
        //
        int wsw = QPSW[wps >> 3] + KPSW[wps & 0b11111];
        
        //
        int bsw = QPSW[bps >> 3] + KPSW[bps & 0b11111];
        
        //
        int wis = ~(bps|(bps>>1)|(bps<<1)) & wps;
        
        //
        int bis = ~(wps|(wps>>1)|(wps<<1)) & bps;
                
        //
        int wiw = QPPW[wis >> 3] + KPPW[wis & 0b11111];
        
        //
        int biw = QPPW[bis >> 3] + KPPW[bis & 0b11111];
        
        // used for tuning
        if (false) 
        {
            //
            print("black struct:", bin(bps, 8), bsw);
            print("white struct:", bin(wps, 8), wsw);
            print(bin(bps >> 3, 5), bin(bps & 0b11111, 5));
            print(bin(wps >> 3, 5), bin(wps & 0b11111, 5));
            print("-");
            print("black passed:", bin(bis, 8), biw);
            print("white passed:", bin(wis, 8), wiw);
            print(bin(bis >> 3, 5), bin(bis & 0b11111, 5));
            print(bin(wis >> 3, 5), bin(wis & 0b11111, 5));
            print("-");
        }
        
        //
        return wsw + wiw - bsw - biw;
    }
}
