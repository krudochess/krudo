/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 * 
 * reference:
 * http://hardy.uhasselt.be/Toga/book_format.html
 */

//
package org.krudo;

//
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

//
import static org.krudo.Tool.*;

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
    private static final String BOOKFILE = path("krudo.bin");
    
    // open binary file
    public static void open() 
    {                    
        //
        try 
        {         
            //
            eof = 0;
           
            //
            fis = new FileInputStream(BOOKFILE);                    
        } 
        
        //
        catch (FileNotFoundException ex) 
        {     
            //
            Krudo.CONSOLE.error(ex);    
            
            //
            Tool.exit();
        }         
    }
    
    // close binary file
    public static final void exit() 
    {    
        //
        try 
        {
            //
            fis.close();                    
        } 
        
        //
        catch (IOException ex)
        {
            //
            Krudo.CONSOLE.error(ex);    
        }
    }
    
    // read next input from file
    public static final boolean read() 
    {    
        //
        try 
        {
            //
            eof = fis.read(RECORD);
            
            //
            key = byte2long(RECORD, 0, 8);            
        } 
        
        //
        catch (IOException ex)
        {
            //
            Krudo.CONSOLE.error(ex);
            
            //
            exit();
        }
        
        //
        return eof != -1;
    }
        
    // get first move into book based on node
    public static final String best(final long lookup) 
    {
        // open file
        open();

        // loop throu records
        while (read()) 
        {    
            // if found correspondent exit with move
            if (key == lookup) 
            {
                //
                exit();
                
                //
                return move();
            }
        }
                
        // if not-found exit with "none"
        exit();
        
        //
        return null;
    }
    
    // get list of moves into book based on node
    public static final Strings list(final long lookup) 
    {
        // prepare a void move list
        Strings m = new Strings();
                        
        // open file
        open();
        
        // loop throu records
        while (read()) 
        {    
            // if found position hash put move into stack
            if (key == lookup) 
            {                
                //
                m.add(move());
            }
        }
                
        // stop and close
        exit();
        
        // return a move list
        return m;
    }
    
    //
    public static final String rand(final long lookup) 
    {
        // get moves stored in book
        Strings m = Book.list(lookup);
    
        //
        return m.size() > 0 ? m.get(Tool.rand(0, m.size()-1)) : null;    
    }
    
    // convert current move-record into move
    public static final String move() 
    {
        // convert byte-array to integer
        int move = byte2int(RECORD, 8, 2);
        
        // return convert integer to move
        return "" + 
            (char)(((move & 0b000000111000000) >> 6) + 'a') +
            (char)(((move & 0b000111000000000) >> 9) + '1') +            
            (char)(((move & 0b000000000000111)     ) + 'a') +
            (char)(((move & 0b000000000111000) >> 3) + '1') ;        
    }
    
    // convert current move-record into move
    public static final int weight() 
    {    
        // convert byte-array to integer
        return byte2int(RECORD, 10, 2);        
    }
    
    //
    public static final void walk(final Node n, final int u)
    {
        //
        Strings m = list(n.phk);
        
        //
        if (m.isEmpty()) { return; }
        
        //
        n.domove(m.get(u % m.size()));
    
        //
        walk(n, u / m.size());
    }
}
