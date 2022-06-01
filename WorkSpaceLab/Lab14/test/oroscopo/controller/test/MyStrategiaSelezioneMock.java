package oroscopo.controller.test;

import java.util.List;
import oroscopo.controller.StrategiaSelezione;
import oroscopo.model.Previsione;
import oroscopo.model.SegnoZodiacale;

public class MyStrategiaSelezioneMock implements StrategiaSelezione {
	
	@Override
	public Previsione seleziona(List<Previsione> previsioni, SegnoZodiacale segno) {
		return previsioni.get(0);
	}

}
