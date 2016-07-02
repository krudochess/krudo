package org.krudo.tests.moves;

//
import org.krudo.tests.base.*;
import static org.krudo.util.Debug.*;
import static org.krudo.util.Tool.*;

// 
import org.krudo.Node;
import org.krudo.Move;
import org.krudo.empty.Moves;

// 
public class Moves1 
{

	//
	public static void main(String[] args)
    {
        Moves.init();
        for(int i=0;i<200;i++){
        Move m = Moves.pick();
        print(m.s[0]);}
	}	
}
