package battleship.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

import battleship.model.ShipBoard;

public class MyBattleshipReader implements BattleshipReader {

	private ShipBoard playerBoard = null;
	private ShipBoard solutionBoard = null;
	private static final long DIM = 8;
	private static final String solutionBoardAdmissibleChars = "<>^vxo~";
	private static final String playerBoardInitializationAdmissibleChars = "<>^vxo#";
	
	public MyBattleshipReader () {}
	
	@Override
	public ShipBoard getSolutionBoard(Reader reader) throws BadFileFormatException, IOException {
		
		if (solutionBoard == null) solutionBoard = readBoard(reader, solutionBoardAdmissibleChars);
		return solutionBoard;
	}

	@Override
	public ShipBoard getPlayerBoard(Reader reader) throws BadFileFormatException, IOException {
		
		if (playerBoard == null) playerBoard = readBoard(reader, playerBoardInitializationAdmissibleChars);
		return playerBoard;
	}
	
	protected ShipBoard readBoard(Reader reader, String admissibleChars) throws IOException, BadFileFormatException {
		
		if (reader == null)
			throw new IllegalArgumentException("The provided reader is null.");
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line = null;
		int counter = 0;
		StringBuilder sb = new StringBuilder();
		while ( (line = bufferedReader.readLine()) != null) {
			++counter;
			if (line.isBlank()) continue;
			if (counter > DIM) throw new BadFileFormatException("File contains more than " + DIM + " lines.");
			String [] items = line.trim().split("\\s+");
			if (items.length != DIM) throw new BadFileFormatException("Line " + counter + " contains more than " + DIM + " characters.");
			if (!Arrays.stream(items).allMatch(c -> c.length() == 1 && admissibleChars.contains(c))) throw new BadFileFormatException("Line " + counter + " contains one or more not-admissible characters.");
			sb.append(String.join(" ", items));
			if (counter < 8) sb.append(System.lineSeparator());
		}
		if (counter < DIM) throw new BadFileFormatException("File contains less than " + DIM + " lines.");
		return new ShipBoard(sb.toString());
	}

}
