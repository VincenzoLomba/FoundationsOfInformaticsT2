package elbonia.ui.controller;

import elbonia.model.CalcolatoreSeggi;
import elbonia.model.CalcolatoreSeggiMaggioritario;
import elbonia.ui.UserInteractor;

import java.util.Map;

public class MyController extends Controller {

	public MyController(UserInteractor userInteractor) {
		super(userInteractor);
	}

	public Map<String, Integer> calcola(int dimensioneCollegio) {
		if (dimensioneCollegio > getSeggiMassimi() || dimensioneCollegio < 1 ) {
			getUserInteractor().showMessage("Dimensione collegio dev'essere compresa fra 1 e " + getSeggiMassimi());
			return null;
		}
		CalcolatoreSeggi cs = new CalcolatoreSeggiMaggioritario();	
		return cs.assegnaSeggi(dimensioneCollegio, getListaCollegi());
	}

}
