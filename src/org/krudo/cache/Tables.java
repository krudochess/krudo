package org.krudo.cache;

//
import java.util.LinkedHashMap;

//
public final class Tables extends LinkedHashMap<Long,TableRecord> {
    
    public static final int ALPHA = 1;
    public static final int BETA = 2;
    public static final int EXACT = 3;
    
    //
    private int w;
    
    //
    private int f;
    
    //
    private TableRecord r;
        
    //
    public final void begin(long h) {
    
        f = ALPHA;
            
        if (containsKey(h)) {
            r = get(h);
        } else {
            r = new TableRecord();
            put(h,r);
        }        
    }
    
    //
    public final boolean probe(int d, int a, int b) {
        
        if (r.f[d] == EXACT) {
            w = r.w[d];
            return true;
        } else if (r.f[d] == ALPHA && r.w[d] <= a) {
            w = a;
            return true;
        } else if (r.f[d] == BETA && r.w[d] >= b) {
            w = b;
            return true;        
        }
                
        return false;
    }
    
    //
    public final void store(int d, int w) {
        
        //
        r.add(d, w, f);
    }
    
    //
	public final void store(int d, int w, int f) {
		
		//
		r.add(d, w, f);				
	}
	
	//
	public final int value() {
		return w;
	}
	
	//
	public final void exact() {
		f = EXACT;
	}	
}

//
class TableRecord {

	//
	public int[] f;
	
	//
	public int[] w;
	
	//
	public TableRecord() {
	
		//
		f = new int[20];
		
		//
		w = new int[20];
	}
	
	//
	public void add(int d0, int w0, int f0) {
	
	
	}	
}
