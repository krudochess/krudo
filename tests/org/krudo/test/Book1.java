package org.krudo.test;

//
import static org.krudo.utils.Tools.*;

//
import org.krudo.utils.Book;
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
