package org.krudo.test.tune;

//
import static org.krudo.utils.Tools.*;
import static org.krudo.utils.Debug.*;

//
import org.krudo.Node;
import org.krudo.Search;

// 
public class Tune2 {

	//
	public static void main(String[] args) {
				
		// create a node to service the search
		Node n = new Node();
		
		//
		Search s = new Search(n);
		
		//
		n.domove("e2e4 c7c5 e4e5 b8c6 g1f3 d8c7 d1e2 f7f6 e5f6 g8f6 f3g5 c6d4 e2d3 c7e5 g5e4 f6e4".split("\\s"));
		
		s.start(4);
		
		dump(n);
		dump(n.legals());
		
		
	}	
}
