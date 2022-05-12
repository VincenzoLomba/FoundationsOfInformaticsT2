package consoleview;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Locale;

import control.CalendarController;
import model.Appointment;
import model.AppointmentCollection;

public class MyCalendarView {
	
	private CalendarController myCalendar;
	private StdInput stdInput = new StdInput();
	private DateTimeFormatter localDateFormatter;
	private DateTimeFormatter localTimeFormatter;

	public MyCalendarView(CalendarController calendarController) {
		
		myCalendar = calendarController;
		localDateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.ITALY);
		localTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.ITALY);
	}

	public void addAppointment() {
		
		System.out.println("--- Data di Inizio ---");
		LocalDate startDate = askUserForDate();
		if (startDate == null)
			return;
		LocalTime startTime = askUserForTime();
		if (startTime == null)
			return;
		System.out.println("Inserisci la durata in minuti");
		int durationInMinutes = stdInput.readInt(-1);
		if (durationInMinutes < 0) {
			System.out.println("Durata non valida - operazione annullata");
			return;
		}
		System.out.println("Inserisci la descrizione");
		String descrizione = stdInput.readString();
		LocalDateTime from = LocalDateTime.of(startDate, startTime);
		Duration duration = Duration.ofMinutes(durationInMinutes);
		Appointment app = new Appointment(descrizione, from, duration);
		myCalendar.add(app);
		System.out.println("Inserimento avvenuto con successo");
	}

	public void removeAppointment() {
		
		LocalDate date = askUserForDate();
		if (date == null)
			return;
		AppointmentCollection appList = myCalendar.getDayAppointments(date);
		String[] menuItems = new String[appList.size()];
		int option;
		for (int i = 0; i < appList.size(); i++) {
			menuItems[i] = appList.get(i).getDescription();
		}
		Menu menu = new Menu("My Calendar", menuItems);
		option = menu.showAndGetOption();
		if (myCalendar.remove(appList.get(option - 1)) == true)
			System.out.println("Rimozione avvenuta con successo");
		else
			System.out.println("Problemi nella rimozione, operazione annullata");
	}

	public void showDay() {
		
		LocalDate date = askUserForDate();
		if (date == null)
			return;
		AppointmentCollection appList = myCalendar.getDayAppointments(date);
		if (appList.size() == 0) {
			System.out.println("Nessun appuntamento presente nella giornata");
			return;
		}
		for (int i = 0; i < appList.size(); i++) {
			System.out.println(appList.get(i));
		}
	}

	public void showMonth() {
		
		LocalDate date = askUserForMonthYear();
		if (date == null)
			return;
		AppointmentCollection appList = myCalendar.getMonthAppointments(date);
		if (appList.size() == 0) {
			System.out.println("Nessun appuntamento presente nel mese");
			return;
		}
		for (int i = 0; i < appList.size(); i++) {
			System.out.println(appList.get(i));
		}
	}

	public void showWeek() {
		
		LocalDate date = askUserForDate();
		if (date == null)
			return;
		AppointmentCollection appList = myCalendar.getWeekAppointments(date);
		if (appList.size() == 0) {
			System.out.println("Nessun appuntamento presente nella settimana");
			return;
		}
		for (int i = 0; i < appList.size(); i++) {
			System.out.println(appList.get(i));
		}
	}

	private LocalDate askUserForMonthYear() {
		
		System.out.println("Inserisci mese e anno (mm/aa): ");
		String insertedDate = stdInput.readString();
		insertedDate = "01/" + insertedDate;
		try {
			return LocalDate.parse(insertedDate, localDateFormatter);
		} catch (DateTimeParseException e) {
			System.out.println("Formato errato.");
			return null;
		}
	}

	private LocalDate askUserForDate() {
		
		System.out.println("Inserisci la data (gg/mm/aa): ");
		String insertedDate = stdInput.readString();

		try {
			return LocalDate.parse(insertedDate, localDateFormatter);
		} catch (DateTimeParseException e) {
			System.out.println("Formato errato.");
			return null;
		}
	}

	private LocalTime askUserForTime() {
		
		System.out.println("Inserisci l'ora (hh:mm): ");
		String insertedTime = stdInput.readString();

		try {
			return LocalTime.parse(insertedTime, localTimeFormatter);
		} catch (DateTimeParseException e) {
			System.out.println("Formato errato.");
			return null;
		}
	}

	public void showAll() {
		
		AppointmentCollection appColl = myCalendar.getAllAppointments();
		for (int i = 0; i < appColl.size(); i++) {
			System.out.println(appColl.get(i));
		}
	}

}
