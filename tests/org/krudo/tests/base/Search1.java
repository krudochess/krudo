package org.krudo.tests.base;

import static org.krudo.util.Tool.*;

//
import org.krudo.Node;
import org.krudo.Search;

// 
public class Search1 {

	//
	public static void main(String[] args) {
				
		// create a node to service the search
		Node n = new Node();
		
		// create a serach engine based-on the node
		Search s = new Search(n);
		
		//n.domove("e2e4");
		//n.domove("e7e5");
		
		// deep limit of iterative deeping
		int deep = 5; 
		
		// time limit of iterative deeping
		long time = 3000;
		
		long d = time(); 
		
		//
		s.start(deep);
		
		echo(time()-d);
	}	
}
