package java.util;


public abstract class AbstractCollection<E> implements Collection<E>, Iterable<E> {

    public abstract Iterator<E> iterator();
    
    /**
     * Returns the number of elements in this collection.
     */
    public abstract int size();
    
    
    /**
     * Returns an array containing all of the elements in this list in the correct order.
     */
    public Object[] toArray() {
        return toArray(new Object[size()]);
    }

    /**
     * Returns an array containing all of the elements in this list in the correct order.
     */
    public <T> T[] toArray(T[] target) {
        int size = size();
        if (target.length < size) target = (T[]) new Object[size];
        int i = 0;
        for (E element : this) {
            target[i++] = (T) element;
        }
        if (target.length > size) target[size] = null;
        return target;
    }
    
    /**
     * Returns a string representation of this collection.
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append('[');
        int i = 0;
        for (E element : this) {
            if (i++>0) buffer.append(", ");
            buffer.append(element);
        }
        buffer.append(']');
        return buffer.toString();
    }
}
