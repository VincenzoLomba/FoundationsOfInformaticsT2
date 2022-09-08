package minesweeper.model;

import java.util.OptionalInt;

public class Cell {
	private CellType type;
	private OptionalInt adjacents;
	
	public Cell(CellType type) {
		this.type=type;
	}

	public Cell(CellType type, int n) {
		if (type!=CellType.NUM) throw new UnsupportedOperationException();
		this.type=type;
		this.adjacents=OptionalInt.of(n);
	}
	
	public CellType getType() {
		return this.type;
	}
	
	public void setAdjacents(int n) {
		if (type!=CellType.NUM) throw new UnsupportedOperationException();
		this.adjacents=OptionalInt.of(n);
	}
	
	public int getAdjacents() {
		if (type==CellType.MINE) throw new UnsupportedOperationException();
		return this.adjacents.getAsInt();
	}
	
	public String toString() {
		switch(type) {
			case MINE: 	 return "M";
			case HIDDEN: return "?";
			default: 	 return this.getAdjacents()+"";
		}
	}
}
