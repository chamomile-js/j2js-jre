package javascript;

import java.util.Map.Entry;

public class JSIteratorEntry extends JSObject implements Entry<String, Object> {

  JSIteratorEntry(Object obj) {
    super(obj);
  }

  @Override
  public String getKey() {
    return (String) ScriptHelper.eval("this.obj.key");
  }

  @Override
  public Object getValue() {
    return ScriptHelper.eval("this.obj.value");
  }

  @Override
  public Object setValue(Object value) {
    throw new UnsupportedOperationException();
  }

}
