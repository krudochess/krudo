package org.krudo.tests.tuning;

//
import static org.krudo.util.Tools.*;
import static org.krudo.util.Debug.*;

//
import org.krudo.Node;
import org.krudo.Search;

// 
public class Sort1 {

	//
	public static void main(String[] args) {
				
		// create a node to service the search
		Node n = new Node();
		
		//
		String m = "e2e4 d7d5 e4d5 d8d5 b1c3 d5d6 d2d4 g8f6 g1f3 b8c6 c1e3 c8e6 f1b5 e8c8";
		// f1b5 c7c6 b5a4 b4d5
		
		//
		n.domove(m.split("\\s"));
			
		//
		dump(n);
		
		//
		desc(n, n.legals().sort());
		
		
	}	
}
