package javascript;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * A simple wrapper around {@link JSObject} to provide
 * {@link java.util.Map}-like semantics for any key type.
 * <p>
 * A key's hashCode is the index in {@code backingMap} which should contain that
 * key. Since several keys may have the same hash, each value in
 * {@code backingMap} is actually an array containing all entries whose keys
 * share the same hash.
 * 
 * @author ggeorg
 */
public class JSHashMap<K, V> implements Iterable<Entry<K, V>> {
  private final JSObject backingMap = new JSObject();
  private int size = 0;

  public V put(K key, V value) {
    String hashCode = String.valueOf(hash(key));
    Entry<K, V>[] chain = getChainOrEmpty(hashCode);

    if (chain.length == 0) {
      // This is a new chain, put it to the map...
      backingMap.put(hashCode, chain);
    } else {
      // Chain already exists, perhaps key also exists.
      Entry<K, V> entry = findEntryInChain(key, chain);
      if (entry != null) {
        return entry.setValue(value);
      }
    }
    chain[chain.length] = new AbstractMap.SimpleEntry<K, V>(key, value);
    ++size;
    structureChanged();
    return null;
  }

  public void structureChanged() {}

  public V remove(Object key) {
    String hashCode = String.valueOf(hash(key));
    Entry<K, V>[] chain = getChainOrEmpty(hashCode);
    for (int i = 0; i < chain.length; i++) {
      Entry<K, V> entry = chain[i];
      if (Objects.equals(key, entry.getKey())) {
        if (chain.length == 1) {
          JSArray.setLength(chain, 0);
          // remove the whole array
          backingMap.remove(hashCode);
        } else {
          // splice out the entry we're removing
          JSArray.removeFrom(chain, i, 1);
        }
        --size;
        structureChanged();
        return entry.getValue();
      }
    }
    return null;
  }

  public Entry<K, V> getEntry(Object key) {
    String hashCode = String.valueOf(hash(key));
    return findEntryInChain(key, getChainOrEmpty(hashCode));
  }

  @Override
  public Iterator<Entry<K, V>> iterator() {
    return new Iterator<Entry<K, V>>() {
      final JSIterator chains = backingMap.iterator();

      int itemIndex = 0;
      Entry<K, V>[] chain = newEntryChain();
      Entry<K, V> lastEntry = null;

      @SuppressWarnings("unchecked")
      @Override
      public boolean hasNext() {
        if (itemIndex < chain.length) {
          return true;
        }
        if (chains.hasNext()) {
          // Move to the beginning of the next chain...
          chain = (Entry<K, V>[]) chains.next().getValue();
          itemIndex = 0;
          return true;
        }
        return false;
      }

      @Override
      public Entry<K, V> next() {
        lastEntry = chain[itemIndex++];
        return lastEntry;
      }

      @Override
      public void remove() {
        JSHashMap.this.remove(lastEntry.getKey());
        // Unless we are in a new chain, all items have shifted so our itemIndex
        // should as well...
        if (itemIndex != 0) {
          --itemIndex;
        }
      }
    };
  }

  public int size() {
    return backingMap.size();
  }

  private Entry<K, V> findEntryInChain(Object key, Entry<K, V>[] chain) {
    for (Entry<K, V> entry : chain) {
      if (Objects.equals(key, entry.getKey())) {
        return entry;
      }
    }
    return null;
  }

  private Entry<K, V>[] getChainOrEmpty(String hashCode) {
    @SuppressWarnings("unchecked")
    Entry<K, V>[] chain = (Entry<K, V>[]) backingMap.get(hashCode);
    return chain == null ? newEntryChain() : chain;
  }

  @SuppressWarnings("unchecked")
  private Entry<K, V>[] newEntryChain() {
    return (Entry<K, V>[]) ScriptHelper.eval("[]");
  }

  private static int hash(Object key) {
    return key == null ? 0 : key.hashCode();
  }
}
