package java.lang;

/**
 * The Byte class wraps a value of primitive type {@code byte} in an object.
 */
public final class Byte extends Number implements Comparable<Byte> {
    /** use serialVersionUID from JDK 1.1. for interoperability */
    private static final long serialVersionUID = -7183698231559129828L;

    public static final byte MAX_VALUE = 127;
    public static final byte MIN_VALUE = -128;
    public static final int SIZE = 8;

    /**
     * The number of bytes used to represent a {@code byte} value in two's
     * complement binary form.
     *
     * @since 1.8
     */
    public static final int BYTES = SIZE / Byte.SIZE;

    /**
     * The {@code Class} instance representing the primitive type {@code byte}.
     * <p>
     * https://android.googlesource.com/platform/libcore/+/android-7.1.0_r4/ojluni/src/main/java/java/lang/Byte.java
     *
     * @since JDK1.1
     */
    @SuppressWarnings("unchecked")
    public static final Class<Byte> TYPE = (Class<Byte>) byte[].class.getComponentType();

    public static int compare(byte x, byte y) {
	return x - y;
    }

    public static Byte decode(String s) throws NumberFormatException {
	return Byte.valueOf((byte) __decodeAndValidateInt(s, MIN_VALUE, MAX_VALUE));
    }

    public static int hashCode(byte value) {
	return value;
    }

    public static byte parseByte(String s) throws NumberFormatException {
	return parseByte(s, 10);
    }

    public static byte parseByte(String s, int radix) throws NumberFormatException {
	return (byte) __parseAndValidateInt(s, radix, MIN_VALUE, MAX_VALUE);
    }

    public static String toString(byte b) {
	return Integer.toString(b, 10);
    }

    public static int toUnsignedInt(byte x) {
	return (x) & 0xff;
    }

    public static long toUnsignedLong(byte x) {
	return (x) & 0xffL;
    }

    private static class ByteCache {
	private ByteCache() {
	}

	static final Byte cache[] = new Byte[-(-128) + 127 + 1];

	static {
	    for (int i = 0; i < cache.length; i++)
		cache[i] = new Byte((byte) (i - 128));
	}
    }

    public static Byte valueOf(byte b) {
	final int offset = 128;
	return ByteCache.cache[b + offset];
    }

    public static Byte valueOf(String s) throws NumberFormatException {
	return valueOf(s, 10);
    }

    public static Byte valueOf(String s, int radix) throws NumberFormatException {
	return valueOf(parseByte(s, radix));
    }

    // ---

    private final byte value;

    public Byte(byte value) {
	this.value = value;
    }

    public Byte(String s) throws NumberFormatException {
	this.value = parseByte(s, 10);
    }

    @Override
    public byte byteValue() {
	return value;
    }

    @Override
    public int compareTo(Byte b) {
	return compare(value, b.value);
    }

    @Override
    public double doubleValue() {
	return value;
    }

    @Override
    public boolean equals(Object o) {
	return (o instanceof Byte) && (((Byte) o).value == value);
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
	return Integer.toString(value);
    }
}
