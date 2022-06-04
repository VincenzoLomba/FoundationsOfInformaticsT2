package edlift.ui;

import java.util.function.Consumer;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ButtonPane extends FlowPane {

	private Button[] buttons;
	private int floors, minFloor, maxFloor;
	private Consumer<ActionEvent> extHandler;
	
	public ButtonPane(int minFloor, int maxFloor) {
		this.minFloor=minFloor;
		this.maxFloor=maxFloor;
		this.floors=maxFloor-minFloor+1;
		Insets insets = new Insets(10);
		//
		buttons = new Button[floors];
		for (int i=0; i<this.floors; i++) {
			buttons[i] = new Button(""+(i+minFloor));
			buttons[i].setFont(Font.font("Verdana", FontWeight.NORMAL, 16));
			buttons[i].setPrefWidth(50);
			buttons[i].setOnAction(this::myHandle);
			this.getChildren().add(buttons[i]);	
			VBox.setMargin(buttons[i], insets);
		}
		//
		extHandler=null;
	}
	
	public int getFloors() {
		return floors;
	}
	
	public int getMinFloor() {
		return minFloor;
	}
	public int getMaxFloor() {
		return maxFloor;
	}
	
	public void setOnAction(Consumer<ActionEvent> extHandler) {
		this.extHandler = extHandler;
	}
	
	private void myHandle(ActionEvent e) {
		if (extHandler!=null) extHandler.accept(e);
	}
	
}
