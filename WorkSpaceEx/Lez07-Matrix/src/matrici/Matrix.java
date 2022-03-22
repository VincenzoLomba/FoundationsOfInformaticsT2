package matrici;

public class Matrix {
	
	private double[][] values;
	
	private Matrix (int row, int col) { values = new double[row][col]; }
	
	public Matrix (double[][] values) {
		
		this.values = new double[values.length][values[0].length];
		for (int i = 0 ; i < values.length ; ++i) {
			for (int j = 0 ; j < values[0].length ; ++j) {
				this.values[i][j] = values[i][j];
			}
		}
	}
	
	public int getRows () { return values.length; }
	public int getCols () { return values[0].length; }
	
	public double getValue (int row, int col) {
		
		if (row < 0 || row >= getRows() || col < 0 || col >= getCols())
			throw new IllegalArgumentException("Invalid index for row or column.");
		return values[row][col];
	}
	
	private void setValue (int row, int col, double value) {
		
		if (row < 0 || row >= getRows() || col < 0 || col >= getCols())
			throw new IllegalArgumentException("Invalid index for row or column.");
		values[row][col] = value;
	}
	
	public boolean isSquared () { return getRows() == getCols(); }
	
	public Matrix mul (Matrix m) {
		
		if (getCols() != m.getRows())
			// throw new IllegalArgumentException("First matrix's column and second matrix's rows must be of the same quantity.");
			return null;
		Matrix response = new Matrix(getRows(), m.getCols());
		for (int i = 0 ; i < getRows() ; ++i) {
			for (int j = 0 ; j < m.getCols() ; ++j) {
				double value = 0;
				for (int k = 0 ; k < m.getCols() ; ++k) {
					value += getValue(i, k) * m.getValue(k, j);
				}
				response.setValue(i, j, value);
			}
		}
		return response;
	}
	
	public Matrix sum (Matrix m) {
		
		if (getRows() != m.getRows() || getCols() != m.getCols())
			// throw new IllegalArgumentException("Tow matrix must have the same dimensions in order to sum them together.");
			return null;
		Matrix response = new Matrix(getRows(), getCols());
		for (int i = 0 ; i < getRows() ; ++i) {
			for (int j = 0 ; j < getCols() ; ++j) {
				response.setValue(i, j, getValue(i, j) + m.getValue(i, j));
			}
		}
		return response;
	}
	
	public Matrix extractMinor (int row, int col) {
		
		if (!isSquared())
			// throw new IllegalArgumentException("Is not possible to extract a minor from a not-squared matrix.");
			// (Notice that this exercise is done on the _wrong_ assumption and design specification that is not possible to extract a minor from a not-squared matrix).
			return null;
		if (getRows() == 1)
			// throw new IllegalArgumentException("Extracting a minor from a one row matrix isn't possible.");
			return null;
		if (getCols() == 1)
			// throw new IllegalArgumentException("Extracting a minor from a one row matrix isn't possible.");
		if (row < 0 || row >= getRows() || col < 0 || col >= getCols())
			// throw new IllegalArgumentException("Invalid index for row or column.");
			return null;
		Matrix response = new Matrix(getRows() - 1, getCols() - 1);
		for (int i = 0 ; i < getRows() ; ++i) {
			if (i == row) continue;
			for (int j = 0 ; j < getCols() ; ++j) {
				if (j == col) continue;
				int iIndex = i < row ? i : i-1;
				int jIndex = j < col ? j : j-1;
				response.setValue(iIndex, jIndex, getValue(i, j));
			}
		}
		return response;
	}
	
	public Matrix extractSubMatrix (int startRow, int startCol, int rowCount, int colCount) {
		
		if (startRow < 0 || startRow >= getRows() || startCol < 0 || startCol >= getCols())
			// throw new IllegalArgumentException("Invalid index for row or column.");
			return null;
		int endRow = startRow + rowCount < getRows() ? startRow + rowCount - 1 : getRows() - 1;
		int endCol = startCol + colCount < getRows() ? startCol + colCount - 1 : getRows() - 1;
		Matrix response = new Matrix(endRow - startRow + 1, endCol - startCol + 1);
		for (int i = startRow ; i <= endRow ; ++i) {
			for (int j = startCol ; j <= endCol ; ++j) {
				response.setValue(i - startRow, j - startCol, getValue(i, j));
			}
		}
		return response;
	}
	
	private double calcDet () {
		
		// ASSUMPTION: getRows() == getCols() (see det() method)
		if (getRows() == 1) return getValue(0, 0);
		double response = 0;
		for (int i = 0 ; i < getCols() ; ++i)
			response += (i % 2 == 0 ? 1 : -1) * getValue(0, i) * extractMinor(0, i).calcDet();
		return response;
	}
	
	public double det () { return getRows() == getCols() ? calcDet() : Double.NaN; }
	
	public String toString () {
		
		StringBuilder b = new StringBuilder("[ " + getValue(0, 0));
		for (int i = 0 ; i < getRows() ; ++i) {
			for (int j = 1 ; j < getCols() ; ++j) {
				b.append(", " + getValue(i, j));
			}
			b.append(i == getRows() - 1 ? " ]" : " ]\n[ " + getValue(1+i,0));
		}
		return b.toString();
	}

}
