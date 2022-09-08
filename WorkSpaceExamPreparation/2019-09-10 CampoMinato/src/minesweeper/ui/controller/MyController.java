package minesweeper.ui.controller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import minesweeper.model.Game;
import minesweeper.model.GameStatus;
import minesweeper.persistence.GameSaver;
import minesweeper.persistence.MyGameSaver;

public class MyController implements Controller {
	private GameSaver printer;
	private Game game;
	private int size, mines;
	private String filename;
	private PrintWriter pw; 
	
	public MyController(int size, int mines, String filename) {
		this.size=size;
		this.mines=mines;
		this.filename=filename;
		init(size,mines,filename);
	}
	
	private void init(int size, int mines, String filename) {
		this.game=new Game(size,mines);
		this.pw = null; 
		try {
			pw = new PrintWriter(filename);			
			this.printer = new MyGameSaver(pw);
		} catch (FileNotFoundException e) {
			Controller.alert("Errore", "Stampa su file fallita", e.getMessage());
		}
	}
		
	@Override
	public void save() {
		printer.print(game);
	}
	
	@Override
	public void close() {
		printer.close();
	}
	
	@Override
	public int getMinesNumber(){
		return game.getMinesNumber();
	}

	@Override
	public int getSize(){
		return game.getSize();
	}
	
	@Override
	public String getPlayerMineField() {
		return game.getPlayerMineField();
	}

	@Override
	public String getRealMineField() {
		return game.getRealMineField();
	}

	@Override
	public GameStatus revealCell(int row, int col) {
		return game.revealCell(row, col);
	}

	@Override
	public void restart() {
		init(size,mines,filename);		
	}
}
