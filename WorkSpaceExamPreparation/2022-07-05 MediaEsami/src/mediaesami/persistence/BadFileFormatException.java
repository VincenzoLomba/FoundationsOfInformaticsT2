package mediaesami.persistence;

import java.io.IOException;

public class BadFileFormatException extends IOException {

	private static final long serialVersionUID = 1L;

	public BadFileFormatException(String message) {
		super(message);
	}

	public BadFileFormatException(Throwable cause) {
		super(cause);
	}
	
}
