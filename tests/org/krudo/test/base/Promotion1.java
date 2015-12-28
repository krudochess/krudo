package org.krudo.test.base;

import static org.krudo.util.Tools.*;

//
import org.krudo.Node;
import org.krudo.Search;

// 
public class Promotion1 {

	//
	public static void main(String[] args) {
				
		// create a node to service the search
		Node n = new Node("k7/8/8/8/8/8/p6P/7K b");
		
		// create a serach engine based-on the node
		Search s = new Search(n);
			
		//
		s.start(4);
		
	}	
}
