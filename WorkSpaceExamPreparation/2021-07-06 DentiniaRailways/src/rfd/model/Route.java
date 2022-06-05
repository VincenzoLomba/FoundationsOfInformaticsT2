package rfd.model;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Route {
	
	private List<Segment> route;
	private NumberFormat formatter;
	
	public Route() {
		
		route = new ArrayList<>();
		formatter = NumberFormat.getNumberInstance();
		formatter.setMaximumFractionDigits(2);
		formatter.setMinimumFractionDigits(2);
	}

	public Route(Segment s) {
		
		this();
		route.add(s);
	}

	public Route(Segment... segments) {
		
		this();
		for(Segment s : segments) route.add(s);
	}

	@Override
	public String toString() {
		
		return "Percorso km " + formatter.format(this.getLength()) +  ":" + System.lineSeparator() +
				route.stream().map(Segment::toString).collect(Collectors.joining(System.lineSeparator()));
	}

	public List<Segment> getRouteSegments() { return route; }
	
	public void add(Segment s) {
		Segment last = route.size()>0 ? route.get(route.size()-1) : null;
		if(last==null || last.getTo().getStationName().equals(s.getFrom().getStationName())) {
			route.add(s);
		}
		else throw new IllegalArgumentException("segment not adjacent: " + s);
	}

	public Segment getRoute() { return new Segment( route.get(0).getFrom(), route.get(route.size()-1).getTo() ); }
	
	public double getLength() { return route.stream().mapToDouble(segment -> segment.getLength()).sum(); }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((route == null) ? 0 : route.hashCode());
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
		Route other = (Route) obj;
		if (route == null) {
			if (other.route != null)
				return false;
		} else if (!route.equals(other.route))
			return false;
		return true;
	}
	
}
