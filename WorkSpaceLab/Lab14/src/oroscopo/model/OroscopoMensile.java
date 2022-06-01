package oroscopo.model;

public class OroscopoMensile implements Oroscopo, Comparable<Oroscopo>{
	
	private Previsione amore, lavoro, salute;
	private SegnoZodiacale segnoZodiacale;
	
	public OroscopoMensile (SegnoZodiacale segnoZodiacale, Previsione amore, Previsione lavoro, Previsione salute) {
		this(segnoZodiacale == null ? null : segnoZodiacale.toString(), amore, lavoro, salute);
	}
	
	public OroscopoMensile (String segnoZodiacale, Previsione amore, Previsione lavoro, Previsione salute) {
		
		if (segnoZodiacale == null || amore == null || lavoro == null || salute == null)
			throw new IllegalArgumentException(
				"Errore nella creazione di un oggetto istanza della classe " + OroscopoMensile.class.getSimpleName() + ": uno o piu' dei parametri passati al costruttore risulta essere nullo."
			);	
		this.segnoZodiacale = SegnoZodiacale.valueOf(segnoZodiacale);
		if (!amore.validaPerSegno(getSegnoZodiacale()) || !lavoro.validaPerSegno(getSegnoZodiacale()) || !salute.validaPerSegno(getSegnoZodiacale()))
			throw new IllegalArgumentException(
				"Errore nella creazione di un oggetto istanza della classe " + OroscopoMensile.class.getSimpleName() 
				+ ": una o piu' delle previsioni passate al costruttore risulta non applicabile per l'indicato segno zodiacale."
			);
		this.amore = amore;
		this.lavoro = lavoro;
		this.salute= salute;
	}

	@Override
	public int compareTo(Oroscopo o) { return getSegnoZodiacale().compareTo(o.getSegnoZodiacale()); }
	
	@Override
	public int getFortuna() { return Math.round( ( (float) (getPrevisioneAmore().getValore() + getPrevisioneLavoro().getValore() + getPrevisioneSalute().getValore()) ) / 3);}
	
	@Override
	public String toString() {
		return new StringBuilder("Amore: ").append(getPrevisioneAmore().getPrevisione())
			.append(System.lineSeparator())
			.append("Lavoro: ").append(getPrevisioneLavoro().getPrevisione())
			.append(System.lineSeparator())
			.append("Salute: ").append(getPrevisioneSalute().getPrevisione())
		.toString();
	}

	@Override
	public SegnoZodiacale getSegnoZodiacale() { return segnoZodiacale; }
	@Override
	public Previsione getPrevisioneAmore() { return amore; }
	@Override
	public Previsione getPrevisioneSalute() { return salute;}
	@Override
	public Previsione getPrevisioneLavoro() { return lavoro; }
}
