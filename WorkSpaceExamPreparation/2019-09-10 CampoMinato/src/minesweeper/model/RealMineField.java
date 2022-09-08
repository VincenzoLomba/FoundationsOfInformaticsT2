package minesweeper.model;

import java.util.Random;

public class RealMineField extends MineField {

	private int mines;
	
	public RealMineField (int size) {
		this(size, 10);
	}
	
	public RealMineField (int size, int mines) {
		super(size);
		this.mines = mines;
		init();
	}
	
	@Override
	protected void init() {
		
		int settedMines = 0;
		while (settedMines < mines) {
			Random r = new Random();
			int row = r.nextInt(0, getSize());
			int col = r.nextInt(0, getSize());
			if (getCell(row, col) == null) {
				setCell(row, col, new Cell(CellType.MINE));
				++settedMines;
			}
		}
		for (int row = 0 ; row < getSize() ; ++row) {
			for (int col = 0 ; col < getSize() ; ++col) {
				if (getCell(row, col) == null) {
					setCell(row, col, new Cell(CellType.NUM, calcAdjacentMines(row, col)));
				}
			}
		}
		
		// TODO Auto-generated method stub

	}
	
	public int getMinesNumber () { return mines; }
	
	private int calcAdjacentMines (int row, int col) {
		
		int response = 0;
		int minRow = row == 0 ? 0 : row - 1;
		int maxRow = row == getSize() - 1 ? getSize() - 1 : row + 1;
		int minCol = col == 0 ? 0 : col - 1;
		int maxCol = col == getSize() - 1 ? getSize() - 1 : col + 1;
		for (int rowIndex = minRow ; rowIndex <= maxRow ; ++rowIndex) {
			for (int colIndex = minCol ; colIndex <= maxCol ; ++colIndex) {
				if (row == rowIndex && col == colIndex) continue;
				Cell cell = getCell(rowIndex, colIndex);
				if (cell != null && cell.getType().equals(CellType.MINE))
					++response;
			}
		}
		return response;
	}

}
