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

import javascript.ScriptHelper;

/**
 * The Integer class wraps a value of the primitive type int in an object. An
 * object of type Integer contains a single field whose type is int.
 * 
 * @author j2js.com
 */
public class Integer extends Number implements Comparable<Integer> {
    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = 1360826667806852920L;

    public static final int MAX_VALUE = 0x7fffffff;
    public static final int MIN_VALUE = 0x80000000;
    public static final int SIZE = 32;

    /**
     * The {@code Class} instance representing the primitive type {@code int}.
     * <p>
     * https://android.googlesource.com/platform/libcore/+/android-7.1.0_r4/ojluni/src/main/java/java/lang/Integer.java
     *
     * @since JDK1.1
     */
    @SuppressWarnings("unchecked")
    public static final Class<Integer> TYPE = (java.lang.Class<Integer>) int[].class.getComponentType();

    public static int bitCount(int x) {
	// Courtesy the University of Kentucky
	// http://aggregate.org/MAGIC/#Population%20Count%20(Ones%20Count)
	x -= ((x >> 1) & 0x55555555);
	x = (((x >> 2) & 0x33333333) + (x & 0x33333333));
	x = (((x >> 4) + x) & 0x0f0f0f0f);
	x += (x >> 8);
	x += (x >> 16);
	return x & 0x0000003f;
    }

    public static int compare(int x, int y) {
	if (x < y) {
	    return -1;
	} else if (x > y) {
	    return 1;
	} else {
	    return 0;
	}
    }

    public static Integer decode(String s) throws NumberFormatException {
	// return Integer.valueOf(__decodeAndValidateInt(s, MIN_VALUE,
	// MAX_VALUE));
	throw new UnsupportedOperationException(); // TODO
    }

    public static Integer getInteger(String nm) {
	throw new UnsupportedOperationException(); // TODO
    }
    
    public static Integer getInteger(String nm, int val) {
	throw new UnsupportedOperationException(); // TODO
    }

    public static Integer getInteger(String nm, Integer val) {
	throw new UnsupportedOperationException(); // TODO
    }

    public static int highestOneBit(int i) {
	if (i < 0) {
	    return MIN_VALUE;
	} else if (i == 0) {
	    return 0;
	} else {
	    int rtn;
	    for (rtn = 0x40000000; (rtn & i) == 0; rtn >>= 1) {
		// loop down until matched
	    }
	    return rtn;
	}
    }

    public static int lowestOneBit(int i) {
	return i & -i;
    }

    public static int numberOfLeadingZeros(int i) {
	// Based on Henry S. Warren, Jr: "Hacker's Delight", p. 80.
	if (i < 0) {
	    return 0;
	} else if (i == 0) {
	    return SIZE;
	} else {
	    int y, m, n;

	    y = -(i >> 16);
	    m = (y >> 16) & 16;
	    n = 16 - m;
	    i = i >> m;

	    y = i - 0x100;
	    m = (y >> 16) & 8;
	    n += m;
	    i <<= m;

	    y = i - 0x1000;
	    m = (y >> 16) & 4;
	    n += m;
	    i <<= m;

	    y = i - 0x4000;
	    m = (y >> 16) & 2;
	    n += m;
	    i <<= m;

	    y = i >> 14;
	    m = y & ~(y >> 1);
	    return n + 2 - m;
	}
    }

    public static int numberOfTrailingZeros(int i) {
	if (i == 0) {
	    return SIZE;
	} else {
	    int rtn = 0;
	    for (int r = 1; (r & i) == 0; r <<= 1) {
		rtn++;
	    }
	    return rtn;
	}
    }

    public static int parseInt(String s) {
	return parseInt(s, 10);
    }

    public static int parseInt(String s, int radix) {
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

    public static int reverse(int i) {
	// HD, Figure 7-1 (OpenJDK).
	i = (i & 0x55555555) << 1 | (i >>> 1) & 0x55555555;
	i = (i & 0x33333333) << 2 | (i >>> 2) & 0x33333333;
	i = (i & 0x0f0f0f0f) << 4 | (i >>> 4) & 0x0f0f0f0f;
	i = (i << 24) | ((i & 0xff00) << 8) | ((i >>> 8) & 0xff00) | (i >>> 24);
	return i;
    }

    public static int reverseBytes(int i) {
	return ((i & 0xff) << 24) | ((i & 0xff00) << 8) | ((i & 0xff0000) >> 8) | ((i & 0xff000000) >>> 24);
    }

    public static int rotateLeft(int i, int distance) {
	while (distance-- > 0) {
	    i = i << 1 | ((i < 0) ? 1 : 0);
	}
	return i;
    }

    public static int rotateRight(int i, int distance) {
	int ui = i & MAX_VALUE; // avoid sign extension
	int carry = (i < 0) ? 0x40000000 : 0; // MIN_VALUE rightshifted 1
	while (distance-- > 0) {
	    int nextcarry = ui & 1;
	    ui = carry | (ui >> 1);
	    carry = (nextcarry == 0) ? 0 : 0x40000000;
	}
	if (carry != 0) {
	    ui = ui | MIN_VALUE;
	}
	return ui;
    }

    public static int signum(int i) {
	if (i == 0) {
	    return 0;
	} else if (i < 0) {
	    return -1;
	} else {
	    return 1;
	}
    }

    public static String toBinaryString(int i) {
	return toString(i, 2);
    }

    public static String toHexString(int i) {
	return toString(i, 16);
    }

    public static String toOctalString(int i) {
	return toString(i, 8);
    }

    public static String toString(int i) {
	return toString(i, 10);
    }

    public static String toString(int i, int radix) {
	ScriptHelper.put("i", i);
	ScriptHelper.put("radix", radix);
	return (String) ScriptHelper.eval("new Number(i).toString(radix)");
    }

    public static String toUnsignedString(int i, int radix) {
	throw new UnsupportedOperationException(); // TODO
    }

    public static Integer valueOf(int value) {
	return new Integer(value);
    }

    public static Integer valueOf(String s) {
	return new Integer(parseInt(s));
    }

    public static Integer valueOf(String s, int radix) {
	return new Integer(parseInt(s, radix));
    }

    // ---

    private final transient int value;

    public Integer(int value) {
	this.value = value;
    }

    public Integer(String s) {
	value = parseInt(s);
    }

    @Override
    public byte byteValue() {
	return (byte) value;
    }

    @Override
    public int compareTo(Integer b) {
	return compare(value, b.value);
    }

    @Override
    public double doubleValue() {
	return value;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null || !(obj instanceof Integer))
	    return false;
	return ((Integer) obj).value == value;
    }

    @Override
    public float floatValue() {
	return value;
    }

    @Override
    public int hashCode() {
	return value;
    }

    @Override
    public int intValue() {
	return value;
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
	return toString(value, 10);
    }

}
