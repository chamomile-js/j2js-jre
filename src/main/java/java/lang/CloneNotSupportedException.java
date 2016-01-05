package java.lang;

/**
 * Thrown when a program attempts to clone an object which does not support the
 * {@link Cloneable} interface.
 *
 * @see Cloneable
 *      
 * @since chamomile
 */
public class CloneNotSupportedException extends Exception {
   
   /**
    * Constructs a new {@code CloneNotSupportedException} that includes the
    * current stack trace.
    */
   public CloneNotSupportedException() {}
   
   /**
    * Constructs a new {@code CloneNotSupportedException} with the current stack
    * trace and the specified detail message.
    *
    * @param detailMessage
    *           the detail message for this exception.
    */
   public CloneNotSupportedException(String detailMessage) {
      super(detailMessage);
   }
}
