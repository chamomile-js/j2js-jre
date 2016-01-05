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
 * General-purpose interface for storing collections of objects.
 * 
 * @param <E>
 *           element type
 */
public interface Collection<E> extends Iterable<E> {
   /**
    * Appends the specified element to the end of this collection (optional
    * operation).
    */
   boolean add(E element);
   
   /**
    * Removes all of the elements from this collection (optional operation).
    */
   void clear();
   
   /**
    * Returns true if this collection contains the specified element.
    */
   boolean contains(Object value);
   
   /**
    * Compares the specified object with this collection for equality.
    */
   boolean equals(Object o);
   
   /**
    * Returns true if this collection contains no elements.
    */
   boolean isEmpty();
   
   /**
    * Returns an iterator over the elements in this collection in proper
    * sequence.
    */
   Iterator<E> iterator();
   
   /**
    * Removes all of the elements from this collection (optional operation).
    */
   boolean remove(Object elem);
   
   /**
    * Returns the number of elements in this collection.
    */
   int size();
   
   /**
    * Returns an array containing all of the elements in this collection in
    * proper sequence.
    */
   Object[] toArray();
   
   /**
    * Returns an array containing all of the elements in this collection in the
    * correct order.
    */
   <T> T[] toArray(T[] target);
}