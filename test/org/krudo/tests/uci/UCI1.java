package org.krudo.tests.uci;

//
import static org.krudo.tests.debug.Debug.*;
import static org.krudo.Constants.*;
import static org.krudo.Tool.*;
import static org.krudo.Decode.*;

//
import org.krudo.*;

//
public class UCI1 {

    //
    public static void main(String[] args) {
        
        //
        String[] inputs = new String[] {
            "go",
        };
        
        //
        for(String input: inputs) {
            
            //
            print(input, "=>", UCI.parse(input));
        }                
    }    
}
