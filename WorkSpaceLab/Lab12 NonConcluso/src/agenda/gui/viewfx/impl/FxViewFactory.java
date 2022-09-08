package agenda.gui.viewfx.impl;

import agenda.controller.ViewFactory;
import agenda.gui.view.DetailDialog;
import agenda.gui.view.InsertEditContactDialog;
import agenda.gui.view.InsertEditDetailDialog;
import agenda.gui.view.MainView;
import agenda.gui.view.MessageDialog;
import agenda.gui.view.SelectDetailTypeDialog;
import agenda.gui.view.YesNoQuestionDialog;
import javafx.stage.Stage;

public class FxViewFactory implements ViewFactory {

	private Stage stage;

	public FxViewFactory(Stage stage) {
		this.stage = stage;
	}
	
	@Override
	public MainView createMainView() {
		return new FxMainView(stage);
	}

	@Override
	public SelectDetailTypeDialog createSelectDetailTypeDialog() {
		return new FxSelectDetailTypeDialog();
	}

	@Override
	public InsertEditDetailDialog createInsertEditDetailDialog(String type) {
		switch (type) {
		case "Phone":
			return new FxInsertEditPhoneDialog();
		case "Address":
			return new FxInsertEditAddressDialog();
		case "Email":
			return new FxInsertEditEmailDialog();
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public InsertEditContactDialog createInsertEditContactDialog() {
		return new FxInsertEditContactDialog();
	}

	@Override
	public DetailDialog createDetailDialog() {
		return new FxDetailDialog();
	}

	@Override
	public MessageDialog createMessageDialog() {
		return new FxMessageDialog();
	}

	@Override
	public YesNoQuestionDialog createYesNoQuestionDialog() {
		return new FxYesNoQuestionDialog();
	}

}
