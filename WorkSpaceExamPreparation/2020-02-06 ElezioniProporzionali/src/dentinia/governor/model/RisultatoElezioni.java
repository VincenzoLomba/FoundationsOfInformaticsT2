package dentinia.governor.model;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class RisultatoElezioni {

	private SortedMap<Partito, Long> mappaSeggi;	
	private boolean seggiSettati;
	
	public RisultatoElezioni(Set<Partito> partiti) {		
		this.mappaSeggi = new TreeMap<>();
		this.seggiSettati = false;
		
		for (Partito p :partiti) {
			mappaSeggi.put(p, 0L);
		}
	}
	
	public Set<Partito> getPartiti(){
		return mappaSeggi.keySet();
	}
	
	public long getSeggiTotali() {
		return mappaSeggi.values().stream().collect(Collectors.summingLong(x -> x));
	}

	public void setSeggi(Partito p, long seggi) {
		mappaSeggi.put(p, seggi);
		seggiSettati = true;
	}

	public long getSeggi(Partito p) {
		return mappaSeggi.get(p) == null ? 0 : mappaSeggi.get(p);
	}
	
	public boolean seggiSettati() {
		return seggiSettati;
	}
	
	@Override
	public String toString() {
		return mappaSeggi.toString();
	}

	
}
