package java.lang;

import javascript.ScriptHelper;

/**
 * Math utility methods and constants.
 */
public final class Math {
  //@formatter:off
  // The following methods are not implemented because JS doesn't provide the
  // necessary pieces:
  //   public static double ulp (double x)
  //   public static float ulp (float x)
  //   public static int getExponent (double d)
  //   public static int getExponent (float f)
  //   public static double IEEEremainder(double f1, double f2)
  //   public static double nextAfter(double start, double direction)
  //   public static float nextAfter(float start, float direction)
  //   public static double nextUp(double start) {
  //     return nextAfter(start, 1.0d);
  //   }
  //   public static float nextUp(float start) {
  //     return nextAfter(start,1.0f);
  //   }
  //   public static int addExact(int x, int y)
  //   public static long addExact(long x, long y)
  //   public static int decrementExact(int a)
  //   public static long decrementExact(long a)
  //   public static int floorDiv(int x, int y)
  //@formatter:on

  public static final double E = ScriptHelper.evalDouble("Math.E");
  public static final double PI = ScriptHelper.evalDouble("Math.PI");

  private static final double PI_OVER_180 = PI / 180.0;
  private static final double PI_UNDER_180 = 180.0 / PI;

  public static double abs(double a) {
    ScriptHelper.put("x", a);
    return ScriptHelper.evalDouble("Math.abs(x)");
  }

  public static float abs(float a) {
    ScriptHelper.put("x", a);
    return ScriptHelper.evalFloat("Math.abs(x)");
  }

  public static int abs(int a) {
    ScriptHelper.put("x", a);
    return ScriptHelper.evalInt("Math.abs(x)");
  }

  public static long abs(long a) {
    ScriptHelper.put("x", a);
    return ScriptHelper.evalLong("Math.abs(x)");
  }

  public static double acos(double a) {
    ScriptHelper.put("x", a);
    return ScriptHelper.evalDouble("Math.acos(x)");
  }

  /* TODO
  @formatter:off
  public static int addExact(int x, int y) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  /* TODO
  @formatter:off
  public static long addExact(long x, long y) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  public static double asin(double a) {
    ScriptHelper.put("x", a);
    return ScriptHelper.evalDouble("Math.asin(x)");
  }

  public static double atan(double a) {
    ScriptHelper.put("x", a);
    return ScriptHelper.evalDouble("Math.atan(x)");
  }

  public static double atan2(double y, double x) {
    ScriptHelper.put("y", y);
    ScriptHelper.put("x", x);
    return ScriptHelper.evalDouble("Math.atan2(y, x)");
  }

  public static double cbrt(double a) {
    return Math.pow(a, 1.0 / 3.0);
  }

  public static double ceil(double a) {
    ScriptHelper.put("x", a);
    return ScriptHelper.evalDouble("Math.ceil(x)");
  }

  public static double copySign(double magnitude, double sign) {
    if (sign < 0) {
      return (magnitude < 0) ? magnitude : -magnitude;
    } else {
      return (magnitude > 0) ? magnitude : -magnitude;
    }
  }

  public static float copySign(float magnitude, float sign) {
    return (float) (copySign((double) magnitude, (double) sign));
  }

  public static double cos(double a) {
    ScriptHelper.put("x", a);
    return ScriptHelper.evalDouble("Math.cos(x)");
  }

  public static double cosh(double a) {
    ScriptHelper.put("x", a);
    return ScriptHelper.evalDouble("(Math.exp(x) + Math.exp(-x)) / 2.0");
  }

  public static int decrementExact(int a) {
    throw new UnsupportedOperationException();
  }

  public static long decrementExact(long a) {
    throw new UnsupportedOperationException();
  }

  public static double exp(double a) {
    ScriptHelper.put("x", a);
    return ScriptHelper.evalDouble("Math.exp(x)");
  }

  public static double expm1(double d) {
    if (d == 0.0 || Double.isNaN(d)) {
      return d; // "a zero with same sign as argument", arg is zero, so...
    } else if (!Double.isInfinite(d)) {
      if (d < 0.0d) {
        return -1.0d;
      } else {
        return Double.POSITIVE_INFINITY;
      }
    }
    return exp(d) + 1.0d;
  }

  public static double floor(double a) {
    ScriptHelper.put("x", a);
    return ScriptHelper.evalDouble("Math.floor(x)");
  }

  /* TODO
  @formatter:off
  public static int floorDiv(int x, int y) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  /* TODO
  @formatter:off
  public static long floorDiv(long x, long y) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  /* TODO
  @formatter:off
  public static int floorMod(int x, int y) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  /* TODO
  @formatter:off
  public static long floorMod(long x, long y) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  /* TODO
  @formatter:off
  public static int getExponent(double d) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  /* TODO
  @formatter:off
  public static int getExponent(float d) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  public static double hypot(double x, double y) {
    return sqrt(x * x + y * y);
  }

  /* TODO
  @formatter:off
  public static double IEEEremainder(double f1, double f2) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  /* TODO
  @formatter:off
  public static int incrementExact(int a) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  /* TODO
  @formatter:off
  public static long incrementExact(long a) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  public static double log(double a) {
    ScriptHelper.put("x", a);
    return ScriptHelper.evalDouble("Math.log(x)");
  }

  public static double log10(double a) {
    ScriptHelper.put("x", a);
    return ScriptHelper.evalDouble("Math.log(x) * Math.LOG10E");
  }

  public static double log1p(double a) {
    return Math.log(a + 1.0d);
  }

  public static double max(double a, double b) {
    ScriptHelper.put("x", a);
    ScriptHelper.put("y", b);
    return ScriptHelper.evalDouble("Math.max(x, y)");
  }

  public static float max(float a, float b) {
    ScriptHelper.put("x", a);
    ScriptHelper.put("y", b);
    return ScriptHelper.evalFloat("Math.max(x, y)");
  }

  public static int max(int a, int b) {
    ScriptHelper.put("x", a);
    ScriptHelper.put("y", b);
    return ScriptHelper.evalInt("Math.max(x, y)");
  }

  public static long max(long a, long b) {
    ScriptHelper.put("x", a);
    ScriptHelper.put("y", b);
    return ScriptHelper.evalLong("Math.max(x, y)");
  }

  public static double min(double a, double b) {
    ScriptHelper.put("x", a);
    ScriptHelper.put("y", a);
    return ScriptHelper.evalDouble("Math.min(x, y)");
  }

  public static float min(float a, float b) {
    ScriptHelper.put("x", a);
    ScriptHelper.put("y", b);
    return ScriptHelper.evalFloat("Math.min(x, y)");
  }

  public static int min(int a, int b) {
    ScriptHelper.put("x", a);
    ScriptHelper.put("y", b);
    return ScriptHelper.evalInt("Math.min(x, y)");
  }

  public static long min(long a, long b) {
    ScriptHelper.put("x", a);
    ScriptHelper.put("y", b);
    return ScriptHelper.evalLong("Math.min(x, y)");
  }

  /* TODO
  @formatter:off
  public static int multiplyExact(int x, int y) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  /* TODO
  @formatter:off
  public static int multiplyExact(long x, long y) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  /* TODO
  @formatter:off
  public static int negateExact(int a) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  /* TODO
  @formatter:off
  public static long negateExact(long a) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  /* TODO
  @formatter:off
  public static double nextAfter(double start, double direction) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  /* TODO
  @formatter:off
  public static float nextAfter(float start, double direction) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  /* TODO
  @formatter:off
  public static double nextDown(double d) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  /* TODO
  @formatter:off
  public static float nextDown(float f) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  /* TODO
  @formatter:off
  public static double nextUp(double d) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  /* TODO
  @formatter:off
  public static float nextUp(float f) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  public static double pow(double x, double exp) {
    ScriptHelper.put("x", x);
    ScriptHelper.put("exp", exp);
    return ScriptHelper.evalDouble("Math.pow(x, exp)");
  }

  public static double random() {
    return ScriptHelper.evalDouble("Math.random()");
  }

  public static double rint(double a) {
    if (Double.isNaN(a)) {
      return a;
    } else if (Double.isInfinite(a)) {
      return a;
    } else if (a == 0.0d) {
      return a;
    } else {
      return round(a);
    }
  }

  public static long round(double a) {
    ScriptHelper.put("x", a);
    return ScriptHelper.evalLong("Math.round(x)");
  }

  public static int round(float a) {
    ScriptHelper.put("x", a);
    return ScriptHelper.evalInt("Math.round(x)");
  };

  public static double scalb(double d, int scaleFactor) {
    if (scaleFactor >= 31 || scaleFactor <= -31) {
      return d * Math.pow(2, scaleFactor);
    } else if (scaleFactor > 0) {
      return d * (1 << scaleFactor);
    } else if (scaleFactor == 0) {
      return d;
    } else {
      return d * 1.0d / (1 << -scaleFactor);
    }
  }

  public static float scalb(float f, int scaleFactor) {
    if (scaleFactor >= 31 || scaleFactor <= -31) {
      return f * (float) Math.pow(2, scaleFactor);
    } else if (scaleFactor > 0) {
      return f * (1 << scaleFactor);
    } else if (scaleFactor == 0) {
      return f;
    } else {
      return f * 1.0f / (1 << -scaleFactor);
    }
  }

  public static double signum(double d) {
    if (d > 0.0d) {
      return 1.0d;
    } else if (d < 0.0d) {
      return -1.0d;
    } else {
      return 0.0d;
    }
  }

  public static float signum(float f) {
    if (f > 0.0f) {
      return 1.0f;
    } else if (f < 0.0f) {
      return -1.0f;
    } else {
      return 0.0f;
    }
  }

  public static double sin(double a) {
    ScriptHelper.put("x", a);
    return ScriptHelper.evalDouble("Math.sin(x)");
  }

  public static double sinh(double a) {
    ScriptHelper.put("x", a);
    return ScriptHelper.evalDouble("(Math.exp(x) - Math.exp(-x)) / 2.0)");
  }

  public static double sqrt(double a) {
    ScriptHelper.put("x", a);
    return ScriptHelper.evalDouble("Math.sqrt(x)");
  }

  /* TODO
  @formatter:off
  public static int subtractExact(int x, int y) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  /* TODO
  @formatter:off
  public static int subtractExact(long x, long y) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  public static double tan(double a) {
    ScriptHelper.put("x", a);
    return ScriptHelper.evalDouble("Math.tan(x)");
  }

  public static double tanh(double a) {
    ScriptHelper.put("x", a);
    return ScriptHelper.evalDouble("(function(x){"
        + "if (x == Infinity) {"
        + " return 1.0;"
        + "}"
        + "var e2x = Math.exp(2.0 * x);"
        + "return (e2x - 1) / (e2x + 1);"
        + "})(x)");
  }

  public static double toDegrees(double a) {
    return a * PI_UNDER_180;
  }

  public static int toIntExact(long value) {
    throw new UnsupportedOperationException();
  }

  public static double toRadians(double a) {
    return a * PI_OVER_180;
  }

  /* TODO
  @formatter:off
  public static double ulp(double d) {
    throw new UnsupportedOperationException(); 
  }
  @formatter:on
  */

  /* TODO
  @formatter:off
  public static float ulp(float f) {
    throw new UnsupportedOperationException();
  }
  @formatter:on
  */

  // ---

  private Math() {}
}
