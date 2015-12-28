package org.krudo.test.tune;

//
import static org.krudo.util.Tools.*;
import static org.krudo.util.Debug.*;

//
import org.krudo.Node;
import org.krudo.Search;

// 
public class Sort2 {

	//
	public static void main(String[] args) {
				
		// create a node to service the search
		Node n = new Node("r2qnrnk/p2b2b1/1p1p2pp/2pPpp2/1PP1P3/PRNBB3/3QNPPP/5RK1 w");
							
		//
		dump(n);
		
		//
		desc(n, n.legals().sort());
		
		
	}	
}
