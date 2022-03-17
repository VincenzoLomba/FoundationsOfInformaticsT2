package matrici;
/**
 * Classe di test
 * 
 * @author Fondamenti di Informatica T-2 marzo 2021
 */
public class MatrixTestMain {
	public static void main(String[] args) {
		Matrix m = new Matrix(new double[][] { { 1, 0, 0 }, { 0, 1, 0 },
				{ 0, 0, 1 } });
		System.out.println(m);
		System.out.println(m.det());

		Matrix m1 = new Matrix(new double[][] { { 2, 1, 1 }, { 1, 1, 0 },
				{ 0, 0, 1 } });
		System.out.println(m1);
		System.out.println(m1.det());

		Matrix sum_m_m1 = m.sum(m1);
		System.out.println("m + m1 =");
		System.out.println(sum_m_m1);

		Matrix mul_m_m1 = m.mul(m1);
		System.out.println("m * m1 =");
		System.out.println(mul_m_m1);

		Matrix subMatrix = m.extractSubMatrix(1, 1, 2, 2);
		System.out.println("SubMatrix: ");
		System.out.println(subMatrix);

		Matrix minor = m1.extractMinor(2, 2);
		System.out.println("Minor: ");
		System.out.println(minor);
	}

}
