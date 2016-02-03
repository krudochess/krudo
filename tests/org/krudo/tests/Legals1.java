/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests;

//
import static org.krudo.Const.*;
import static org.krudo.util.Debug.*;
import static org.krudo.util.Tools.*;
import static org.krudo.util.Trans.*;

//
import org.krudo.*;

//
public class Legals1 {

	//
	public static void main(String[] args) {
		
		//
		if (!MOVE_LEGALS) {
			exit("set MOVE_LEGALS = true and retry");
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
