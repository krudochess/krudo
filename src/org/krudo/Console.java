/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// root package
package org.krudo;

// require non-static class
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

// console main class
public final class Console 
{                
    // log-file handler
    private FileWriter w;
    
    // read input buffer
    private BufferedReader b;

    // constructor
    public final void start(String log) 
    {    
        //
        try 
        {       
            //
            w = new FileWriter(log, true);
            
            //
            w.flush();
            
            //
            b = new BufferedReader(new InputStreamReader(System.in)); 
            
            //
            log("$ "+new java.util.Date().toString()+"\n");
        } 
        
        //
        catch (IOException ex) { write(trace(ex)); }
    }
    
    // read input and log it into log-file
    public final String input() 
    {                    
        // prepare empty to-return string
        String s = "";
            
        // try to read input from stdin
        try
        {   
            //
            s = b.readLine();
            
            //
            log("? "+s+"\n");        
        } 
        
        //
        catch (IOException ex) { write(trace(ex)); }
                
        // return string
        return s;            
    }

    // send output to console and log it into log-file 
    public final void print(Object... args) 
    {                  
        // try to send output
        try 
        {   
            // send out to stdout
            String s = write(args);
            
            // log this action in log file
            log("! "+s+"\n");
        } 
        
        // if fail send to out then output an error
        catch (Exception ex) { write(trace(ex)); }        
    }

    // clear console like cls or clear command in bash
    public final void clear()
    {
        //
        System.out.print(((char) 27)+"[2J");
    }

    // close stdin and stdout and flush pending data
    public final void close() 
    {    
        //
        try 
        {                
            if (w != null && b != null)
            {
                //
                log("\n");
                
                //
                w.flush();

                //
                w.close();

                //
                b.close(); 
            }
        } 
        
        //
        catch (IOException ex) { write(trace(ex)); }        
    }
    
    // handler program generated errors
    public final void error(Throwable e) 
    {    
        //
        String o = trace(e);
        
        //
        log("& "+o);
        
        //
        write(o);
    }
    
    // write into log file
    public final void log(String arg)
    {    
        //
        try
        {
            if (w != null)
            {
                w.write(arg);
            }
        } 
        
        //
        catch (IOException ex) { write(trace(ex)); }        
    }

    // send output to console and return compound string 
    private String write(Object... args) 
    {                            
        // output string
        String o = "";
        
        // separetor string
        String s = "";
        
        // compund output string
        for (Object a: args) { o += s + a; s = " "; }
        
        // print output in console
        System.out.print(o+"\n");
        
        // return output string
        return o;
    }

    // convert Throwable to string for print it
    private static String trace(Throwable e)
    {    
        //
        StringWriter s = new StringWriter();
        
        //
        e.printStackTrace(new PrintWriter(s));
        
        //
        return s.toString();     
    }
}
