package org.krudo.tests.uci;

//
import static org.krudo.util.Debug.*;
import static org.krudo.Constant.*;
import static org.krudo.util.Tool.*;
import static org.krudo.util.Decode.*;

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
