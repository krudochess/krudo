package org.krudo.test.base;

// required static class
import static org.krudo.utils.Debug.*;
import static org.krudo.utils.Tools.*;

// required non-static class
import org.krudo.utils.Book;
import org.krudo.Node;

// 
public class Book2 {

	//
	public static void main(String[] args) {
				
		// 
		Node n = new Node();
		
		//
		//n.domove("f2f4");
		
		//
		String m = Book.rand(n);

		//
		while(m != null) {
			
			//
			n.domove(m);

			//
			m = Book.rand(n);
		}
		
		//
		dump(n);	
		
		//
		desc(n.L);			
	}	
}
