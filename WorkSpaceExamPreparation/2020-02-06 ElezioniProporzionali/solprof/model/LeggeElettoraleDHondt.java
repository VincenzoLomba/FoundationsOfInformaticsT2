package dentinia.governor.model;

import java.util.ArrayList;
import java.util.Collections;

public class LeggeElettoraleDHondt implements LeggeElettorale {

	@Override
	public RisultatoElezioni apply(Elezioni elezioni) {
		long seggiDaAssegnare = elezioni.getSeggiDaAssegnare();
		RisultatoElezioni risultato = new RisultatoElezioni(elezioni.getPartiti());
		ArrayList<Quoziente> quozienti = new ArrayList<>();
		//
		for (Partito p : elezioni.getPartiti()) {
			for (int i=1; i<=seggiDaAssegnare; i++) {
				long votiPartito = elezioni.getVoti(p);
				Quoziente q = new Quoziente(p, (long) Math.floor(votiPartito/i));
				quozienti.add(q);
			}
		}
		Collections.sort(quozienti);
		
		int seggiAssegnati = 0;
		for (int k = 0; k < quozienti.size() && seggiAssegnati < seggiDaAssegnare; k++) {
			Partito p = quozienti.get(k).getPartito();
			long seggiAttuali = risultato.getSeggi(p);
			risultato.setSeggi(p, seggiAttuali + 1);
			seggiAssegnati++;
		}
		return risultato;
	}
	
}
