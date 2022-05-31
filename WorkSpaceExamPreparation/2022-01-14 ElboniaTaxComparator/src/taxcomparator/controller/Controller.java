package taxcomparator.controller;

import java.util.List;

import taxcomparator.model.CalcolatoreImposta;
import taxcomparator.model.Fasce;
import taxcomparator.ui.TaxComparatorApp;

public class Controller {
	
	private CalcolatoreImposta calcolatoreImposte;
	private Fasce fasceCorrenti;
	private List<Fasce> listaFasce;

	public Controller(CalcolatoreImposta calcolatoreRedditi, List<Fasce> listaFasce) {
		
		this.calcolatoreImposte = calcolatoreRedditi;
		this.listaFasce = listaFasce;
	}
	
	public void setFasce(Fasce fasce) { this.fasceCorrenti=fasce; }
	public Fasce getFasce() { return fasceCorrenti; }

	public double calcolaImposte(double reddito) { return calcolatoreImposte.calcolaImposta(reddito, fasceCorrenti); }
	
	public String getLog() { return calcolatoreImposte.getLog(); }
	public List<Fasce> getListaFasceDisponibili() { return listaFasce; }
	
	public static void alert(String title, String headerMessage, String contentMessage) { TaxComparatorApp.alert(title, headerMessage, contentMessage); }
}
