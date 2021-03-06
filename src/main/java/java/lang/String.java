/*
 * Copyright (c) 2005 j2js.com,
 *
 * All Rights Reserved. This work is distributed under the j2js Software License [1]
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.
 *
 * [1] http://www.j2js.com/license.txt
 */

package java.lang;

import java.util.Calendar;
import java.util.Date;

import javascript.ScriptHelper;

/**
 * The {@code String} class represents character strings.
 */
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence {
  /** use serialVersionUID from JDK 1.0.2 for interoperability */
  private static final long serialVersionUID = -6849794470754667710L;

  private static void consume(String me) {
    // Tagging method.
    // All String constructors are converted to methods returning the argument
    // of the last method call.
  }

  public String() {
    consume("");
  }

  public String(String value) {
    // Constructor needs to throw a NullPointerException if value is null.
    consume(value.toString());
  }

  public String(char[] data) {
    consume(String.valueOf(data));
  }

  public String(char[] data, int offset, int count) {
    consume(String.valueOf(data, offset, count));
  }

  private static String init(byte[] bytes, int offset, int count, String enc) {
    char[] chars = new char[count];
    for (int i = 0; i < count; i++) {
      chars[i] = (char) bytes[offset + i];
    }

    return String.valueOf(chars);
  }

  public String(byte[] bytes, int offset, int count, String enc) {
    consume(init(bytes, offset, count, enc));
  }

  public String(byte[] bytes, String enc) {
    consume(init(bytes, 0, bytes.length, enc));
  }

  public String(byte[] bytes, int offset, int count) {
    consume(init(bytes, offset, count, null));
  }

  public String(byte[] bytes) {
    consume(init(bytes, 0, bytes.length, null));
  }

  public String(StringBuffer buffer) {
    consume(buffer.toString());
  }

  /**
   * Returns the length of this string.
   */
  public int length() {
    return ScriptHelper.evalInt("this.length");
  }

  /**
   * Returns the character at the specified index.
   */
  public char charAt(int index) {
    if (index < 0 || index >= length()) {
      throw new IndexOutOfBoundsException();
    }
    ScriptHelper.put("index", index);
    return ScriptHelper.evalChar("this.charCodeAt(index)");
  }

  /**
   * Copies characters from this string into the destination character array.
   */
  public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
    for (int i = srcBegin; i < srcEnd; i++)
      dst[i - srcBegin + dstBegin] = charAt(i);
  }

  /**
   * Encodes this String into a sequence of bytes using the named charset,
   * storing the result into a new byte array.
   */
  public byte[] getBytes(String enc) {
    return getBytes();
  }

  /**
   * Encodes this String into a sequence of bytes using the platform's default
   * charset, storing the result into a new byte array.
   */
  public byte[] getBytes() {
    byte[] data = new byte[length()];
    for (int i = 0; i < data.length; i++) {
      data[i] = (byte) charAt(i);
    }
    return data;
  }

  /**
   * Compares this string to the specified object.
   */
  public boolean equals(Object obj) {
    ScriptHelper.put("obj", obj);
    // Note that method.apply(thisArg) will passed ToObject(thisArg) as the this
    // value.
    return ScriptHelper.evalBoolean("String(this) == String(obj)");
  }

  /**
   * Compares this String to another String, ignoring case considerations.
   */
  public boolean equalsIgnoreCase(String other) {
    if (other == null)
      return false;
    ScriptHelper.put("other", other);
    return ScriptHelper.evalBoolean("this.toLowerCase() == other.toLowerCase()");
  }

  /**
   * Compares two strings lexicographically.
   */
  public int compareTo(String other) {
    ScriptHelper.put("other", other);
    return ScriptHelper.evalInt("this<other?-1:(this>other?1:0)");
  }

  /**
   * Tests if two string regions are equal.
   */
  public boolean regionMatches(boolean ignoreCase, int toffset, String other, int ooffset, int len) {
    if (ignoreCase) {
      return this.substring(toffset).equalsIgnoreCase(other.substring(ooffset));
    } else {
      return this.substring(toffset).equals(other.substring(ooffset));
    }
  }

  /**
   * Tests if this string starts with the specified prefix beginning a specified
   * index.
   */
  public boolean startsWith(String prefix, int offset) {
    return substring(offset, prefix.length()).equals(prefix);
  }

  /**
   * Tests if this string starts with the specified prefix.
   */
  public boolean startsWith(String prefix) {
    return startsWith(prefix, 0);
  }

  /**
   * Tests if this string ends with the specified suffix.
   */
  public boolean endsWith(String suffix) {
    return substring(length() - suffix.length()).equals(suffix);
  }

  private static String leftPadding(int width, int number) {
    String sign = "";
    if (number < 0) {
      sign = "-";
      number = -number;
    }
    String s = String.valueOf(number);
    for (int i = 0; i < width - s.length(); i++)
      s = "0" + s;
    return sign + s;
  }

  /**
   * Returns a formatted string using the specified format string and arguments.
   * 
   * For example:
   * 
   * <pre>
   * String.format("%1$ta, %1$td %1$tb %1$tY %1$tT %1$tZ", new Date())
   * </pre>
   * 
   * may return
   * 
   * <pre>
   * Wed, 18 Apr 2007 20:08:05 CEST
   * </pre>
   * <p>
   * Currently supported are only the following 't' conversions:
   * <tt>a, d, b, Y, H, M, S, T, z</tt>
   * </p>
   */
  public static String format(String format, Object... args) {
    StringBuilder sb = new StringBuilder();
    String[] parts = format.split("%");

    sb.append(parts[0]);

    for (int i = 1; i < parts.length; i++) {
      String[] daysOfWeek = new String[] { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
      String[] months = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dez" };

      String part = parts[i];
      int j = part.indexOf("$");
      int argIndex = Integer.parseInt(part.substring(0, j));
      Object arg = args[argIndex - 1];
      char type = part.charAt(j + 1);
      char conv = part.charAt(j + 2);
      String chunk = null;
      if (type == 't') {
        Date d = (Date) arg;
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        if (conv == 'a') {
          // System.out.println(cal.get(Calendar.DAY_OF_WEEK));
          chunk = daysOfWeek[cal.get(Calendar.DAY_OF_WEEK) - 1];
        } else if (conv == 'd') {
          chunk = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        } else if (conv == 'b') {
          chunk = months[cal.get(Calendar.MONTH)];
        } else if (conv == 'Y') {
          chunk = leftPadding(4, cal.get(Calendar.YEAR));
        } else if (conv == 'H') {
          chunk = leftPadding(2, cal.get(Calendar.HOUR_OF_DAY));
        } else if (conv == 'M') {
          chunk = leftPadding(2, cal.get(Calendar.MINUTE));
        } else if (conv == 'S') {
          chunk = leftPadding(2, cal.get(Calendar.SECOND));
        } else if (conv == 'z') {
          chunk = leftPadding(4, cal.get(Calendar.ZONE_OFFSET));
        } else if (conv == 'T') {
          chunk = String.format("%1$tH:%1$tM:%1$tS", d);
        }
      }
      if (chunk == null)
        throw new RuntimeException("Illegal format at: " + part);
      sb.append(chunk);
      sb.append(part.substring(j + 3));
    }

    return sb.toString();
  }

  /**
   * Returns a hash code for this string.
   */
  public int hashCode() {
    int length = length();
    int code = 0;
    for (int i = 0; i < length; i++)
      code = code * 31 + charAt(i);
    return code;
  }

  /**
   * Returns the index within this string of the last occurrence of the
   * specified character.
   */
  public int lastIndexOf(int ch) {
    ScriptHelper.put("str", String.valueOf((char) ch));
    return ScriptHelper.evalInt("this.lastIndexOf(str)");
  }

  /**
   * Returns the index within this string of the last occurrence of the
   * specified character, searching backward starting at the specified index.
   */
  public int lastIndexOf(int ch, int position) {
    ScriptHelper.put("str", String.valueOf((char) ch));
    ScriptHelper.put("position", position);
    return ScriptHelper.evalInt("this.lastIndexOf(str, position)");
  }

  /**
   * Returns the index within this string of the first occurrence of the
   * specified substring.
   */
  public int indexOf(String str) {
    return indexOf(str, 0);
  }

  /**
   * Returns the index within this string of the first occurrence of the
   * specified substring, starting at the specified index.
   */
  public int indexOf(String str, int position) {
    ScriptHelper.put("str", str);
    ScriptHelper.put("position", position);
    return ScriptHelper.evalInt("this.indexOf(str, position)");
  }

  /**
   * Returns the index within this string of the first occurrence of the
   * specified character.
   */
  public int indexOf(int ch) {
    ScriptHelper.put("ch", ch);
    return ScriptHelper.evalInt("this.indexOf(String.fromCharCode(ch), 0)");
  }

  /**
   * Returns a new string that is a substring of this string.
   */
  public String substring(int beginIndex) {
    return substring(beginIndex, length());
  }

  /**
   * Returns a new string that is a substring of this string.
   */
  public String substring(int beginIndex, int endIndex) {
    ScriptHelper.put("beginIndex", beginIndex);
    ScriptHelper.put("endIndex", endIndex);
    return (String) ScriptHelper.eval("this.substring(beginIndex, endIndex)");
  }

  /**
   * Concatenates the specified string to the end of this string.
   */
  public String concat(String str) {
    ScriptHelper.put("str", str);
    return (String) ScriptHelper.eval("this + str");
  }

  /**
   * Returns a new string resulting from replacing all occurrences of oldChar in
   * this string with newChar.
   */
  public String replace(char oldChar, char newChar) {
    ScriptHelper.put("oldChar", String.valueOf(oldChar));
    ScriptHelper.put("newChar", String.valueOf(newChar));
    return (String) ScriptHelper.eval("this.replace(new RegExp(oldChar, 'g'), newChar)");
  }

  /**
   * Converts all of the characters in this String to lower case using the rules
   * of the default locale.
   */
  public String toLowerCase() {
    return (String) ScriptHelper.eval("this.toLowerCase()");
  }

  /**
   * Converts all of the characters in this String to upper case using the rules
   * of the default locale.
   */
  public String toUpperCase() {
    return (String) ScriptHelper.eval("this.toUpperCase()");
  }

  /**
   * Returns a copy of the string, with leading and trailing whitespace omitted.
   */
  public String trim() {
    return (String) ScriptHelper.eval("this.replace(/^\\s+/, '').replace(/\\s+$/, '')");
  }

  /**
   * This object (which is already a string!) is itself returned.
   */
  public String toString() {
    return this;
  }

  /**
   * Converts this string to a new character array.
   */
  public char[] toCharArray() {
    char[] data = new char[length()];
    for (int i = 0; i < data.length; i++) {
      data[i] = charAt(i);
    }
    return data;
  }

  /**
   * Returns the string representation of the Object argument.
   */
  public static String valueOf(Object obj) {
    if (obj == null)
      return "null";
    if (obj instanceof String)
      return (String) obj; // avoid string recursion
    return obj.toString();
  }

  /**
   * Returns the string representation of the char array argument.
   */
  public static String valueOf(char[] data) {
    return valueOf(data, 0, data.length);
  }

  /**
   * Returns the string representation of a specific subarray of the char array
   * argument.
   */
  public static String valueOf(char[] data, int offset, int count) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < count; i++) {
      sb.append(data[offset + i]);
    }
    return sb.toString();
  }

  /**
   * Returns the string representation of the boolean argument.
   */
  public static String valueOf(boolean b) {
    return Boolean.toString(b);
  }

  /**
   * Returns the string representation of the char argument.
   */
  public static String valueOf(char c) {
    ScriptHelper.put("c", c);
    return (String) ScriptHelper.eval("String.fromCharCode(c)");
  }

  /**
   * Returns the string representation of the int argument.
   */
  public static String valueOf(int i) {
    return Integer.toString(i);
  }

  /**
   * Returns the string representation of the long argument.
   */
  public static String valueOf(long l) {
    ScriptHelper.put("l", l);
    return (String) ScriptHelper.eval("String(l)");
  }

  /**
   * Returns the string representation of the float argument.
   */
  public static String valueOf(float f) {
    ScriptHelper.put("f", f);
    return (String) ScriptHelper.eval("String(f)");
  }

  /**
   * Returns the string representation of the double argument.
   */
  public static String valueOf(double d) {
    ScriptHelper.put("d", d);
    return (String) ScriptHelper.eval("String(d)");
  }

  /**
   * Splits this string around matches of the given regular expression.
   */
  public String[] split(String regex) {
    ScriptHelper.put("regex", regex);
    // String.prototype.split(separator, limit) accepts both a string and a
    // regular expression as separator.
    // However, the string is not converted to a regular expression, so we have
    // to do it.
    return (String[]) ScriptHelper.eval("this.split(new RegExp(regex))");
  }

  /**
   * Tells whether or not this string matches the given regular expression.
   */
  public boolean matches(String regex) {
    ScriptHelper.put("regex", "^" + regex + "$");
    return ScriptHelper.evalBoolean("this.match(new RegExp(regex))!=null");
  }

  private String replace(String regex, String flags, String replacement) {
    // \a -> a
    // \\ -> \
    // \" -> "
    // \n -> newline
    // \newline -> newline

    ScriptHelper.put("regex", regex);
    ScriptHelper.put("flags", flags);
    ScriptHelper.put("replacement", replacement);
    return (String) ScriptHelper.eval("this.replace(new RegExp(regex, flags), j2js.unquote(replacement) )");
  }

  /**
   * Replaces the first substring of this string that matches the given regular
   * expression with the given replacement.
   */
  public String replaceFirst(String regex, String replacement) {
    return replace(regex, "", replacement);
  }

  /**
   * Replaces each substring of this string that matches the given regular
   * expression with the given replacement.
   */
  public String replaceAll(String regex, String replacement) {
    return replace(regex, "g", replacement);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.CharSequence#subSequence(int, int)
   */
  public CharSequence subSequence(int start, int end) {
    return substring(start, end);
  }

}
