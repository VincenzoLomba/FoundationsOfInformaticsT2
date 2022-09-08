package agenda.gui.view;

import agenda.model.Contact;

public interface InsertEditContactDialog {
	void setContact(Contact contact);

	void updateContact(Contact contact);

	DialogResult showDialog();
}
