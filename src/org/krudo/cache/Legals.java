package org.krudo.cache;

//
import static org.krudo.Const.*;

//
import org.krudo.Move;

//
import java.util.Map;
import java.util.LinkedHashMap;

//	
public class Legals extends LinkedHashMap<Long,Move> {	
		
	// 
	public Legals() {		

		//
		super(MOVE_CACHE_SIZE, 0.95f, true);	
	}

	//
	public final void add(Long h, Move m) {			

		//
		if (MOVE_CACHE) { 

			//
			m.c = 1;

			//
			put(h, m);				
		}		
	}

	//
	public final boolean has(long h) {			

		//
		if (MOVE_CACHE) {			
			return containsKey(h);
		} 
		
		//		
		else {		
			return  false;
		}
	}
	
	//
	public final void clr() {

		//
		if (MOVE_BUFFER) {
			for(Move m: this.values()) {
				Move.psh(m);
			}
		}

		//
		clear();
	}

	//
	@Override
	protected boolean removeEldestEntry(Map.Entry<Long,Move> e) {

		// 
		if (size() > MOVE_CACHE_SIZE) {

			//
			e.getValue().c = 0;

			// if move not used of a loop recycle it into buffer
			if (MOVE_BUFFER && e.getValue().n == 0) {
				Move.psh(e.getValue());
			} 	

			// return true than remove
			return true;													
		}

		// return false not remove
		return false; 
	}
}
