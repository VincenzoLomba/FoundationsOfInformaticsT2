package battleship.ui;

import java.util.Arrays;

import battleship.controller.Controller;
import battleship.model.ShipItem;
import battleship.model.ShipType;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainPane extends BorderPane {
	
	private ComboBox<ShipItem> combo;
	private BattleshipGrid grid;
	private Button verifica;
	
	public MainPane (Controller controller, Stage stage) {
		
		VBox left = new VBox();
		combo = new ComboBox<>(FXCollections.observableArrayList(ShipItem.values()));
		combo.getSelectionModel().select(ShipItem.SEA);
		left.getChildren().add(combo);
		Label els = new Label("Elementi da inserire: ");
		left.getChildren().add(els);
		left.getChildren().addAll(Arrays.stream(ShipType.values()).map(st -> new Label(st.toString())).toList());
		verifica = new Button("Verifica");
		left.getChildren().add(verifica);
		
		grid = new BattleshipGrid(controller, combo);
		
		setLeft(left);
		setCenter(grid);
		
		verifica.setOnAction(event -> {
			int v = controller.verify();
			Controller.alert("Esito della verifica", controller.isGameOver() ? "Game over" : v == 0 ? "Nessuna casella errata" : ("Trovate " + v + " casella/e errata/e."), "");
			grid.updateGridStatus();
		});
		
		setPadding(new Insets(10));
		left.setPadding(new Insets(0, 10, 0, 0));
		left.setAlignment(Pos.TOP_CENTER);
		VBox.setMargin(els, new Insets(10, 0, 0, 0));
		VBox.setMargin(verifica, new Insets(20, 0, 0, 0));
	}
}
