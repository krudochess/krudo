/**
 * Krudo 0.18a - Java chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.test.debug;

//
import org.krudo.*;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;

//
import static org.krudo.Tool.*;
import static org.krudo.test.debug.Reflect.*;

//
public class Info 
{
    //
    public static int debug_get_moves_count()
    {
        return debug_get_declared_field_as_int(Moves.class, "count");
    }

    //
    public static void debug_print_moves_info()
    {
        int count = debug_get_moves_count();
        int stack = debug_get_declared_field_as_int(Moves.class, "STACK_SIZE");
                     
        print("@Moves: count="+count+"/"+stack);
    }
    
    //
    public static void info_legals() 
    {       
        LinkedHashMap<Long, Move> cache = debug_get_declared_field_as_linked_hash_map(Legals.class, "CACHE");
        Map.Entry[] table = debug_get_field_value_as_map_entry_array(HashMap.class, "table", cache);

        print("@Legals: table="+table.length);
    }

    //
    public static int[] debug_captures_get_info()
    {
        int count = debug_get_declared_field_as_int(Captures.class, "count");
        Capture[] stack = debug_get_declared_field_as_capture_array(Captures.class, "STACK");
        LinkedHashMap<Long, Move> cache = debug_get_declared_field_as_linked_hash_map(Captures.class, "CACHE");
        Map.Entry[] table = debug_get_field_value_as_map_entry_array(HashMap.class, "table", cache);

        return new int[] {
            count,
            stack.length,
            cache.size(),
            table.length
        };
    }

    //
    public static void debug_captures_info()
    {
        //
        int[] info = debug_captures_get_info();

        //
        print("@Captures: stack=" + info[0] + "/" + info[1] + " cache=" + info[2] + "/" + info[3]);
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
    
    
    /*
      //INFO EVAL
    public final static void info()
    {
        //
        print("Eval position (size:"+POSITION.size()+" q:"+POSITION.queries+" s:"+POSITION.success+")");
        
        //
        print("Eval material (size:"+MATERIAL.size()+" q:"+MATERIAL.queries+" s:"+MATERIAL.success+")");
    }    
    */



    //
    public static void debug_pvs_info()
    {
        /*
        print(
                "PV free",
                count,
                count == PV_STACK_SIZE ? "(PERFECT!!)" : "(PROBLEM??)"
        );*/
    }

}
