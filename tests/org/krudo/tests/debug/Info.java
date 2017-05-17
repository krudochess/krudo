package org.krudo.tests.debug;

import static org.krudo.Tool.*;
import static org.krudo.tests.debug.Reflect.*;

import org.krudo.*;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;
import static org.krudo.Describe.desc;


public class Info 
{        
    public static void info_moves() 
    {
        int count = get_field_value_as_int(Moves.class, "count");
        int stack = get_field_value_as_int(Moves.class, "MOVES_STACK_SIZE");
                     
        print("@Moves: count="+count+"/"+stack);
    }
    
    public static void info_legals() 
    {
        Moves.init();
        Legals.add(0, Moves.pick());
        
        LinkedHashMap<Long, Move> cache = get_field_value_as_linked_hash_map(Legals.class, "CACHE");
       
        Map.Entry[] table = get_field_value_as_map_entry_array(
            HashMap.class, "table", cache);
        

        //print(cache.getClass().getSuperclass().getSuperclass().getName());
        
       
        
        print("@Legals: table="+table.length);
    }
    
    /*
    
    **** FUNZIONI PER Legals 
        //
    public final static void dump()
    {
        //
        print("Legals (size:"+CACHE.size()+")");
        
        //
        CACHE.entrySet().stream().map((i) -> {
            print(Long.toHexString(i.getKey()));
            return i;
        }).forEach((i) -> {    
            print(desc(i.getValue()));
        });
    }
    
    //
    public final static void info()
    {
        //
        print("Legals (size:"+CACHE.size()+" q:"+queries+" s:"+success+")");
    }   
    
    public final static int size()
    {
        return CACHE.size();
    }
    */
}
