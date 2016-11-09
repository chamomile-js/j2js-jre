package java.lang;

/**
 * The {@code Short} class wraps a value of primitive type {@code
 * short} in an object.
 */
public final class Short extends Number implements Comparable<Short> {
    /** use serialVersionUID from JDK 1.1. for interoperability */
    private static final long serialVersionUID = 7515723908773894738L;

    public static final short MAX_VALUE = (short) 0x7fff;
    public static final short MIN_VALUE = (short) 0x8000;
    public static final int SIZE = 16;

    /**
     * The number of bytes used to represent a {@code short} value in two's
     * complement binary form.
     *
     * @since 1.8
     */
    public static final int BYTES = SIZE / Byte.SIZE;

    /**
     * The {@code Class} instance representing the primitive type {@code short}.
     * <p>
     * https://android.googlesource.com/platform/libcore/+/android-7.1.0_r4/ojluni/src/main/java/java/lang/Short.java
     *
     * @since JDK1.1
     */
    @SuppressWarnings("unchecked")
    public static final Class<Short> TYPE = (Class<Short>) short[].class.getComponentType();

    public static int compare(short x, short y) {
	return x - y;
    }

    public static Short decode(String s) throws NumberFormatException {
	return Short.valueOf((short) Number.__decodeAndValidateInt(s, MIN_VALUE, MAX_VALUE));
    }

    public static int hashCode(short value) {
	return value;
    }

    public static short parseShort(String s) throws NumberFormatException {
	return parseShort(s, 10);
    }

    public static short parseShort(String s, int radix) throws NumberFormatException {
	return (short) Number.__parseAndValidateInt(s, radix, MIN_VALUE, MAX_VALUE);
    }

    public static short reverseBytes(short s) {
	return (short) (((s & 0xff) << 8) | ((s & 0xff00) >> 8));
    }

    public static String toString(short b) {
	return String.valueOf(b);
    }

    public static int toUnsignedInt(short x) {
	return ((int) x) & 0xffff;
    }

    public static long toUnsignedLong(short x) {
	return ((long) x) & 0xffffL;
    }

    public static Short valueOf(short s) {
	return new Short(s);
    }

    public static Short valueOf(String s) throws NumberFormatException {
	return valueOf(s, 10);
    }

    public static Short valueOf(String s, int radix) throws NumberFormatException {
	return new Short(parseShort(s, radix));
    }

    // ---

    private final transient short value;

    public Short(short value) {
	this.value = value;
    }

    public Short(String s) {
	value = parseShort(s);
    }

    @Override
    public byte byteValue() {
	return (byte) value;
    }

    @Override
    public int compareTo(Short b) {
	return compare(value, b.value);
    }

    @Override
    public double doubleValue() {
	return value;
    }

    @Override
    public boolean equals(Object o) {
	return (o instanceof Short) && (((Short) o).value == value);
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
	return value;
    }

    @Override
    public String toString() {
	return toString(value);
    }
}
