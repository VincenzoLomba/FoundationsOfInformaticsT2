package flights;

import flights.persistence.DataManager;
import flights.presistence.tests.DataManagerMock;

public class FlightsApplicationMock extends FlightsApplication {
	
	@Override
	protected DataManager createDataManager() {
		DataManager dataManager = new DataManagerMock();
		return dataManager;
	}	

	public static void main(String[] args) {
		launch(args);
	}

}
