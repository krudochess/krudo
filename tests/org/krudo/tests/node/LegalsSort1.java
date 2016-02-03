/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.node;

//
import org.krudo.Node;
import org.krudo.Move;

//
import static org.krudo.util.Debug.*;
import static org.krudo.util.Tools.print;

//
public class LegalsSort1 {

	//
	public static void main(String[] args) {
		
		//
		DEBUG_SHOW_WEIGTH = true;
		
		//
		Node n = new Node();

		//
		n.startpos();
		
		//
		Move m = n.legals();
		
		//
		dump(n, m);
		
		//
		m.sort();
		
		//
		dump(n, m);
		
		//
		print();
	}	
}
