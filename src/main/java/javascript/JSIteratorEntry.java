package javascript;

import java.util.Map.Entry;

public class JSIteratorEntry<V> extends JSObject<V> implements Entry<String, V> {

  JSIteratorEntry(Object obj) {
    super(obj);
  }

  @Override
  public String getKey() {
    return (String) ScriptHelper.eval("this.obj.key");
  }

  @SuppressWarnings("unchecked")
  @Override
  public V getValue() {
    return (V) ScriptHelper.eval("this.obj.value");
  }

  @Override
  public V setValue(V value) {
    throw new UnsupportedOperationException();
  }

}
