package javascript;

import java.util.Iterator;
import java.util.Map.Entry;

public class JSIterator<V> extends JSObject<V> implements Iterator<Entry<String, V>> {

  JSIterator(Object obj) {
    super(obj);
  }

  @Override
  public boolean hasNext() {
    return ScriptHelper.evalBoolean("this.obj.hasNext()");
  }

  @Override
  public JSIteratorEntry<V> next() {
    Object obj = ScriptHelper.eval("this.obj.next()");
    return obj != null ? new JSIteratorEntry<V>(obj) : null;
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
