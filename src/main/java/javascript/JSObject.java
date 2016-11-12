package javascript;

import java.util.Map.Entry;

/** Represents a native JavaScript object. */
public class JSObject implements Iterable<Entry<String, Object>> {

  public static boolean containsKey(Object obj, String key) {
    ScriptHelper.put("obj", obj);
    ScriptHelper.put("key", key);
    return ScriptHelper.evalBoolean("typeof(obj[key]) != 'undefined'");
  }

  public static Object get(Object obj, String key) {
    if (containsKey(obj, key)) {
      ScriptHelper.put("obj", obj);
      ScriptHelper.put("key", key);
      return ScriptHelper.eval("obj[key]");
    }
    return null;
  }

  public static void put(Object obj, String key, Object value) {
    ScriptHelper.put("obj", obj);
    ScriptHelper.put("key", key);
    ScriptHelper.put("value", value);
    ScriptHelper.eval("obj[key] = value");
  }

  public static void remove(Object obj, String key) {
    ScriptHelper.put("obj", obj);
    ScriptHelper.put("key", key);
    ScriptHelper.eval("delete obj[key]");
  }

  public static String[] keys(Object obj) {
    ScriptHelper.put("obj", obj);
    return (String[]) ScriptHelper.eval("Object.getOwnPropertyNames(obj)");
  }

  // ---

  public JSObject() {
    ScriptHelper.eval("this.obj = new Object()");
  }

  public JSObject(String javascriptRef) {
    ScriptHelper.put("javascriptRef", javascriptRef);
    ScriptHelper.eval("this.obj = eval(javascriptRef)");
  }

  public JSObject(Object obj) {
    ScriptHelper.put("obj", obj);
    ScriptHelper.eval("this.obj = obj");
  }

  public boolean containsKey(String key) {
    ScriptHelper.put("key", key);
    return ScriptHelper.evalBoolean("typeof(this.obj[key]) != 'undefined'");
  }

  public Object get(String key) {
    ScriptHelper.put("key", key);
    if (containsKey(key)) {
      return System.scriptEngine.eval("this.obj[key]");
    }
    return null;
  }

  public Object put(String key, Object value) {
    ScriptHelper.put("key", key);
    ScriptHelper.put("value", value);
    Object oldValue = ScriptHelper.eval("(function(map){"
        + " var oldValue = map[key];"
        + " map[key] = value;"
        + " return oldValue;"
        + "})(this.obj)");

    if (oldValue == null) {
      structureChanged();
    }

    return oldValue;
  }

  public Object remove(String key) {
    ScriptHelper.put("key", key);
    Object value = ScriptHelper.eval("(function(map){"
        + " var oldValue = map[key];"
        + " delete map[key];"
        + " return oldValue;"
        + "})(this.obj)");

    if (value != null) {
      structureChanged();
    }

    return value;
  }

  public String[] keys() {
    return (String[]) ScriptHelper.eval("Object.getOwnPropertyNames(this.obj)");
  }

  public int size() {
    return keys().length;
  }

  @Override
  public JSIterator iterator() {
    Object obj = ScriptHelper.eval("(function(map){"
        + "var keys = Object.getOwnPropertyNames(map);"
        + "var nextIndex = 0;"
        + "return {"
        + " hasNext: function(){"
        + "  return nextIndex < keys.length;"
        + " },"
        + " next: function(){"
        + "  if(nextIndex >= keys.length)"
        + "   return null;"
        + "  var key = keys[nextIndex++];"
        + "  return { key: key, value: map[key]  };"
        + " }"
        + "};"
        + "})(this.obj)");

    return obj != null ? new JSIterator(obj) : null;
  }

  public void structureChanged() {}
}
