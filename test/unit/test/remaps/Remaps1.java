/**
 * Krudo 0.16a 
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.test.remaps;

import java.lang.reflect.Field;
import java.util.*;

//

//


// 
public class Remaps1
{
    //
    public static void main(String[] args) 
    {            
        try {
            Long l = new Long(1000);
            
            Map m = new LinkedHashMap<Integer, Integer>(262144, 1) {                
                public boolean removeEldestEntry(Map.Entry<Integer, Integer> e) {                    
                    return this.size() > 262144-1;
                }                
            };

            for (int i =0 ;i< 262144*32; i++) {
                m.put(i,i);
            }
            
            
            Field f = m.getClass().getSuperclass().getSuperclass().getDeclaredField("threshold");
            f.setAccessible(true);
            int v = (Integer) f.get(m);

            System.out.println(v);
            System.out.println(m.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
}
