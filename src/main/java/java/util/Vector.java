package java.util;

import static org.chamomile.util.InternalPreconditions.checkArgument;
import static org.chamomile.util.InternalPreconditions.checkElementIndex;
import static org.chamomile.util.InternalPreconditions.checkPositionIndex;

import javascript.ScriptHelper;

/**
 * To keep performance characteristics in line with Java community expectations,
 * <code>Vector</code> is a wrapper around <code>ArrayList</code>.
 * <a href="http://java.sun.com/j2se/1.5.0/docs/api/java/util/Vector.html">[Sun
 * docs]</a>
 *
 * @param <E>
 *          element type.
 */
public class Vector<E> extends AbstractList<E>
    implements List<E>, RandomAccess, Cloneable, java.io.Serializable {
  /** use serialVersionUID from JDK 1.0.2 for interoperability */
  private static final long serialVersionUID = -2767605614048989439L;

  @SuppressWarnings("unchecked")
  private E[] array = (E[]) new Object[0];

  public Vector() {}

  public Vector(Collection<E> list) {
    for (E elem : list) {
      add(elem);
    }
  }

  public Vector(int initialCapacity) {
    checkArgument(initialCapacity >= 0, "Initial capacity must not be negative");
  }

  public Vector(int initialCapacity, int capacityIncrement) {
    checkArgument(initialCapacity >= 0, "Initial capacity must not be negative");
  }

  public synchronized void copyInto(Object[] anArray) {
    System.arraycopy(array, 0, anArray, 0, array.length);
  }

  public synchronized void trimToSize() {
    // We are always trimmed to size.
  }

  public synchronized void ensureCapacity(int minCapacity) {
    // Ignored.
  }

  public synchronized void setSize(int newSize) {
    if (newSize < array.length) {
      removeRange(newSize, array.length);
    } else {
      while (array.length < newSize) {
        add(null);
      }
    }
  }

  public synchronized int capacity() {
    return array.length;
  }

  @Override
  public synchronized int size() {
    return array.length;
  }

  @Override
  public synchronized boolean isEmpty() {
    return array.length == 0;
  }

  @SuppressWarnings("unchecked")
  public synchronized Enumeration<E> elements() {
    return Collections.enumeration(this);
  }

  @Override
  public synchronized int lastIndexOf(Object o) {
    return super.lastIndexOf(o);
  }

  public synchronized int lastIndexOf(Object o, int index) {
    if (index >= array.length)
      throw new IndexOutOfBoundsException(index + " >= " + array.length);

    if (o == null) {
      for (int i = index; i >= 0; i--)
        if (array[i] == null)
          return i;
    } else {
      for (int i = index; i >= 0; i--)
        if (o.equals(array[i]))
          return i;
    }
    return -1;
  }

  public synchronized E elementAt(int index) {
    checkElementIndex(index, array.length);
    return array[index];
  }

  public synchronized Object firstElement() {
    checkElementIndex(0, array.length);
    return array[0];
  }

  public synchronized E lastElement() {
    int index = array.length - 1;
    checkElementIndex(index, array.length);
    return array[index];
  }

  public synchronized void setElementAt(E obj, int index) {
    checkElementIndex(index, array.length);
    array[index] = obj;
  }

  public synchronized void removeElementAt(int index) {
    checkElementIndex(index, array.length);
    ScriptHelper.put("index", index);
    ScriptHelper.put("newSize", array.length - 1);
    ScriptHelper.eval("this.array.splice(index, 1)");
    ScriptHelper.eval("this.array.length = newSize");
  }

  public synchronized void insertElementAt(E obj, int index) {
    checkPositionIndex(index, array.length);
    ScriptHelper.put("index", index);
    ScriptHelper.put("element", obj);
    ScriptHelper.eval("this.array.splice(index, 0, element)");
  }

  public synchronized void addElement(E obj) {
    ScriptHelper.put("element", obj);
    ScriptHelper.eval("this.array.push(element)");
  }

  public synchronized boolean removeElement(Object obj) {
    return super.remove(obj);
  }

  public synchronized void removeAllElements() {
    super.clear();
  }

  @Override
  public synchronized Object clone() {
    return new Vector<E>(this);
  }

  // ---

  @Override
  public synchronized E get(int index) {
    checkElementIndex(index, array.length);
    return array[index];
  }

  public synchronized E set(int index, E element) {
    checkElementIndex(index, array.length);
    E old = array[index];
    array[index] = element;
    return old;
  }

  public synchronized boolean add(E e) {
    ScriptHelper.put("element", e);
    ScriptHelper.eval("this.array.push(element)");
    return true;
  }

  public boolean remove(Object o) {
    return super.remove(o);
  }

  public void add(int index, E element) {
    checkPositionIndex(index, array.length);
    ScriptHelper.put("index", index);
    ScriptHelper.put("element", element);
    ScriptHelper.eval("this.array.splice(index, 0, element)");
  }

  public synchronized E remove(int index) {
    checkElementIndex(index, array.length);
    E obj = array[index];

    ScriptHelper.put("index", index);
    ScriptHelper.put("newSize", array.length - 1);
    ScriptHelper.eval("this.array.splice(index, 1)");
    ScriptHelper.eval("this.array.length = newSize");

    return obj;
  }

  public synchronized boolean containsAll(Collection<?> c) {
    return super.containsAll(c);
  }

  public synchronized boolean addAll(Collection<? extends E> c) {
    return super.addAll(c);
  }

  public synchronized boolean removeAll(Collection<?> c) {
    return super.removeAll(c);
  }

  public synchronized boolean retainAll(Collection<?> c) {
    return super.retainAll(c);
  }

  public synchronized boolean addAll(int index, Collection<? extends E> c) {
    return super.addAll(index, c);
  }

  public synchronized boolean equals(Object o) {
    return super.equals(o);
  }

  public synchronized int hashCode() {
    return super.hashCode();
  }

  public synchronized String toString() {
    return super.toString();
  }

  public synchronized List<E> subList(int fromIndex, int toIndex) {
    return super.subList(fromIndex, toIndex);
  }
  
  public synchronized ListIterator<E> listIterator(int index) {
    return super.listIterator(index);
  }
  
  public synchronized ListIterator<E> listIterator() {
    return super.listIterator();
  }
  
  /*
  @formatter:of
  public synchronized void replaceAll(UnaryOperator<E> operator) {
    
  }
  
  public synchronized void sort(Comparator<? super E> c) {
    
  }

  public Spliterator<E> spliterator() {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */
}
