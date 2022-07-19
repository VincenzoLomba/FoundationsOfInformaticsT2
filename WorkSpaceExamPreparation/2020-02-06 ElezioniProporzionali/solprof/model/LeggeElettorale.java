package dentinia.governor.model;

import java.util.function.Function;

public interface LeggeElettorale extends Function<Elezioni,RisultatoElezioni> {
	
	public static Elezioni applicaSbarramento(Elezioni elezioni, double sbarramento) {
		if (sbarramento < 0.0)
			throw new IllegalArgumentException("sbarramento negativo in metodo applica");
		if (elezioni == null)
			throw new IllegalArgumentException("Elezioni null in metodo applica");
		Elezioni elezioniFiltrate = new Elezioni(elezioni.getSeggiDaAssegnare());
		elezioniFiltrate.setAlgoritmo(elezioni.getAlgoritmo());
		long votiTotali = elezioni.getVotiTotali();
		long sogliaVoti = Math.round(votiTotali * sbarramento);
		for (Partito p : elezioni.getPartiti()) {
			long votiOttenuti = elezioni.getVoti(p);
			long votiFiltrati = votiOttenuti >= sogliaVoti ? votiOttenuti : 0L;
			elezioniFiltrate.setVoti(p, votiFiltrati);
		}
		return elezioniFiltrate;
	}
}