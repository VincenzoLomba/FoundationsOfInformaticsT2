package zannotaxi.model;

import java.time.LocalTime;

/* This class has been provided by the professor. */

public class ZannoTassametro extends Tassometro {
	
	private static ITariffaTaxi[] getTariffe() {
		
		return new ITariffaTaxi[] {
				new TariffaATempo("T0", 0, 27, 0.15, 12),
				new TariffaADistanza("T1", 27, Double.MAX_VALUE, 0, 10, 0.25, 100),
				new TariffaADistanza("T2", 27, Double.MAX_VALUE, 10, 25, 0.20, 85),
				new TariffaADistanza("T3", 27, Double.MAX_VALUE, 25, Double.MAX_VALUE, 0.15, 65)
		};
	}
	
	private static FasciaOraria[] getFasceOrarie() {
		
		return new FasciaOraria[] { 
				new FasciaOraria(LocalTime.of(06, 00), LocalTime.of(21, 59), 4), 
				new FasciaOraria(LocalTime.of(22, 00), LocalTime.of(23, 59), 6),
				new FasciaOraria(LocalTime.of(00, 00), LocalTime.of(05, 59), 6),
				};
	}
	
	public ZannoTassametro() {
		
		super(getTariffe(), getFasceOrarie());
	}

}
