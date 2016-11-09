/*
 * Copyright (c) 2006 j2js.com,
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
 * The Double class wraps a value of the primitive type double in an object.
 * 
 * @author j2js.com
 */
public final class Double extends Number implements Comparable<Double> {
    public static final double MAX_VALUE = 1.7976931348623157e+308;
    public static final double MIN_VALUE = 4.9e-324;
    public static final double MIN_NORMAL = 2.2250738585072014e-308;
    public static final int MAX_EXPONENT = 1023;
                               // ==Math.getExponent(Double.MAX_VALUE);
    public static final int MIN_EXPONENT = -1022;
                               // ==Math.getExponent(Double.MIN_NORMAL);

    public static final double NaN = ScriptHelper.evalDouble("Number.NaN");
    // public static final double NaN = 0d / 0d;
    public static final double NEGATIVE_INFINITY = -1d / 0d;
    public static final double POSITIVE_INFINITY = 1d / 0d;
    public static final int SIZE = 64;
    
    @SuppressWarnings("unchecked")
    public static final Class<Double> TYPE = (Class<Double>) double[].class.getComponentType();

    // 2^512, 2^-512
    private static final double POWER_512 = 1.3407807929942597E154;
    private static final double POWER_MINUS_512 = 7.458340731200207E-155;
    // 2^256, 2^-256
    private static final double POWER_256 = 1.157920892373162E77;
    private static final double POWER_MINUS_256 = 8.636168555094445E-78;
    // 2^128, 2^-128
    private static final double POWER_128 = 3.4028236692093846E38;
    private static final double POWER_MINUS_128 = 2.9387358770557188E-39;
    // 2^64, 2^-64
    private static final double POWER_64 = 18446744073709551616.0;
    private static final double POWER_MINUS_64 = 5.421010862427522E-20;
    // 2^52, 2^-52
    private static final double POWER_52 = 4503599627370496.0;
    private static final double POWER_MINUS_52 = 2.220446049250313E-16;
    // 2^32, 2^-32
    private static final double POWER_32 = 4294967296.0;
    private static final double POWER_MINUS_32 = 2.3283064365386963E-10;
    // 2^31
    private static final double POWER_31 = 2147483648.0;
    // 2^20, 2^-20
    private static final double POWER_20 = 1048576.0;
    private static final double POWER_MINUS_20 = 9.5367431640625E-7;
    // 2^16, 2^-16
    private static final double POWER_16 = 65536.0;
    private static final double POWER_MINUS_16 = 0.0000152587890625;
    // 2^8, 2^-8
    private static final double POWER_8 = 256.0;
    private static final double POWER_MINUS_8 = 0.00390625;
    // 2^4, 2^-4
    private static final double POWER_4 = 16.0;
    private static final double POWER_MINUS_4 = 0.0625;
    // 2^2, 2^-2
    private static final double POWER_2 = 4.0;
    private static final double POWER_MINUS_2 = 0.25;
    // 2^1, 2^-1
    private static final double POWER_1 = 2.0;
    private static final double POWER_MINUS_1 = 0.5;
    // 2^-1022 (smallest double non-denorm)
    private static final double POWER_MINUS_1022 = 2.2250738585072014E-308;

    private static final double[] powers = {
      POWER_512, POWER_256, POWER_128, POWER_64, POWER_32, POWER_16, POWER_8,
      POWER_4, POWER_2, POWER_1
    };

    private static final double[] invPowers = {
      POWER_MINUS_512, POWER_MINUS_256, POWER_MINUS_128, POWER_MINUS_64,
      POWER_MINUS_32, POWER_MINUS_16, POWER_MINUS_8, POWER_MINUS_4, POWER_MINUS_2,
      POWER_MINUS_1
    };
    
    public static int compare(double x, double y) {
	if (x < y) {
	    return -1;
	}
	if (x > y) {
	    return 1;
	}
	if (x == y) {
	    return 0;
	}

	if (isNaN(x)) {
	    if (isNaN(y)) {
		return 0;
	    } else {
		return 1;
	    }
	} else {
	    return -1;
	}
    }
    
    public static long doubleToLongBits(double value) {
	if (isNaN(value)) {
	    return 0x7ff8000000000000L;
	}

	boolean negative = false;
	if (value == 0.0) {
	    if (1.0 / value == NEGATIVE_INFINITY) {
		return 0x8000000000000000L; // -0.0
	    } else {
		return 0x0L;
	    }
	}
	if (value < 0.0) {
	    negative = true;
	    value = -value;
	}
	if (isInfinite(value)) {
	    if (negative) {
		return 0xfff0000000000000L;
	    } else {
		return 0x7ff0000000000000L;
	    }
	}

	int exp = 0;

	// Scale d by powers of 2 into the range [1.0, 2.0)
	// If the exponent would go below -1023, scale into (0.0, 1.0) instead
	if (value < 1.0) {
	    int bit = 512;
	    for (int i = 0; i < 10; i++, bit >>= 1) {
		if (value < invPowers[i] && exp - bit >= -1023) {
		    value *= powers[i];
		    exp -= bit;
		}
	    }
	    // Force into [1.0, 2.0) range
	    if (value < 1.0 && exp - 1 >= -1023) {
		value *= 2.0;
		exp--;
	    }
	} else if (value >= 2.0) {
	    int bit = 512;
	    for (int i = 0; i < 10; i++, bit >>= 1) {
		if (value >= powers[i]) {
		    value *= invPowers[i];
		    exp += bit;
		}
	    }
	}

	if (exp > -1023) {
	    // Remove significand of non-denormalized mantissa
	    value -= 1.0;
	} else {
	    // Insert 0 bit as significand of denormalized mantissa
	    value *= 0.5;
	}

	// Extract high 20 bits of mantissa
	long ihi = (long) (value * POWER_20);

	// Extract low 32 bits of mantissa
	value -= ihi * POWER_MINUS_20;

	long ilo = (long) (value * POWER_52);

	// Exponent bits
	ihi |= (exp + 1023) << 20;

	// Sign bit
	if (negative) {
	    ihi |= 0x80000000L;
	}

	return (ihi << 32) | ilo;
    }

    /**
     * @skip Here for shared implementation with Arrays.hashCode
     */
    public static int hashCode(double d) {
      return (int) d;
    }
    
    public static boolean isInfinite(double x) {
	ScriptHelper.put("value", x);
	return ScriptHelper.evalBoolean("!isFinite(x) && !isNaN(x)");
    }

    /**
     * Returns true if the specified number is a Not-a-Number (NaN) value, false
     * otherwise.
     */
    public static boolean isNaN(double v) {
	ScriptHelper.put("value", v);
	return ScriptHelper.evalBoolean("isNaN(value)");
    }
    
    public static double longBitsToDouble(long bits) {
	long ihi = bits >> 32;
	long ilo = bits & 0xffffffffL;
	if (ihi < 0) {
	    ihi += 0x100000000L;
	}
	if (ilo < 0) {
	    ilo += 0x100000000L;
	}

	boolean negative = (ihi & 0x80000000) != 0;
	int exp = (int) ((ihi >> 20) & 0x7ff);
	ihi &= 0xfffff; // remove sign bit and exponent

	if (exp == 0x0) {
	    double d = (ihi * POWER_MINUS_20) + (ilo * POWER_MINUS_52);
	    d *= POWER_MINUS_1022;
	    return negative ? (d == 0.0 ? -0.0 : -d) : d;
	} else if (exp == 0x7ff) {
	    if (ihi == 0 && ilo == 0) {
		return negative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
	    } else {
		return Double.NaN;
	    }
	}

	// Normalize exponent
	exp -= 1023;

	double d = 1.0 + (ihi * POWER_MINUS_20) + (ilo * POWER_MINUS_52);
	if (exp > 0) {
	    int bit = 512;
	    for (int i = 0; i < 10; i++, bit >>= 1) {
		if (exp >= bit) {
		    d *= powers[i];
		    exp -= bit;
		}
	    }
	} else if (exp < 0) {
	    while (exp < 0) {
		int bit = 512;
		for (int i = 0; i < 10; i++, bit >>= 1) {
		    if (exp <= -bit) {
			d *= invPowers[i];
			exp += bit;
		    }
		}
	    }
	}
	return negative ? -d : d;
    }

//    public static double parseDouble(String s) throws NumberFormatException {
//	return __parseAndValidateDouble(s);
//    }

    /**
     * Returns a new double initialized to the value represented by the
     * specified String, as performed by the valueOf method of class Double.
     */
    public static double parseDouble(String s) throws NumberFormatException {
	if (s == null)
	    throw new NullPointerException();
	s = s.trim();
	if (s.matches("(\\+|\\-)?NaN"))
	    return NaN;

	if (!s.matches("(\\+|\\-)?\\d+(\\.\\d+)?"))
	    throw new NumberFormatException("Invalid double: " + s);

	ScriptHelper.put("s", s);
	double d = ScriptHelper.evalDouble("parseFloat(s)");
	if (isNaN(d)) {
	    throw new NumberFormatException("Not a parsable double: " + s);
	}
	return d;
    }

    public static String toString(double b) {
      return String.valueOf(b);
    }

    /**
     * Returns an Double object holding the specified value. Calls to this
     * method may be generated by the autoboxing feature.
     */
    public static Double valueOf(double value) {
        return new Double(value);
    }
    
    /**
     * Returns a Double object holding the double value represented by the argument string s.
     */
    public static Double valueOf(String s) throws NumberFormatException {
        return new Double(s);
    }
        
    private final transient double value;
    
    /**
     * Constructs a newly allocated Double object that represents the primitive double argument.
     */
    public Double(double d) {
        value = d;
    }

    /**
     * Constructs a newly allocated Double object that represents the floating-point value of type double represented by the string.    * @param s
     */
    public Double(String s) {
        value = parseDouble(s);
    }
    
    @Override
    public byte byteValue() {
      return (byte) value;
    }
    
    @Override
    public int compareTo(Double b) {
	return compare(this.value, b.value);
    }

    @Override
    public double doubleValue() {
      return value;
    }

    @Override
    public boolean equals(Object o) {
      return (o instanceof Double) && (((Double) o).value == value);
    }

    @Override
    public float floatValue() {
        return (float) value;
    }
    
    @Override
    public int hashCode() {
      return hashCode(value);
    }

    @Override
    public int intValue() {
      return (int) value;
    }

    public boolean isInfinite() {
      return isInfinite(value);
    }
    
    public boolean isNaN() {
        return isNaN(value);
    }

    @Override
    public long longValue() {
      return (long) value;
    }

    @Override
    public short shortValue() {
      return (short) value;
    }

    @Override
    public String toString() {
      return toString(value);
    }
}
