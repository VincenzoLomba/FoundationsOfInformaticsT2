package rfd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class OneChangeRouteFinder extends RouteFinder {

	public OneChangeRouteFinder(Set<RailwayLine> rwylines) {
		super(rwylines);
		if (rwylines == null)
			throw new IllegalArgumentException(
				"Nell'istanziare un oggetto della classe " + DirectRouteFinder.class.getSimpleName() + " si e' fornito al cosstruttore un parametro nullo."
			);
	}

	@Override
	public List<Route> getRoutes(String from, String to) {
		
		RouteFinder directRouteFinder = new DirectRouteFinder(rwylines);
		ArrayList<Route> response = new ArrayList<>();
		getLinesAtStation(from).stream().filter(rl -> !rl.getStations().contains(to)).forEach(rl -> {
			Optional<List<Route>> routeFromTpToB = rl.getTransferPoints().stream().map(tp -> directRouteFinder.getRoutes(tp, to)).filter((List<Route> rs) -> !rs.isEmpty()).findFirst();
			if (routeFromTpToB.isEmpty()) return;
			Segment second = routeFromTpToB.get().get(0).getRoute();
			Segment first = directRouteFinder.getRoutes(from, second.getFrom().getStationName()).get(0).getRoute();
			response.add(new Route(first, second));
		});
		return response;
	}

}
