/**
 * Krudo 0.18a - Java chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.debug;

//
import org.krudo.*;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;

//
import static org.krudo.Tool.*;
import static org.krudo.tests.debug.Reflect.*;

//
public class Info 
{        
    //
    public static void info_moves() 
    {
        int count = get_field_value_as_int(Moves.class, "count");
        int stack = get_field_value_as_int(Moves.class, "STACK_SIZE");
                     
        print("@Moves: count="+count+"/"+stack);
    }
    
    //
    public static void info_legals() 
    {       
        LinkedHashMap<Long, Move> cache = get_field_value_as_linked_hash_map(Legals.class, "CACHE");       
        Map.Entry[] table = get_field_value_as_map_entry_array(
            HashMap.class, "table", cache);        
        //print(cache.getClass().getSuperclass().getSuperclass().getName());                       
        print("@Legals: table="+table.length);
    }

    //
    public static void info_captures() 
    {
        int count = get_field_value_as_int(Captures.class, "count");
        Capture[] stack = get_field_value_as_capture_array(Captures.class, "STACK", null);
        LinkedHashMap<Long, Move> cache = get_field_value_as_linked_hash_map(Captures.class, "CACHE");       
        Map.Entry[] table = get_field_value_as_map_entry_array(HashMap.class, "table", cache);        
        //print(cache.getClass().getSuperclass().getSuperclass().getName());                       
        print("@Captures: stack="+count+"/"+stack.length+" cache="+cache.size()+"/"+table.length);
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
