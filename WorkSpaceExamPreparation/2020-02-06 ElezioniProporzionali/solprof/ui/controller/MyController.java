package dentinia.governor.ui.controller;

import java.io.IOException;

import dentinia.governor.model.Elezioni;
import dentinia.governor.model.LeggeElettorale;
import dentinia.governor.model.LeggeElettoraleDHondt;
import dentinia.governor.persistence.BadFileFormatException;
import dentinia.governor.persistence.SeggiWriter;

public class MyController extends Controller {
	
	private SeggiWriter writer;
	private Elezioni elezioni;	
	

	public MyController(Elezioni elezioni, SeggiWriter writer) throws IOException, BadFileFormatException {
		this.writer = writer;
		this.elezioni = elezioni;
		elezioni.setAlgoritmo(new LeggeElettoraleDHondt());
	}
	
	@Override
	public Elezioni ricalcola(double sbarramento) {
		Elezioni elezioniFiltrate = LeggeElettorale.applicaSbarramento(elezioni, sbarramento);
		return elezioniFiltrate;
	}

	@Override
	public void salvaSuFile(String msg) throws IOException {
		if (elezioni == null) return;
		writer.stampaSeggi(elezioni, msg);
	}

	@Override
	public Elezioni getElezioni() {
		return elezioni;
	}

}
