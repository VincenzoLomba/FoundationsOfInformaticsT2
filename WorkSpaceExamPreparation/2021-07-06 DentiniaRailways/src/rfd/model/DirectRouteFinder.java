package rfd.model;

import java.util.List;
import java.util.Set;

public class DirectRouteFinder extends RouteFinder {

	public DirectRouteFinder(Set<RailwayLine> rwylines) {
		super(rwylines);
		if (rwylines == null)
			throw new IllegalArgumentException(
				"Nell'istanziare un oggetto della classe " + DirectRouteFinder.class.getSimpleName() + " si e' fornito al cosstruttore un parametro nullo."
			);
	}

	@Override
	public List<Route> getRoutes(String from, String to) {
		
		if (from == null || to == null)
			throw new IllegalArgumentException(
				"Parametri nulli passati al metodo \"getRoutes(String from, String to)\" della classe \"" + DirectRouteFinder.class.getSimpleName() + "\"."
			);
		
		return rwylines.stream()
			.map(rl -> rl.getSegment(from, to))
			.filter(s -> s.isPresent())
			.map(s -> new Route(s.get()))
		.toList();
	}

}
