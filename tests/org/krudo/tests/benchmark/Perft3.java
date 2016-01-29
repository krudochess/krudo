package org.krudo.tests.benchmark;

import static org.krudo.util.Debug.*;
import static org.krudo.util.Tools.*;

import org.krudo.Node;

//
public class Perft3 {

	//
	public static void main(String[] args) {
		
		/*\
		FEN: 8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -
		14			0 ms
		191			0 ms
		2812		0 ms
		43238		0 ms
		193690690	0 ms		
		\*/
				
		Node n = new Node("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");
				
		try {
			/*_*/
			for(int i=1; i<=1; i++) {
				echo(perft(n,i));
			}
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
