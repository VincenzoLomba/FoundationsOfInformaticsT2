package consoleview;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class StdInput {
	private BufferedReader reader = new BufferedReader(new InputStreamReader(
			System.in));

	public String readString() {
		String line = null;
		try {
			line = reader.readLine();
		} catch (IOException e) {
		}
		return line;
	}

	public int readInt(int errorValue) {
		int option = errorValue;
		try {
			String line = readString();
			if (line != null) {
				option = Integer.parseInt(line);
			}
		} catch (NumberFormatException e) {
		}
		return option;
	}
}
