package mediaesami.persistence.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.jupiter.api.Test;

import mediaesami.model.Carriera;
import mediaesami.model.Esame;
import mediaesami.persistence.BadFileFormatException;
import mediaesami.persistence.MyCarrieraReader;

public class MyCarrieraReaderTest {

	@Test
	void testOK1() throws IOException {
		StringReader fakeReader = new StringReader(
				        "27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	RT\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
				      + "29228	GEOMETRIA E ALGEBRA T				6,0		18/1/2020	26\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0\r\n"
				      + "28011	RETI LOGICHE T						6,0"
				);	
		Carriera carriera = new MyCarrieraReader().leggiCarriera(fakeReader);
		
		List<Esame> listaEsami = carriera.getListaEsami();
		assertEquals(6, listaEsami.size());
		assertEquals(27, carriera.creditiAcquisiti(), 0.01);
		assertEquals(23.78, carriera.mediaPesata(), 0.01);
	}
	
	@Test
	void testOK2() throws IOException {
		StringReader fakeReader = new StringReader(
				        "27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	RT\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
				      + "29228	GEOMETRIA E ALGEBRA T				6,0		18/1/2020	26\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		22/7/2020	23\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0	21/7/2020	27\r\n"
				      + "28011	RETI LOGICHE T						6,0"
				);	
		Carriera carriera = new MyCarrieraReader().leggiCarriera(fakeReader);
		
		List<Esame> listaEsami = carriera.getListaEsami();
		assertEquals(8, listaEsami.size());
		assertEquals(45, carriera.creditiAcquisiti(), 0.01);
		assertEquals(24.53, carriera.mediaPesata(), 0.01);
	}

	@Test
	void testOK5() throws IOException {
		StringReader fakeReader = new StringReader(
				        "27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	RT\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
				      + "29228	GEOMETRIA E ALGEBRA T				6,0		18/1/2020	26\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0		18/6/2020	ID\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		22/7/2020	23\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0	21/7/2020	27\r\n"
				      + "28011	RETI LOGICHE T						6,0		22/2/2020	22\r\n"
				      + "\r\n"
				      + "28012	CALCOLATORI ELETTRONICI T			6,0		22/1/2021	RT\r\n"
				      + "28012	CALCOLATORI ELETTRONICI T			6,0		22/2/2021	20\r\n"
				      + "30780	FISICA GENERALE T					9,0		12/2/2021	25\r\n"
				      + "28032	MATEMATICA APPLICATA T				6,0		02/2/2021	24\r\n"
				      + "28027	SISTEMI INFORMATIVI T				9,0		03/6/2021	28\r\n"
				      + "28030	ECONOMIA E ORGANIZZAZIONE AZIENDALE T	6,0		02/7/2021	RE\r\n"
				      + "28030	ECONOMIA E ORGANIZZAZIONE AZIENDALE T	6,0		22/7/2021	24\r\n"
				      + "28029	ELETTROTECNICA T					6,0		02/9/2021	26\r\n"
				      + "28014	FONDAMENTI DI TELECOMUNICAZIONI T	9,0		15/9/2021	30\r\n"
				      + "28020	SISTEMI OPERATIVI T					9,0		12/1/2022	24\r\n"
				      + "\r\n"
				      + "28015	CONTROLLI AUTOMATICI T				9,0		13/1/2021	25\r\n"
				      + "28016	ELETTRONICA T						6,0		10/2/2021	22\r\n"
				      + "28024	RETI DI CALCOLATORI T				9,0		05/2/2021	23\r\n"
				      + "28659	TECNOLOGIE WEB T					9,0		12/6/2021	25\r\n"
				      + "28021	INGEGNERIA DEL SOFTWARE T			9,0		24/6/2021	27\r\n"
				      + "17268	PROVA FINALE						3,0		07/3/2022	ID\r\n"
				      + "\r\n"
				      + "28074	TIROCINIO T							6,0		27/9/2021	ID\r\n"
				      + "\r\n"
				      + "88324	AMMINISTRAZIONE DI SISTEMI T		6,0		13/7/2021	29\r\n"
				      + "88325	LABORATORIO DI SICUREZZA INFORMATICA T	6,0		7/6/2021	30L"
				);	
		Carriera carriera = new MyCarrieraReader().leggiCarriera(fakeReader);
		
		List<Esame> listaEsami = carriera.getListaEsami();
		assertEquals(29, listaEsami.size());
		assertEquals(180, carriera.creditiAcquisiti(), 0.01);
		assertEquals(25.15, carriera.mediaPesata(), 0.01);
	}

	@Test
	void testKO_ProvaFinalePrimaDiUnAltroEsame() throws IOException {
		StringReader fakeReader = new StringReader(
		        "27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	RT\r\n"
		      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
		      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
		      + "29228	GEOMETRIA E ALGEBRA T				6,0		18/1/2020	26\r\n"
		      + "26337	LINGUA INGLESE B-2					6,0		18/6/2020	ID\r\n"
		      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
		      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
		      + "27993	ANALISI MATEMATICA T-2				6,0		22/7/2020	23\r\n"
		      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0	21/7/2020	27\r\n"
		      + "28011	RETI LOGICHE T						6,0		22/2/2020	22\r\n"
		      + "\r\n"
		      + "28012	CALCOLATORI ELETTRONICI T			6,0		22/1/2021	RT\r\n"
		      + "28012	CALCOLATORI ELETTRONICI T			6,0		22/2/2021	20\r\n"
		      + "30780	FISICA GENERALE T					9,0		12/2/2021	25\r\n"
		      + "28032	MATEMATICA APPLICATA T				6,0		02/2/2021	24\r\n"
		      + "28027	SISTEMI INFORMATIVI T				9,0		03/6/2021	28\r\n"
		      + "28030	ECONOMIA E ORGANIZZAZIONE AZIENDALE T	6,0		02/7/2021	RE\r\n"
		      + "28030	ECONOMIA E ORGANIZZAZIONE AZIENDALE T	6,0		22/7/2021	24\r\n"
		      + "28029	ELETTROTECNICA T					6,0		02/9/2021	26\r\n"
		      + "28014	FONDAMENTI DI TELECOMUNICAZIONI T	9,0		15/9/2021	30\r\n"
		      + "28020	SISTEMI OPERATIVI T					9,0		12/1/2022	24\r\n"
		      + "\r\n"
		      + "28015	CONTROLLI AUTOMATICI T				9,0		13/1/2021	25\r\n"
		      + "28016	ELETTRONICA T						6,0		10/2/2021	22\r\n"
		      + "28024	RETI DI CALCOLATORI T				9,0		05/2/2021	23\r\n"
		      + "28659	TECNOLOGIE WEB T					9,0		12/6/2021	25\r\n"
		      + "28021	INGEGNERIA DEL SOFTWARE T			9,0		24/6/2021	27\r\n"
		      + "17268	PROVA FINALE						3,0		26/9/2021	ID\r\n"
		      + "\r\n"
		      + "28074	TIROCINIO T							6,0		27/9/2021	ID\r\n"
		      + "\r\n"
		      + "88324	AMMINISTRAZIONE DI SISTEMI T		6,0		13/7/2021	29\r\n"
		      + "88325	LABORATORIO DI SICUREZZA INFORMATICA T	6,0		7/6/2021	30L"
				);	
		assertThrows(BadFileFormatException.class, () -> new MyCarrieraReader().leggiCarriera(fakeReader));
	}

	@Test
	void testKO_readerNull() throws IOException {
		assertThrows(IllegalArgumentException.class, () -> new MyCarrieraReader().leggiCarriera(null));
	}

	@Test
	void testKO_MissingIdInPrimaRiga() throws IOException {
		StringReader fakeReader = new StringReader(
				        "		ANALISI MATEMATICA T-1				9,0		12/1/2020	RT\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
				      + "29228	GEOMETRIA E ALGEBRA T				6,0		18/1/2020	26\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0\r\n"
				      + "28011	RETI LOGICHE T						6,0"
				);	
		assertThrows(BadFileFormatException.class, () -> new MyCarrieraReader().leggiCarriera(fakeReader));	
	}
	
	@Test
	void testKO_MissingIdInRigheSuccessive() throws IOException {
		StringReader fakeReader = new StringReader(
				        "27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	RT\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
				      + "		GEOMETRIA E ALGEBRA T				6,0		18/1/2020	26\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0\r\n"
				      + "28011	RETI LOGICHE T						6,0"
				);	
		assertThrows(BadFileFormatException.class, () -> new MyCarrieraReader().leggiCarriera(fakeReader));	
	}
	
	@Test
	void testKO_MissingNomeAttivitàInPrimaRiga() throws IOException {
		StringReader fakeReader = new StringReader(
				        "27991										9,0		12/1/2020	RT\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
				      + "29228	GEOMETRIA E ALGEBRA T				6,0		18/1/2020	26\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0\r\n"
				      + "28011	RETI LOGICHE T						6,0"
				);	
		assertThrows(BadFileFormatException.class, () -> new MyCarrieraReader().leggiCarriera(fakeReader));	
	}

	@Test
	void testKO_MissingNomeAttivitàInAltraRiga() throws IOException {
		StringReader fakeReader = new StringReader(
				        "27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	RT\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
				      + "29228	 				6,0		18/1/2020	26\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0\r\n"
				      + "28011	RETI LOGICHE T						6,0"
				);	
		assertThrows(BadFileFormatException.class, () -> new MyCarrieraReader().leggiCarriera(fakeReader));	
	}
	
	@Test
	void testKO_MissingCfuInPrimaRiga() throws IOException {
		StringReader fakeReader = new StringReader(
				        "27991	ANALISI MATEMATICA T-1				12/1/2020	RT\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
				      + "29228	GEOMETRIA E ALGEBRA T				6,0		18/1/2020	26\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0\r\n"
				      + "28011	RETI LOGICHE T						6,0"
				);	
		assertThrows(BadFileFormatException.class, () -> new MyCarrieraReader().leggiCarriera(fakeReader));	
	}
	
	@Test
	void testKO_MissingCfuInAltraRiga() throws IOException {
		StringReader fakeReader = new StringReader(
				        "27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	RT\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
				      + "29228	GEOMETRIA E ALGEBRA T						18/1/2020	26\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0\r\n"
				      + "28011	RETI LOGICHE T						6,0"
				);	
		assertThrows(BadFileFormatException.class, () -> new MyCarrieraReader().leggiCarriera(fakeReader));	
	}
	
	@Test
	void testKO_CfuwithDecimalPointInAltraRiga() throws IOException {
		StringReader fakeReader = new StringReader(
				        "27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	RT\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
				      + "29228	GEOMETRIA E ALGEBRA T				6.0		18/1/2020	26\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0\r\n"
				      + "28011	RETI LOGICHE T						6,0"
				);	
		assertThrows(BadFileFormatException.class, () -> new MyCarrieraReader().leggiCarriera(fakeReader));	
	}

	@Test
	void testKO_IdNonNumericalInAltraRiga() throws IOException {
		StringReader fakeReader = new StringReader(
				        "27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	RT\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28A28	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
				      + "29228	GEOMETRIA E ALGEBRA T				6,0		18/1/2020	26\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0\r\n"
				      + "28011	RETI LOGICHE T						6,0"
				);	
		assertThrows(BadFileFormatException.class, () -> new MyCarrieraReader().leggiCarriera(fakeReader));	
	}

	@Test
	void testKO_WrongCfuInAltraRiga() throws IOException {
		StringReader fakeReader = new StringReader(
				        "27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	RT\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
				      + "29228	GEOMETRIA E ALGEBRA T				x,0		18/1/2020	26\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0\r\n"
				      + "28011	RETI LOGICHE T						6,0"
				);	
		assertThrows(BadFileFormatException.class, () -> new MyCarrieraReader().leggiCarriera(fakeReader));	
	}
	
	@Test
	void testKO_DataConAnnoSuDueCifreInAltraRiga() throws IOException {
		StringReader fakeReader = new StringReader(
				        "27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	RT\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
				      + "29228	GEOMETRIA E ALGEBRA T				6,0		18/1/20	26\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0\r\n"
				      + "28011	RETI LOGICHE T						6,0"
				);	
		assertThrows(BadFileFormatException.class, () -> new MyCarrieraReader().leggiCarriera(fakeReader));	
	}

	@Test
	void testKO_DataIllegaleInAltraRiga() throws IOException {
		StringReader fakeReader = new StringReader(
				        "27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	RT\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
				      + "29228	GEOMETRIA E ALGEBRA T				6,0		18/13/2020	26\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0\r\n"
				      + "28011	RETI LOGICHE T						6,0"
				);	
		assertThrows(BadFileFormatException.class, () -> new MyCarrieraReader().leggiCarriera(fakeReader));	
	}

	@Test
	void testKO_DataNegativaInAltraRiga() throws IOException {
		StringReader fakeReader = new StringReader(
				        "27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	RT\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
				      + "29228	GEOMETRIA E ALGEBRA T				6,0		-18/3/2020	26\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0\r\n"
				      + "28011	RETI LOGICHE T						6,0"
				);	
		assertThrows(BadFileFormatException.class, () -> new MyCarrieraReader().leggiCarriera(fakeReader));	
	}
	
	@Test
	void testKO_VotoMinoreDi18InAltraRiga() throws IOException {
		StringReader fakeReader = new StringReader(
				        "27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	RT\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
				      + "29228	GEOMETRIA E ALGEBRA T				6,0		18/3/2020	16\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0\r\n"
				      + "28011	RETI LOGICHE T						6,0"
				);	
		assertThrows(BadFileFormatException.class, () -> new MyCarrieraReader().leggiCarriera(fakeReader));	
	}
	
	@Test
	void testKO_VotoNegativoInAltraRiga() throws IOException {
		StringReader fakeReader = new StringReader(
				        "27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	RT\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
				      + "29228	GEOMETRIA E ALGEBRA T				6,0		18/3/2020	-19\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0\r\n"
				      + "28011	RETI LOGICHE T						6,0"
				);	
		assertThrows(BadFileFormatException.class, () -> new MyCarrieraReader().leggiCarriera(fakeReader));	
	}

	@Test
	void testKO_VotoZeroInAltraRiga() throws IOException {
		StringReader fakeReader = new StringReader(
				        "27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	RT\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
				      + "29228	GEOMETRIA E ALGEBRA T				6,0		18/3/2020	0\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0\r\n"
				      + "28011	RETI LOGICHE T						6,0"
				);	
		assertThrows(BadFileFormatException.class, () -> new MyCarrieraReader().leggiCarriera(fakeReader));	
	}
	
	@Test
	void testKO_VotoOltre30InAltraRiga() throws IOException {
		StringReader fakeReader = new StringReader(
				        "27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	RT\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
				      + "29228	GEOMETRIA E ALGEBRA T				6,0		18/3/2020	40\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0\r\n"
				      + "28011	RETI LOGICHE T						6,0"
				);	
		assertThrows(BadFileFormatException.class, () -> new MyCarrieraReader().leggiCarriera(fakeReader));	
	}
	
	@Test
	void testKO_VotoConLodeNon30InAltraRiga() throws IOException {
		StringReader fakeReader = new StringReader(
				        "27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	RT\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24L\r\n"
				      + "29228	GEOMETRIA E ALGEBRA T				6,0		18/3/2020	20\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0\r\n"
				      + "28011	RETI LOGICHE T						6,0"
				);	
		assertThrows(BadFileFormatException.class, () -> new MyCarrieraReader().leggiCarriera(fakeReader));	
	}
	
	@Test
	void testKO_VotoRtErratoInAltraRiga() throws IOException {
		StringReader fakeReader = new StringReader(
				        "27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	RIT\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
				      + "29228	GEOMETRIA E ALGEBRA T				6,0		18/3/2020	20\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0\r\n"
				      + "28011	RETI LOGICHE T						6,0"
				);	
		assertThrows(BadFileFormatException.class, () -> new MyCarrieraReader().leggiCarriera(fakeReader));	
	}

	@Test
	void testKO_VotoRtErrato2InAltraRiga() throws IOException {
		StringReader fakeReader = new StringReader(
				        "27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	R T\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
				      + "29228	GEOMETRIA E ALGEBRA T				6,0		18/3/2020	20\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0\r\n"
				      + "28011	RETI LOGICHE T						6,0"
				);	
		assertThrows(BadFileFormatException.class, () -> new MyCarrieraReader().leggiCarriera(fakeReader));	
	}

	@Test
	void testKO_VotoReErratoInAltraRiga() throws IOException {
		StringReader fakeReader = new StringReader(
				        "27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	RT\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
				      + "29228	GEOMETRIA E ALGEBRA T				6,0		18/3/2020	20\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	xE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0\r\n"
				      + "28011	RETI LOGICHE T						6,0"
				);	
		assertThrows(BadFileFormatException.class, () -> new MyCarrieraReader().leggiCarriera(fakeReader));	
	}
	
	@Test
	void testKO_VotoIdErratoInAltraRiga() throws IOException {
		StringReader fakeReader = new StringReader(
				        "27991	ANALISI MATEMATICA T-1				9,0		12/1/2020	RT\r\n"
				      + "27991	ANALISI MATEMATICA T-1				9,0		10/2/2020	22\r\n"
				      + "28004	FONDAMENTI DI INFORMATICA T-1		12,0	13/2/2020	24\r\n"
				      + "29228	GEOMETRIA E ALGEBRA T				6,0		18/3/2020	20\r\n"
				      + "26337	LINGUA INGLESE B-2					6,0		18/6/2020	IDONEO\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		10/6/2020	RE\r\n"
				      + "27993	ANALISI MATEMATICA T-2				6,0		02/7/2020	RT\r\n"
				      + "28006	FONDAMENTI DI INFORMATICA T-2		12,0\r\n"
				      + "28011	RETI LOGICHE T						6,0"
				);	
		assertThrows(BadFileFormatException.class, () -> new MyCarrieraReader().leggiCarriera(fakeReader));	
	}

}
