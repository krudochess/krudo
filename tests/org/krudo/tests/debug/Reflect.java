/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.krudo.tests.debug;

//
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.lang.reflect.Field;
import org.krudo.Moves;
import java.lang.reflect.Field;

/**
 *
 * @author francescobianco
 */
public final class Reflect
{    
    public static int get_field_value_as_int(Class c, String f) 
    {    
        int value = 0;
         
        try {
            Field field = c.getDeclaredField(f);
            field.setAccessible(true);
            value = (Integer) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return value;
    }
    
    public static LinkedHashMap get_field_value_as_linked_hash_map(Class c, String f) 
    {    
        LinkedHashMap value = null;
         
        try {
            Field field = c.getDeclaredField(f);
            field.setAccessible(true);
            value = (LinkedHashMap) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return value;
    }
    
    public static Map.Entry[] get_field_value_as_map_entry_array(Class c, String f, Object o) 
    {    
        Map.Entry[] value = null;
         
        try {
            Field field = c.getDeclaredField(f);
            
            field.setAccessible(true);
            value = (Map.Entry[]) field.get(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return value;
    }
}
