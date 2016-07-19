package org.krudo.tests.moves;

//
import static org.krudo.Debug.*;
import static org.krudo.Tool.*;

// 
import org.krudo.Node;
import org.krudo.Move;
import org.krudo.Moves;

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
