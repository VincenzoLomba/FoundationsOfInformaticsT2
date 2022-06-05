package rfd.model;

public class MyPointOfInterest extends PointOfInterest {

	public MyPointOfInterest(String name, String progressivaKm) {
		
		super(name, progressivaKm);
		if (name == null || progressivaKm == null)
			throw new IllegalArgumentException(
				"Nell'istanziare un oggetto della classe " + MyPointOfInterest.class.getSimpleName() + " si sono forniti al costruttore uno o piu' parametri nulli."
			);
		String items[] = progressivaKm.split("\\+");
		try {
			if (items.length != 2 || items[0].length() == 0 || items[1].length() != 3) throw new NumberFormatException();
			Integer.parseInt(items[0]);
			Integer.parseInt(items[1]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
				"La stringa \"progressivaKm\" deve essere nel formato \"chilometri+metri\" dove chilometri e' intero di almeno una cifra e metri un intero di esattamente tre cifre"
			);
		}
	}

	@Override
	public double getKmAsNum() {
		
		String items[] = getKm().split("\\+");
		return Double.parseDouble(items[0]) + Double.parseDouble(items[1]) / 1000;
	}

}
