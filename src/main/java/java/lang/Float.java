package java.lang;

public final class Float extends Number {

    @SuppressWarnings("unchecked")
	public static final Class<Float> TYPE = (Class<Float>) float[].class.getComponentType();
    
    private float value;

    /**
     * Allocates a Float object representing the value argument.
     */
    public Float(float theValue) {
        value = theValue;
    }
    
    public double doubleValue() {
        return value;
    }

    public float floatValue() {
        return value;
    }

    public int intValue() {
        return (int) value;
    }

    public long longValue() {
        return (long) value;
    }
    
}
