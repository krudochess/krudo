package org.krudo.tests.benchmark;

//
import static org.krudo.util.Debug.*;
import static org.krudo.util.Tools.*;

//
import org.krudo.Node;

//
public class Perft1 {

	//
	public static void main(String[] args) {
		
		/*\
		FEN: STARTPOS
		20			0 ms
		400			16 ms
		8902		141 ms
		197281		173 ms
		4865609		2313 ms
		119060324	51176 ms
		\*/
	
		//
		Node n = new Node();
		
		//
		try {
			/*_*/
			for(int i=1; i<=6; i++) {
				echo(perft(n,i));
			}
			//echo (Cache.legals.size());
			//dump(n);
			/*/
			Perft.table(n,5);
			/*_*/
		} catch (Exception e) {
			dump(n);
			dump(n.L);
			dump(e);
		}			
	}	
}
