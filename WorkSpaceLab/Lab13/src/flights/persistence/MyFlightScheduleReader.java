package flights.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import flights.model.Aircraft;
import flights.model.Airport;
import flights.model.FlightSchedule;

public class MyFlightScheduleReader implements FlightScheduleReader{

	public static final String SEPARATOR = ",";
	
	@Override
	public Collection<FlightSchedule> read (Reader reader, Map<String, Airport> airportMap, Map<String, Aircraft> aircraftMap) throws IOException, BadFileFormatException {
		
		BufferedReader br = new BufferedReader(reader);
		String line = null;
		Collection<FlightSchedule> response = new ArrayList<FlightSchedule>();
		while ((line = br.readLine()) != null) {
			if (line.trim().isBlank()) continue;
			StringTokenizer tokenizer = new StringTokenizer(line, ",");
			if (tokenizer.countTokens() != 8)
				bedFileFormatException("ogni riga deve presentare 8 elementi distinti separati mediante \"" + SEPARATOR + "\".");
			String code = tokenizer.nextToken().trim();
			if (code.length() == 0 || code.length() > 6)
				bedFileFormatException("l'identificatore di volo deve essere presente e di massimo 6 caratteri.");
			Airport departureAirport = getAirport(tokenizer, airportMap);
			LocalTime departureLocalTime = getLocalTime(tokenizer);
			Airport arrivalAirport = getAirport(tokenizer, airportMap);
			LocalTime arrivalLocalTime = getLocalTime(tokenizer);
			Integer dayOffset = null;
			try {
				dayOffset = Integer.parseInt(tokenizer.nextToken().trim());
				if (dayOffset < -1 || dayOffset > 1)
					bedFileFormatException("il parametro \"dayOffset\" puo' valere solamente -1, 0 oppure 1.");
			} catch (NumberFormatException e) { bedFileFormatException("il parametro \"dayOffset\" risulta non numerico intero."); }
			String eff = tokenizer.nextToken().trim();
			if (eff.length() != 7 || !eff.chars().allMatch(c -> "-1234567".contains("" + ((char) c))))
				bedFileFormatException("la stringa che indica i giorni di effettuazione deve essere di 7 caratteri che possono essere solamente \"-\" oppure numeri da 1 a 7.");
			ArrayList<DayOfWeek> daysOfWeekList = new ArrayList<DayOfWeek>(eff.chars().filter(c -> ((char) c) != '-').mapToObj(c -> DayOfWeek.of(Integer.parseInt("" + ((char) c)))).toList());
			Set<DayOfWeek> daysOfWeek = new TreeSet<DayOfWeek>(daysOfWeekList);
			if (!daysOfWeekList.equals(new ArrayList<DayOfWeek>(daysOfWeek)))
				bedFileFormatException("la stringa che indica i giorni di effettuazione deve presentare elementi numerici non ripetuti e ordinati per ordine crescente.");
			Aircraft aircraft = getAircraft(tokenizer, aircraftMap);
			response.add(new FlightSchedule(code, departureAirport, departureLocalTime, arrivalAirport, arrivalLocalTime, dayOffset, daysOfWeek, aircraft));
		}
		return response;
	}
	
	private Aircraft getAircraft (StringTokenizer tokenizer, Map<String, Aircraft> aircraftMap) throws BadFileFormatException {
		
		String aircraftIdentifier = tokenizer.nextToken().trim();
		Aircraft aircraft = aircraftMap.get(aircraftIdentifier);
		if (aircraft == null) bedFileFormatException("trovato un identificatore di aereoporto che non risulta corrispondere ad alcun aereoporto (" + aircraftIdentifier + ").");
		return aircraft;
	}
	
	private Airport getAirport (StringTokenizer tokenizer, Map<String, Airport> airportMap) throws BadFileFormatException {
		
		String flightIdentifier = tokenizer.nextToken().trim();
		Airport airport = airportMap.get(flightIdentifier);
		if (airport == null) bedFileFormatException("trovato un identificatore di volo che non risulta corrispondere ad alcun volo (" + flightIdentifier + ").");
		return airport;
	}
	
	private LocalTime getLocalTime (StringTokenizer tokenizer) throws BadFileFormatException {
		
		String time = tokenizer.nextToken().trim();
		LocalTime localTime = null;
		try {
			localTime = LocalTime.parse(time, DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).localizedBy(Locale.ITALY));
		} catch (DateTimeParseException e) { bedFileFormatException("impossibile effettuare il parsing di una data (essa e' scritta secondo un formato errato oppure contiene valori non accettabili. Il formato richiesto e' \"hh:mm\")."); }
		return localTime;
	}
	
	private void bedFileFormatException (String message) throws BadFileFormatException {
		throw new BadFileFormatException("Formato errato per il file contenente oggetti " + FlightSchedule.class.getSimpleName() + ": " + message);
	}
}
