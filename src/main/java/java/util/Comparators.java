package java.util;

import static org.chamomile.util.InternalPreconditions.checkNotNull;

class Comparators {
   
   /*
    * This is a utility class that provides a default Comparator. This class
    * exists so Arrays and Collections can share the natural comparator without
    * having to know internals of each other.
    * 
    * This class is package protected since it is not in the JRE.
    */
   
   /**
    * Compares two Objects according to their <i>natural ordering</i>.
    * 
    * @see java.lang.Comparable
    */
   private static final Comparator<Object> NATURAL = new Comparator<Object>() {
      @Override
      public int compare(Object o1, Object o2) {
         checkNotNull(o1);
         checkNotNull(o2);
         return ((Comparable<Object>) o1).compareTo(o2);
      }
   };
   
   /**
    * Returns the natural {@link Comparator}.
    * 
    * @return the natural {@link Comparator}.
    */
   public static <T> Comparator<T> natural() {
      return (Comparator<T>) NATURAL;
   }
}
