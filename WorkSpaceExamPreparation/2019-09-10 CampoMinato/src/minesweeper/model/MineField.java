package minesweeper.model;

public abstract class MineField {

	private Cell[][] board;
	private int size;
	
	public MineField(int size) {
		board = new Cell [size][size];
		this.size = size;
		//NB: it is the caller's responsibility to properly call init at due time
	}
	
	protected abstract void init();
	
	protected Cell getCell(int row, int col) {
		// might throw NPE if the referenced board cell is null
		return board[row][col];
	}
	
	protected void setCell(int row, int col, Cell cell) {
		// might throw NPE if the referenced board cell is null 
		board[row][col] = cell; 
	}

	public int getSize() {
		return size;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int k=0; k<size*size; k++) {
			sb.append(board[k/size][k%size]);
			sb.append(k%size==size-1 ? System.lineSeparator() : '\t');
		}
		return sb.toString();
	}
	
}

