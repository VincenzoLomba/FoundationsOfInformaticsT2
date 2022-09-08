package agenda.persistence;

public class BadFileFormatException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public BadFileFormatException() {}
	public BadFileFormatException(String message) { super(message); }
	public BadFileFormatException(Throwable innerException) { super(innerException); }
	public BadFileFormatException(String message, Throwable innerException) { super(message, innerException); }

}
