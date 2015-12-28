package org.krudo.test.base;

//
import static org.krudo.util.Debug.*;
import static org.krudo.util.Tools.*;

// 
import org.krudo.Node;

// 
public class Node1 {

	//
	public static void main(String[] args) {
		
		// 
		Node n = new Node();
		
		// do move e2e4
		n.domove("e2e4 d7d5 d1f3 d5d4 f1c4 e7e5".split("\\s"));
			
		// print out position
		dump(n);		
	}	
}
