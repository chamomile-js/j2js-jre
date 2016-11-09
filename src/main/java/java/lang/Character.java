package java.lang;

import static org.chamomile.util.InternalPreconditions.checkCriticalArgument;

import javascript.ScriptHelper;

/**
 * The Character class wraps a value of the primitive type char in an object.
 */
public final class Character {
    /**
     * Helper class to share code between implementations, by making a char
     * array look like a CharSequence.
     */
    static class CharSequenceAdapter implements CharSequence {
	private char[] charArray;
	private int start;
	private int end;

	public CharSequenceAdapter(char[] charArray) {
	    this(charArray, 0, charArray.length);
	}

	public CharSequenceAdapter(char[] charArray, int start, int end) {
	    this.charArray = charArray;
	    this.start = start;
	    this.end = end;
	}

	@Override
	public char charAt(int index) {
	    return charArray[index + start];
	}

	@Override
	public int length() {
	    return end - start;
	}

	@Override
	public java.lang.CharSequence subSequence(int start, int end) {
	    return new CharSequenceAdapter(charArray, this.start + start, this.start + end);
	}
    }

    /**
     * The {@code Class} instance representing the primitive type {@code char}.
     * <p>
     * https://android.googlesource.com/platform/libcore/+/android-7.1.0_r4/ojluni/src/main/java/java/lang/Character.java
     *
     * @since JDK1.1
     */
    @SuppressWarnings("unchecked")
    public static final Class<Character> TYPE = (Class<Character>) char[].class.getComponentType();
    public static final int MIN_RADIX = 2;

    public static final int MAX_RADIX = 36;
    public static final char MIN_VALUE = '\u0000';

    public static final char MAX_VALUE = '\uFFFF';
    public static final char MIN_SURROGATE = '\uD800';
    public static final char MAX_SURROGATE = '\uDFFF';
    public static final char MIN_LOW_SURROGATE = '\uDC00';
    public static final char MAX_LOW_SURROGATE = '\uDFFF';
    public static final char MIN_HIGH_SURROGATE = '\uD800';

    public static final char MAX_HIGH_SURROGATE = '\uDBFF';
    public static final int MIN_SUPPLEMENTARY_CODE_POINT = 0x10000;
    public static final int MIN_CODE_POINT = 0x0000;

    public static final int MAX_CODE_POINT = 0x10FFFF;

    public static final int SIZE = 16;

    public static int charCount(int codePoint) {
	return codePoint >= MIN_SUPPLEMENTARY_CODE_POINT ? 2 : 1;
    }

    public static int codePointAt(char[] a, int index) {
	return codePointAt(new CharSequenceAdapter(a), index, a.length);
    }

    public static int codePointAt(char[] a, int index, int limit) {
	return codePointAt(new CharSequenceAdapter(a), index, limit);
    }

    public static int codePointAt(CharSequence seq, int index) {
	return codePointAt(seq, index, seq.length());
    }

    public static int codePointBefore(char[] a, int index) {
	return codePointBefore(new CharSequenceAdapter(a), index, 0);
    }

    public static int codePointBefore(char[] a, int index, int start) {
	return codePointBefore(new CharSequenceAdapter(a), index, start);
    }

    public static int codePointBefore(CharSequence cs, int index) {
	return codePointBefore(cs, index, 0);
    }

    public static int codePointCount(char[] a, int offset, int count) {
	return codePointCount(new CharSequenceAdapter(a), offset, offset + count);
    }

    public static int codePointCount(CharSequence seq, int beginIndex, int endIndex) {
	int count = 0;
	for (int idx = beginIndex; idx < endIndex;) {
	    char ch = seq.charAt(idx++);
	    if (isHighSurrogate(ch) && idx < endIndex && (isLowSurrogate(seq.charAt(idx)))) {
		// skip the second char of surrogate pairs
		++idx;
	    }
	    ++count;
	}
	return count;
    }

    /*
     * TODO: correct Unicode handling.
     */
    public static int digit(char c, int radix) {
	if (radix < MIN_RADIX || radix > MAX_RADIX) {
	    return -1;
	}

	if (c >= '0' && c < '0' + Math.min(radix, 10)) {
	    return c - '0';
	}

	// The offset by 10 is to re-base the alpha values
	if (c >= 'a' && c < (radix + 'a' - 10)) {
	    return c - 'a' + 10;
	}

	if (c >= 'A' && c < (radix + 'A' - 10)) {
	    return c - 'A' + 10;
	}

	return -1;
    }

    public static char forDigit(int digit, int radix) {
	if (radix < MIN_RADIX || radix > MAX_RADIX) {
	    return 0;
	}

	if (digit < 0 || digit >= radix) {
	    return 0;
	}

	return forDigit(digit);
    }

    public static int compare(char x, char y) {
	// JLS specifies that the chars are promoted to int before subtraction.
	return x - y;
    }

    /*
     * TODO: correct Unicode handling.
     */
    public static boolean isDigit(char c) {
	ScriptHelper.put("c", c);
	return ScriptHelper.evalBoolean("(null != String.fromCharCode(c).match(/\\d/));");
    }

    public static boolean isHighSurrogate(char ch) {
	return ch >= MIN_HIGH_SURROGATE && ch <= MAX_HIGH_SURROGATE;
    }

    /*
     * TODO: correct Unicode handling.
     */
    public static boolean isLetter(char c) {
	ScriptHelper.put("c", c);
	return ScriptHelper.evalBoolean("(null != String.fromCharCode(c).match(/[A-Z]/i));");
    }

    /*
     * TODO: correct Unicode handling.
     */
    public static boolean isLetterOrDigit(char c) {
	ScriptHelper.put("c", c);
	return ScriptHelper.evalBoolean("(null != String.fromCharCode(c).match(/[A-Z\\d]/i));");
    }

    /*
     * TODO: correct Unicode handling.
     */
    public static boolean isLowerCase(char c) {
	return toLowerCase(c) == c && isLetter(c);
    }

    public static boolean isLowSurrogate(char ch) {
	return ch >= MIN_LOW_SURROGATE && ch <= MAX_LOW_SURROGATE;
    }

    /** @deprecated see isWhitespace(char) */
    public static boolean isSpace(char c) {
	switch (c) {
	case ' ':
	    return true;
	case '\n':
	    return true;
	case '\t':
	    return true;
	case '\f':
	    return true;
	case '\r':
	    return true;
	default:
	    return false;
	}
    }

    public static boolean isSupplementaryCodePoint(int codePoint) {
	return codePoint >= MIN_SUPPLEMENTARY_CODE_POINT && codePoint <= MAX_CODE_POINT;
    }

    public static boolean isSurrogatePair(char highSurrogate, char lowSurrogate) {
	return isHighSurrogate(highSurrogate) && isLowSurrogate(lowSurrogate);
    }

    /*
     * TODO: correct Unicode handling.
     */
    public static boolean isUpperCase(char c) {
	return toUpperCase(c) == c && isLetter(c);
    }

    public static boolean isValidCodePoint(int codePoint) {
	return codePoint >= MIN_CODE_POINT && codePoint <= MAX_CODE_POINT;
    }

    public static int offsetByCodePoints(char[] a, int start, int count, int index, int codePointOffset) {
	return offsetByCodePoints(new CharSequenceAdapter(a, start, count), index, codePointOffset);
    }

    public static int offsetByCodePoints(CharSequence seq, int index, int codePointOffset) {
	if (codePointOffset < 0) {
	    // move backwards
	    while (codePointOffset < 0) {
		--index;
		if (Character.isLowSurrogate(seq.charAt(index)) && Character.isHighSurrogate(seq.charAt(index - 1))) {
		    --index;
		}
		++codePointOffset;
	    }
	} else {
	    // move forwards
	    while (codePointOffset > 0) {
		if (Character.isHighSurrogate(seq.charAt(index)) && Character.isLowSurrogate(seq.charAt(index + 1))) {
		    ++index;
		}
		++index;
		--codePointOffset;
	    }
	}
	return index;
    }

    public static char[] toChars(int codePoint) {
	checkCriticalArgument(codePoint >= 0 && codePoint <= MAX_CODE_POINT);

	if (codePoint >= MIN_SUPPLEMENTARY_CODE_POINT) {
	    return new char[] { getHighSurrogate(codePoint), getLowSurrogate(codePoint), };
	} else {
	    return new char[] { (char) codePoint, };
	}
    }

    public static int toChars(int codePoint, char[] dst, int dstIndex) {
	checkCriticalArgument(codePoint >= 0 && codePoint <= MAX_CODE_POINT);

	if (codePoint >= MIN_SUPPLEMENTARY_CODE_POINT) {
	    dst[dstIndex++] = getHighSurrogate(codePoint);
	    dst[dstIndex] = getLowSurrogate(codePoint);
	    return 2;
	} else {
	    dst[dstIndex] = (char) codePoint;
	    return 1;
	}
    }

    public static int toCodePoint(char highSurrogate, char lowSurrogate) {
	/*
	 * High and low surrogate chars have the bottom 10 bits to store the
	 * value above MIN_SUPPLEMENTARY_CODE_POINT, so grab those bits and add
	 * the offset.
	 */
	return MIN_SUPPLEMENTARY_CODE_POINT + ((highSurrogate & 1023) << 10) + (lowSurrogate & 1023);
    }

    public static char toLowerCase(char c) {
	ScriptHelper.put("c", c);
	return ScriptHelper.evalChar("String.fromCharCode(c).toLowerCase().charCodeAt(0);");
    }

    public static String toString(char x) {
	return String.valueOf(x);
    }

    public static char toUpperCase(char c) {
	ScriptHelper.put("c", c);
	return ScriptHelper.evalChar("String.fromCharCode(c).toUpperCase().charCodeAt(0);");
    }

    public static Character valueOf(char c) {
	return new Character(c);
    }

    static int codePointAt(CharSequence cs, int index, int limit) {
	char hiSurrogate = cs.charAt(index++);
	char loSurrogate;
	if (Character.isHighSurrogate(hiSurrogate) && index < limit
		&& Character.isLowSurrogate(loSurrogate = cs.charAt(index))) {
	    return Character.toCodePoint(hiSurrogate, loSurrogate);
	}
	return hiSurrogate;
    }

    static int codePointBefore(CharSequence cs, int index, int start) {
	char loSurrogate = cs.charAt(--index);
	char highSurrogate;
	if (isLowSurrogate(loSurrogate) && index > start && isHighSurrogate(highSurrogate = cs.charAt(index - 1))) {
	    return toCodePoint(highSurrogate, loSurrogate);
	}
	return loSurrogate;
    }

    static char forDigit(int digit) {
	final int overBaseTen = digit - 10;
	return (char) (overBaseTen < 0 ? '0' + digit : 'a' + overBaseTen);
    }

    /**
     * Computes the high surrogate character of the UTF16 representation of a
     * non-BMP code point. See {@link getLowSurrogate}.
     * 
     * @param codePoint
     *            requested codePoint, required to be >=
     *            MIN_SUPPLEMENTARY_CODE_POINT
     * @return high surrogate character
     */
    static char getHighSurrogate(int codePoint) {
	return (char) (MIN_HIGH_SURROGATE + (((codePoint - MIN_SUPPLEMENTARY_CODE_POINT) >> 10) & 1023));
    }

    /**
     * Computes the low surrogate character of the UTF16 representation of a
     * non-BMP code point. See {@link getHighSurrogate}.
     * 
     * @param codePoint
     *            requested codePoint, required to be >=
     *            MIN_SUPPLEMENTARY_CODE_POINT
     * @return low surrogate character
     */
    static char getLowSurrogate(int codePoint) {
	return (char) (MIN_LOW_SURROGATE + ((codePoint - MIN_SUPPLEMENTARY_CODE_POINT) & 1023));
    }

    // ---

    private final transient char value;

    public Character(char value) {
	this.value = value;
    }

    public char charValue() {
	return value;
    }

    public int compareTo(Character c) {
	return compare(value, c.value);
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null || !(obj instanceof Character))
	    return false;
	return ((Character) obj).value == value;
    }
    
    @Override
    public int hashCode() {
      return value;
    }
    
    @Override
    public String toString() {
	// Duplicate code to String#valueOf(char)
	ScriptHelper.put("c", value);
	return (String) ScriptHelper.eval("String.fromCharCode(c)");
    }
    
    // --- TODO ---

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

}
