package org.krudo.tests;

//
import static org.krudo.util.Tools.*;

//
import org.krudo.util.Book;
import org.krudo.Node;

//
public class Book1 {

	//
	public static void main(String[] args) {
				
		//
		Node n = new Node();
		
		//
		String m = Book.rand(n);
		
		//
		while (!m.equals("")) {

			//
			echo(m);			
			
			//
			n.domove(m);
			
			//
			m = Book.rand(n);
		} 
	}	
}
