/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package java.util;

/**
 * This class provides a skeletal implementation of the <tt>Collection</tt>
 * interface, to minimize the effort required to implement this interface.
 * <p>
 *
 * To implement an unmodifiable collection, the programmer needs only to extend
 * this class and provide implementations for the <tt>iterator</tt> and
 * <tt>size</tt> methods. (The iterator returned by the <tt>iterator</tt> method
 * must implement <tt>hasNext</tt> and <tt>next</tt>.)
 * <p>
 *
 * To implement a modifiable collection, the programmer must additionally
 * override this class's <tt>add</tt> method (which otherwise throws an
 * <tt>UnsupportedOperationException</tt>), and the iterator returned by the
 * <tt>iterator</tt> method must additionally implement its <tt>remove</tt>
 * method.
 * <p>
 *
 * The programmer should generally provide a void (no argument) and
 * <tt>Collection</tt> constructor, as per the recommendation in the
 * <tt>Collection</tt> interface specification.
 * <p>
 *
 * The documentation for each non-abstract method in this class describes its
 * implementation in detail. Each of these methods may be overridden if the
 * collection being implemented admits a more efficient implementation.
 * <p>
 *
 * This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html"> Java
 * Collections Framework</a>.
 *
 * @author Josh Bloch
 * @author Neal Gafter
 * @see Collection
 * @since 1.2
 */
public abstract class AbstractCollection<E> implements Collection<E>, Iterable<E> {
  /**
   * Sole constructor. (For invocation by subclass constructors, typically
   * implicit.)
   */
  protected AbstractCollection() {}

  // Query Operations

  /**
   * Returns an iterator over the elements contained in this collection.
   *
   * @return an iterator over the elements contained in this collection
   */
  @Override
  public abstract Iterator<E> iterator();

  @Override
  public abstract int size();

  /**
   * {@inheritDoc}
   * <p>
   * This implementation returns <tt>size() == 0</tt>.
   */
  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  /**
   * {@inheritDoc}
   *
   * <p>
   * This implementation iterates over the elements in the collection, checking
   * each element in turn for equality with the specified element.
   *
   * @throws ClassCastException
   *           {@inheritDoc}
   * @throws NullPointerException
   *           {@inheritDoc}
   */
  public boolean contains(Object o) {
    Iterator<E> it = iterator();
    if (o == null) {
      while (it.hasNext())
        if (it.next() == null)
          return true;
    } else {
      while (it.hasNext())
        if (o.equals(it.next()))
          return true;
    }
    return false;
  }

  @Override
  public Object[] toArray() {
    return toArray(new Object[size()]);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T[] toArray(T[] target) {
    int size = size();
    if (target.length < size)
      target = (T[]) new Object[size];
    int i = 0;
    for (E element : this) {
      target[i++] = (T) element;
    }
    if (target.length > size)
      target[size] = null;
    return target;
  }

  // Modification Operations

  /**
   * {@inheritDoc}
   *
   * <p>
   * This implementation always throws an
   * <tt>UnsupportedOperationException</tt>.
   *
   * @throws UnsupportedOperationException
   *           {@inheritDoc}
   * @throws ClassCastException
   *           {@inheritDoc}
   * @throws NullPointerException
   *           {@inheritDoc}
   * @throws IllegalArgumentException
   *           {@inheritDoc}
   * @throws IllegalStateException
   *           {@inheritDoc}
   */
  public boolean add(E e) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   *
   * <p>
   * This implementation iterates over the collection looking for the specified
   * element. If it finds the element, it removes the element from the
   * collection using the iterator's remove method.
   *
   * <p>
   * Note that this implementation throws an
   * <tt>UnsupportedOperationException</tt> if the iterator returned by this
   * collection's iterator method does not implement the <tt>remove</tt> method
   * and this collection contains the specified object.
   *
   * @throws UnsupportedOperationException
   *           {@inheritDoc}
   * @throws ClassCastException
   *           {@inheritDoc}
   * @throws NullPointerException
   *           {@inheritDoc}
   */
  public boolean remove(Object o) {
    Iterator<E> it = iterator();
    if (o == null) {
      while (it.hasNext()) {
        if (it.next() == null) {
          it.remove();
          return true;
        }
      }
    } else {
      while (it.hasNext()) {
        if (o.equals(it.next())) {
          it.remove();
          return true;
        }
      }
    }
    return false;
  }

  // Bulk Operations

  /**
   * {@inheritDoc}
   *
   * <p>
   * This implementation iterates over the specified collection, checking each
   * element returned by the iterator in turn to see if it's contained in this
   * collection. If all elements are so contained <tt>true</tt> is returned,
   * otherwise <tt>false</tt>.
   *
   * @throws ClassCastException
   *           {@inheritDoc}
   * @throws NullPointerException
   *           {@inheritDoc}
   * @see #contains(Object)
   */
  public boolean containsAll(Collection<?> c) {
    for (Object e : c)
      if (!contains(e))
        return false;
    return true;
  }

  /**
   * {@inheritDoc}
   *
   * <p>
   * This implementation iterates over the specified collection, and adds each
   * object returned by the iterator to this collection, in turn.
   *
   * <p>
   * Note that this implementation will throw an
   * <tt>UnsupportedOperationException</tt> unless <tt>add</tt> is overridden
   * (assuming the specified collection is non-empty).
   *
   * @throws UnsupportedOperationException
   *           {@inheritDoc}
   * @throws ClassCastException
   *           {@inheritDoc}
   * @throws NullPointerException
   *           {@inheritDoc}
   * @throws IllegalArgumentException
   *           {@inheritDoc}
   * @throws IllegalStateException
   *           {@inheritDoc}
   *
   * @see #add(Object)
   */
  public boolean addAll(Collection<? extends E> c) {
    boolean modified = false;
    for (E e : c)
      if (add(e))
        modified = true;
    return modified;
  }

  /**
   * {@inheritDoc}
   *
   * <p>
   * This implementation iterates over this collection, checking each element
   * returned by the iterator in turn to see if it's contained in the specified
   * collection. If it's so contained, it's removed from this collection with
   * the iterator's <tt>remove</tt> method.
   *
   * <p>
   * Note that this implementation will throw an
   * <tt>UnsupportedOperationException</tt> if the iterator returned by the
   * <tt>iterator</tt> method does not implement the <tt>remove</tt> method and
   * this collection contains one or more elements in common with the specified
   * collection.
   *
   * @throws UnsupportedOperationException
   *           {@inheritDoc}
   * @throws ClassCastException
   *           {@inheritDoc}
   * @throws NullPointerException
   *           {@inheritDoc}
   *
   * @see #remove(Object)
   * @see #contains(Object)
   */
  public boolean removeAll(Collection<?> c) {
    Objects.requireNonNull(c);
    boolean modified = false;
    Iterator<?> it = iterator();
    while (it.hasNext()) {
      if (c.contains(it.next())) {
        it.remove();
        modified = true;
      }
    }
    return modified;
  }

  /**
   * {@inheritDoc}
   *
   * <p>
   * This implementation iterates over this collection, checking each element
   * returned by the iterator in turn to see if it's contained in the specified
   * collection. If it's not so contained, it's removed from this collection
   * with the iterator's <tt>remove</tt> method.
   *
   * <p>
   * Note that this implementation will throw an
   * <tt>UnsupportedOperationException</tt> if the iterator returned by the
   * <tt>iterator</tt> method does not implement the <tt>remove</tt> method and
   * this collection contains one or more elements not present in the specified
   * collection.
   *
   * @throws UnsupportedOperationException
   *           {@inheritDoc}
   * @throws ClassCastException
   *           {@inheritDoc}
   * @throws NullPointerException
   *           {@inheritDoc}
   *
   * @see #remove(Object)
   * @see #contains(Object)
   */
  public boolean retainAll(Collection<?> c) {
    Objects.requireNonNull(c);
    boolean modified = false;
    Iterator<E> it = iterator();
    while (it.hasNext()) {
      if (!c.contains(it.next())) {
        it.remove();
        modified = true;
      }
    }
    return modified;
  }

  /**
   * {@inheritDoc}
   *
   * <p>
   * This implementation iterates over this collection, removing each element
   * using the <tt>Iterator.remove</tt> operation. Most implementations will
   * probably choose to override this method for efficiency.
   *
   * <p>
   * Note that this implementation will throw an
   * <tt>UnsupportedOperationException</tt> if the iterator returned by this
   * collection's <tt>iterator</tt> method does not implement the
   * <tt>remove</tt> method and this collection is non-empty.
   *
   * @throws UnsupportedOperationException
   *           {@inheritDoc}
   */
  public void clear() {
    Iterator<E> it = iterator();
    while (it.hasNext()) {
      it.next();
      it.remove();
    }
  }

  // String conversion

  /**
   * Returns a string representation of this collection. The string
   * representation consists of a list of the collection's elements in the order
   * they are returned by its iterator, enclosed in square brackets
   * (<tt>"[]"</tt>). Adjacent elements are separated by the characters
   * <tt>", "</tt> (comma and space). Elements are converted to strings as by
   * {@link String#valueOf(Object)}.
   *
   * @return a string representation of this collection
   */
  public String toString() {
    Iterator<E> it = iterator();
    if (!it.hasNext())
      return "[]";

    StringBuilder sb = new StringBuilder();
    sb.append('[');
    for (;;) {
      E e = it.next();
      sb.append(e == this ? "(this Collection)" : e);
      if (!it.hasNext())
        return sb.append(']').toString();
      sb.append(',').append(' ');
    }
  }
}
