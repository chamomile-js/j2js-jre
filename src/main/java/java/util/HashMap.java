package java.util;

import java.io.Serializable;

public class HashMap<K, V> extends AbstractHashMap<K, V>
    implements Map<K, V>, Cloneable, Serializable {
  private static final long serialVersionUID = 362498820763181265L;

  public HashMap() {
  }

  public HashMap(int ignored) {
    super(ignored);
  }

  public HashMap(int ignored, float alsoIgnored) {
    super(ignored, alsoIgnored);
  }

  public HashMap(Map<? extends K, ? extends V> toBeCopied) {
    super(toBeCopied);
  }

  public Object clone() {
    return new HashMap<K, V>(this);
  }

  @Override
  boolean equals(Object value1, Object value2) {
    return Objects.equals(value1, value2);
  }

  @Override
  int getHashCode(Object key) {
    return key.hashCode();
  }
  
}
