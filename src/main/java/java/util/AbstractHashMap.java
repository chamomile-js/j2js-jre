package java.util;

import static java.util.ConcurrentModificationDetector.checkStructuralChange;
import static java.util.ConcurrentModificationDetector.recordLastKnownStructure;
import static java.util.ConcurrentModificationDetector.structureChanged;
import static org.chamomile.util.InternalPreconditions.checkArgument;
import static org.chamomile.util.InternalPreconditions.checkElement;
import static org.chamomile.util.InternalPreconditions.checkState;

import java.io.Serializable;

public abstract class AbstractHashMap<K, V> extends AbstractMap<K, V>
    implements Map<K, V>, Cloneable, Serializable {
  private static final long serialVersionUID = 362498820763181265L;

  private final class EntrySet extends AbstractSet<Entry<K, V>> {

    @Override
    public void clear() {
      AbstractHashMap.this.clear();
    }

    @Override
    public boolean contains(Object o) {
      if (o instanceof Map.Entry) {
        return containsEntry((Map.Entry<?, ?>) o);
      }
      return false;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
      return new EntrySetIterator();
    }

    @Override
    public boolean remove(Object entry) {
      if (contains(entry)) {
        Object key = ((Map.Entry<?, ?>) entry).getKey();
        AbstractHashMap.this.remove(key);
        return true;
      }
      return false;
    }

    @Override
    public int size() {
      return AbstractHashMap.this.size();
    }
  }
  
  boolean containsEntry(Entry<?, ?> entry) {
    Object key = entry.getKey();
    Object value = entry.getValue();
    Object ourValue = get(key);

    if (!Objects.equals(value, ourValue)) {
      return false;
    }

    // Perhaps it was null and we don't contain the key?
    if (ourValue == null && !containsKey(key)) {
      return false;
    }

    return true;
  }

  /**
   * Iterator for <code>EntrySet</code>.
   */
  private final class EntrySetIterator implements Iterator<Entry<K, V>> {
    @SuppressWarnings("rawtypes")
    private Iterator stringMapEntries = stringMap.iterator();
    @SuppressWarnings("rawtypes")
    private Iterator current = stringMapEntries;
    @SuppressWarnings("rawtypes")
    private Iterator last;
    private boolean hasNext = computeHasNext();

    public EntrySetIterator() {
      recordLastKnownStructure(AbstractHashMap.this, this);
    }

    @Override
    public boolean hasNext() {
      return hasNext;
    }

    private boolean computeHasNext() {
      if (current.hasNext()) {
        return true;
      }
      if (current != stringMapEntries) {
        return false;
      }
      current = hashCodeMap.iterator();
      return current.hasNext();
    }

    @Override
    public Entry<K, V> next() {
      checkStructuralChange(AbstractHashMap.this, this);
      checkElement(hasNext());

      last = current;
      @SuppressWarnings("unchecked")
      Entry<K, V> rv = (Entry<K, V>) current.next();
      hasNext = computeHasNext();

      return rv;
    }

    @Override
    public void remove() {
      checkState(last != null);
      checkStructuralChange(AbstractHashMap.this, this);

      last.remove();
      last = null;
      hasNext = computeHasNext();

      recordLastKnownStructure(AbstractHashMap.this, this);
    }
  }

  /** A map of integral hashCodes onto entries. */
  private transient InternalHashCodeMap<K, V> hashCodeMap;

  /** A map of String onto value. */
  private transient InternalStringMap<V> stringMap;

  public AbstractHashMap() {
    reset();
  }

  public AbstractHashMap(int ignored) {
    // This implementation of HashMap has no need of initial capacities.
    this(ignored, 0);
  }

  public AbstractHashMap(int ignored, float alsoIgnored) {
    // This implementation of HashMap has no need of load factors or capacities.
    checkArgument(ignored >= 0, "Negative initial capacity");
    checkArgument(alsoIgnored >= 0, "Non-positive load factor");
    reset();
  }

  public AbstractHashMap(Map<? extends K, ? extends V> toBeCopied) {
    reset();
    this.putAll(toBeCopied);
  }

  @Override
  public void clear() {
    reset();
  }

  private void reset() {
    hashCodeMap = new InternalHashCodeMap<K, V>(this);
    stringMap = new InternalStringMap<V>(this);
    structureChanged(this);
  }

  @Override
  public boolean containsKey(Object key) {
    return key instanceof String
        ? hasStringValue((String) key) : hasHashValue(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return containsStringValue(value, stringMap) || containsHashValue(value, hashCodeMap);
  }

  private boolean containsHashValue(Object value, Iterable<Entry<K, V>> entries) {
    for (Entry<K, V> entry : entries) {
      if (equals(value, entry.getValue())) {
        return true;
      }
    }
    return false;
  }
  
  private boolean containsStringValue(Object value, InternalStringMap<V> stringMap) {
    for (Entry<String, Object> entry : stringMap) {
      if (equals(value, entry.getValue())) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    return new EntrySet();
  }

  @Override
  public V get(Object key) {
    return key instanceof String
        ? getStringValue((String) key) : getHashValue(key);
  }

  @Override
  public V put(K key, V value) {
    return key instanceof String
        ? putStringValue((String) key, value) : putHashValue(key, value);
  }

  @Override
  public V remove(Object key) {
    return key instanceof String
        ? removeStringValue((String) key) : removeHashValue(key);
  }

  @Override
  public int size() {
    return hashCodeMap.size() + stringMap.size();
  }

  abstract boolean equals(Object value1, Object value2);

  /**
   * Subclasses must override to return a hash code for a given key. The key is
   * guaranteed to be non-null and not a String.
   */
  abstract int getHashCode(Object key);

  @SuppressWarnings("unchecked")
  private V getHashValue(Object key) {
    return (V) hashCodeMap.getEntry(key);
  }

  @SuppressWarnings("unchecked")
  private V getStringValue(String key) {
    return key == null ? getHashValue(null) : (V) stringMap.get(key);
  }

  private boolean hasHashValue(Object key) {
    return hashCodeMap.getEntry(key) != null;
  }

  private boolean hasStringValue(String key) {
    return key == null ? hasHashValue(null) : stringMap.containsKey(key);
  }

  private V putHashValue(K key, V value) {
    return hashCodeMap.put(key, value);
  }
  
  @SuppressWarnings("unchecked")
  private V putStringValue(String key, V value) {
    return key == null ? putHashValue(null, value) : (V) stringMap.put(key, value);
  }
  
  private V removeHashValue(Object key) {
    return hashCodeMap.remove(key);
  }
  
  @SuppressWarnings("unchecked")
  private V removeStringValue(String key) {
    return key == null ? removeHashValue(null) : (V) stringMap.remove(key);
  }

}
