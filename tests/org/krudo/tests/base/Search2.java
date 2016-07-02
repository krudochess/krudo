package org.krudo.tests.base;

import static org.krudo.util.Tool.*;

//
import org.krudo.Node;
import org.krudo.Move;
import org.krudo.cache.Cache;
import org.krudo.Search;

// 
public class Search2 {

	//
	public static void main(String[] args) {
				
		// create a node to service the search
		Node n = new Node();
		
		// create a serach engine based-on the node
		Search s = new Search(n);
		
		//
		echo(s.eval(7), s.ns, time()-s.timeStart);		
		
		//
		echo(Move.b.size(),Cache.legals.size());
	}	
}
