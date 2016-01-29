package org.krudo.tests.benchmark;

//
import static org.krudo.util.Debug.*;

//
import org.krudo.Engine;

// 
public class Thinker1 {

	//
	public static void main(String[] args) {
				
		// 
		Engine t = Engine.getInstance();
	
		//
		t.domove("e2e4 e7e5 g1f3 b8c6 f1c4 f8c5 e1g1 d8e7 g1h1 g8f6 b1c3 e8g8 c3d5 f6d5 e4d5 e5e4 d5c6 e4f3 d1f3 d7c6 d2d3 c5d4 f1d1 g8h8".split("\\s"));
		
		//t.domove("d1d2");
		
		dump(t.node);
		
		//
		t.search.start(10, 10000);
	}	
}
