package org.krudo.test;

import static org.krudo.util.Debug.*;
import static org.krudo.Const.*;

import org.krudo.Node;
import org.krudo.Thinker;
import org.krudo.Search;
import static org.krudo.util.Tools.*;

//
public class Checkmate1 {

	//
	public static void main(String[] args) {

		// link per altri fen:
		// http://www.ideachess.com/chess_tactics_puzzles/checkmate_n/11509
	
		// MATTO 1
		// 6k1/2p3pp/p7/1p1pP3/3PP2r/1B1K4/PPQ3q1/3R3r b
		// 4b1r1/1k3p2/4pP2/2Q1P2P/1Pp5/2P5/2qB2P1/R6K w
		// r5k1/5ppp/8/8/8/8/8/R6K w
		// 4rk2/p6Q/1pp2bP1/3p1N2/5P2/1Pq5/P6P/5R1K w
		// r5q1/5p2/p2p1Pr1/2p2R2/PpP1P2k/1P1PQ3/6P1/6K1 w
				
		// MATTO 2/4r2k/pp1p2r1
		// 8/4r2k/pp1p2r1/8/1PP2pqp/3Q4/P1N2Kb1/6RR b
		// r2k1b2/p4p1p/5n2/8/3P3q/7b/PPP3QP/RNB3KR b
		// 3r2k1/p1RB3p/1p2p2q/n4pp1/4P3/2Q5/P4PPP/6K1 w
		// 6Q1/ppp3p1/2qpB2p/3n2k1/3P1p1N/2P4P/PP3PP1/4R1K1 w
		
		// MATTO 3
		// 8/K1Q5/6qk/P3Q2b/4p3/8/8/8 w
		// 8/8/5Pk1/3P4/1r2P3/5N2/1p1Q1P2/qR4K1 w
		// 1n3r2/p2qR2p/1p1P2pk/1Np2p2/8/PQ6/1P3PPP/6K1 w
		// 1r4k1/6b1/2np1pQ1/2q1p3/1pP1P3/1P4PB/r1N4P/3R3K w
		// 2kq3r/ppn5/4p3/2Q5/4P2P/PP1P2p1/2P3B1/5R1K b
		// 4r1k1/5p1p/1R1P2p1/8/5K2/1B2r2P/P7/8 b
		
		// MATTO 4
		// 3r2k1/b1p3p1/p2p2np/4p3/1PP5/P4PPq/2RB1P2/4QRK1 b
		// 8/2Q1p1kp/6p1/p3Pp1n/1r1B2P1/1p2PP1P/7K/8 w
				
		String fen = "8/4r2k/pp1p2r1/8/1PP2pqp/3Q4/P1N2Kb1/6RR b";
		
		final Thinker t = new Thinker();
				
		t.startpos(fen);
	
		t.go();		
	}	
}
