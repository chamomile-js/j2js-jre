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

    public static void put(Object obj, String key, Object value) {
	System.scriptEngine.put("obj", obj);
	System.scriptEngine.put("key", key);
	System.scriptEngine.put("value", value);
	System.scriptEngine.eval("obj[key] = value");
    }

    public static void remove(Object obj, String key) {
	System.scriptEngine.put("obj", obj);
	System.scriptEngine.put("key", key);
	System.scriptEngine.eval("delete obj[key]");
    }

    public static String[] keys(Object obj) {
	System.scriptEngine.put("obj", obj);
	System.scriptEngine.eval("var keys = new Array(); for(var e in obj) keys.push(e)");
	return (String[]) System.scriptEngine.eval("keys");
    }

    // ---

    public JSObject() {
	System.scriptEngine.eval("this.obj = new Object()");
    }

    /**
     * 
     * @param obj a JavaScript object.
     */
    public JSObject(Object obj) {
	System.scriptEngine.put("obj", obj);
	System.scriptEngine.eval("this.obj = obj");
    }

    public JSObject(String javascriptRef) {
	System.scriptEngine.put("javascriptRef", javascriptRef);
	System.scriptEngine.eval("this.obj = eval(javascriptRef)");
    }

    public boolean containsKey(String propertyName) {
	return containsKey(System.scriptEngine.eval("this.obj"), propertyName);
    }

    public Object get(String key) {
	System.scriptEngine.put("key", key);
	if (containsKey(System.scriptEngine.eval("this.obj"), key)) {
	    return System.scriptEngine.eval("this.obj[key]");
	}
	return null;
    }

    public void put(String key, Object value) {
	System.scriptEngine.put("key", key);
	System.scriptEngine.put("value", value);
	System.scriptEngine.eval("this.obj[key] = value");
    }

    public void remove(String key) {
	System.scriptEngine.put("key", key);
	System.scriptEngine.eval("delete this.obj[key]");
    }

    public String[] keys() {
	System.scriptEngine.eval("var keys = new Array(); for (var e in this.obj) keys.push(e)");
	return (String[]) System.scriptEngine.eval("keys");
    }
}
