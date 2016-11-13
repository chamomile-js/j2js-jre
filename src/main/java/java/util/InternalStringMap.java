package java.util;

import javascript.JSObject;

final class InternalStringMap<V> extends JSObject<V> {
  
  private final Map<?, V> host;

  InternalStringMap(Map<?, V> host) {
    this.host = host;
  }
  
  @Override
  public void structureChanged() {
    ConcurrentModificationDetector.structureChanged(host);
  }
}
