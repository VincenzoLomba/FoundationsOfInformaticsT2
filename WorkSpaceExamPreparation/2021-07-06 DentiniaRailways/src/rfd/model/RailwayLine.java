package rfd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

public class RailwayLine {
	
	private SortedMap<String,PointOfInterest> map;
	private SortedSet<String> hubs;
	
	public RailwayLine(Map<String,PointOfInterest> map, SortedSet<String> hubs){
		
		this.map=new TreeMap<>(map);
		this.hubs=hubs;
	}
	
	public Optional<PointOfInterest> getPointOfInterest(String name) {
		return Optional.ofNullable(map.get(name));
	}
	
	public OptionalDouble getDistance(String name1, String name2){
		if(name1==null || name2==null) throw new IllegalArgumentException("null input station name in one of "+ name1 + ", " + name2);
		if (name1.equals(name2)) return OptionalDouble.empty();
		Optional<PointOfInterest> poi1 = this.getPointOfInterest(name1);
		Optional<PointOfInterest> poi2 = this.getPointOfInterest(name2);
		if (!poi1.isPresent() || !poi2.isPresent()) return OptionalDouble.empty();
		double km1 = this.getPointOfInterest(name1).get().getKmAsNum();
		double km2 = this.getPointOfInterest(name2).get().getKmAsNum();
		return OptionalDouble.of(Math.abs(km1-km2));
	}
	
	public Optional<Segment> getSegment(String name1, String name2){
		if(name1==null || name2==null) throw new IllegalArgumentException("null input station name in one of "+ name1 + ", " + name2);
		if (name1.equals(name2)) return Optional.empty();
		Optional<PointOfInterest> poi1 = this.getPointOfInterest(name1);
		Optional<PointOfInterest> poi2 = this.getPointOfInterest(name2);
		if (!poi1.isPresent() || !poi2.isPresent()) return Optional.empty();
		return Optional.of(new Segment(poi1.get(), poi2.get()));
	}
	
	public List<String> getStations(){ return new ArrayList<>(this.map.keySet()); }
	public SortedSet<String> getTransferPoints(){ return hubs; }
	
	@Override
	public String toString() {
		return this.map.toString() + " - hubs: " + this.hubs.toString();
	}
}
