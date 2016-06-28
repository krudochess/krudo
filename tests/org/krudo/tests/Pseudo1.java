/**
 * Krudo 0.16a 
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests;

//
import org.krudo.*;

//
import static org.krudo.Constant.*;
import static org.krudo.Config.*;
import static org.krudo.util.Debug.*;
import static org.krudo.util.Tool.*;
import static org.krudo.util.Decode.*;

//
public class Pseudo1 {

	//
	public static void main(String[] args) {
		
		//
		if (MOVE_LEGALS) {
			exit("set MOVE_LEGALS = false and retry");
		}
		
		//
		Node n = new Node();

		//
		n.startpos();
		
		//
		print(n);
		
		//
		print(n.legals());
	}
}
