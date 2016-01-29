package org.krudo.tests.tuning;

//
import static org.krudo.util.Tools.*;
import static org.krudo.util.Debug.*;
import static org.krudo.Eval.eval;

//
import org.krudo.Node;
import org.krudo.Search;

// 
public class Eval1 {

	//
	public static void main(String[] args) {
				
		// create a node to service the search
		Node n = new Node();
	
		//
		dump(n);
		
		//
		eval(n,true);		
	}	
}
