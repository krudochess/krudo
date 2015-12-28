package org.krudo;

// require non-static class
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

// console main class
public final class Console {
		
	// log content 
	private String l;
		
	// log-file handler
	private FileWriter w;
	
	// read input buffer
	private BufferedReader b;

	//
	public Console() { 
		
		//
		this("Console.log"); 	
	}
	
	// constructor
	public Console(String log) {
		try {		
			l = "";
			w = new FileWriter(log,true);
			w.flush();
			b = new BufferedReader(new InputStreamReader(System.in)); 
			log("\n");
			log("$ "+new java.util.Date().toString()+"\n");
		} catch (IOException ex) {
			out(trk(ex));
		}
	}
	
	// read input and log it into log-file
    public final String get() {				
		
		// prepare empty to-return string
		String s = "";
			
		// try to read input from stdin
		try {
			
			//
			s = b.readLine();
			
			//
			log("? "+s+"\n");		
		} 
		
		//
		catch (IOException ex) {
			out(trk(ex));
		}
				
		// return string
		return s;			
	}

	// send output to console and log it into log-file 
    public final void put(Object... args) {        		
		
		// try to send output
		try {
			
			// send out to stdout
			String s = out(args);
			
			// log this action in log file
			log("! "+s+"\n");
		} 
		
		// if fail send to out then output an error
		catch (Exception ex) {			
			out(trk(ex));
		}		
    }

	// send output to console and return compund string 
	public final String out(Object... args) {        				
		
		// output string
		String o = "";
		
		// separetor string
		String s = "";
		
		// compund output string
		for(Object a: args) { o += s + a; s = " "; }
		
		// print output in console
		System.out.print(o+"\n");
		
		// return output string
		return o;
    }

	// clear console like cls or clear command in bash
    public final void clr() {
    
		//
		System.out.print(((char) 27)+"[2J");
    }

	// close stdin and stdout and flush pending data
    public final void end() {
		
		//
		try {			
			
			//
			w.flush();
			
			//
			w.close();
			
			//
			b.close(); 
		} 
		
		//
		catch (IOException ex) {
			out(trk(ex));
		}		
	}
	
	//
	public final void err(Throwable e) {
		
		//
		String o = trk(e);
		
		//
		log("& "+o);
		
		//
		out(o);
	}
	
	//
	public final void log(String arg) {
		
		//
		try {
			
			//
			l += arg;
			
			//
			w.write(arg);
		} 
		
		//
		catch (IOException ex) {
			out(trk(ex));
		}		
	}
	
	//
	public final String log() {	
		return l;
	}
	
	// 
	public final static String trk(Throwable e) {
		StringWriter s = new StringWriter();
		e.printStackTrace(new PrintWriter(s));
		return s.toString(); 	
	}
	
	//
	public final static String pth(String f) {
		return (System.getenv().containsKey("ENGINEDIR") ? System.getenv().get("ENGINEDIR") : System.getProperty("user.dir"))+File.separator+f;	
	}
}
