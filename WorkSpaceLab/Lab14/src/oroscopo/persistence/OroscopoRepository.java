package oroscopo.persistence;

import java.util.List;
import java.util.Set;

import oroscopo.model.Previsione;

public interface OroscopoRepository {

	public Set<String> getSettori();
	public List<Previsione> getPrevisioni(String settore);
}
