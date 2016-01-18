package org.krudo.test.base;

//
import static org.krudo.utils.Debug.*;

//
import org.krudo.utils.Book;
import org.krudo.Move;
import org.krudo.Node;

// 
public class Book1 {

	//
	public static void main(String[] args) {
				
		// 
		Node p = new Node();
		
		//
		Move m = Book.list(p);

		//
		dump(p);
		
		// print out position
		dump(m);
	}	
}
