//@formatter:off
/*
 *
 * Copyright (c) 2005 j2js.com,
 *
 * All Rights Reserved. This work is distributed under the j2js Software License [1]
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.
 *
 * [1] http://www.j2js.com/license.txt
 */
//@formatter:on

package java.lang;

import java.io.PrintStream;

import javax.script.ScriptEngine;

import javascript.JSObject;
import javascript.ScriptHelper;

/**
 * The System class contains several useful class fields and methods. It cannot
 * be instantiated.
 */
public final class System {
   
   /**
    * The "standard" error output stream. By default, the output is directed to
    * the {@link ConsoleOutputStream}.
    */
   public static PrintStream err;
   
   /**
    * The "standard" output stream. By default, the output is directed to the
    * {@link ConsoleOutputStream}.
    */
   public static PrintStream out;
   
   /**
    * System property set through the URL query parameters on startup.
    */
   public static JSObject properties = new JSObject();
   
   public static ScriptEngine scriptEngine;
   
   private System() {}
   
   /**
    * Returns the current time in milliseconds.
    */
   public static long currentTimeMillis() {
      return ScriptHelper.evalLong("new Date().getTime()");
   }
   
   /**
    * Copies an array from the specified source array, beginning at the
    * specified position, to the specified position of the destination array.
    */
   public static void arraycopy(Object src, int srcPosition, Object dst, int dstPosition, int length) {
      Object[] srcArray = (Object[]) src;
      Object[] dstArray = (Object[]) dst;
      
      if (src == dst && srcPosition < dstPosition && srcPosition + length >= dstPosition) {
         for (int i = length - 1; i >= 0; i--)
            dstArray[dstPosition + i] = srcArray[srcPosition + i];
      } else {
         for (int i = 0; i < length; i++)
            dstArray[dstPosition + i] = srcArray[srcPosition + i];
      }
   }
   
   /**
    * Gets the system property indicated by the specified key. On startup,
    * system properties are set through the URL query parameters.
    * 
    * @param key
    *           the name of the system property
    */
   public static String getProperty(String key) {
      return (java.lang.String) properties.get(key);
   }
   
   /**
    * Gets the system property indicated by the specified key. On startup,
    * system properties are set through the URL query parameters.
    * 
    * @param key
    *           the name of the system property
    */
   public static String getProperty(String key, String def) {
      String propertyValue = (java.lang.String) properties.get(key);
      if (propertyValue == null)
         return def;
      return propertyValue;
   }
   
   /**
    * Closes the window which is running this Java Virtual Machine.
    * 
    * @param status
    *           ignored
    */
   public static void exit(int status) {
      // Global.window.close();
   }
   
   /**
    * Runs the garbage collector.
    */
   public static void gc() {}
   
}
