package org.krudo.tests.base;

// required static
import static org.krudo.util.Debug.*;

// required non-static 
import org.krudo.Move;
import org.krudo.Node;

// test class
public class Node2 {

	// entry-point of test
	public static void main(String[] args) {
				
		// get start position 
		Node p = new Node();
		
		// do move e2e4
		p.domove("e2e4");
		
		// do move d7d5
		Move m = p.legals();
		
		// print out position
		dump(p);

		m.w[3] = 120;
		
		// print out moves
		dump(m);
		
		Move a = p.legals();
		
		dump(a);
		
	}	
}
