package org.krudo.tests.tuning;

//
import static org.krudo.util.Tool.*;
import static org.krudo.util.Debug.*;
import static org.krudo.Eval.eval;

//
import org.krudo.Node;
import org.krudo.Search;
import static org.krudo.Eval.eval;

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
