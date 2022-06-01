package oroscopo;


import java.io.IOException;

import oroscopo.controller.AbstractController;
import oroscopo.controller.MyController;
import oroscopo.controller.test.MyStrategiaSelezioneMock;
import oroscopo.persistence.OroscopoRepository;
import oroscopo.persistence.test.TextFileOroscopoRepositoryMock;

public class OroscopoApplicationMock extends OroscopoApplication {

	
	@Override
	protected OroscopoRepository createRepo() {
		try {
		OroscopoRepository repo = new TextFileOroscopoRepositoryMock(null);
		 return repo;
		}
		catch(Exception e)
		{e.printStackTrace(); return null;}
		
		
	}

	@Override
	protected AbstractController createController(OroscopoRepository repository) {		
		try {
			return new MyController(repository, new MyStrategiaSelezioneMock());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		System.setProperty("java.locale.providers", "JRE");
		launch(args);
	}

}
