package org.krudo.tests.base;

import static org.krudo.util.Tool.*;
import static org.krudo.util.Debug.*;

//
import org.krudo.Node;
import org.krudo.Cache;
import org.krudo.Search;

// 
public class Castling1 {

	//
	public static void main(String[] args) {
	
		String p = "8/8/8/8/8/3n4/8/4K2R w KQkq";
		
		Node n = new Node(p);
		
		dump(n);
		
		
		dump(n.legals());		
	}	
}
