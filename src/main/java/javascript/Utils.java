package javascript;

public class Utils {

  /**
   * This method unifies the instructions lcmp, fcmp<op> and dcmp<op> with op =
   * (g|l). It compares two numerical values. The value gORl must be 1 for (g)
   * or -1 for (l), otherwise 0. If either value1 or value2 is NaN, then lg is
   * returned.
   */
  public static int cmp(double value1, double value2, int gORl) {
    if (gORl != 0 && (Double.isNaN(value1) || Double.isNaN(value2)))
      return gORl;
    ScriptHelper.put("value1", value1);
    ScriptHelper.put("value2", value2);
    return ScriptHelper.evalInt("j2js.cmp(value1, value2)");
  }

  public static String getMethodSignature(String name,
      Class<?> returnType, Class<?>... parameterTypes) {
    StringBuilder sb = new StringBuilder(name);

    sb.append("(");
    if (parameterTypes != null) {
      boolean firstIteration = true;
      for (Class<?> type : parameterTypes) {
        if (firstIteration) {
          firstIteration = false;
        } else {
          sb.append(',');
        }
        getSignature(sb, type);
      }
    }
    sb.append(")");

    if (returnType != null) {
      getSignature(sb, returnType);
    }

    return sb.toString();
  }

  private static void getSignature(StringBuilder sb, Class<?> type) {
    if (type.isPrimitive()) {
      sb.append(type.getName());
    } else if (type.isArray()) {
      System.out.println("-------------------");
      String className = type.getName();
      className = className
          .substring(0, className.length() - 1)
          .substring(2);
      sb.append(className)
          .append("[]");
    } else {
      sb.append(type.getName());
    }
  }
}
