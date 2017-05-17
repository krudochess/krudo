/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.krudo.debug;

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
            value = (Integer) field.get(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return value;
    }
}
