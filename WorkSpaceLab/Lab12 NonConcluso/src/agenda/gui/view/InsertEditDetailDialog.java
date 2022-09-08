package agenda.gui.view;

import agenda.model.Detail;

public interface InsertEditDetailDialog {
	void setDetail(Detail detail);

	void updateDetail(Detail detail);

	DialogResult showDialog();
}
