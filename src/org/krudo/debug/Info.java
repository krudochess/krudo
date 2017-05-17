package org.krudo.debug;

import static org.krudo.Tool.*;
import static org.krudo.debug.Reflect.*;

import org.krudo.*;


public class Info 
{        
    public static void info_moves() 
    {
        int count = get_field_value_as_int(Moves.class, "count");
        int stack = get_field_value_as_int(Moves.class, "MOVES_STACK_SIZE");
                     
        print("@Moves: count="+count+"/"+stack);
    }
}
