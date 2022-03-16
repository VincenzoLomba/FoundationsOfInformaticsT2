package fractioncollection;
import fraction.Fraction;

public class FractionCollectionTests {

	public static void main(String[] args) {
		// testMyFractionCollectionFractionArray
		Fraction f1 = new Fraction(1, 2);
		Fraction f2 = new Fraction(2, 3);
		Fraction[] fa = new Fraction[] { f1, f2 };
		// act
		FractionCollection c = new FractionCollection(fa);
		// assert
		assert(2==c.size());
		assert(f1.equals(c.get(0))==true);
		assert(f2.equals(c.get(1))==true);
	
		// testMyFractionCollectionInt
		c = new FractionCollection(5);
		// assert
		assert(0==c.size());

		// testMyFractionCollection
		c = new FractionCollection();
		// assert
		assert(0==c.size());

		// testPut
		f1 = new Fraction(1, 2);
		c = new FractionCollection();
		c.put(f1);

		// assert
		assert(1==c.size());
		assert(f1.equals(c.get(0))==true);

		//testPutWithResize
		f1 = new Fraction(1, 1);
		f2 = new Fraction(1, 2);
		Fraction f3 = new Fraction(1, 3);
		Fraction f4 = new Fraction(1, 4);
		Fraction f5 = new Fraction(1, 5);
		c = new FractionCollection(3);
		c.put(f1);
		c.put(f2);
		c.put(f3);
		c.put(f4); // needs resize
		c.put(f5);
		// assert
		assert(5==c.size());
		assert(f1.equals(c.get(0))==true);
		assert(f2.equals(c.get(1))==true);
		assert(f3.equals(c.get(2))==true);
		assert(f4.equals(c.get(3))==true);
		assert(f5.equals(c.get(4))==true);
		
		//testRemove
		f1 = new Fraction(1, 1);
		f2 = new Fraction(1, 2);
		f3 = new Fraction(1, 3);
		f4 = new Fraction(1, 4);
		f5 = new Fraction(1, 5);
		c = new FractionCollection();
		c.put(f1);
		c.put(f2);
		c.put(f3);
		c.put(f4);
		c.put(f5);
		c.remove(2);

		// assert
		assert(4==c.size());
		assert(f1.equals(c.get(0))==true);
		assert(f2.equals(c.get(1))==true);
		assert(f4.equals(c.get(2))==true);
		assert(f5.equals(c.get(3))==true);
		
		//testSum
		// fail("Do it yourself");
		FractionCollection c1 = new FractionCollection();
		c1.put(new Fraction(1, 1));
		c1.put(new Fraction(1, 2));
		c1.put(new Fraction(1, 3));
		c1.put(new Fraction(1, 4));
		c1.put(new Fraction(1, 5));
		
		FractionCollection c2 = new FractionCollection();
		c2.put(new Fraction(1, 1));
		c2.put(new Fraction(1, 2));
		c2.put(new Fraction(1, 3));
		c2.put(new Fraction(1, 4));
		c2.put(new Fraction(1, 5));
		
		FractionCollection res = c1.sum(c2);
		
		// assert
		assert(5==res.size());
		assert(new Fraction(2).equals(res.get(0))==true);
		assert(new Fraction(1).equals(res.get(1))==true);
		assert(new Fraction(2, 3).equals(res.get(2))==true);
		assert(new Fraction(1, 2).equals(res.get(3))==true);
		assert(new Fraction(2, 5).equals(res.get(4))==true);
	
		//testMul
		//("Do it yourself");
	}

}
