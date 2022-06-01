package oroscopo.persistence;

public class BadFileFormatException extends Exception {

	public BadFileFormatException(Throwable t) {
		super(t);
	}
	
	public BadFileFormatException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = 1L;

}
