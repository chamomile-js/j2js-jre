package javascript;

import java.util.Iterator;
import java.util.Map.Entry;

public class JSIterator extends JSObject implements Iterator<Entry<String, Object>> {

  JSIterator(Object obj) {
    super(obj);
  }

  @Override
  public boolean hasNext() {
    return ScriptHelper.evalBoolean("this.obj.hasNext()");
  }

  @Override
  public JSIteratorEntry next() {
    Object obj = ScriptHelper.eval("this.obj.next()");
    return obj != null ? new JSIteratorEntry(obj) : null;
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
