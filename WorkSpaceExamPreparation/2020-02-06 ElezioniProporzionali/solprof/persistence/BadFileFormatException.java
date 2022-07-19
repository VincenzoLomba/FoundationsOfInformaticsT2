package dentinia.governor.persistence;

public class BadFileFormatException extends Exception {

	static final long serialVersionUID = 1L;

	public BadFileFormatException() {
	}

	public BadFileFormatException(String message) {
		super(message);
	}

	public BadFileFormatException(Throwable cause) {
		super(cause);
	}

	public BadFileFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadFileFormatException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
