package org.krudo.test.base;

//
import static org.krudo.util.Debug.*;
import static org.krudo.util.Tools.*;

//
import org.krudo.Console;

// 
public class Console1 {

	//
	public static void main(String[] args) {
		
		//
		Console c = Console.getInstance();

		c.put("start");
			
		for(int i=0;i<3;i++) {
			try {		
				c.get();
				c.put("start divion");
				int a = 5 / 0;
			} catch (Exception e) {
				c.err(e);
			}
		}
		
		c.err(new Exception("Ciao"));
		
		c.put("exit");
		
		c.end();	
	
		echo ("----");	
		echo (c.log());
	}	
}
