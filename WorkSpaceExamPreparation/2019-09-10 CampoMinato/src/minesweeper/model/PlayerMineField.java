package minesweeper.model;

public class PlayerMineField extends MineField {

	private int hiddenCells;
	
	public PlayerMineField (int size) {
		super(size);
		init();
	}
	
	@Override
	protected void init() {
		
		for (int i = 0 ; i < getSize() ; ++i) {
			for (int j = 0 ; j < getSize() ; ++j) {
				super.setCell(i, j, new Cell(CellType.HIDDEN));
			}
		}
		hiddenCells = getSize()*getSize();
	}
	
	public int getHiddenCellsNumber() { return hiddenCells; }
	
	@Override
	protected void setCell(int row, int col, Cell cell) {
		
		if (cell.getType().equals(CellType.HIDDEN)) {
			throw new UnsupportedOperationException(
				"Non e' possibile sostituire una cella con una di tipo \"" + CellType.HIDDEN.toString() + "\"."
			);
		}
		super.setCell(row, col, cell);
		--hiddenCells;
	}
}
