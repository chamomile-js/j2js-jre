package javax.script;

final class ScriptEngineImpl implements ScriptEngine {
   
   @Override
   public native Object eval(String script);
   
   @Override
   public native void put(String key, Object value);
   
}
