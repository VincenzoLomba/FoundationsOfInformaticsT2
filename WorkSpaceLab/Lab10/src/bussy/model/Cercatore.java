package bussy.model;
import java.util.Map;
import java.util.OptionalInt;
import java.util.SortedSet;

public interface Cercatore {
	
	public SortedSet<Percorso> cercaPercorsi(String fermataDa, String fermataA, OptionalInt durataMax);
	public Map<String,Linea> getMappaLinee();
}
