package bussy.model;
import java.util.*;

public interface Cercatore {
	public SortedSet<Percorso> cercaPercorsi(String fermataDa, String fermataA, OptionalInt durataMax);
	public Map<String,Linea> getMappaLinee();
}
