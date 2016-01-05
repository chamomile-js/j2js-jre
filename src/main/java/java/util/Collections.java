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

/**
 * Utility methods that operate on collections.
 */
public class Collections {
   
   private static class IteratorEnumarator<E> implements Enumeration<E> {
      
      Iterator<E> iterator;
      
      IteratorEnumarator(Iterator<E> theIterator) {
         iterator = theIterator;
      }
      
      public boolean hasMoreElements() {
         return iterator.hasNext();
      }
      
      public E nextElement() {
         if (!iterator.hasNext()) {
            throw new NoSuchElementException();
         }
         return iterator.next();
      }
   }
   
   /**
    * Returns an enumeration over the specified collection. This provides
    * interoperatbility with legacy APIs that require an enumeration as input.
    */
   public static Enumeration enumeration(Collection c) {
      return new IteratorEnumarator(c.iterator());
   }
   
   private static Random r = new Random();
   
   /**
    * Randomly permutes the specified list using a default source of randomness.
    */
   public static void shuffle(List<?> list) {
      int size = list.size();
      for (int i = size; i > 1; i--) {
         swap(list, i - 1, r.nextInt(i));
      }
   }
   
   /**
    * Sorts the specified list into ascending order, according to the natural
    * ordering of its elements. <br/>
    * <b>Important</b>: This is a restriction of the Java API sort(List<T>)
    * function.
    */
   public static void sort(List<String> list) {
      String[] array = (String[]) list.toArray();
      Arrays.sort(array);
      int count = array.length;
      list.clear();
      for (int i = 0; i < count; i++) {
         list.add(array[i]);
      }
   }
   
   /**
    * Swaps the two specified elements in the specified array.
    */
   public static void swap(List<?> list, int i, int j) {
      final List l = list;
      l.set(i, l.set(j, l.get(i)));
   }
   
   // ---
   
   /**
    * Perform a binary search on a sorted {@link List}, using natural ordering.
    * <p>
    * Note: the implementation is based on GWT, which differs from the JDK
    * implementation in that it does not do an iterator-based binary search for
    * Lists that do not implement RandomAccess.
    * 
    * @param sortedList
    *           object array to search
    * @param key
    *           value to search for
    * @return the index of an element with a matching value, or a negative
    *         number which is the index of the next larger value (or just past
    *         the end of the array if the searched value is larger than all
    *         elements in the array) minus 1 (to ensure error returns are
    *         negative)
    * @throws ClassCastException
    *            if <code>key</code> is not comparable to
    *            <code>sortedList</code>'s elements.
    */
   public static <T> int binarySearch(final List<? extends Comparable<? super T>> sortedList, final T key) {
      return binarySearch(sortedList, key, null);
   }
   
   /**
    * Perform a binary search on a sorted {@link List}, using a user-specified
    * comparison function.
    *
    * <p>
    * Note: the implementation is based on GWT, which differs from the JDK
    * implementation in that it does not do an iterator-based binary search for
    * Lists that do not implement RandomAccess.
    * </p>
    *
    * @param sortedList
    *           List to search
    * @param key
    *           value to search for
    * @param comparator
    *           comparison function, <code>null</code> indicates <i>natural
    *           ordering</i> should be used.
    * @return the index of an element with a matching value, or a negative
    *         number which is the index of the next larger value (or just past
    *         the end of the array if the searched value is larger than all
    *         elements in the array) minus 1 (to ensure error returns are
    *         negative)
    * @throws ClassCastException
    *            if <code>key</code> and <code>sortedList</code>'s elements
    *            cannot be compared by <code>comparator</code>.
    */
   public static <T> int binarySearch(final List<? extends T> sortedList,
         final T key, Comparator<? super T> comparator) {
      /*
       * TODO: This doesn't implement the "iterator-based binary search"
       * described in the JDK docs for non-RandomAccess Lists. Until GWT
       * provides a LinkedList, this shouldn't be an issue.
       */
      if (comparator == null) {
         comparator = Comparators.natural();
      }
      int low = 0;
      int high = sortedList.size() - 1;
      
      while (low <= high) {
         final int mid = low + ((high - low) >> 1);
         final T midVal = sortedList.get(mid);
         final int compareResult = comparator.compare(midVal, key);
         
         if (compareResult < 0) {
            low = mid + 1;
         } else if (compareResult > 0) {
            high = mid - 1;
         } else {
            // key found
            return mid;
         }
      }
      // key not found.
      return -low - 1;
   }
}
