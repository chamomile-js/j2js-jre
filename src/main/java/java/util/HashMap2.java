package java.util;

import java.io.Serializable;

import javascript.JSObject;

public class HashMap2<K, V> extends AbstractMap<K, V>
    implements Map<K, V>, Cloneable, Serializable {
  private static final long serialVersionUID = 362498820763181265L;

  JSObject nativeMap;

  public HashMap2(Object theNativeObject) {
    nativeMap = new JSObject(theNativeObject);
  }
  
  // ---

  /**
   * Constructs an empty HashMap.
   */
  public HashMap2() {
    nativeMap = new JSObject();
  }

  /**
   * Removes all mappings from this map.
   */
  @Override
  public void clear() {
    nativeMap = new JSObject();
  }

  /**
   * Returns true if this map contains a mapping for the specified key.
   */
  @Override
  public boolean containsKey(Object key) {
    return nativeMap.containsKey((String) key);
  }

  /**
   * Returns true if this map maps one or more keys to the specified value.
   */
  @Override
  public boolean containsValue(Object value) {
    String[] keys = nativeMap.keys();
    for (String key : keys) {
      if (nativeMap.get(key) == value) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the value to which the specified key is mapped in this identity
   * hash map, or null if the map contains no mapping for this key.
   */
  @Override
  public V get(Object key) {
    return (V) nativeMap.get((String) key);
  }

  /**
   * Returns true if this map contains no key-value mappings.
   */
  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  /**
   * Returns a set view of the keys contained in this map.
   */
  @Override
  public HashSet<K> keySet() {
    HashSet<K> set = new HashSet<K>();
    K[] keys = (K[]) nativeMap.keys();
    for (int i = 0; i < keys.length; i++) {
      set.add(keys[i]);
    }
    return set;
  }

  /**
   * Associates the specified value with the specified key in this map.
   */
  @Override
  public V put(K key, V value) {
    V oldValue = (V) nativeMap.get((String) key);
    if (oldValue == null)
      oldValue = null;
    nativeMap.put((String) key, value);
    return oldValue;
  }

  /**
   * Removes the mapping for this key from this map if present.
   */
  @Override
  public V remove(Object key) {
    V oldValue = (V) nativeMap.get((String) key);
    if (oldValue == null)
      oldValue = null;
    nativeMap.remove((String) key);
    return oldValue;
  }

  /**
   * Returns the number of key-value mappings in this map.
   */
  @Override
  public int size() {
    return nativeMap.keys().length;
  }

  /**
   * Returns a string representation of this map.
   */
  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("{");
    String[] keys = nativeMap.keys();
    for (int i = 0; i < keys.length; i++) {
      String key = keys[i];
      if (i > 0)
        sb.append(", ");
      Object value = nativeMap.get(key);
      sb.append(key.toString());
      sb.append("=");
      sb.append(value == null ? "null" : value.toString());
    }
    sb.append("}");
    return sb.toString();
  }

  /**
   * Returns a collection view of the values contained in this map.
   */
  @Override
  public Collection<V> values() {
    List<V> list = new ArrayList<V>();
    String[] keys = nativeMap.keys();
    for (int i = 0; i < keys.length; i++) {
      list.add((V) nativeMap.get(keys[i]));
    }
    return list;
  }

  private class DefaultEntry implements Map.Entry<K, V> {
    private K k;

    public DefaultEntry(K k) {
      this.k = k;
    }

    @Override
    public K getKey() {
      return k;
    }

    @Override
    public V getValue() {
      return get(k);
    }

    @Override
    public V setValue(V value) {
      return put(k, value);
    }
  }

  @Override
  public Set<java.util.Map.Entry<K, V>> entrySet() {
    Set<Map.Entry<K, V>> result = new HashSet<Map.Entry<K, V>>();
    HashSet<K> keySet = keySet();

    for (final K k : keySet)
      result.add(new DefaultEntry(k));

    return result;
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    for (Entry<? extends K, ? extends V> entry : m.entrySet())
      put(entry.getKey(), entry.getValue());
  }
}
