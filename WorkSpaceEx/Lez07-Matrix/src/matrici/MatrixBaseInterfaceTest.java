package matrici;
/**
 * Classe di test
 * 
 * @author Fondamenti di Informatica T-2 marzo 2021
 */

public class MatrixBaseInterfaceTest {

	public static void main(String[] args) {
		//testGetRows() {
		Matrix m = new Matrix(new double[][] { { 1, 0, 0 }, { 0, 1, 0 },
				{ 0, 0, 1 } });
		assert(3==m.getRows());
		
		//testGetCols() {
		m = new Matrix(new double[][] { { 1, 0, 0 }, { 0, 1, 0 },
				{ 0, 0, 1 } });
		assert(3 == m.getCols());
		
		//testIsSquared() {
		m = new Matrix(new double[][] { { 1, 0, 0 }, { 0, 1, 0 },
				{ 0, 0, 1 } });
		assert(m.isSquared()==true);
		m = new Matrix(new double[][] { { 1, 0, 0, 1 }, { 0, 1, 0, 1 },
				{ 0, 0, 1, 1 } });
		assert(m.isSquared()==false);
	
		//testGetValue() {
		m = new Matrix(new double[][] { { 1, 0, 0 }, { 0, 1, 0 },
				{ 0, 0, 1 } });
		assert(1 == m.getValue(0, 0));
	}

}
