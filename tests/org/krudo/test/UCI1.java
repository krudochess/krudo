package org.krudo.test;

//
import static org.krudo.utils.Debug.*;
import static org.krudo.Const.*;
import static org.krudo.utils.Tools.*;
import static org.krudo.utils.Trans.*;

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
