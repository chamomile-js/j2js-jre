package java.lang;

public final class Short extends Number {

    @SuppressWarnings("unchecked")
	public static final Class<Short> TYPE = (Class<Short>) short[].class.getComponentType();
    
    private short value;
    
    /**
     * Constructs a newly allocated Byte object that represents the specified byte value.
     */
    public Short(short value) {
        this.value = value;
    }

    public double doubleValue() {
        return (double) value;
    }

    public float floatValue() {
        return (float) value;
    }

    public int intValue() {
        return (int) value;
    }

    public long longValue() {
        return (long) value;
    }
    
}
