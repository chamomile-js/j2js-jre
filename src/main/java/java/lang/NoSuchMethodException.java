package java.lang;

/**
 * Thrown when the virtual machine notices that a program tries to reference, on
 * a class or object, a method that does not exist.
 */
public class NoSuchMethodException extends java.lang.Exception {
   
   /**
    * Constructs a new {@code NoSuchMethodException} that includes the current
    * stack trace.
    */
   public NoSuchMethodException() {
      super();
   }
   
   /**
    * Constructs a new {@code NoSuchMethodException} with the current stack
    * trace and the specified detail message.
    *
    * @param detailMessage
    *           the detail message for this exception.
    */
   public NoSuchMethodException(String detailMessage) {
      super(detailMessage);
   }
}
