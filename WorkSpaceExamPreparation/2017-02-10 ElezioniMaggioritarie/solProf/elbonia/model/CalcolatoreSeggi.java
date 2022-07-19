package elbonia.model;

import java.util.List;
import java.util.Map;

public interface CalcolatoreSeggi {
	public Map<String, Integer> assegnaSeggi(int dimensioneCollegio, List<Collegio> listaCollegi);
}
