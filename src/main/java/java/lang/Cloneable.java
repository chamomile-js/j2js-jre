package java.lang;

/**
 * This (empty) interface must be implemented by all classes that wish to
 * support cloning. The implementation of {@code clone()} in {@code Object}
 * checks if the object being cloned implements this interface and throws
 * {@code CloneNotSupportedException} if it does not.
 *
 * @see Object#clone
 * @see CloneNotSupportedException
 *      
 * @since chamomile
 */
public interface Cloneable {
   // Marker interface
}
