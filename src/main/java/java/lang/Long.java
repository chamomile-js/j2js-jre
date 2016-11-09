package java.lang;

import javascript.ScriptHelper;

/**
 * The {@code Long} class wraps a value of the primitive type {@code
 * long} in an object.
 */
public final class Long extends Number implements Comparable<Long> {
  /** use serialVersionUID from JDK 1.0.2 for interoperability */
  private static final long serialVersionUID = 4290774380558885855L;

  public static final long MAX_VALUE = 0x7fffffffffffffffL;
  public static final long MIN_VALUE = 0x8000000000000000L;
  public static final int SIZE = 64;

  /**
   * The number of bytes used to represent a {@code long} value in two's
   * complement binary form.
   *
   * @since 1.8
   */
  public static final int BYTES = SIZE / Byte.SIZE;

  /**
   * The {@code Class} instance representing the primitive type {@code long}.
   * <p>
   * https://android.googlesource.com/platform/libcore/+/android-7.1.0_r4/ojluni/src/main/java/java/lang/Long.java
   *
   * @since JDK1.1
   */
  @SuppressWarnings("unchecked")
  public static final Class<Long> TYPE = (Class<Long>) long[].class.getComponentType();

  public static int bitCount(long i) {
    int high = (int) (i >> 32);
    int low = (int) i;
    return Integer.bitCount(high) + Integer.bitCount(low);
  }

  public static int compare(long x, long y) {
    if (x < y) {
      return -1;
    } else if (x > y) {
      return 1;
    } else {
      return 0;
    }
  }

  public static Long decode(String s) throws NumberFormatException {
    __Decode decode = __decodeNumberString(s);
    return valueOf(decode.payload, decode.radix);
  }

  public static int hashCode(long l) {
    return (int) l;
  }

  public static long highestOneBit(long i) {
    int high = (int) (i >> 32);
    if (high != 0) {
      return ((long) Integer.highestOneBit(high)) << 32;
    } else {
      return Integer.highestOneBit((int) i);
    }
  }

  public static long lowestOneBit(long i) {
    return i & -i;
  }

  public static int numberOfLeadingZeros(long i) {
    int high = (int) (i >> 32);
    if (high != 0) {
      return Integer.numberOfLeadingZeros(high);
    } else {
      return Integer.numberOfLeadingZeros((int) i) + 32;
    }
  }

  public static int numberOfTrailingZeros(long i) {
    int low = (int) i;
    if (low != 0) {
      return Integer.numberOfTrailingZeros(low);
    } else {
      return Integer.numberOfTrailingZeros((int) (i >> 32)) + 32;
    }
  }

  public static long parseLong(String s) throws NumberFormatException {
    return parseLong(s, 10);
  }

  public static long parseLong(String s, int radix) throws NumberFormatException {
    return Number.__parseAndValidateLong(s, radix);
  }

  public static long reverse(long i) {
    int high = (int) (i >>> 32);
    int low = (int) i;
    return ((long) Integer.reverse(low) << 32) | (Integer.reverse(high) & 0xffffffffL);
  }

  public static long reverseBytes(long i) {
    int high = (int) (i >>> 32);
    int low = (int) i;
    return ((long) Integer.reverseBytes(low) << 32) | (Integer.reverseBytes(high) & 0xffffffffL);
  }

  public static long rotateLeft(long i, int distance) {
    while (distance-- > 0) {
      i = i << 1 | ((i < 0) ? 1 : 0);
    }
    return i;
  }

  public static long rotateRight(long i, int distance) {
    long ui = i & MAX_VALUE; // avoid sign extension
    long carry = (i < 0) ? 0x4000000000000000L : 0; // MIN_VALUE
    // rightshifted 1
    while (distance-- > 0) {
      long nextcarry = ui & 1;
      ui = carry | (ui >> 1);
      carry = (nextcarry == 0) ? 0 : 0x4000000000000000L;
    }
    if (carry != 0) {
      ui = ui | MIN_VALUE;
    }
    return ui;
  }

  public static int signum(long i) {
    if (i == 0) {
      return 0;
    } else if (i < 0) {
      return -1;
    } else {
      return 1;
    }
  }

  public static String toBinaryString(long value) {
    return toPowerOfTwoUnsignedString(value, 1);
  }

  public static String toHexString(long value) {
    return toPowerOfTwoUnsignedString(value, 4);
  }

  public static String toOctalString(long value) {
    return toPowerOfTwoUnsignedString(value, 3);
  }

  public static String toString(long value) {
    return String.valueOf(value);
  }

  public static String toString(long value, int intRadix) {
    throw new UnsupportedOperationException();
  }

  static class BoxedValues {
    // Box values according to JLS - between -128 and 127
    static Long[] boxedValues = new Long[256];
  }

  public static Long valueOf(long i) {
    if (i > -129 && i < 128) {
      int rebase = (int) i + 128;
      Long result = BoxedValues.boxedValues[rebase];
      if (result == null) {
        result = BoxedValues.boxedValues[rebase] = new Long(i);
      }
      return result;
    }
    return new Long(i);
  }

  public static Long valueOf(String s) throws NumberFormatException {
    return valueOf(s, 10);
  }

  public static Long valueOf(String s, int radix) throws NumberFormatException {
    return valueOf(parseLong(s, radix));
  }

  private static String toPowerOfTwoUnsignedString(long value, int shift) {
    throw new UnsupportedOperationException();
  }

  // ---

  private final long value;

  public Long(long value) {
    this.value = value;
  }

  public Long(String s) {
    value = parseLong(s);
  }

  @Override
  public byte byteValue() {
    return (byte) value;
  }

  @Override
  public int compareTo(Long b) {
    return compare(value, b.value);
  }

  @Override
  public double doubleValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    return (o instanceof Long) && (((Long) o).value == value);
  }

  @Override
  public float floatValue() {
    return value;
  }

  @Override
  public int hashCode() {
    return (int) value;
  }

  @Override
  public int intValue() {
    return (int) value;
  }

  @Override
  public long longValue() {
    return value;
  }

  @Override
  public short shortValue() {
    return (short) value;
  }

  @Override
  public String toString() {
    ScriptHelper.put("value", value);
    return (String) ScriptHelper.eval("String(value)");
  }
}
