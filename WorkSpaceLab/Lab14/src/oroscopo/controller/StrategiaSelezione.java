package oroscopo.controller;

import java.util.List;

import oroscopo.model.Previsione;
import oroscopo.model.SegnoZodiacale;

public interface StrategiaSelezione {

	Previsione seleziona(List<Previsione> previsioni, SegnoZodiacale segno);

}
