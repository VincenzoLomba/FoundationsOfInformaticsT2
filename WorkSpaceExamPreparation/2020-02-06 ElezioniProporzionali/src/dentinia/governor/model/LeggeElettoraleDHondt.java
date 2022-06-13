package dentinia.governor.model;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LeggeElettoraleDHondt implements LeggeElettorale {

	@Override
	public RisultatoElezioni apply(Elezioni elezioni) {
		
		ArrayList<Quoziente> quozienti = new ArrayList<>();
		elezioni.getPartiti().stream().forEach(p -> {
			Long votiPartito = elezioni.getVoti(p);
			quozienti.addAll(
				Stream.iterate(1, x -> x+1)
				.limit(elezioni.getSeggiDaAssegnare())
				.map(divisore -> new Quoziente(p, votiPartito / divisore))
				.collect(Collectors.toList())
			);
		});
		RisultatoElezioni response = new RisultatoElezioni(elezioni.getPartiti());
		quozienti.stream().sorted().limit(elezioni.getSeggiDaAssegnare()).forEach(q -> response.setSeggi(q.getPartito(), response.getSeggi(q.getPartito()) + 1));
		return response;
	}
}
