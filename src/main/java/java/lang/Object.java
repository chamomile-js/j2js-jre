/*
 * Copyright (c) 2005 j2js.com,
 *
 * All Rights Reserved. This work is distributed under the j2js Software License
 * [1] WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * [1] http://www.j2js.com/license.txt
 */
package java.lang;

import javascript.ScriptHelper;

/**
 * Class {@code Object} is the root of the class hierarchy. Every class has
 * {@code Object} as a superclass. All objects, including arrays, implement the
 * methods of this class.
 */
public class Object {

  private static int __hashCodeCount = 0;
  private transient final int __hashCode;

  public Object() {
    if (__hashCodeCount >= Integer.MAX_VALUE) {
      // TODO out of memory exception...
      throw new RuntimeException();
    }
    this.__hashCode = __hashCodeCount++;
  }

  public Class<?> getClass() {
    try {
      return Class.forName((String) ScriptHelper.eval("this.clazz.name"));
    } catch (ClassNotFoundException e) {
      // XXX should never happen...
      throw new RuntimeException(e);
    }
  }

  public int hashCode() {
    return __hashCode;
  }

  public boolean equals(Object obj) {
    return this == obj;
  }

  protected Object clone() throws CloneNotSupportedException {
    String className = (String) ScriptHelper.eval("this.clazz.name");

    // Special treatment for arrays, because for those we have no class to
    // overwrite the clone() method.
    if (className.startsWith("[")) {
      return ScriptHelper.eval("j2js.cloneArray(this)");
    }

    return null;
  }

  public String toString() {
    return getClass().getName() + "@" + Integer.toHexString(hashCode());
  }

  /**
   * Never called; here for JRE compatibility.
   * 
   * @skip
   */
  protected void finalize() throws Throwable {}
}
