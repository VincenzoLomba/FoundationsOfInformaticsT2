package agenda.controller;

import agenda.gui.view.DetailDialog;
import agenda.gui.view.InsertEditContactDialog;
import agenda.gui.view.InsertEditDetailDialog;
import agenda.gui.view.MainView;
import agenda.gui.view.MessageDialog;
import agenda.gui.view.SelectDetailTypeDialog;
import agenda.gui.view.YesNoQuestionDialog;

public interface ViewFactory {

	MainView createMainView();

	SelectDetailTypeDialog createSelectDetailTypeDialog();

	InsertEditDetailDialog createInsertEditDetailDialog(String type);

	InsertEditContactDialog createInsertEditContactDialog();

	DetailDialog createDetailDialog();
	
	MessageDialog createMessageDialog();
	
	YesNoQuestionDialog createYesNoQuestionDialog();

}