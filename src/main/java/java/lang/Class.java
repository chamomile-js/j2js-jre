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
import java.util.HashMap;

import javascript.JSObject;
import javascript.ScriptHelper;

/**
 * The in-memory representation of a Java class. This representation serves as
 * the starting point for querying class-related information, a process usually
 * called "reflection". There are basically three types of {@code Class}
 * instances: those representing real classes and interfaces, those representing
 * primitive types, and those representing array classes.
 */
public final class Class<T> {
   
   private class AnnotationInvocationHandler implements InvocationHandler {
      
      private JSObject object;
      
      AnnotationInvocationHandler(JSObject theObject) {
         object = theObject;
      }
      
      public Object invoke(Object proxy, Method method, Object[] args) {
         // TODO: Annotation
         String name = method.getName();
         if (name.equals("toString")) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("@" + object.get("$signature"));
            return buffer.toString();
         }
         return object.get(method.getName());
         //@formatter:off
         // return "@" + object.get("signature") + "(" + method.getName() + "=13)";
         //@formatter:on
      }
   }
   
   private static HashMap<String, Class<?>> classesByName = new HashMap<String, Class<?>>();
   private Object nativeClass;
   
   private Class(Object theNativeClass) {
      nativeClass = theNativeClass;
   }
   
   public String toString() {
      return "class " + getName();
   }
   
   public static Class<?> forName(String className) throws ClassNotFoundException {
      Class<?> clazz = classesByName.get(className);
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
   
   /**
    * Returns the Class representing the component type of an array. If this
    * class does not represent an array class this method returns null.
    */
   public Class<?> getComponentType() {
      throw new UnsupportedOperationException();
   }
   
   public T newInstance() throws InstantiationException, IllegalAccessException {
      // Create a new instance.
      ScriptHelper.eval("var o = this.nativeClass.newInstance()");
      // Initialize it.
      ScriptHelper.eval("j2js.invoke(o, '<init>()void', [])");
      return (T) ScriptHelper.eval("o");
   }
   
   /**
    * Determines if the specified Object is assignment-compatible with the
    * object represented by this Class.
    */
   public boolean isInstance(Object obj) {
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
    * Determines if the class or interface represented by this Class object is
    * either the same as, or is a superclass or superinterface of, the class or
    * interface represented by the specified Class parameter.
    */
   public boolean isAssignableFrom(Class<?> otherClass) {
      if (otherClass == null)
         throw new NullPointerException();
      ScriptHelper.put("otherClass", otherClass);
      return ScriptHelper.evalBoolean("this.nativeClass.isAssignable(otherClass.nativeClass)");
   }
   
   public boolean isInterface() {
      throw new UnsupportedOperationException();
   }
   
   public boolean isArray() {
      // throw new UnsupportedOperationException();
      // return getComponentType() != null;
      return false;
   }
   
   /**
    * Returns the class loader for the class.
    */
   public Object getClassLoader() {
      return null;
   }
   
   public String getName() {
      return (String) ScriptHelper.eval("this.nativeClass.name");
   }
   
   public String getSimpleName() {
      //@formatter:off
      /*
      if (isArray()) {
         return getComponentType().getSimpleName() + "[]";
      }
      
      if (isAnonymousClass()) {
         return "";
      }
      
      if (isMemberClass() || isLocalClass()) {
         return getInnerClassName();
      }
      */
      //@formatter:on
      
      String name = getName();
      
      int dot = name.lastIndexOf('.');
      if (dot != -1) {
         return name.substring(dot + 1);
      }
      
      return name;
   }
   
   /**
    * Returns the Class representing the superclass of the entity (class,
    * interface, primitive type or void) represented by this Class.
    */
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
         // Do not include constructors, class initializers or member variables.
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
