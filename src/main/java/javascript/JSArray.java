package javascript;

/**
 * Provides utilities to perform operations on arrays.
 */
public class JSArray {

  public static int getLength(Object[] array) {
    ScriptHelper.put("array", array);
    return ScriptHelper.evalInt("array.length");
  }
  
  public static void setLength(Object[] array, int length) {
    ScriptHelper.put("array", array);
    ScriptHelper.put("length", length);
    ScriptHelper.eval("array.length = length");
  }
  
  public static void removeFrom(Object[] array, int index, int deleteCount) {
    ScriptHelper.put("array", array);
    ScriptHelper.put("index", index);
    ScriptHelper.put("deleteCount", deleteCount);
    ScriptHelper.eval("array.splice(index, deleteCount)");
  }
}
