package oroscopo.model;

import java.util.HashSet;
import java.util.Set;

public class Previsione {

	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((previsione == null) ? 0 : previsione.hashCode());
		result = prime * result + ((segni == null) ? 0 : segni.hashCode());
		result = prime * result + valore;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Previsione other = (Previsione) obj;
		if (previsione == null) {
			if (other.previsione != null)
				return false;
		} else if (!previsione.equals(other.previsione))
			return false;
		if (segni == null) {
			if (other.segni != null)
				return false;
		} else if (!segni.equals(other.segni))
			return false;
		if (valore != other.valore)
			return false;
		return true;
	}

	private String previsione;
	private int valore;
	private Set<SegnoZodiacale> segni;

	private static Set<SegnoZodiacale> getDefaultSegniSet() {
		
		HashSet<SegnoZodiacale> defaults = new HashSet<>();
		for (SegnoZodiacale s : SegnoZodiacale.values()) {
			defaults.add(s);
		}
		return defaults;
	}

	public Previsione(String previsione, int valore) { this(previsione, valore, getDefaultSegniSet()); }

	public Previsione(String previsione, int valore, Set<SegnoZodiacale> segni) {
		
		if (previsione == null || previsione.isEmpty())
			throw new IllegalArgumentException("previsione");
		if (valore < 0)
			throw new IllegalArgumentException("valore");
		if (segni == null)
			throw new IllegalArgumentException("segni");

		this.previsione = previsione;
		this.valore = valore;
		this.segni = segni;
	}

	public String getPrevisione() { return previsione; }
	public int getValore() { return valore; }
	public boolean validaPerSegno(SegnoZodiacale segno) { return segni.contains(segno); }
}
