package org.krudo.test.tune;

//
import static org.krudo.utils.Tools.*;
import static org.krudo.utils.Debug.*;
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
