package oroscopo.model;

public interface Oroscopo {

	SegnoZodiacale getSegnoZodiacale();
	Previsione getPrevisioneAmore();
	Previsione getPrevisioneSalute();
	Previsione getPrevisioneLavoro();
	int getFortuna();
}
