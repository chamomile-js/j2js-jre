/*
 * Copyright (c) 2005 j2js.com,
 *
 * All Rights Reserved. This work is distributed under the j2js Software License [1]
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.
 *
 * [1] http://www.j2js.com/license.txt
 */

package java.lang;

import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Objects;

import javascript.ScriptHelper;

public class Throwable implements Serializable {
	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = -3042686055658047285L;

	private String message;
	private Throwable cause;
	private String stackTrace;

	public Throwable() {
	}

	public Throwable(String newMessage) {
		message = newMessage;
	}

	public Throwable(String newMessage, Throwable theCause) {
		message = newMessage;
		cause = theCause;
	}

	public Throwable(Throwable theCause) {
		cause = theCause;
	}

	protected Throwable(String message, Throwable cause,
			boolean enableSuppression,
			boolean writableStackTrace) {
		this(message, cause);
	}

	public String getMessage() {
		return message;
	}

	public String getLocalizedMessage() {
		return getMessage();
	}
	
  /**
   * Initializes the <i>cause</i> of this throwable to the specified value.
   * (The cause is the throwable that caused this throwable to get thrown.)
   *
   * <p>This method can be called at most once.  It is generally called from
   * within the constructor, or immediately after creating the
   * throwable.  If this throwable was created
   * with {@link #Throwable(Throwable)} or
   * {@link #Throwable(String,Throwable)}, this method cannot be called
   * even once.
   *
   * <p>An example of using this method on a legacy throwable type
   * without other support for setting the cause is:
   *
   * <pre>
   * try {
   *     lowLevelOp();
   * } catch (LowLevelException le) {
   *     throw (HighLevelException)
   *           new HighLevelException().initCause(le); // Legacy constructor
   * }
   * </pre>
   *
   * @param  cause the cause (which is saved for later retrieval by the
   *         {@link #getCause()} method).  (A {@code null} value is
   *         permitted, and indicates that the cause is nonexistent or
   *         unknown.)
   * @return  a reference to this {@code Throwable} instance.
   * @throws IllegalArgumentException if {@code cause} is this
   *         throwable.  (A throwable cannot be its own cause.)
   * @throws IllegalStateException if this throwable was
   *         created with {@link #Throwable(Throwable)} or
   *         {@link #Throwable(String,Throwable)}, or this method has already
   *         been called on this throwable.
   * @since  1.4
   */
  public synchronized Throwable initCause(Throwable cause) {
      if (this.cause != this)
          throw new IllegalStateException("Can't overwrite cause with " +
                                          Objects.toString(cause, "a null"), this);
      if (cause == this)
          throw new IllegalArgumentException("Self-causation not permitted", this);
      this.cause = cause;
      return this;
  }

	public synchronized Throwable getCause() {
		return (cause == this ? null : cause);
	}

	@Override
	public String toString() {
		String s = getClass().getName();
		String message = getLocalizedMessage();
		return (message != null) ? (s + ": " + message) : s;
	}

	public void printStackTrace() {
		printStackTrace(System.err);
	}

	public void printStackTrace(PrintStream stream) {
		printStackTrace(new PrintWriter(new OutputStreamWriter(stream)));
	}

	public void printStackTrace(PrintWriter printWriter) {
		printWriter.print(toString());
		printWriter.println(stackTrace);

		if (cause != null) {
			printWriter.println("Caused by:");
			cause.printStackTrace(printWriter);
		}
	}

	public Throwable fillInStackTrace() {
		stackTrace = (String) ScriptHelper.eval("stackTrace.toString()");
		return this;
	}

}
