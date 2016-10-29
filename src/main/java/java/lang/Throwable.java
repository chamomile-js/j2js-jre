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
