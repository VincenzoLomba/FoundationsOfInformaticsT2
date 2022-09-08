package minesweeper.persistence;

import minesweeper.model.Game;

public interface GameSaver {
	void print(Game match);
	void close();
}
