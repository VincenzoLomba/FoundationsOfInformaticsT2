package rfd.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class RouteFinder {
	
	protected Set<RailwayLine> rwylines;
	private Map<String,Set<RailwayLine>> linesMap; /* Per ogni stazione, presenta le rwylins passanti */
	
	public RouteFinder(Set<RailwayLine> rwylines) {
		this.rwylines=rwylines;
		linesMap = new HashMap<>();
		for(RailwayLine railway: rwylines) {
			for(String station : railway.getStations()) {
				Set<RailwayLine> thisStationLines = linesMap.get(station);
				if (thisStationLines==null) {
					thisStationLines = new HashSet<>(List.of(railway));
				}
				else {
					thisStationLines.add(railway);
				}
				linesMap.put(station, thisStationLines); 
			}
		}
	}
	
	public Set<RailwayLine> getLinesAtStation(String station) {
		Set<RailwayLine> linesAtStation = linesMap.get(station);
		return linesAtStation == null ? Collections.emptySet() : linesAtStation;
	}
	
	public abstract List<Route> getRoutes(String from, String to);

	@Override
	public String toString() {
		return "Finder [rwylines=" + rwylines + "]";
	}
	
}
