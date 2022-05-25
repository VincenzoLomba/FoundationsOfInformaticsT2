package flights.ui.console;

import fondt2.ioutils.StdInput;

public class Menu {
	private String[] menuItems;
	private String title;
	private StdInput stdInput = new StdInput();

	public Menu(String title, String[] menuItems) {
		this.title = title;
		this.menuItems = menuItems;
	}

	public int showAndGetOption() {
		show();
		return getOption();
	}

	private int getOption() {
		int option;
		do {
			option = stdInput.readInt(-1);

			if (option < 0 || option > menuItems.length) {
				System.out.println("Scelta non valida.");
			}

		} while (option < 0 || option > menuItems.length);
		return option;
	}

	private void show() {
		System.out.println("**** " + title + " ****");
		System.out.println();

		for (int i = 0; i < menuItems.length; i++) {
			System.out.print(i + 1);
			System.out.println(" - " + menuItems[i]);
		}

		System.out.println("0 - Termina");
		System.out.println("Scegli...");
	}
}
