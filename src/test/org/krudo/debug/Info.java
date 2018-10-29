/**
 * Krudo 0.18a - Java chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.debug;

//
import org.krudo.*;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;

//
import static org.krudo.Tool.*;
import static org.krudo.debug.Reflect.*;

//
public class Info 
{
    // get info about moves stacks
    public static int[] debug_moves_get_info()
    {
        //
        int count = debug_get_declared_field_as_int(Moves.class, "count");
        int stack = debug_get_declared_field_as_int(Moves.class, "SIZE");

        //
        return new int[] {
            count,
            stack
        };
    }

    // print moves info
    public static void debug_moves_info()
    {
        int[] info = debug_moves_get_info();

        print("@Moves: count="+info[0]+"/"+info[1]);
    }

    //
    public static int[] debug_legals_get_info()
    {
        //
        int size = debug_get_declared_field_as_int(Legals.class, "SIZE");
        LinkedHashMap<Long, Move> cache = debug_get_declared_field_as_linked_hash_map(Legals.class, "CACHE");
        Map.Entry[] table = debug_get_field_value_as_map_entry_array(HashMap.class, "table", cache);

        //
        return new int[] {
            cache.size(),
            size,
            table.length
        };
    }

    //
    public static void debug_legals_info()
    {
        int[] info = debug_legals_get_info();

        print("@Legals:", "cache="+info[0]+"/"+info[1]  , "table="+info[2]);
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
        print("EvalUtility position (size:"+POSITION.size()+" q:"+POSITION.queries+" s:"+POSITION.success+")");
        
        //
        print("EvalUtility material (size:"+MATERIAL.size()+" q:"+MATERIAL.queries+" s:"+MATERIAL.success+")");
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
