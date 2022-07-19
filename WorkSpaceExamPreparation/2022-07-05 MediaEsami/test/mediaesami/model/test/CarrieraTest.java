package mediaesami.model.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import mediaesami.model.AttivitaFormativa;
import mediaesami.model.Carriera;
import mediaesami.model.Esame;
import mediaesami.model.Voto;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;


class CarrieraTest {

	Carriera carriera;
	
	@Test
	void testOK_singoloEsameConVotoDatoUnaSolaVolta() {
		carriera = new Carriera();
		AttivitaFormativa af = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		Esame esame = new Esame(af, LocalDate.of(2020, 6, 6), Voto.DICIOTTO);
		assertEquals(Collections.EMPTY_LIST, carriera.getListaEsami());
		carriera.inserisci(esame);
		assertEquals(List.of(esame), carriera.getListaEsami());
		assertEquals(12, carriera.creditiAcquisiti());
	}

	@Test
	void testOK_singoloEsameConGiudizioDatoUnaSolaVolta() {
		carriera = new Carriera();
		AttivitaFormativa af = new AttivitaFormativa(26337, "LINGUA INGLESE B-2", 6);
		Esame esame = new Esame(af, LocalDate.of(2020, 6, 6), Voto.IDONEO);
		assertEquals(Collections.EMPTY_LIST, carriera.getListaEsami());
		carriera.inserisci(esame);
		assertEquals(List.of(esame), carriera.getListaEsami());
		assertEquals(6, carriera.creditiAcquisiti());
	}
	
	@Test
	void testOK_singoloEsameConVotoDatoDueVolte_PrimaVoltaRitirato() {
		carriera = new Carriera();
		AttivitaFormativa af = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		Esame esame1 = new Esame(af, LocalDate.of(2020, 6, 6), Voto.RITIRATO);
		Esame esame2 = new Esame(af, LocalDate.of(2020, 7, 1), Voto.DICIOTTO);
		assertEquals(Collections.EMPTY_LIST, carriera.getListaEsami());
		carriera.inserisci(esame1);
		assertEquals(List.of(esame1), carriera.getListaEsami());
		assertEquals(0, carriera.creditiAcquisiti());
		carriera.inserisci(esame2);
		assertEquals(List.of(esame1,esame2), carriera.getListaEsami());
		assertEquals(12, carriera.creditiAcquisiti());
	}
	
	@Test
	void testOK_singoloEsameConVotoDatoDueVolte_PrimaVoltaRespinto() {
		carriera = new Carriera();
		AttivitaFormativa af = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		Esame esame1 = new Esame(af, LocalDate.of(2020, 6, 6), Voto.RESPINTO);
		Esame esame2 = new Esame(af, LocalDate.of(2020, 7, 1), Voto.DICIOTTO);
		assertEquals(Collections.EMPTY_LIST, carriera.getListaEsami());
		carriera.inserisci(esame1);
		assertEquals(0, carriera.creditiAcquisiti());
		assertEquals(List.of(esame1), carriera.getListaEsami());
		carriera.inserisci(esame2);
		assertEquals(List.of(esame1,esame2), carriera.getListaEsami());
		assertEquals(12, carriera.creditiAcquisiti());
	}

	@Test
	void testOK_singoloEsameConGiudizioDatoDueVolte_PrimaVoltaRespinto() {
		carriera = new Carriera();
		AttivitaFormativa af = new AttivitaFormativa(26337, "LINGUA INGLESE B-2", 6);
		Esame esame1 = new Esame(af, LocalDate.of(2020, 6, 6), Voto.RESPINTO);
		Esame esame2 = new Esame(af, LocalDate.of(2020, 7, 1), Voto.IDONEO);
		assertEquals(Collections.EMPTY_LIST, carriera.getListaEsami());
		carriera.inserisci(esame1);
		assertEquals(0, carriera.creditiAcquisiti());
		assertEquals(List.of(esame1), carriera.getListaEsami());
		carriera.inserisci(esame2);
		assertEquals(List.of(esame1,esame2), carriera.getListaEsami());
		assertEquals(6, carriera.creditiAcquisiti());
	}
	
	@Test
	void testOK_singoloEsameConGiudizioDatoDueVolte_PrimaVoltaRitirato() {
		carriera = new Carriera();
		AttivitaFormativa af = new AttivitaFormativa(26337, "LINGUA INGLESE B-2", 6);
		Esame esame1 = new Esame(af, LocalDate.of(2020, 6, 6), Voto.RITIRATO);
		Esame esame2 = new Esame(af, LocalDate.of(2020, 7, 1), Voto.IDONEO);
		assertEquals(Collections.EMPTY_LIST, carriera.getListaEsami());
		carriera.inserisci(esame1);
		assertEquals(0, carriera.creditiAcquisiti());
		assertEquals(List.of(esame1), carriera.getListaEsami());
		carriera.inserisci(esame2);
		assertEquals(List.of(esame1,esame2), carriera.getListaEsami());
		assertEquals(6, carriera.creditiAcquisiti());
	}
	
	@Test
	void testOK_singoloEsameConVotoDatoPiuVolte() {
		carriera = new Carriera();
		AttivitaFormativa af = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		Esame esame1a = new Esame(af, LocalDate.of(2020, 6, 6), Voto.RESPINTO);
		Esame esame1b = new Esame(af, LocalDate.of(2020, 6, 28), Voto.RITIRATO);
		Esame esame2 = new Esame(af, LocalDate.of(2020, 7, 1), Voto.DICIOTTO);
		assertEquals(Collections.EMPTY_LIST, carriera.getListaEsami());
		carriera.inserisci(esame1a);
		assertEquals(0, carriera.creditiAcquisiti());
		assertEquals(List.of(esame1a), carriera.getListaEsami());
		carriera.inserisci(esame1b);
		assertEquals(0, carriera.creditiAcquisiti());
		assertEquals(List.of(esame1a,esame1b), carriera.getListaEsami());
		carriera.inserisci(esame2);
		assertEquals(List.of(esame1a,esame1b,esame2), carriera.getListaEsami());
		assertEquals(12, carriera.creditiAcquisiti());
	}
	
	@Test
	void testOK_variEsamiConVoto_DueConEsitoPositivo_21CfuTot() {
		carriera = new Carriera();
		AttivitaFormativa af1 = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		Esame esame1a = new Esame(af1, LocalDate.of(2020, 6, 6), Voto.RESPINTO);
		Esame esame1b = new Esame(af1, LocalDate.of(2020, 6, 28), Voto.RITIRATO);
		Esame esame2 = new Esame(af1, LocalDate.of(2020, 7, 1), Voto.DICIOTTO);
		carriera.inserisci(esame1a);
		carriera.inserisci(esame1b);
		carriera.inserisci(esame2);
		assertEquals(12, carriera.creditiAcquisiti());
		AttivitaFormativa af2 = new AttivitaFormativa(27991, "Analisi Matematica T-1", 9);
		Esame esame3 = new Esame(af2, LocalDate.of(2020, 6, 28), Voto.RESPINTO);
		Esame esame4 = new Esame(af2, LocalDate.of(2020, 7, 1), Voto.VENTICINQUE);
		carriera.inserisci(esame3);
		carriera.inserisci(esame4);
		assertEquals(21, carriera.creditiAcquisiti());
		assertEquals(List.of(esame1a,esame3,esame1b,esame4,esame2), carriera.getListaEsami());
	}
	
	@Test
	void testOK_variEsamiConVoto_27CfuTot() {
		carriera = new Carriera();
		AttivitaFormativa af1 = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		Esame esameFondT2 = new Esame(af1, LocalDate.of(2020, 7, 1), Voto.DICIOTTO);
		carriera.inserisci(esameFondT2);
		assertEquals(12, carriera.creditiAcquisiti());
		AttivitaFormativa af2 = new AttivitaFormativa(27991, "Analisi Matematica T-1", 9);
		Esame esameAnalisi1 = new Esame(af2, LocalDate.of(2020, 7, 1), Voto.VENTICINQUE);
		carriera.inserisci(esameAnalisi1);
		assertEquals(21, carriera.creditiAcquisiti());
		AttivitaFormativa af3 = new AttivitaFormativa(27993, "Analisi Matematica T-2", 6);
		Esame esameAnalisi2 = new Esame(af3, LocalDate.of(2020, 7, 1), Voto.TRENTAELODE);
		carriera.inserisci(esameAnalisi2);
		assertEquals(27, carriera.creditiAcquisiti());
		assertEquals(List.of(esameAnalisi1,esameAnalisi2,esameFondT2), carriera.getListaEsami());
	}
	
	@Test
	void testOK_variEsamiConVoto_27CfuTot_OrdineInverso() {
		carriera = new Carriera();
		AttivitaFormativa af1 = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		Esame esameFondT2 = new Esame(af1, LocalDate.of(2020, 7, 1), Voto.DICIOTTO);
		carriera.inserisci(esameFondT2);
		assertEquals(12, carriera.creditiAcquisiti());
		AttivitaFormativa af2 = new AttivitaFormativa(27993, "Analisi Matematica T-2", 6);
		Esame esameAnalisi2 = new Esame(af2, LocalDate.of(2020, 7, 1), Voto.VENTICINQUE);
		carriera.inserisci(esameAnalisi2);
		assertEquals(18, carriera.creditiAcquisiti());
		AttivitaFormativa af3 = new AttivitaFormativa(27991, "Analisi Matematica T-1", 9);
		Esame esameAnalisi1 = new Esame(af3, LocalDate.of(2020, 7, 1), Voto.TRENTAELODE);
		carriera.inserisci(esameAnalisi1);
		assertEquals(27, carriera.creditiAcquisiti());
		assertEquals(List.of(esameAnalisi1,esameAnalisi2,esameFondT2), carriera.getListaEsami());
	}
	
	@Test
	void testOK_variEsamiConVotoOGiudizio_33CfuTot_OrdineInverso() {
		carriera = new Carriera();
		AttivitaFormativa af1 = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		Esame esameFondT2 = new Esame(af1, LocalDate.of(2020, 7, 1), Voto.DICIOTTO);
		carriera.inserisci(esameFondT2);
		assertEquals(12, carriera.creditiAcquisiti());
		AttivitaFormativa af2 = new AttivitaFormativa(27993, "Analisi Matematica T-2", 6);
		Esame esameAnalisi2 = new Esame(af2, LocalDate.of(2020, 7, 1), Voto.VENTICINQUE);
		carriera.inserisci(esameAnalisi2);
		assertEquals(18, carriera.creditiAcquisiti());
		AttivitaFormativa af3 = new AttivitaFormativa(27991, "Analisi Matematica T-1", 9);
		Esame esameAnalisi1 = new Esame(af3, LocalDate.of(2020, 7, 1), Voto.TRENTAELODE);
		carriera.inserisci(esameAnalisi1);
		assertEquals(27, carriera.creditiAcquisiti());
		assertEquals(List.of(esameAnalisi1,esameAnalisi2,esameFondT2), carriera.getListaEsami());
		AttivitaFormativa af4 = new AttivitaFormativa(26337, "LINGUA INGLESE B-2", 6);
		Esame esameInglese = new Esame(af4, LocalDate.of(2020, 7, 2), Voto.IDONEO);
		carriera.inserisci(esameInglese);
		assertEquals(33, carriera.creditiAcquisiti());
		assertEquals(List.of(esameAnalisi1,esameAnalisi2,esameFondT2,esameInglese), carriera.getListaEsami());		
	}
	
	@Test
	void testOK_variEsamiConVotoOGiudizio_ProvaFinalePerUltima_30CfuTot() {
		carriera = new Carriera();
		AttivitaFormativa af1 = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		Esame esameFondT2 = new Esame(af1, LocalDate.of(2020, 7, 1), Voto.DICIOTTO);
		carriera.inserisci(esameFondT2);
		assertEquals(12, carriera.creditiAcquisiti());
		AttivitaFormativa af2 = new AttivitaFormativa(27993, "Analisi Matematica T-2", 6);
		Esame esameAnalisi2 = new Esame(af2, LocalDate.of(2020, 7, 1), Voto.VENTICINQUE);
		carriera.inserisci(esameAnalisi2);
		assertEquals(18, carriera.creditiAcquisiti());
		AttivitaFormativa af3 = new AttivitaFormativa(27991, "Analisi Matematica T-1", 9);
		Esame esameAnalisi1 = new Esame(af3, LocalDate.of(2020, 7, 1), Voto.TRENTAELODE);
		carriera.inserisci(esameAnalisi1);
		assertEquals(27, carriera.creditiAcquisiti());
		assertEquals(List.of(esameAnalisi1,esameAnalisi2,esameFondT2), carriera.getListaEsami());
		AttivitaFormativa af4 = new AttivitaFormativa(26337, "PROVA FINALE", 3);
		Esame provaFinale = new Esame(af4, LocalDate.of(2020, 7, 2), Voto.IDONEO);
		carriera.inserisci(provaFinale);
		assertEquals(30, carriera.creditiAcquisiti());
		assertEquals(List.of(esameAnalisi1,esameAnalisi2,esameFondT2,provaFinale), carriera.getListaEsami());		
	}

	@Test
	void testOK_MediaPesata_VariEsamiConVoto_27CfuTot_OrdineInverso() {
		carriera = new Carriera();
		AttivitaFormativa af1 = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		Esame esameFondT2 = new Esame(af1, LocalDate.of(2020, 7, 1), Voto.DICIOTTO);
		carriera.inserisci(esameFondT2);
		AttivitaFormativa af2 = new AttivitaFormativa(27993, "Analisi Matematica T-2", 6);
		Esame esameAnalisi2 = new Esame(af2, LocalDate.of(2020, 7, 1), Voto.VENTICINQUE);
		carriera.inserisci(esameAnalisi2);
		AttivitaFormativa af3 = new AttivitaFormativa(27991, "Analisi Matematica T-1", 9);
		Esame esameAnalisi1 = new Esame(af3, LocalDate.of(2020, 7, 1), Voto.TRENTAELODE);
		carriera.inserisci(esameAnalisi1);
		assertEquals(23.56, carriera.mediaPesata(), 0.01);
	}
	
	@Test
	void testOK_MediaPesata_VariEsamiConVotoOGiudizio_33CfuTot_OrdineInverso() {
		carriera = new Carriera();
		AttivitaFormativa af1 = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		Esame esameFondT2 = new Esame(af1, LocalDate.of(2020, 7, 1), Voto.DICIOTTO);
		carriera.inserisci(esameFondT2);
		AttivitaFormativa af2 = new AttivitaFormativa(27993, "Analisi Matematica T-2", 6);
		Esame esameAnalisi2 = new Esame(af2, LocalDate.of(2020, 7, 1), Voto.VENTICINQUE);
		carriera.inserisci(esameAnalisi2);
		AttivitaFormativa af3 = new AttivitaFormativa(27991, "Analisi Matematica T-1", 9);
		Esame esameAnalisi1 = new Esame(af3, LocalDate.of(2020, 7, 1), Voto.TRENTAELODE);
		carriera.inserisci(esameAnalisi1);
		assertEquals(23.56, carriera.mediaPesata(), 0.01);
		AttivitaFormativa af4 = new AttivitaFormativa(26337, "LINGUA INGLESE B-2", 6);
		Esame esameInglese = new Esame(af4, LocalDate.of(2020, 7, 2), Voto.IDONEO);
		carriera.inserisci(esameInglese);
		assertEquals(23.56, carriera.mediaPesata(), 0.01);
	}
		
	@Test
	void testOK_MediaPesata_VariEsamiConVoto_27CfuTot() {
		carriera = new Carriera();
		AttivitaFormativa af1 = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		Esame esameFondT2 = new Esame(af1, LocalDate.of(2020, 7, 1), Voto.DICIOTTO);
		carriera.inserisci(esameFondT2);
		AttivitaFormativa af2 = new AttivitaFormativa(27991, "Analisi Matematica T-1", 9);
		Esame esameAnalisi1 = new Esame(af2, LocalDate.of(2020, 7, 1), Voto.VENTICINQUE);
		carriera.inserisci(esameAnalisi1);
		AttivitaFormativa af3 = new AttivitaFormativa(27993, "Analisi Matematica T-2", 6);
		Esame esameAnalisi2 = new Esame(af3, LocalDate.of(2020, 7, 1), Voto.TRENTAELODE);
		carriera.inserisci(esameAnalisi2);
		assertEquals(23.0, carriera.mediaPesata(), 0.01);
	}

	
	@Test
	void testOK_MediaPesata_VariEsamiConVoto_DueConEsitoPositivo_21CfuTot() {
		carriera = new Carriera();
		AttivitaFormativa af1 = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		Esame esame1a = new Esame(af1, LocalDate.of(2020, 6, 6), Voto.RESPINTO);
		Esame esame1b = new Esame(af1, LocalDate.of(2020, 6, 28), Voto.RITIRATO);
		Esame esame2 = new Esame(af1, LocalDate.of(2020, 7, 1), Voto.DICIOTTO);
		carriera.inserisci(esame1a);
		carriera.inserisci(esame1b);
		carriera.inserisci(esame2);
		assertEquals(12, carriera.creditiAcquisiti());
		AttivitaFormativa af2 = new AttivitaFormativa(27991, "Analisi Matematica T-1", 9);
		Esame esame3 = new Esame(af2, LocalDate.of(2020, 6, 28), Voto.RESPINTO);
		Esame esame4 = new Esame(af2, LocalDate.of(2020, 7, 1), Voto.VENTICINQUE);
		carriera.inserisci(esame3);
		carriera.inserisci(esame4);
		assertEquals(21, carriera.creditiAcquisiti());
		assertEquals(List.of(esame1a,esame3,esame1b,esame4,esame2), carriera.getListaEsami());
		assertEquals(21.0, carriera.mediaPesata(), 0.01);
	}
	
	@Test
	void testOK_MediaPesata_SingoloEsameConVotoDatoPiuVolte() {
		carriera = new Carriera();
		AttivitaFormativa af = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		Esame esame1a = new Esame(af, LocalDate.of(2020, 6, 6), Voto.RESPINTO);
		Esame esame1b = new Esame(af, LocalDate.of(2020, 6, 28), Voto.RITIRATO);
		Esame esame2 = new Esame(af, LocalDate.of(2020, 7, 1), Voto.DICIOTTO);
		carriera.inserisci(esame1a);
		assertEquals(0, carriera.creditiAcquisiti());
		assertEquals(List.of(esame1a), carriera.getListaEsami());
		carriera.inserisci(esame1b);
		assertEquals(0, carriera.creditiAcquisiti());
		carriera.inserisci(esame2);
		assertEquals(12, carriera.creditiAcquisiti());
		assertEquals(18.0, carriera.mediaPesata(), 0.01);
	}
	
	@Test
	void testOK_MediaPesata_NessunEsameConVotoSuperato() {
		carriera = new Carriera();
		AttivitaFormativa af = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		Esame esame1a = new Esame(af, LocalDate.of(2020, 6, 6), Voto.RESPINTO);
		Esame esame1b = new Esame(af, LocalDate.of(2020, 6, 28), Voto.RITIRATO);
		carriera.inserisci(esame1a);
		carriera.inserisci(esame1b);
		assertEquals(Double.NaN, carriera.mediaPesata(), 0.01);
		// NB: in JUnit, gli NaN sono considerati uguali fra loro, difformemente dal classico == fra Double 
	}
	
	@Test
	void testKO_DueDateUgualiConVotoNonNumerico() {
		carriera = new Carriera();
		AttivitaFormativa af = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		Esame esame1a = new Esame(af, LocalDate.of(2020, 6, 6), Voto.RESPINTO);
		Esame esame1b = new Esame(af, LocalDate.of(2020, 6, 6), Voto.RITIRATO);
		carriera.inserisci(esame1a);
		assertThrows(IllegalArgumentException.class, () -> carriera.inserisci(esame1b));
	}
	
	@Test
	void testKO_DueDateUgualiConVotoNumerico() {
		carriera = new Carriera();
		AttivitaFormativa af = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		Esame esame1a = new Esame(af, LocalDate.of(2020, 6, 6), Voto.RESPINTO);
		Esame esame1b = new Esame(af, LocalDate.of(2020, 6, 6), Voto.VENTI);
		carriera.inserisci(esame1a);
		assertThrows(IllegalArgumentException.class, () -> carriera.inserisci(esame1b));
	}

	@Test
	void testKO_EsameGiaSuperato_Respinto() {
		carriera = new Carriera();
		AttivitaFormativa af = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		Esame esame1a = new Esame(af, LocalDate.of(2020, 6, 6), Voto.VENTI);
		Esame esame1b = new Esame(af, LocalDate.of(2020, 6, 28), Voto.RESPINTO);
		carriera.inserisci(esame1a);
		assertThrows(IllegalArgumentException.class, () -> carriera.inserisci(esame1b));
	}
	
	@Test
	void testKO_EsameGiaSuperato_Ritirato() {
		carriera = new Carriera();
		AttivitaFormativa af = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		Esame esame1a = new Esame(af, LocalDate.of(2020, 6, 6), Voto.VENTI);
		Esame esame1b = new Esame(af, LocalDate.of(2020, 6, 28), Voto.RITIRATO);
		carriera.inserisci(esame1a);
		assertThrows(IllegalArgumentException.class, () -> carriera.inserisci(esame1b));
	}
	
	@Test
	void testKO_EsameGiaSuperato_Idoneo() {
		carriera = new Carriera();
		AttivitaFormativa af = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		Esame esame1a = new Esame(af, LocalDate.of(2020, 6, 6), Voto.VENTI);
		Esame esame1b = new Esame(af, LocalDate.of(2020, 6, 28), Voto.IDONEO);
		carriera.inserisci(esame1a);
		assertThrows(IllegalArgumentException.class, () -> carriera.inserisci(esame1b));
	}	
	@Test
	void testKO_EsameNullo() {
		carriera = new Carriera();
		assertThrows(IllegalArgumentException.class, () -> carriera.inserisci(null));
	}


	@Test
	void testKO_variEsamiConVotoOGiudizio_ProvaFinaleNONPerUltima() {
		carriera = new Carriera();
		AttivitaFormativa af1 = new AttivitaFormativa(28006, "Fondamenti di Informatica T-2", 12);
		Esame esameFondT2 = new Esame(af1, LocalDate.of(2020, 7, 1), Voto.DICIOTTO);
		carriera.inserisci(esameFondT2);
		assertEquals(12, carriera.creditiAcquisiti());
		AttivitaFormativa af2 = new AttivitaFormativa(27993, "Analisi Matematica T-2", 6);
		Esame esameAnalisi2 = new Esame(af2, LocalDate.of(2020, 7, 1), Voto.VENTICINQUE);
		carriera.inserisci(esameAnalisi2);
		assertEquals(18, carriera.creditiAcquisiti());
		AttivitaFormativa af3 = new AttivitaFormativa(27991, "Analisi Matematica T-1", 9);
		Esame esameAnalisi1 = new Esame(af3, LocalDate.of(2020, 7, 1), Voto.TRENTAELODE);
		carriera.inserisci(esameAnalisi1);
		assertEquals(27, carriera.creditiAcquisiti());
		assertEquals(List.of(esameAnalisi1,esameAnalisi2,esameFondT2), carriera.getListaEsami());
		
		AttivitaFormativa af4 = new AttivitaFormativa(26337, "PROVA FINALE", 3);
		Esame provaFinale = new Esame(af4, LocalDate.of(2020, 6, 20), Voto.IDONEO);
		assertThrows(IllegalArgumentException.class, () -> carriera.inserisci(provaFinale));	
	}
}
