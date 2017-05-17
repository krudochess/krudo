package org.krudo.tests.tuning;

//
import static org.krudo.Tool.*;
import static org.krudo.debug.Debug.*;

//
import org.krudo.Node;
import org.krudo.Search;

// 
public class Tune2 
{
    //
    public static void main(String[] args) 
    {                
        // create a node to service the search
        Search s = new Search();
        
        //
        s.node.domove("e2e4 c7c5 e4e5 b8c6 g1f3 d8c7 d1e2 f7f6 e5f6 g8f6 f3g5 c6d4 e2d3 c7e5 g5e4 f6e4".split("\\s"));
        
        //
        s.start(4);
        
        //
        dump(s);
        //dump(n.legals());                
    }    
}
