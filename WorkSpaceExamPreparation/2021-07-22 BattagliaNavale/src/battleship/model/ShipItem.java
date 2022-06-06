package battleship.model;

import java.util.Optional;

import javafx.scene.image.Image;


public enum ShipItem {
	
	LEFT("<", "sinistra.jpg", "Elemento SINISTRO"), RIGHT(">", "destra.jpg", "Elemento DESTRO"), 
	UP("^", "su.jpg", "Elemento SUPERIORE"), DOWN("v", "giu.jpg", "Elemento INFERIORE"), 
	CENTRE("x", "centro.jpg", "Elemento CENTRALE"), SINGLE("o", "singolo.jpg", "Elemento SINGOLO"),
	SEA("~", "mare.jpg", "MARE"), EMPTY("#", "vuoto.jpg", "Elemento VUOTO");

	ShipItem(String ch, String imageFileName, String descrizione) {
		if(ch.length()!=1) throw new IllegalArgumentException("Unexpected string length: " + ch);
		this.value=ch;
		this.descrizione=descrizione;
		try {
			// OPPURE: new Image(new FileInputStream("src/battleship/model/sinistra.jpg"));
			this.image=Optional.of(new Image(ShipItem.class.getResource(imageFileName).toString()));
		} catch (/* NullPointerException | IllegalArgumentException |*/ RuntimeException e) {
			// The real exceptions to be caught should be the above one, but
			// JUnit does fire RuntimeException during testing, due to JavaFX not yet initialised
			// So we catch RuntimeException, which is a superclass of the two others.  
			this.image=Optional.empty();
		} 
	}
	
	public String getValue() { return value; }
	public Optional<Image> getImage() { return image; }

	private String value, descrizione;
	private Optional<Image> image;
	
	public static ShipItem of(char ch) {
		// System.out.println("CHECK: |" + ch + "|");
		return switch(ch) {
			case '<' -> LEFT;
			case '>' -> RIGHT;
			case '^' -> UP;
			case 'v' -> DOWN;
			case 'x' -> CENTRE;
			case 'o' -> SINGLE;
			case '~' -> SEA;
			case '#' -> EMPTY;
			default -> throw new IllegalArgumentException("Unexpected value: " + ch);
		};
	}
	
	public static ShipItem of(String ch) {
		if (ch.length()!=1) throw new IllegalArgumentException("Unexpected value: " + ch);
		return of(ch.charAt(0));
	}
	
	@Override
	public String toString() {
		return descrizione;
	}
}
