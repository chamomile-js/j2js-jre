package java.lang;

import javascript.ScriptHelper;

/**
 * The Character class wraps a value of the primitive type char in an object.
 */
public final class Character {
   
   public static final Class<Character> TYPE = null;
   
   private char value;
   
   /**
    * Constructs a newly allocated Character object that represents the
    * specified char value.
    */
   public Character(char c) {
      value = c;
   }
   
   /**
    * Compares this object to the specified object.
    */
   public boolean equals(Object obj) {
      if (obj == null || !(obj instanceof Character))
         return false;
      return ((Character) obj).value == value;
   }
   
   /**
    * Determines if the specified character is a digit. <br/>
    * Warning: This method will only detect ISO-LATIN-1 digits ('0' through
    * '9').
    */
   public static boolean isDigit(char ch) {
      return String.valueOf(ch).matches("[0-9]");
   }
   
   public static boolean isJavaIdentifierPart(char c) {
      return isJavaIdentifierPart((int) c);
   }
   
   public static boolean isJavaIdentifierPart(int codePoint) {
      return "abcdefghklmnopqurstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_".indexOf(codePoint) != -1;
   }
   
   public static boolean isISOControl(char c) {
      return isISOControl((int) c);
   }
   
   public static boolean isISOControl(int c) {
      return (c >= 0 && c <= 0x1f) || (c >= 0x7f && c <= 0x9f);
   }
   
   /**
    * Determines if the specified character is a letter. <br/>
    * Warning: This method will only detect ISO-LATIN-1 letters ('a' through
    * 'Z').
    */
   public static boolean isLetter(char ch) {
      return String.valueOf(ch).matches("[a-zA-Z]");
   }
   
   /**
    * Returns an Long object holding the specified value. Calls to this method
    * may be generated by the autoboxing feature.
    */
   public static Character valueOf(char value) {
      return new Character(value);
   }
   
   /**
    * Returns the value of this Character object.
    */
   public char charValue() {
      return value;
   }
   
   /**
    * Returns a String object representing this Character's value.
    */
   public String toString() {
      // Duplicate code to String#valueOf(char)
      ScriptHelper.put("c", value);
      return (String) ScriptHelper.eval("String.fromCharCode(c)");
   }
   
}
