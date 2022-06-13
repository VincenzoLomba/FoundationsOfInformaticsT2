package dentinia.governor.ui.javafx;

import java.io.IOException;

import dentinia.governor.model.Elezioni;
import dentinia.governor.model.LeggeElettoraleDHondt;
import dentinia.governor.model.Partito;
import dentinia.governor.persistence.BadFileFormatException;
import dentinia.governor.persistence.SeggiWriter;
import dentinia.governor.ui.controller.Controller;
import dentinia.governor.ui.controller.MyController;

public class ElectionApplicationMock extends ElectionApplication {

	private Elezioni creaElezioni() {
		Elezioni elezioni = new Elezioni(100,new LeggeElettoraleDHondt());
		elezioni.setVoti(new Partito("Zero"), 20);
		elezioni.setVoti(new Partito("Uno"), 80);
		elezioni.setVoti(new Partito("Due"), 200);
		elezioni.setVoti(new Partito("Tre"), 300);
		elezioni.setVoti(new Partito("Quattro"), 400);
		return elezioni;
	}
	
	@Override
	protected Controller createController() {
		SeggiWriter seggiWriter = (Elezioni elezioni, String msg) -> System.out.println(msg + " " + elezioni); 
		try {
			return new MyController(creaElezioni(), seggiWriter);
		} catch (IOException | BadFileFormatException e) {
			return null;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
