package org.krudo.uci;

//
import static org.krudo.Tool.*;

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
