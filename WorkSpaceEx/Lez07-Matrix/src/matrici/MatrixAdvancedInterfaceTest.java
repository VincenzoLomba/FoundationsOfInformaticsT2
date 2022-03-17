package matrici;
/**
 * Classe di test
 * 
 * @author Fondamenti di Informatica T-2 marzo 2021
 */

public class MatrixAdvancedInterfaceTest {
	
	public static void main(String[] args) {
		//testDet()
		Matrix m = new Matrix(new double[][] { { 1, 0, 0 }, { 0, 1, 0 },
				{ 0, 0, 1 } });
		assert(1==m.det());
	

		//testDetNaN()
		m = new Matrix(new double[][] { { 1, 0, 0, 1 }, { 0, 1, 0, 1 }, { 0, 0, 1, 1 } });
		assert(Double.isNaN(m.det())==true);
	

		//testSum()
		m = new Matrix(new double[][] { { 1, 0, 0 }, { 0, 1, 0 },
				{ 0, 0, 1 } });
		Matrix m1 = new Matrix(new double[][] { { 2, 1, 1 }, { 1, 1, 0 },
				{ 0, 0, 1 } });
		Matrix sum_m_m1 = m.sum(m1);
		assert(3 == sum_m_m1.getCols());
		assert(3 == sum_m_m1.getRows());
		// verifico la prima riga
		assert(3.0 == sum_m_m1.getValue(0, 0));
		assert(1.0 == sum_m_m1.getValue(0, 1));
		assert(1.0 == sum_m_m1.getValue(0, 2));
		// verifico la seconda riga
		assert(1.0 == sum_m_m1.getValue(1, 0));
		assert(2.0 == sum_m_m1.getValue(1, 1));
		assert(0.0 == sum_m_m1.getValue(1, 2));
		// verifico la terza riga
		assert(0.0 == sum_m_m1.getValue(2, 0));
		assert(0.0 == sum_m_m1.getValue(2, 1));
		assert(2.0 == sum_m_m1.getValue(2, 2));
		m1 = new Matrix(new double[][] { { 2, 1, 1, 1 }, { 1, 1, 0, 1 },
				{ 0, 0, 1, 1 } });
		sum_m_m1 = m.sum(m1);
		assert(sum_m_m1 == null);
		
		//testMul() {
		m = new Matrix(new double[][] { { 1, 0, 0 }, { 0, 1, 0 },
				{ 0, 0, 1 } });
		m1 = new Matrix(new double[][] { { 2, 1, 1 }, { 1, 1, 0 },
				{ 0, 0, 1 } });
		Matrix mul_m_m1 = m.mul(m1);
		assert(3 == mul_m_m1.getCols());
		assert(3 == mul_m_m1.getRows());
		// verifico la prima riga
		assert(2.0 == mul_m_m1.getValue(0, 0));
		assert(1.0 == mul_m_m1.getValue(0, 1));
		assert(1.0 == mul_m_m1.getValue(0, 2));
		// verifico la seconda riga
		assert(1.0 == mul_m_m1.getValue(1, 0));
		assert(1.0 == mul_m_m1.getValue(1, 1));
		assert(0.0 == mul_m_m1.getValue(1, 2));
		// verifico la terza riga
		assert(0.0 == mul_m_m1.getValue(2, 0));
		assert(0.0 == mul_m_m1.getValue(2, 1));
		assert(1.0 == mul_m_m1.getValue(2, 2));
		m1 = new Matrix(new double[][] { { 2, 1, 1 }, { 1, 1, 0 }, { 0, 0, 1 },
				{ 1, 0, 1 } });
		mul_m_m1 = m.mul(m1);
		assert(mul_m_m1==null);
	
		//testExtractSubMatrix() {
		m = new Matrix(new double[][] { { 1, 0, 0 }, { 0, 1, 0 },
				{ 0, 0, 1 } });
		Matrix sub_m = m.extractSubMatrix(1, 1, 2, 2);
		assert(2 == sub_m.getCols());
		assert(2 == sub_m.getRows());
		// verifico la prima riga
		assert(1.0 == sub_m.getValue(0, 0));
		assert(0.0 == sub_m.getValue(0, 1));
		// verifico la seconda riga
		assert(0.0 == sub_m.getValue(1, 0));
		assert(1.0 == sub_m.getValue(1, 1));

		Matrix sub_wrong = m.extractSubMatrix(3, 0, 1, 4);
		assert(sub_wrong==null);
		
		//testExtractMinor() {
		m1 = new Matrix(new double[][] { { 2, 1, 1 }, { 1, 1, 0 },
				{ 0, 0, 1 } });
		Matrix minor = m1.extractMinor(2, 2);
		assert(2 == minor.getCols());
		assert(2 == minor.getRows());
		// verifico la prima riga
		assert(2.0 == minor.getValue(0, 0));
		assert(1.0 == minor.getValue(0, 1));
		// verifico la seconda riga
		assert(1.0 == minor.getValue(1, 0));
		assert(1.0 == minor.getValue(1, 1));

		m1 = new Matrix(new double[][] { { 2, 1, 1, 2 }, { 1, 1, 0, 2 },
				{ 0, 0, 1, 2 } });
		minor = m1.extractMinor(2, 2);
		// assertSame(null, minor);
		assert(minor==null);
	}

}
