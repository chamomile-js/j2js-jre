package javax.script;

/**
 * This class provides basic scripting functionality.
 */
public interface ScriptEngine {
   
   /**
    * Executes the specified script.
    * <p>
    * <b>Important</b>: In the current version the script must be a string
    * literal.
    * 
    * @param script
    *           The script language source to be executed.
    * @return The value returned from the execution of the script.
    * 
    * @throws ScriptException
    *            if error in script.
    * @throws NullPointerException
    *            is script is {@code null}.
    */
   Object eval(String script) /* throws ScriptException */;
   
   /**
    * Sets a key/value pair in the state of the ScriptEngine.
    * <p>
    * <b>Important</b>: In the current version the key must be a string literal.
    * 
    * @param key
    *           The name of named value to add.
    * @param value
    *           The value of named value to add.
    *           
    * @throws NullPointerException
    *            if key is {@code null}.
    * @throws IllegalArgumentException
    *            if key is empty.
    */
   void put(String key, Object value);
   
}
