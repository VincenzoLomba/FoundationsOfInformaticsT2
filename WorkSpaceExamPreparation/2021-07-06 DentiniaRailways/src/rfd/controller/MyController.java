package rfd.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import rfd.model.DirectRouteFinder;
import rfd.model.OneChangeRouteFinder;
import rfd.model.RailwayLine;
import rfd.model.Route;

public class MyController implements Controller {
	
	private DirectRouteFinder directRouteFinder;
	private OneChangeRouteFinder indirectRouteFinder;
	private List<String> stationNames;

	public MyController(Set<RailwayLine> rwylines) {
		this.directRouteFinder = new DirectRouteFinder(rwylines);
		this.indirectRouteFinder = new OneChangeRouteFinder(rwylines);
		this.stationNames = rwylines.stream()
			.flatMap(rwy -> rwy.getStations().stream())
			.distinct()
			.sorted()
			.collect(Collectors.toList());
	}
	
	@Override
	public List<String> getStationNames() {
		return stationNames;
	}
	
	@Override
	public List<Route> getDirectRoutes(String from, String to){
		return directRouteFinder.getRoutes(from, to);
	}
	
	@Override
	public List<Route> getIndirectRoutes(String from, String to){
		return indirectRouteFinder.getRoutes(from, to);
	}

}
