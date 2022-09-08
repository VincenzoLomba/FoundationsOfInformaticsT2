package mediaesami.ui;

import java.text.NumberFormat;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
		formatter = NumberFormat.getInstance(Locale.ITALY);
		formatter.setMaximumFractionDigits(2);
		formatter.setMinimumFractionDigits(2);

		VBox leftBox = new VBox();
		leftBox.setPrefWidth(100);
		HBox miniBoxIniziale = new HBox(); miniBoxIniziale.setAlignment(Pos.CENTER);
		Label titolino = new Label("Carriere studenti");
		titolino.setStyle("-fx-font-weight: bold");
		miniBoxIniziale.getChildren().addAll(titolino);
		leftBox.getChildren().addAll(new Label("  "), miniBoxIniziale, new Label("  "));
		
		// DA FARE: popolamento combo
		carriereCombo = new ComboBox<>(FXCollections.observableArrayList(controller.getListaIdCarriere()));
		
		carriereCombo.setTooltip(new Tooltip("Scegliere la carriera da visualizzare"));
		leftBox.getChildren().addAll(new Label(" Scelta carriera:  "), carriereCombo);
		
		// DA FARE: aggancio ascoltatore
		carriereCombo.setOnAction(this::myHandler);
		
		txtMediaPesata = new TextField();
		txtCreditiAcquisiti = new TextField();
		
		// DA FARE: impostazione font, allineamento, e immodificabilità dei campi di testo
		txtMediaPesata.setAlignment(Pos.CENTER_RIGHT);
		txtCreditiAcquisiti.setAlignment(Pos.CENTER_RIGHT);
		txtMediaPesata.setEditable(false);
		txtCreditiAcquisiti.setEditable(false);
		txtMediaPesata.setFont(Font.font("Courier New", FontWeight.BOLD, 11));
		txtCreditiAcquisiti.setFont(Font.font("Courier New", FontWeight.BOLD, 11));
		
		leftBox.getChildren().addAll(new Label(" Media pesata:  "), txtMediaPesata);
		leftBox.getChildren().addAll(new Label(" Crediti acquisiti:  "), txtCreditiAcquisiti);
		this.setLeft(leftBox);
		VBox rightBox = new VBox();
		rightBox.setPrefWidth(550);
		area = new TextArea();
		area.setPrefSize(550,500);
		
		// DA FARE: impostazione font e immodificabilità dell'area di testo
		area.setEditable(false);
		area.setFont(Font.font("Courier New", FontWeight.NORMAL, 11));
		
		rightBox.getChildren().addAll(area);
		this.setRight(rightBox);
	}
	
	private void myHandler (ActionEvent ev) {
		
		selectedValue = carriereCombo.getValue();
		txtMediaPesata.setText(formatter.format(controller.getMediaPesata(selectedValue)) + "/30");
		txtCreditiAcquisiti.setText(formatter.format(controller.getCreditiAcquisiti(selectedValue)) + "/180");
		area.setText(controller.getCarriera(selectedValue).toString());
	}
}
