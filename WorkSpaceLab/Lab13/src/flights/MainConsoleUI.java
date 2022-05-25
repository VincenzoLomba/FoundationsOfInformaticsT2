package flights;

import java.io.IOException;

import flights.controller.MyController;
import flights.persistence.BadFileFormatException;
import flights.persistence.DataManager;
import flights.persistence.MyAircraftsReader;
import flights.persistence.MyCitiesReader;
import flights.persistence.MyFlightScheduleReader;
import flights.ui.console.FlightConsoleUi;

public class MainConsoleUI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		DataManager dataManager = createDataManager();
		if (!readData(dataManager))
			return;

		MyController controller = new MyController(dataManager);
		
		FlightConsoleUi flightConsoleUI = new FlightConsoleUi(controller);
		
		flightConsoleUI.run();
		
	}
	
	protected static DataManager createDataManager() {
		MyCitiesReader citiesReader = new MyCitiesReader();
		MyAircraftsReader aircraftsReader = new MyAircraftsReader();
		MyFlightScheduleReader flightScheduleReader = new MyFlightScheduleReader();

		DataManager dataManager = new DataManager(citiesReader, 
				aircraftsReader, flightScheduleReader);
		return dataManager;
	}
	
	private static boolean readData(DataManager dataManager) {
		try {
			dataManager.readAll();
			return true;
		} catch (IOException e) {
			System.out.println("Errore di I/O");
			e.printStackTrace();
		} catch (BadFileFormatException e) {
			System.out.println("Formato del file errato");
			e.printStackTrace();
		}
		return false;
	}
}
