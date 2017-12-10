/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

// krudo package
package org.krudo;

// required static class
import static org.krudo.Tool.*;
import static org.krudo.Constants.*;

//
public final class Clock 
{
    //
    private static Timeout t;
    private static Polling p;
    
    //
    public static final int diff(String wtime, String btime, int t) 
    {
        //
        int w = Integer.parseInt(wtime);
        int b = Integer.parseInt(btime);
        
        //
        return t==Constants.w ? w-b : b-w;
    }
        
    //
    public static final void timeout(int millisec, Runnable callback) {
        t = new Timeout(millisec,callback);
        t.start();        
    }
    
    //
    public static final void timeout() {        
        t.stop();        
    }
        
    //
    private static class Timeout implements Runnable {
    
        //
        private final int m;        
        private final Thread t;         
        private final Runnable c;
        
        //
        public Timeout(int millisec, Runnable callback) {
            t = new Thread(this);
            c = callback;
            m = millisec;
        }
        
        //
        @Override
        public void run() {
            try {
                Thread.sleep(m);
                c.run();
            } catch (InterruptedException ex) {
                //Krudo.c.err(ex);
            }
        }    
        
        //
        public void start() {        
            t.start();            
        }
        
        //
        public void stop() {        
            t.interrupt();
        }
    }
    
    
    //
    public static final void polling(int millisec, Runnable callback) {
        p = new Polling(millisec,callback);
        p.start();        
    }
    
    //
    public static final void polling() {        
        p.stop();        
    }
    
    
    
    //
    private static class Polling implements Runnable {
    
        //
        private final int m;        
        private final Thread t;         
        private final Runnable c;
        
        //
        public Polling(int millisec, Runnable callback) {
            t = new Thread(this);
            c = callback;
            m = millisec;
        }
        
        //
        @Override
        public void run() {
            try {
                while(true) {
                    Thread.sleep(m);
                    c.run();
                }
            } catch (InterruptedException ex) {
                //Krudo.c.err(ex);
            }
        }    
        
        //
        public void start() {        
            t.start();            
        }
        
        //
        public void stop() {        
            t.interrupt();
        }
    }
    
    
}
