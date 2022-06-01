package oroscopo.controller;

import java.io.IOException;

import oroscopo.model.Oroscopo;
import oroscopo.model.SegnoZodiacale;
import oroscopo.persistence.OroscopoRepository;

public abstract class AbstractController {

	public static final int NUMERO_SEGNI = SegnoZodiacale.values().length;

	private OroscopoRepository repository;

	public AbstractController(OroscopoRepository repository) throws IOException {
		this.repository = repository;
	}

	public abstract SegnoZodiacale[] getSegni();

	public abstract Oroscopo[] generaOroscopoAnnuale(SegnoZodiacale segno, int fortunaMin);

	public abstract Oroscopo generaOroscopoCasuale(SegnoZodiacale segno);

	protected OroscopoRepository getRepository() {
		return repository;
	}

}