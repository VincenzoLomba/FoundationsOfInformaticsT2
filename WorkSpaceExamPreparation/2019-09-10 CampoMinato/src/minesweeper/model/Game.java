package minesweeper.model;

public class Game {

	private RealMineField realField;
	private PlayerMineField gameField;
	private GameStatus status;
	
	public Game(int size) {
		realField = new RealMineField(size);
		gameField = new PlayerMineField(size);
		status = GameStatus.CONTINUING;
	}

	public Game(int size, int mines) {
		realField = new RealMineField(size,mines);
		gameField = new PlayerMineField(size);
		status = GameStatus.CONTINUING;
	}
	
	public Game(RealMineField realField) {
		// for testing purposes only
		this.realField = realField;
		gameField = new PlayerMineField(realField.getSize());
		status = GameStatus.CONTINUING;
	}
	
	public int getMinesNumber(){
		return realField.getMinesNumber();
	}

	public int getSize(){
		return realField.getSize();
	}
	
	public String getPlayerMineField() {
		return gameField.toString();
	}

	public String getRealMineField() {
		return realField.toString();
	}
	
	public GameStatus getCurrentStatus() {
		return status;
	}
	
	@Override
	public String toString() {
		return 	"Current game status:" + System.lineSeparator() + this.getPlayerMineField() + System.lineSeparator() +
				"Real board:" + System.lineSeparator() + this.getRealMineField() + System.lineSeparator() +
				this.getCurrentStatus() + System.lineSeparator();
	}
	
	public GameStatus revealCell(int row, int col) {
		Cell realCell = realField.getCell(row, col);
		Cell gameCell = gameField.getCell(row, col);
		if(gameCell.getType()!=CellType.HIDDEN) throw new UnsupportedOperationException("cannot reveal an already disclosed cell)");
		switch(realCell.getType()) {
			case MINE:  gameField.setCell(row, col, realCell);
						status = GameStatus.EXPLODED;
						return status;
			case NUM:	gameField.setCell(row, col, realCell);
						if(realCell.getAdjacents()==0) revealAdjacentCells(row,col);
						if (gameField.getHiddenCellsNumber()==realField.getMinesNumber()) status=GameStatus.WON;
						else status=GameStatus.CONTINUING;
						return status;
			default:	return null; // fake line, code will never get there
		}
	}
	
	private void revealAdjacentCells(int row, int col) {
		int jMin = (row==0) ? 0 : row-1; 
		int iMin = (col==0) ? 0 : col-1; 
		int jMax = (row==gameField.getSize()-1) ? row : row+1; 
		int iMax = (col==gameField.getSize()-1) ? col : col+1;
		//  System.err.println("revealing adjacents from col " + jMin + " to " + jMax +" and from row " + iMin + " to " + iMax);
		for (int j=jMin; j<=jMax; j++)
		 for (int i=iMin; i<=iMax; i++) {
			if(i==col && j==row) continue;
			else {
				Cell gameCell = gameField.getCell(j,i);
				Cell realCell = realField.getCell(j,i);
				if (gameCell.getType()==CellType.HIDDEN && realCell.getType()==CellType.NUM) 
					revealCell(j,i); 
			}
		 }
	}

}

