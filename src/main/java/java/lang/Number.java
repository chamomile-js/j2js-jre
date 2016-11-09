/*
 * Copyright (c) 1994, 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package java.lang;

import javascript.ScriptHelper;

/**
 * The abstract class {@code Number} is the superclass of platform classes
 * representing numeric values that are convertible to the primitive types
 * {@code byte}, {@code double}, {@code float}, {@code
 * int}, {@code long}, and {@code short}.
 *
 * The specific semantics of the conversion from the numeric value of a
 * particular {@code Number} implementation to a given primitive type is defined
 * by the {@code Number} implementation in question.
 *
 * For platform classes, the conversion is often analogous to a narrowing
 * primitive conversion or a widening primitive conversion as defining in
 * <cite>The Java&trade; Language Specification</cite> for converting between
 * primitive types. Therefore, conversions may lose information about the
 * overall magnitude of a numeric value, may lose precision, and may even return
 * a result of a different sign than the input.
 *
 * See the documentation of a given {@code Number} implementation for conversion
 * details.
 *
 * @author Lee Boynton
 * @author Arthur van Hoff
 * @jls 5.1.2 Widening Primitive Conversions
 * @jls 5.1.3 Narrowing Primitive Conversions
 * @since JDK1.0
 */
public abstract class Number implements java.io.Serializable {
  /**
   * Returns the value of the specified number as an {@code int}, which may
   * involve rounding or truncation.
   *
   * @return the numeric value represented by this object after conversion to
   *         type {@code int}.
   */
  public abstract int intValue();

  /**
   * Returns the value of the specified number as a {@code long}, which may
   * involve rounding or truncation.
   *
   * @return the numeric value represented by this object after conversion to
   *         type {@code long}.
   */
  public abstract long longValue();

  /**
   * Returns the value of the specified number as a {@code float}, which may
   * involve rounding.
   *
   * @return the numeric value represented by this object after conversion to
   *         type {@code float}.
   */
  public abstract float floatValue();

  /**
   * Returns the value of the specified number as a {@code double}, which may
   * involve rounding.
   *
   * @return the numeric value represented by this object after conversion to
   *         type {@code double}.
   */
  public abstract double doubleValue();

  /**
   * Returns the value of the specified number as a {@code byte}, which may
   * involve rounding or truncation.
   *
   * <p>
   * This implementation returns the result of {@link #intValue} cast to a
   * {@code byte}.
   *
   * @return the numeric value represented by this object after conversion to
   *         type {@code byte}.
   * @since JDK1.1
   */
  public byte byteValue() {
    return (byte) intValue();
  }

  /**
   * Returns the value of the specified number as a {@code short}, which may
   * involve rounding or truncation.
   *
   * <p>
   * This implementation returns the result of {@link #intValue} cast to a
   * {@code short}.
   *
   * @return the numeric value represented by this object after conversion to
   *         type {@code short}.
   * @since JDK1.1
   */
  public short shortValue() {
    return (short) intValue();
  }

  /** use serialVersionUID from JDK 1.0.2 for interoperability */
  private static final long serialVersionUID = -8742448824652078965L;

  // ---

  static class __Decode {
    public final String payload;
    public final int radix;

    public __Decode(int radix, String payload) {
      this.radix = radix;
      this.payload = payload;
    }
  }

  /**
   * Use nested class to avoid clinit on outer.
   */
  static class __ParseLong {
    /**
     * The number of digits (excluding minus sign and leading zeros) to process
     * at a time. The largest value expressible in maxDigits digits as well as
     * the factor radix^maxDigits must be strictly less than 2^31.
     */
    private static final int[] maxDigitsForRadix = { -1, -1, // unused
        30, // base 2
        19, // base 3
        15, // base 4
        13, // base 5
        11, 11, // base 6-7
        10, // base 8
        9, 9, // base 9-10
        8, 8, 8, 8, // base 11-14
        7, 7, 7, 7, 7, 7, 7, // base 15-21
        6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, // base 22-35
        5 // base 36
    };

    /**
     * A table of values radix*maxDigitsForRadix[radix].
     */
    private static final int[] maxDigitsRadixPower = new int[37];

    /**
     * The largest number of digits (excluding minus sign and leading zeros)
     * that can fit into a long for a given radix between 2 and 36, inclusive.
     */
    private static final int[] maxLengthForRadix = { -1, -1, // unused
        63, // base 2
        40, // base 3
        32, // base 4
        28, // base 5
        25, // base 6
        23, // base 7
        21, // base 8
        20, // base 9
        19, // base 10
        19, // base 11
        18, // base 12
        18, // base 13
        17, // base 14
        17, // base 15
        16, // base 16
        16, // base 17
        16, // base 18
        15, // base 19
        15, // base 20
        15, // base 21
        15, // base 22
        14, // base 23
        14, // base 24
        14, // base 25
        14, // base 26
        14, // base 27
        14, // base 28
        13, // base 29
        13, // base 30
        13, // base 31
        13, // base 32
        13, // base 33
        13, // base 34
        13, // base 35
        13 // base 36
    };

    /**
     * A table of floor(MAX_VALUE / maxDigitsRadixPower).
     */
    private static final long[] maxValueForRadix = new long[37];

    static {
      for (int i = 2; i <= 36; i++) {
        maxDigitsRadixPower[i] = (int) Math.pow(i, maxDigitsForRadix[i]);
        maxValueForRadix[i] = Long.MAX_VALUE / maxDigitsRadixPower[i];
      }
    }
  }

  /**
   * 
   * This function will determine the radix that the string is expressed in
   * based on the parsing rules defined in the Javadocs for Integer.decode() and
   * invoke __parseAndValidateInt.
   * 
   * 
   * @skip
   */
  protected static int __decodeAndValidateInt(String s, int lowerBound, int upperBound) throws NumberFormatException {
    __Decode decode = __decodeNumberString(s);
    return __parseAndValidateInt(decode.payload, decode.radix, lowerBound, upperBound);
  }

  protected static __Decode __decodeNumberString(String s) {
    final boolean negative;
    if (s.startsWith("-")) {
      negative = true;
      s = s.substring(1);
    } else {
      negative = false;
      if (s.startsWith("+")) {
        s = s.substring(1);
      }
    }

    final int radix;
    if (s.startsWith("0x") || s.startsWith("0X")) {
      s = s.substring(2);
      radix = 16;
    } else if (s.startsWith("#")) {
      s = s.substring(1);
      radix = 16;
    } else if (s.startsWith("0")) {
      radix = 8;
    } else {
      radix = 10;
    }

    if (negative) {
      s = "-" + s;
    }
    return new __Decode(radix, s);
  }

  /**
   * This function contains commin logic for parsing a String in a given radix
   * and validating the result.
   * 
   * @skip
   */
  protected static int __parseAndValidateInt(String s, int radix, int lowerBound, int upperBound)
      throws NumberFormatException {
    if (s == null) {
      throw NumberFormatException.forNullInputString();
    }
    if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
      throw NumberFormatException.forRadix(radix);
    }

    int length = s.length();
    int startIndex = (length > 0) && (s.charAt(0) == '-' || s.charAt(0) == '+') ? 1 : 0;

    for (int i = startIndex; i < length; i++) {
      if (Character.digit(s.charAt(i), radix) == -1) {
        throw NumberFormatException.forInputString(s);
      }
    }

    int toReturn = __parseInt(s, radix);
    boolean isTooLow = toReturn < lowerBound;
    if (__isNaN(toReturn)) {
      throw NumberFormatException.forInputString(s);
    } else if (isTooLow || toReturn > upperBound) {
      throw NumberFormatException.forInputString(s);
    }

    return toReturn;
  }

  /**
   * This function contains common logic for parsing a String in a given radix
   * and validating the result.
   * 
   * @skip
   */
  public static long __parseAndValidateLong(java.lang.String s, int radix) {
    if (s == null) {
      throw NumberFormatException.forNullInputString();
    }
    if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
      throw NumberFormatException.forRadix(radix);
    }

    final String orig = s;

    int length = s.length();
    boolean negative = false;
    if (length > 0) {
      char c = s.charAt(0);
      if (c == '-' || c == '+') {
        s = s.substring(1);
        length--;
        negative = (c == '-');
      }
    }
    if (length == 0) {
      throw NumberFormatException.forInputString(orig);
    }

    // Strip leading zeros
    while (s.length() > 0 && s.charAt(0) == '0') {
      s = s.substring(1);
      length--;
    }

    // Immediately eject numbers that are too long -- this avoids more
    // complex overflow handling below
    if (length > __ParseLong.maxLengthForRadix[radix]) {
      throw NumberFormatException.forInputString(orig);
    }

    // Validate the digits
    for (int i = 0; i < length; i++) {
      if (Character.digit(s.charAt(i), radix) == -1) {
        throw NumberFormatException.forInputString(orig);
      }
    }

    long toReturn = 0;
    int maxDigits = __ParseLong.maxDigitsForRadix[radix];
    long radixPower = __ParseLong.maxDigitsRadixPower[radix];
    long minValue = -__ParseLong.maxValueForRadix[radix];

    boolean firstTime = true;
    int head = length % maxDigits;
    if (head > 0) {
      // accumulate negative numbers, as -Long.MAX_VALUE == Long.MIN_VALUE
      // + 1
      // (in other words, -Long.MIN_VALUE overflows, see issue 7308)
      toReturn = -__parseInt(s.substring(0, head), radix);
      s = s.substring(head);
      length -= head;
      firstTime = false;
    }

    while (length >= maxDigits) {
      head = __parseInt(s.substring(0, maxDigits), radix);
      s = s.substring(maxDigits);
      length -= maxDigits;
      if (!firstTime) {
        // Check whether multiplying by radixPower will overflow
        if (toReturn < minValue) {
          throw NumberFormatException.forInputString(orig);
        }
        toReturn *= radixPower;
      } else {
        firstTime = false;
      }
      toReturn -= head;
    }

    // A positive value means we overflowed Long.MIN_VALUE
    if (toReturn > 0) {
      throw NumberFormatException.forInputString(orig);
    }

    if (!negative) {
      toReturn = -toReturn;
      // A negative value means we overflowed Long.MAX_VALUE
      if (toReturn < 0) {
        throw NumberFormatException.forInputString(orig);
      }
    }
    return toReturn;
  }

  /**
   * @skip
   */
  protected static int __parseInt(String s, int radix) {
    if (s == null)
      throw new NullPointerException();
    s = s.trim();
    // if (!s.matches("-?(\\d[A-Z])+")) throw new
    // NumberFormatException("Invalid integer: " + s);
    ScriptHelper.put("s", s);
    ScriptHelper.put("radix", radix);
    int i = ScriptHelper.evalInt("parseInt(s, radix)");

    ScriptHelper.put("i", i);
    ScriptHelper.put("radix", radix);
    if (!s.equals(ScriptHelper.eval("new Number(i).toString(radix)")))
      throw new NumberFormatException("Invalid integer: " + s);

    // TODO check bounds

    return i;
  }

  /**
   * @skip
   */
  private static boolean __isNaN(double x) {
    ScriptHelper.put("value", x);
    return ScriptHelper.evalBoolean("isNaN(value)");
  }
}
