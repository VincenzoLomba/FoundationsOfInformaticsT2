package battleship.persistence;

import java.io.IOException;
import java.io.Reader;

import battleship.model.ShipBoard;

public interface BattleshipReader {

	ShipBoard getSolutionBoard(Reader reader) throws BadFileFormatException, IOException;
	ShipBoard getPlayerBoard(Reader reader) throws BadFileFormatException, IOException;

}