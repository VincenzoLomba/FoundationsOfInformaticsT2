package battleship.persistence;

public class BadFileFormatException extends Exception
{

	private static final long serialVersionUID = 1L;

	public BadFileFormatException()
	{
	}

	public BadFileFormatException(String arg0)
	{
		super(arg0);
	}

	public BadFileFormatException(Throwable arg0)
	{
		super(arg0);
	}

	public BadFileFormatException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
	}

}
