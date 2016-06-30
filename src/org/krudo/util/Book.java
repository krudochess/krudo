/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.util;

//
import org.krudo.Node;
import org.krudo.Move;
import org.krudo.Krudo;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

//
import static org.krudo.util.Tool.*;
import static org.krudo.util.Zobrist.*;

// book access tool
public final class Book 
{		
	// end of file flag handler
	private static int eof = 0;

	// key-hash of current move after a .read() method 
	private static long key = 0;	

	// book file input stream handler
	private static FileInputStream fis;

    // current record after .read() method
	private static final byte[] RECORD = new byte[16];

    // constant 
    private static final String BOOKFILE = path("Krudo.bin");
    
	// open binary file
	public static void open() 
    {					
		//
		try 
        {			
			eof = 0;
			fis = new FileInputStream(BOOKFILE);					
		} 
		
		//
		catch (FileNotFoundException ex) 
        {
			Krudo.CONSOLE.error(ex);			
		} 		
	}
	
	// close binary file
	public static final void exit() 
    {	
		//
		try 
        {
			fis.close();					
		} 
		
		//
		catch (IOException ex)
        {
			Krudo.CONSOLE.error(ex);			
		}
	}
	
	// read next input from file
	public static final boolean read() 
    {	
		//
		try 
        {
			eof = fis.read(RECORD);
			key = byte2long(RECORD, 0, 8);			
		} 
		
		//
		catch (IOException ex) {
			Krudo.CONSOLE.error(ex);
			exit();
		}
		
		//
		return eof != -1;
	}
		
	// get first move into book based on node
	public static final String best(
		final Node n
	) {
		// open file
		open();
		
		//
		long hash = hash(n);
		
		// loop throu records
		while(read()) {
			
			// if found correspondent exit with move
			if (key == hash) {
				exit();
				return move();
			}
		}
				
		// if not-found exit with "none"
		exit();
		
		//
		return null;
	}
	
	// get list of moves into book based on node
	public static final Move list(
		final Node n
	) {
				
		// preparea a void move list
		Move a = new Move();
						
		// open file
		open();
		
		//
		h = hash(n);
		
		// loop throu records
		while(read()) {
			
			// if found position hash put move into stack
			if (key == h) {				
				a.add(n, move(), weight());
			}
		}
				
		// stop and close
		exit();
		
		// return a move list
		return a;
	}
	
	//
	public static final String rand(
		final Node n
	) {
		// get moves stored in book
		//Move a = Book.list(n);
		
		// re-give move to buffer
		//Move.psh(a);
		
		// return a random move on the availables
		//return a.l > 0 ? i2m(a, rand(0, a.l-1)) : null;
		return null;
				
	}
	
	// convert current move-record into move
	public static final String move() {
		
		// convert byte-array to integer
		m = byte2int(r,8,2);
		
		// return convert integer to move
		return "" + 
			(char)(((m & 0b000000111000000) >> 6) + 'a') +
			(char)(((m & 0b000111000000000) >> 9) + '1') +			
			(char)(((m & 0b000000000000111)		) + 'a') +
			(char)(((m & 0b000000000111000) >> 3) + '1') ;		
	}
	
	// convert current move-record into move
	public static final int weight() {
		
		// convert byte-array to integer
		return byte2int(r,10,2);		
	}
}
