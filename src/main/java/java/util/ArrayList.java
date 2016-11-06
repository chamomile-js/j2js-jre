/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package java.util;

import javascript.ScriptHelper;

/**
 * Resizable-array implementation of the List interface.
 * 
 * @param <E>
 *           the element type.
 */
public class ArrayList<E> extends AbstractCollection<E> implements List<E>, RandomAccess {
   
   class ArrayListIterator implements Iterator<E> {
      private int currentIndex, last;
      
      ArrayListIterator(ArrayList<E> theList) {
         currentIndex = 0;
         last = -1;
      }
      
      @Override
      public boolean hasNext() {
         return currentIndex < ArrayList.this.size();
      }
      
      @Override
      public E next() {
         // TODO Preconditions.checkState(hasNext());
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         return ArrayList.this.get(last = currentIndex++);
      }
      
      @Override
      public void remove() {
         // TODO Preconditions.checkState(last != -1);
         if (currentIndex == 0) {
            throw new RuntimeException();// TODO: IllegalStateException();
         }
         ArrayList.this.remove(last);
         currentIndex = last;
         last = -1;
      }
   }
   
   private E[] array;
   private int start;
   private int end;
   private ArrayList<E> backingList;
   
   /**
    * Constructs an empty vector so that its internal data array has size 10 and
    * its standard capacity increment is zero.
    *
    */
   public ArrayList() {
      array = (E[]) (new Object[0]);
      start = 0;
      end = array.length;
   }
   
   /**
    * Constructs a list containing the elements of the specified collection, in
    * the order they are returned by the collection's iterator.
    */
   public ArrayList(Collection<E> list) {
      this();
      for (E elem : list) {
         add(elem);
      }
   }
   
   protected ArrayList(ArrayList<E> theBackingList, int theStart, int theEnd) {
      start = theStart;
      end = theEnd;
      backingList = theBackingList;
      array = backingList.array;
   }
   
   /**
    * Constructs an empty list with the specified initial capacity.
    */
   public ArrayList(int initialCapacity) {
      initialCapacity++;
   }
   
   /**
    * Appends the specified element to the end of this list.
    */
   @Override
   public boolean add(E element) {
      ScriptHelper.put("element", element);
      ScriptHelper.eval("this.array.push(element)");
      end++;
      return true;
   }
   
   /**
    * Inserts the specified element at the specified position in this list.
    */
   @Override
   public void add(int index, E element) {
      if (index < 0 || index > end - start) {
         throw new ArrayIndexOutOfBoundsException(index);
      }
      ScriptHelper.put("index", index);
      ScriptHelper.put("element", element);
      ScriptHelper.eval("this.array.splice(index, 0, element)");
      end++;
      // for (int i=array.length; i>index; i--) {
      // array[i] = array[i-1];
      // }
      // array[index] = elem;
      //return true;
   }
   
   /**
    * Removes all of the elements from this list.
    */
   @Override
   public void clear() {
      removeRange(0, end - start);
   }
   
   /**
    * Returns true if this list contains the specified element.
    */
   @Override
   public boolean contains(Object value) {
      for (int i = start; i < end; i++) {
         // TODO: Do we have to use equals()?
         if (array[i] == value)
            return true;
      }
      return false;
   }
   
   /**
    * Returns an enumeration of the components of this vector.
    */
   @Override
   public Iterator<E> iterator() {
      return new ArrayListIterator(this);
   }
   
   /**
    * Increases the capacity of this ArrayList instance, if necessary, to ensure
    * that it can hold at least the number of elements specified by the minimum
    * capacity argument.
    */
   public void ensureCapacity(int capacity) {}
   
   /**
    * Compares the specified object with this list for equality.
    */
   @Override
   public boolean equals(Object o) {
      if (o == null || !(o instanceof ArrayList))
         return false;
      ArrayList other = (ArrayList) o;
      if (size() != other.size())
         return false;
         
      for (int i = 0; i < size(); i++) {
         Object e1 = get(i);
         Object e2 = other.get(i);
         if (!(e1 == null ? e2 == null : e1.equals(e2)))
            return false;
      }
      
      return true;
   }
   
   /**
    * Returns the element at the specified position in this list.
    */
   @Override
   public E get(int index) {
      if (index < 0 || index >= end - start) {
         throw new ArrayIndexOutOfBoundsException(index);
      }
      return array[start + index];
   }
   
   /**
    * Searches for the first occurrence of the given argument, testing for
    * equality using the equals method.
    */
   @Override
   public int indexOf(Object elem) {
      for (int i = start; i < end; i++) {
         if (elem.equals(array[i]))
            return i - start;
      }
      return -1;
   }
   
   /**
    * Tests if this list has no elements.
    */
   @Override
   public boolean isEmpty() {
      return start == end;
   }
   
   /**
    * Returns the index of the last occurrence of the specified object in this
    * list.
    */
   @Override
   public int lastIndexOf(Object elem) {
      for (int i = end; i >= start; i--) {
         // TODO: Do we have to use equals()?
         if (elem == array[i])
            return i - start;
      }
      return -1;
   }
   
   /**
    * Removes a single instance of the specified element from this collection,
    * if it is present (optional operation).
    */
   @Override
   public boolean remove(Object elem) {
      int index = indexOf(elem);
      if (index == -1)
         return false;
      remove(index);
      return true;
   }
   
   /**
    * Removes the element at the specified position in this list.
    */
   @Override
   public E remove(int index) {
      E obj = array[index];
      removeRange(index, index + 1);
      return obj;
   }
   
   /**
    * Removes from this List all of the elements whose index is between
    * fromIndex, inclusive and toIndex, exclusive.
    */
   protected void removeRange(int fromIndex, int toIndex) {
      int deleteCount = toIndex - fromIndex;
      
      if (fromIndex < 0 || toIndex > end - start) {
         throw new ArrayIndexOutOfBoundsException();
      }
      
      int srcPosition = start + fromIndex + deleteCount;
      System.arraycopy(array, srcPosition, array, start + fromIndex, array.length - srcPosition);
      
      ScriptHelper.put("newSize", array.length - deleteCount);
      ScriptHelper.eval("this.array.length = newSize");
      incEnd(-deleteCount);
   }
   
   private void incEnd(int count) {
      end += count;
      if (backingList != null)
         backingList.incEnd(count);
   }
   
   /**
    * Replaces the element at the specified position in this list with the
    * specified element.
    */
   @Override
   public E set(int index, E elem) {
      if (index < 0 || index >= end - start) {
         throw new ArrayIndexOutOfBoundsException();
      }
      E old = array[start + index];
      array[start + index] = elem;
      return old;
   }
   
   /**
    * Returns the number of elements in this list.
    */
   @Override
   public int size() {
      return end - start;
   }
   
   /**
    * Returns a view of the portion of this list between the specified
    * fromIndex, inclusive, and toIndex, exclusive.
    */
   @Override
   public List<E> subList(int fromIndex, int toIndex) {
      return new ArrayList<E>(this, fromIndex, toIndex);
   }
   
   /**
    * Trims the capacity of this ArrayList instance to be the list's current
    * size.
    */
   public void trimToSize() {}

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }
   
}
