package battleship.model;

import java.util.Arrays;
import java.util.stream.Stream;

public class ShipBoard {
	
	private ShipItem[][] board;
	private static final int DIM = 8;

	public ShipBoard() {
		board = new ShipItem[DIM][DIM];
		Arrays.stream(board).forEach(arr -> Arrays.fill(arr, ShipItem.EMPTY));
	}
	
	public ShipBoard(String eightLines) {
		board = new ShipItem[DIM][DIM];
		String[] lines = eightLines.split("\\r?\\n");
		for (int i=0; i<DIM; i++) setCellRow(i, lines[i]);
	}

	public void setCellRow(int row, String line) {
		String[] items = line.split("\\s");
		if (row < 0 || row >= DIM || items.length!=DIM)
			throw new IllegalArgumentException("Errore nei parametri");
		for (int col = 0; col < DIM; col++) {
				board[row][col] = ShipItem.of(items[col].trim());
		}
	}

	public ShipItem getCell(int row, int col) {
		if (row < 0 || row >= DIM || col < 0 || col >= DIM)
			throw new IllegalArgumentException("Errore nei parametri");
		return board[row][col];
	}

	public int getSize() { return DIM; }
	
	public int countShipCellsInRow(int row) {
		
		if (row < 0 || row >= DIM)
			throw new IllegalArgumentException("Errore nei parametri");
		return countShipCellsInLine(Arrays.stream(board[row]));
	}

	public int countShipCellsInColumn(int col) {
		
		if (col < 0 || col >= DIM)
			throw new IllegalArgumentException("Errore nei parametri");
		return countShipCellsInLine(Arrays.stream(board).map(row -> row[col]));
	}
	
	private int countShipCellsInLine (Stream<ShipItem> line) { return line.filter(si -> si != ShipItem.SEA && si != ShipItem.EMPTY).mapToInt(si -> 1).sum(); }
	
	public int[] getCountingRow() {
		
		return Stream.iterate(0, x -> x + 1).limit(DIM).mapToInt(index -> countShipCellsInColumn(index)).toArray();
	}

	public int[] getCountingCol() {
		return Stream.iterate(0, x -> x + 1).limit(DIM).mapToInt(index -> countShipCellsInRow(index)).toArray();
	}
	
	public void clearCell(int row, int col)	{
		if (row < 0 || row >= DIM || col < 0 || col >= DIM )
			throw new IllegalArgumentException("Errore nei parametri");
		   board[row][col]=ShipItem.EMPTY;
	}
	
	public void setCell(int row, int col, ShipItem item) {
		if (row < 0 || row >= DIM || col < 0 || col >= DIM)
			throw new IllegalArgumentException("Errore nei parametri");
		board[row][col] = item;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int k = 0; k < DIM * DIM; k++) {
			sb.append(board[k / DIM][k % DIM].getValue());
			sb.append(k % DIM == DIM - 1 ? System.lineSeparator() : ' ');
		}
		return sb.toString();
	}
}
