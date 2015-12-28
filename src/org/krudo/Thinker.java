package org.krudo;

//
import static org.krudo.Const.*;
import static org.krudo.util.Tools.*;

//
public final class Thinker {	
	
	//
	public final Node n;
	
	//
	public final Search s;
		
	//
	private long wtime = 60000;
	
	//
	private long btime = 60000;
	
	//
	public String bm;
	
	// best move default callback
	public Runnable bmc = new Runnable() {
		@Override
		public void run() {
			
		}
	};
	
	//
	public Thinker() {
			
		//
		n = new Node();
		
		//
		s = new Search(n);
	}
	
	//
	public final void startpos() {
		n.startpos();
	}
	
	//
	public final void startpos(String fen) {
		n.startpos(fen);
	}
	
	//
	public final void domove(String move) {
		n.domove(move);
	}
	
	//
	public final void domove(String[] moves) {
		n.domove(moves);
	}
	
	//
	public final void unmove() {
		n.unmove();
	}
	
	//
	public final void go(String wtime0, String btime0) {
	
		//
		go(toLong(wtime0), toLong(btime0));		
	}
	
	//
	public final void go(long wtime0, long btime0) {
	
		//
		wtime = wtime0;
		
		//
		btime = btime0;
		
		//
		go();
	}
	
	// start thinking process
	public final void go() {
		
		//
		String m = Book.rand(n);
		
		//
		if (m != null) {
			
			//
			bm = m;		
			
			//
			bmc.run();
			
			//
			return;
		}
		
		//
		Cache.legals.clr();
		
		//
		long time = n.T == w ? (wtime / 80) + 1000 : (btime / 80) + 1000; 
		
		// call iterative deeping (wait here)
		s.start(4);
	}
	
	//
	public final int eval() {

		//
		return s.eval(2);
	}
	
	//
	public final void setBestMoveCallback(Runnable callback) {
		bmc = callback;
	}	

	//
	public final void setSearchLogCallback(Runnable callback) {
		s.log = callback;
	}	
	
	//
	public final boolean isReady() {
	
		//
		n.legals();
		
		//
		return true;
	}
}
