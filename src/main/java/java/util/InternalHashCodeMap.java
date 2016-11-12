package java.util;

import javascript.JSHashMap;

final class InternalHashCodeMap<K, V> extends JSHashMap<K, V> {

  private final Map<?, V> host;

  InternalHashCodeMap(Map<K, V> host) {
    this.host = host;
  }

  @Override
  public void structureChanged() {
    ConcurrentModificationDetector.structureChanged(host);
  }
}
