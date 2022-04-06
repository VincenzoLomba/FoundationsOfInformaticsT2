package consoleview;

public class Program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int option;
		MyCalendarView myCalenView = new MyCalendarView();
		do {
			String[] menuItems = new String[] { "Aggiungi Appuntamento",
					"Elimina Appuntamento", "Vedi Giorno", "Vedi Settimana",
					"Vedi Mese", "Vedi Tutti" };

			Menu menu = new Menu("My Calendar", menuItems);
			option = menu.showAndGetOption();

			switch (option) {
			case 1:
				myCalenView.addAppointment();
				break;
			case 2:
				myCalenView.removeAppointment();
				break;
			case 3:
				myCalenView.showDay();
				break;
			case 4:
				myCalenView.showWeek();
				break;
			case 5:
				myCalenView.showMonth();
				break;
			case 6:
				myCalenView.showAll();
				break;
			}
		} while (option != 0);
	}
}
