package mediaesami.model;

import java.util.OptionalInt;

public enum Voto {
	RESPINTO, RITIRATO, IDONEO, // devono essere all'inizio per facilitare gli ordinamenti degli esami con tentativi multipli, così che quello col voto sia l'ultimo
	DICIOTTO(18), DICIANNOVE(19), VENTI(20), VENTUNO(21), VENTIDUE(22), VENTITRE(23), VENTIQUATTRO(24),
	VENTICINQUE(25), VENTISEI(26), VENTISETTE(27), VENTOTTO(28), VENTINOVE(29), TRENTA(30),
	TRENTAELODE(30);
	
	private OptionalInt value;
	private Voto(int v) { value=OptionalInt.of(v); }
	private Voto() { value=OptionalInt.empty(); }
	
	public OptionalInt getValue() {
		return value;
	}
	
	public boolean hasLaude() {
		return this==TRENTAELODE;
	}

	public boolean superato() {
		return this!=Voto.RESPINTO && this!=Voto.RITIRATO;
	}
	
	public static Voto of(String votoAsString) throws IllegalArgumentException {
		int v;
		return switch(votoAsString) {
				case "RE" -> RESPINTO;
				case "RT" -> RITIRATO;
				case "ID" -> IDONEO;
				case "30L" -> TRENTAELODE;
				default -> {
					v = Integer.parseInt(votoAsString);
					if(v<18 || v>30) throw new IllegalArgumentException("Wrong mark: " + v);
					yield Voto.values()[v-18+3]; // offset 3 dovuto ai 3 giudizi	
				}
		};
	}
	
	public String toString(){
		return switch(this) {
				case RESPINTO -> "RE";
				case RITIRATO -> "RT";
				case IDONEO -> "ID";
				case TRENTAELODE -> "30L";
				default -> String.valueOf(this.getValue().getAsInt());
		};
	}
	
}
