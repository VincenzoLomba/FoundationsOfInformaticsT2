package bussy.persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import bussy.model.Fermata;
import bussy.model.Linea;
import bussy.model.LineaPaP;

public class MyTransportLinesReader implements TransportLinesReader {

	/* Formato del file:
	Linea 32
	0,  40, Porta San Mamolo 
	3,  42, Aldini
	...	*/

	@Override
	public Map<String,Linea> readTransportLines(BufferedReader reader) throws IOException, BadFileFormatException {
		Map<String,Linea> result = new HashMap<>();
		Linea linea;
		while((linea=this.readTransportLine(reader)) != null){
			result.put(linea.getId(), linea);
		}
		return result;
	}

	private Linea readTransportLine(BufferedReader reader) throws IOException, BadFileFormatException {
		int time = -1; 
		String idLinea, idFermata, nomeFermata;
		Map<Integer,Fermata> mappaFermate = new HashMap<>();
		String line=reader.readLine(); // should be "Linea ID" (or null if EOF)
		if (line==null) return null; // EOF
		// validazione ed estrazione header
		String[] parti = line.split(" ");
		if (parti.length!=2) throw new BadFileFormatException("Formato errato nella riga di intestazione: " + line);
		if (!parti[0].trim().equals("Linea")) throw new BadFileFormatException("Manca la keyword 'Linea' nell'intestazione: " + line);
		if (parti[1].trim().isEmpty()) throw new BadFileFormatException("Manca ID linea nell'intestazione: " + line);
		else idLinea = parti[1].trim();
		// ciclo estrazione fermate
		while((line=reader.readLine()) != null && !line.startsWith("--")){
			parti = line.split(",");
			if (parti.length!=3) throw new BadFileFormatException("Formato errato nella linea: " + line);
			try	{
				time = Integer.parseInt(parti[0].trim()); 
			} catch (NumberFormatException e0) {
				throw new BadFileFormatException("Manca progressiva temporale nella linea: " + line);
			}
			idFermata = parti[1].trim(); 
			nomeFermata = parti[2].trim();
			try {
				mappaFermate.put(time, new Fermata(idFermata, nomeFermata));
			}
			catch(IllegalArgumentException e) {
				throw new BadFileFormatException("Dati errati in descrizione fermata: " + idFermata + ", " + nomeFermata);
			}
		}
		if (mappaFermate.size()<2) throw new BadFileFormatException("Non ci sono almeno due fermate: " + mappaFermate);
		boolean circolare = mappaFermate.get(0).getNome().equals(mappaFermate.get(time).getNome());
		return circolare ? new LineaPaP(idLinea, mappaFermate) : new LineaPaP(idLinea, mappaFermate); 
	}

}
