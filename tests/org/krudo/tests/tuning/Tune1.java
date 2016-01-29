package org.krudo.tests.tuning;

//
import static org.krudo.util.Tools.*;
import static org.krudo.util.Debug.*;

//
import org.krudo.Node;
import org.krudo.Search;

// 
public class Tune1 {

	//
	public static void main(String[] args) {
				
		// create a node to service the search
		Node n = new Node();
		
		//
		Search s = new Search(n);

		String m = "e2e4 c7c6 d2d4 h7h5 c1e3 d8b6 b2b3 d7d5 f1d3 d5e4 d3e4 g8f6 b1c3 f6e4 c3e4 c8f5 d1d3 b6a5 e3d2 a5d5 f2f3 b8a6 d2f4 a6b4";
		
		//
		n.domove(m.split("\\s"));
		
		//n.domove("c1d2");
		
		//n.domove("a5b6");
		
		//
		s.start(5);
		
		dump(n);
		dump(n.legals());
		
		
	}	
}
