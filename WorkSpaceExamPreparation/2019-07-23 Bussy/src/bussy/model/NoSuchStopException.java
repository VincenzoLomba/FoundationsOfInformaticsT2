package bussy.model;

public class NoSuchStopException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public NoSuchStopException() {
		super();
	}

	public NoSuchStopException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchStopException(String s) {
		super(s);
	}

	public NoSuchStopException(Throwable cause) {
		super(cause);
	}

}
