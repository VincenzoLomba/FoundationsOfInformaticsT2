package fondt2.ioutils;

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

	public String readString(String msg) {
		System.out.println("Immettere " + msg + " :");
		return readString();
	}

	public String[] readStringArray(String msg1, String msg2) {
		System.out.println("Immettere numero di " + msg1 + ":");
		int num = readInt(-1);
		if (num == -1)
			return null;
		String[] appoggio = new String[num];
		for (int i = 0; i < num; i++) {
			System.out.println("Immettere " + msg2 + ":");
			appoggio[i] = readString();

		}
		return appoggio;

	}

	public int readInteger(String str) {
		System.out.println("Immettere " + str + " :");
		return readInt(-1);
	}
}
