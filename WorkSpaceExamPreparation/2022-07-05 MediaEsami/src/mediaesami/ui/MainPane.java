package mediaesami.ui;

import java.text.NumberFormat;

import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import mediaesami.controller.Controller;

public class MainPane extends BorderPane {
	
	private Controller controller;
	private ComboBox<String> carriereCombo;
	private TextArea area;
	private String selectedValue;
	private TextField txtMediaPesata, txtCreditiAcquisiti;
	private NumberFormat formatter;

	public MainPane(Controller controller) {
		this.controller=controller;
		
		// DA FARE: impostare il formattatore numerico

		VBox leftBox = new VBox();
		leftBox.setPrefWidth(100);
			HBox miniBoxIniziale = new HBox(); miniBoxIniziale.setAlignment(Pos.CENTER);
			Label titolino = new Label("Carriere studenti");
			titolino.setStyle("-fx-font-weight: bold");
			miniBoxIniziale.getChildren().addAll(titolino);
			leftBox.getChildren().addAll(new Label("  "), miniBoxIniziale, new Label("  "));
			//
			// DA FARE: popolamento combo
			//
			carriereCombo.setTooltip(new Tooltip("Scegliere la carriera da visualizzare"));
			leftBox.getChildren().addAll(new Label(" Scelta carriera:  "), carriereCombo);
			//
			// DA FARE: aggancio ascoltatore
			//
			txtMediaPesata = new TextField();
			txtCreditiAcquisiti = new TextField();
			//
			// DA FARE: impostazione font, allineamento, e immodificabilità dei campi di testo
			//
			leftBox.getChildren().addAll(new Label(" Media pesata:  "), txtMediaPesata);
			leftBox.getChildren().addAll(new Label(" Crediti acquisiti:  "), txtCreditiAcquisiti);
			this.setLeft(leftBox);
		VBox rightBox = new VBox();
			rightBox.setPrefWidth(550);
			area = new TextArea();
			area.setPrefSize(550,500);
			//
			// DA FARE: impostazione font e immodificabilità dell'area di testo
			//
			rightBox.getChildren().addAll(area);
		this.setRight(rightBox);
	}
	
}
