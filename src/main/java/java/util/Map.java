package java.util;

/**
 * An object that maps keys to values. A map cannot contain duplicate keys; each
 * key can map to at most one value.
 */
public interface Map<K, V> {
   
   /**
    * Removes all mappings from this map.
    */
   void clear();
   
   /**
    * Returns true if this map contains a mapping for the specified key.
    */
   boolean containsKey(Object key);
   
   /**
    * Returns true if this map maps one or more keys to the specified value.
    */
   boolean containsValue(Object value);
   
   /**
    * Returns the value to which the specified key is mapped in this identity
    * hash map, or null if the map contains no mapping for this key.
    */
   V get(Object key);
   
   /**
    * Returns true if this map contains no key-value mappings.
    */
   boolean isEmpty();
   
   /**
    * Returns a set view of the keys contained in this map.
    */
   Set<K> keySet();
   
   /**
    * Associates the specified value with the specified key in this map.
    */
   V put(K key, V value);
   
   /**
    * Removes the mapping for this key from this map if present.
    */
   V remove(Object key);
   
   /**
    * Returns the number of key-value mappings in this map.
    */
   int size();
   
   /**
    * Returns a collection view of the values contained in this map.
    */
   Collection<V> values();
   
   interface Entry<K, V> {
      K getKey();
      
      V getValue();
      
      V setValue(V value);
      
      boolean equals(Object o);
      
      int hashCode();
   }
   
   Set<Map.Entry<K, V>> entrySet();
   
   void putAll(Map<? extends K, ? extends V> m);
   
}