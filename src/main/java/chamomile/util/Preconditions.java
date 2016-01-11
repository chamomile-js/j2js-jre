package chamomile.util;

/**
 * A utility class that provides utility functions to do precondition checks.
 */
public class InternalPreconditions {
   // Some parts adapted from Guava
   
   /**
    * Ensures that an object reference passed as a parameter to the calling
    * method is not {@code null}.
    * 
    * @param reference
    * @return
    */
   public static <T> T checkNotNull(T reference) {
      if (reference == null) {
         throw new NullPointerException();
      }
      return reference;
   }
   
   /**
    * Ensures that an object reference passed as a parameter to the calling
    * method is not {@code null}.
    * 
    * @param reference
    * @return
    */
   public static <T> T checkNotNull(T reference, Object errorMessage) {
      if (reference == null) {
         throw new NullPointerException(String.valueOf(errorMessage));
      }
      return reference;
   }
   
}
