package org.krudo.tests;

//
import static org.krudo.util.Debug.*;
import static org.krudo.Const.*;
import static org.krudo.util.Tools.*;
import static org.krudo.util.Trans.*;

//
import org.krudo.*;

//
public class UCI1 {

	//
	public static void main(String[] args) {
		
		//
		String[] inputs = new String[] {
			"go",
		};
		
		//
		for(String input: inputs) {
			
			//
			print(input, "=>", UCI.parse(input));
		}				
	}	
}
