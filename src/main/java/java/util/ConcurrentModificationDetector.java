/*
 * Copyright 2014 Google Inc.
 * 
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

import javascript.JSObject;

/**
 * A helper to detect concurrent modifications to collections. This is
 * implemented as a helper utility so that we could remove the checks easily by
 * a flag.
 */
final class ConcurrentModificationDetector {

  private static final String MOD_COUNT_PROPERTY = "__j2js_modCount";

  public static int getModCount(Object map) {
    Object o = JSObject.get(map, MOD_COUNT_PROPERTY);
    return o != null ? (int) o : 0;
  }

  public static void setModCount(Object map, int modCount) {
    JSObject.put(map, MOD_COUNT_PROPERTY, modCount);
  }

  private static final boolean API_CHECK = true;
  // System.getProperty("checks.api").equals("ENABLED");

  public static void structureChanged(Object map) {
    if (!API_CHECK) {
      return;
    }
    setModCount(map, getModCount(map) + 1);
  }

  public static void recordLastKnownStructure(Object host, Iterator<?> iterator) {
    if (!API_CHECK) {
      return;
    }
    setModCount(iterator, getModCount(host));
  }

  public static void checkStructuralChange(Object host, Iterator<?> iterator) {
    if (!API_CHECK) {
      return;
    }
    if (getModCount(iterator) != getModCount(host)) {
      throw new ConcurrentModificationException();
    }
  }
}
