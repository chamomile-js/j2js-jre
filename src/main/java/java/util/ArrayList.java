package java.util;

import static org.chamomile.util.InternalPreconditions.checkArgument;
import static org.chamomile.util.InternalPreconditions.checkElementIndex;
import static org.chamomile.util.InternalPreconditions.checkPositionIndex;

import java.io.Serializable;

import javascript.ScriptHelper;

public class ArrayList<E> extends AbstractList<E>
    implements List<E>, Cloneable, RandomAccess, Serializable {
  private static final long serialVersionUID = 8683452581122892189L;

  @SuppressWarnings("unchecked")
  private transient E[] array = (E[]) new Object[0];

  public ArrayList() {}

  public ArrayList(Collection<E> list) {
    for (E elem : list) {
      add(elem);
    }
  }

  public ArrayList(int initialCapacity) {
    checkArgument(initialCapacity >= 0, "Initial capacity must not be negative");
  }
  
  @Override
  public Object clone() {
    return new ArrayList<E>(this);
  }

  @Override
  public boolean add(E element) {
    ScriptHelper.put("element", element);
    ScriptHelper.eval("this.array.push(element)");
    return true;
  }

  @Override
  public void add(int index, E element) {
    checkPositionIndex(index, array.length);
    ScriptHelper.put("index", index);
    ScriptHelper.put("element", element);
    ScriptHelper.eval("this.array.splice(index, 0, element)");
  }

  public void ensureCapacity(int capacity) {
    // Ignored.
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || !(o instanceof ArrayList))
      return false;

    return super.equals(o);
  }

  @Override
  public E get(int index) {
    checkElementIndex(index, array.length);
    return array[index];
  }

  @Override
  public E remove(int index) {
    checkElementIndex(index, array.length);
    E obj = array[index];

    ScriptHelper.put("index", index);
    ScriptHelper.put("newSize", array.length - 1);
    ScriptHelper.eval("this.array.splice(index, 1)");
    ScriptHelper.eval("this.array.length = newSize");

    return obj;
  }

  @Override
  public E set(int index, E elem) {
    checkElementIndex(index, array.length);
    E old = array[index];
    array[index] = elem;
    return old;
  }

  @Override
  public int size() {
    return array.length;
  }

  public void trimToSize() {
    // We are always trimmed to size.
  }

}
