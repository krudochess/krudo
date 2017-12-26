package org.krudo.test.capture;

//
import org.krudo.Capture;
import org.krudo.Captures;

import static org.krudo.test.debug.Info.*;
import static org.krudo.test.debug.Reflect.*;
import static org.krudo.Tool.*;

//


// 
public class Capture1 
{
    //
    public static void main(String[] args)
    {
        //
        Captures.init();   
        info_captures();
        
        //
        int l = 2 * debug_get_declared_field_as_int(Captures.class, "STACK_SIZE");
        long[] h = new long[l];
        for (int i = 0; i < l; i++) {
            Capture c = Captures.pick();
            h[i] = uuid();
            Captures.add(h[i], c);
        }
        info_captures();       
        
        //
        for (int i = 0; i < l; i++) {
            Captures.del(h[i]);
        }        
        info_captures();       
    }
}
