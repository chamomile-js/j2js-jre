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
 * Represents a sequence of objects.
 * 
 * @param <E>
 *           element type
 */
public interface List<T> extends Collection<T> {
   /**
    * Inserts the specified element at the specified position in this list
    * (optional operation).
    */
   void add(int index, T element);
   
   /**
    * Compares the specified object with this list for equality.
    */
   boolean equals(Object o);
   
   /**
    * Returns the element at the specified position in this list.
    */
   T get(int index);
   
   /**
    * Returns the index in this list of the first occurrence of the specified
    * element, or -1 if this list does not contain this element.
    */
   int indexOf(Object elem);
   
   /**
    * Returns the index in this list of the last occurrence of the specified
    * element, or -1 if this list does not contain this element.
    */
   int lastIndexOf(Object elem);
   
   /**
    * Removes the element at the specified position in this list (optional
    * operation).
    */
   T remove(int index);
   
   /**
    * Replaces the element at the specified position in this list with the
    * specified element (optional operation).
    */
   T set(int index, T elem);
   
   /**
    * Returns a view of the portion of this list between the specified
    * fromIndex, inclusive, and toIndex, exclusive.
    */
   List<T> subList(int fromIndex, int toIndex);
   
}