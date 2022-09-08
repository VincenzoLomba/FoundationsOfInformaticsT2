package bussy.model;
public class BadLineException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;
	public BadLineException() { super(); }
	public BadLineException(String msg) { super(msg); }
}