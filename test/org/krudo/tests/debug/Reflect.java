/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.krudo.tests.debug;

import static org.krudo.Tool.*;

//
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.lang.reflect.Field;
import org.krudo.Capture;
import org.krudo.Moves;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

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
    
    public static Capture[] get_field_value_as_capture_array(Class c, String f, Object o) 
    {    
        Capture[] value = null;
         
        try {
            Field field = c.getDeclaredField(f);            
            field.setAccessible(true);
            value = (Capture[]) field.get(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return value;
    }
    
    //
    public final static void set_field_value_as_boolean(
        final Class c, 
        final String f, 
        final boolean v
    ) {
        try {
            Field field = c.getDeclaredField(f);            
            field.setAccessible(true);
          
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                          
            field.set(null, false);
            
        } catch (Exception e) {
            e.printStackTrace();
        }      
    } 
}