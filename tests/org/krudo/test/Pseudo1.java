/**
 * Krudo 0.16a 
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.test;

//
import org.krudo.*;

//
import static org.krudo.Const.*;
import static org.krudo.utils.Debug.*;
import static org.krudo.utils.Tools.*;
import static org.krudo.utils.Trans.*;

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