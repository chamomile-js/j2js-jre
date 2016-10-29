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

package javascript;

/** Represents a native JavaScript object. */
public class JSObject {

  public static boolean containsKey(Object obj, String key) {
    System.scriptEngine.put("obj", obj);
    System.scriptEngine.put("key", key);
    // Warning: Cannot use (obj[key] != undefined) because
    // (null == undefined) evaluates to true.
    return ScriptHelper.evalBoolean("typeof(obj[key]) != 'undefined'");
  }

  public static Object get(Object obj, String key) {
    if (containsKey(obj, key)) {
      System.scriptEngine.put("obj", obj);
      System.scriptEngine.put("key", key);
      return System.scriptEngine.eval("obj[key]");
    }
    return null;
  }

  public static Object put(Object obj, String key, Object value) {
    System.scriptEngine.put("obj", obj);
    System.scriptEngine.put("key", key);
    System.scriptEngine.put("value", value);
    System.scriptEngine.eval("obj[key] = value");
    return obj;
  }

  public static Object remove(Object obj, String key) {
    System.scriptEngine.put("obj", obj);
    System.scriptEngine.put("key", key);
    System.scriptEngine.eval("delete obj[key]");
    return obj;
  }
  
  public static String[] keys(Object obj) {
    System.scriptEngine.put("obj", obj);
    System.scriptEngine.eval("var keys = new Array(); for(var e in obj) keys.push(e)");
    return (String[]) System.scriptEngine.eval("keys");
  }
  
  public static boolean isArray(Object obj) {
	    System.scriptEngine.put("obj", obj);
	    return ScriptHelper.evalBoolean("");
  }

  // ---

  public JSObject() {
    System.scriptEngine.eval("this.obj = new Object()");
  }

  public JSObject(Object obj) {
    System.scriptEngine.put("obj", obj);
    System.scriptEngine.eval("this.obj = obj");
  }

  public JSObject(String javascriptRef) {
    System.scriptEngine.put("javascriptRef", javascriptRef);
    System.scriptEngine.eval("this.obj = eval(javascriptRef)");
  }

  public boolean containsKey(String key) {
    return JSObject.containsKey(this, key);
  }

  public Object get(String key) {
    return JSObject.get(this, key);
  }

  public JSObject put(String key, Object value) {
    return (JSObject) JSObject.put(this, key, value);
  }

  public JSObject remove(String key) {
    return (JSObject) JSObject.remove(this, key);
  }

  public String[] keys() {
	  return JSObject.keys(this);
  }
}
