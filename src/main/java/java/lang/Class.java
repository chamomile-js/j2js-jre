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

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javascript.JSObject;
import javascript.ScriptHelper;

/**
 * The in-memory representation of a Java class. This representation serves as
 * the starting point for querying class-related information, a process usually
 * called "reflection". There are basically three types of {@code Class}
 * instances: those representing real classes and interfaces, those representing
 * primitive types, and those representing array classes.
 */
public final class Class<T> implements java.io.Serializable {
  /** use serialVersionUID from JDK 1.1 for interoperability */
  private static final long serialVersionUID = 3206093459760846163L;

  private class AnnotationInvocationHandler implements InvocationHandler {
    private JSObject object;

    AnnotationInvocationHandler(JSObject theObject) {
      object = theObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
      // TODO: Annotation
      String name = method.getName();
      if (name.equals("toString")) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("@" + object.get("$signature"));
        return buffer.toString();
      }
      return object.get(method.getName());
      // @formatter:off
	    // return "@" + object.get("signature") + "(" + method.getName() +
	    // "=13)";
	    // @formatter:on
    }
  }
  
  // ---

  private static final JSObject classesByName = new JSObject();
  @SuppressWarnings("unused")
  private Object nativeClass;

  /**
   * Constructor: only the VM creates {@code Class} objects.
   * 
   * @param theNativeClass
   *          the native class.
   */
  private Class(Object theNativeClass) {
    nativeClass = theNativeClass;
  }

  @Override
  public String toString() {
    return "class " + getName();
  }

  /**
   * Returns the {@code Class} object associated with the class or interface
   * with the given string name.
   * <p>
   * A call to {@code forName("X")} causes the class named {@code X} to be
   * initialized.
   * 
   * @param className
   *          the fully qualified name of the desired class.
   * @return the {@code Class} object for the class with the specified name.
   * @throws ClassNotFoundException
   *           if the class cannot be located.
   */
  public static Class<?> forName(String className) throws ClassNotFoundException {
    Class<?> clazz = (Class<?>) classesByName.get(className);
    if (clazz != null)
      return clazz;

    ScriptHelper.put("className", className);
    Object nativeClass = ScriptHelper.eval("j2js.forName(className)");

    if (nativeClass == null) {
      throw new ClassNotFoundException(className);
    }

    clazz = new Class<Object>(nativeClass);
    classesByName.put(className, clazz);
    return clazz;
  }

  private transient Class<?> componentType;

  /**
   * Returns the Class representing the component type of an array. If this
   * class does not represent an array class this method returns null.
   */
  public Class<?> getComponentType() {
    if (componentType == null) {
      String className = getName();
      if (className != null && className.charAt(0) == '[') {
        className = className.substring(1);
        try {
          componentType = Class.forName(className);
        } catch (java.lang.ClassNotFoundException e) {
          throw new NullPointerException(e.getMessage());
        }
      }
    }
    return componentType;
  }
  
  /*
   * Return the Virtual Machine's Class object for the named
   * primitive type.
   */
  static Class<?> getPrimitiveClass(String signature) {
    try {
      return forName(signature);
    } catch (java.lang.ClassNotFoundException e) {
      return null;
    }
  }

  /**
   * Creates a new instance of the class represented by this {@code Class}
   * object. The class is instantiated as if by a {@code new} expression with an
   * empty argument list. The class is initialized if it has not already been
   * initialized.
   * 
   * @return a newly allocated instance of the class represented by this object.
   * @throws IllegalAccessException
   *           if the class or its nullary constructor is not accessible.
   * @throws InstantiationException
   *           if this {@code Class} represents an abstract class, an interface,
   *           an array class, a primitive type, or void; or if the class has no
   *           nullary constructor; or if the instantiation fails for some other
   *           reason.
   */
  @SuppressWarnings("unchecked")
  public T newInstance() throws InstantiationException, IllegalAccessException {
    // Create a new instance.
    ScriptHelper.eval("var o = this.nativeClass.newInstance()");
    // Initialize it.
    ScriptHelper.eval("j2js.invoke(o, '<init>()void', [])");
    return (T) ScriptHelper.eval("o");
  }

  /**
   * Determines if the specified {@code Object} is assignment-compatible with
   * the object represented by this {@code Class}. This method is the dynamic
   * equivalent of the Java language {@code instanceof} operator. The method
   * returns {@code true} if the specified {@code Object} argument is non-null
   * and can be cast to the reference type represented by this {@code Class}
   * object without raising a {@code ClassCastException.} It returns
   * {@code false} otherwise.
   *
   * <p>
   * Specifically, if this {@code Class} object represents a declared class,
   * this method returns {@code true} if the specified {@code Object} argument
   * is an instance of the represented class (or of any of its subclasses); it
   * returns {@code false} otherwise. If this {@code Class} object represents an
   * array class, this method returns {@code true} if the specified
   * {@code Object} argument can be converted to an object of the array class by
   * an identity conversion or by a widening reference conversion; it returns
   * {@code false} otherwise. If this {@code Class} object represents an
   * interface, this method returns {@code true} if the class or any superclass
   * of the specified {@code Object} argument implements this interface; it
   * returns {@code false} otherwise. If this {@code Class} object represents a
   * primitive type, this method returns {@code false}.
   *
   * @param object
   *          the object to check
   * @return true if {@code object} is an instance of this class
   *
   * @since JDK1.1
   */
  public boolean isInstance(Object object) {
    if (object == null) {
      return false;
    }
    return isAssignableFrom(object.getClass());
  }

  public boolean _isInstance(Object obj) {
    Class<?> cls = obj.getClass();
    do {
      if (cls.getName().equals(getName())) {
        return true;
      }
      if (cls.getName().equals("java.lang.Object")) {
        return false;
      }
      cls = cls.getSuperclass();
    } while (true);
  }

  /**
   * Determines if the class or interface represented by this {@code Class}
   * object is either the same as, or is a superclass or superinterface of, the
   * class or interface represented by the specified {@code Class} parameter. It
   * returns {@code true} if so; otherwise it returns {@code false}. If this
   * {@code Class} object represents a primitive type, this method returns
   * {@code true} if the specified {@code Class} parameter is exactly this
   * {@code Class} object; otherwise it returns {@code false}.
   * <p>
   * Specifically, this method tests whether the type represented by the
   * specified {@code Class} parameter can be converted to the type represented
   * by this {@code Class} object via an identity conversion or via a widening
   * reference conversion. See <em>The Java Language Specification</em>,
   * sections 5.1.1 and 5.1.4 , for details.
   *
   * @param c
   *          the {@code Class} object to be checked
   * @return the {@code boolean} value indicating whether objects of the type
   *         {@code cls} can be assigned to objects of this class
   * @exception NullPointerException
   *              if the specified Class parameter is null.
   * @since JDK1.1
   */
  public boolean isAssignableFrom(Class<?> otherClass) { // XXX check specs
    if (otherClass == null)
      throw new NullPointerException();
    ScriptHelper.put("otherClass", otherClass);
    return ScriptHelper.evalBoolean("this.nativeClass.isAssignable(otherClass.nativeClass)");
  }

  /**
   * Determines if the specified {@code Class} object represents an interface
   * type.
   *
   * @return {@code true} if this object represents an interface; {@code false}
   *         otherwise.
   */
  public boolean isInterface() {
    throw new UnsupportedOperationException();
  }

  /**
   * Determines if this {@code Class} object represents an array class.
   *
   * @return {@code true} if this object represents an array class;
   *         {@code false} otherwise.
   * @since JDK1.1
   */
  public boolean isArray() {
    return getComponentType() != null;
  }

  /**
   * Determines if the specified {@code Class} object represents a primitive
   * type.
   *
   * <p>
   * There are nine predefined {@code Class} objects to represent the eight
   * primitive types and void. These are created by the Java Virtual Machine,
   * and have the same names as the primitive types that they represent, namely
   * {@code boolean}, {@code byte}, {@code char}, {@code short}, {@code int},
   * {@code long}, {@code float}, and {@code double}.
   *
   * <p>
   * These objects may only be accessed via the following public static final
   * variables, and are the only {@code Class} objects for which this method
   * returns {@code true}.
   *
   * @return true if and only if this class represents a primitive type
   *
   * @see java.lang.Boolean#TYPE
   * @see java.lang.Character#TYPE
   * @see java.lang.Byte#TYPE
   * @see java.lang.Short#TYPE
   * @see java.lang.Integer#TYPE
   * @see java.lang.Long#TYPE
   * @see java.lang.Float#TYPE
   * @see java.lang.Double#TYPE
   * @see java.lang.Void#TYPE
   * @since JDK1.1
   */
  public boolean isPrimitive() {
    throw new UnsupportedOperationException();
  }

  /**
   * Indicates whether this {@code Class} or its parents override finalize.
   *
   * @return {@code true} if and if this class or its parents override finalize;
   *
   * @hide
   */
  public boolean isFinalizable() {
    throw new UnsupportedOperationException();
  }

  /**
   * Returns true if this {@code Class} object represents an annotation type.
   * Note that if this method returns true, {@link #isInterface()} would also
   * return true, as all annotation types are also interfaces.
   *
   * @return {@code true} if this class object represents an annotation type;
   *         {@code false} otherwise
   * @since 1.5
   */
  public boolean isAnnotation() {
    throw new UnsupportedOperationException();
  }

  /**
   * Returns {@code true} if this class is a synthetic class; returns
   * {@code false} otherwise.
   * 
   * @return {@code true} if and only if this class is a synthetic class as
   *         defined by the Java Language Specification.
   * @since 1.5
   */
  public boolean isSynthetic() {
    throw new UnsupportedOperationException();
  }

  /**
   * Returns the name of the entity (class, interface, array class, primitive
   * type, or void) represented by this {@code Class} object, as a
   * {@code String}.
   *
   * @return the name of the class or interface represented by this object.
   */
  public String getName() {
    return (String) ScriptHelper.eval("this.nativeClass.name");
  }

  /**
   * Returns the class loader for the class. This implementation returns always
   * {@code null} indicated that thes class was loaded by the bootstrap class
   * loader.
   * 
   * @return always {@code null}.
   */
  public Object getClassLoader() {
    return null;
  }

  public String getSimpleName() {
    if (isArray()) {
      return getComponentType().getSimpleName() + "[]";
    }

  // @formatter:off
	/*
	 * if (isAnonymousClass()) { return ""; }
	 * 
	 * if (isMemberClass() || isLocalClass()) { return getInnerClassName();
	 * }
	 */
	// @formatter:on

    String name = getName();

    int dot = name.lastIndexOf('.');
    if (dot != -1) {
      return name.substring(dot + 1);
    }

    return name;
  }

  /**
   * Returns the {@code Class} representing the superclass of the entity (class,
   * interface, primitive type or void) represented by this {@code Class}. If
   * this {@code Class} represents either the {@code Object} class, an
   * interface, a primitive type, or void, then null is returned. If this object
   * represents an array class then the {@code Class} object representing the
   * {@code Object} class is returned.
   *
   * @return the superclass of the class represented by this object.
   */
  @SuppressWarnings("unchecked")
  public Class<? super T> getSuperclass() {
    if (ScriptHelper.evalBoolean("this.nativeClass.superClass == null"))
      return null;
    String superClassName = (String) ScriptHelper.eval("this.nativeClass.superClass.name");
    try {
      return (Class<? super T>) Class.forName(superClassName);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Returns an array of Method objects reflecting all the methods declared by
   * the class or interface represented by this Class object.
   */
  public Method[] getDeclaredMethods() {
    Method[] methods = new Method[0];
    String[] signatures = new String[0];
    ScriptHelper.put("signatures", signatures);
    ScriptHelper.eval("for (var e in this.nativeClass.constr.prototype) { signatures.push(e); }");
    for (int i = 0, j = 0; i < signatures.length; i++) {
      String signature = signatures[i];
      System.out.println(">>>>" + signature);
      // Do not include constructors, class initializers or member
      // variables.
      if (signature.startsWith("<init>") 
          || signature.startsWith("<clinit>") 
          || signature.indexOf("(") == -1) {
        continue;
      }
      methods[j] = new Method(this, signatures[i]);
      j++;
    }
    return methods;
  }

  /**
   * Returns all annotations that are directly present on this element.
   */
  public Annotation[] getDeclaredAnnotations() {
    Annotation[] annotations = new Annotation[0];
    Object[] maps = (Object[]) ScriptHelper.eval("this.nativeClass.annotations");
    if (maps == null)
      return annotations;

    int i = 0;
    for (Object map : maps) {
      InvocationHandler handler = new AnnotationInvocationHandler(new JSObject(map));
      Annotation annotation = (Annotation) Proxy.newProxyInstance(null, new Class[] { null }, handler);
      annotations[i++] = annotation;
    }
    return annotations;
  }

  /**
   * Returns the assertion status that would be assigned to this class if it
   * were to be initialized at the time this method is invoked.
   * 
   * @return the desired assertion status of the class.
   */
  public boolean desiredAssertionStatus() {
    return false;
  }

}
