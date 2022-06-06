package gasforlife.ui;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;

import gasforlife.controller.Controller;
import gasforlife.model.Bill;
import gasforlife.model.BillingFrequency;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainPane extends BorderPane {
	
	private Controller controller;
    private TextArea output;
    private TextField totalCost, totalCons, fixedCost, variableCost, costm3, extram3;
    private RadioButton radioAnnual, radioMonthly;
    private ToggleGroup radioGroup;
    private DatePicker pickerMonth;
    private  VBox pickerPane; 
    private Button calcola;
    
    public MainPane(Controller controller, Stage stage) {
    	
    	this.controller = controller;
    	Insets insets = new Insets(10);
    	VBox ls = new VBox(10);
    	totalCost= new TextField();
    	ls.getChildren().add(new Label("Importo totale"));
    	ls.getChildren().add(totalCost);
    	fixedCost= new TextField();
    	ls.getChildren().add(new Label("Costo fisso"));
    	ls.getChildren().add(fixedCost);
    	costm3= new TextField();
    	ls.getChildren().add(new Label("Costo standard al m3"));
    	ls.getChildren().add(costm3);

    	VBox ld = new VBox(10);
    	totalCons= new TextField();
    	ld.getChildren().add(new Label("Consumo totale"));
    	ld.getChildren().add(totalCons);
    	variableCost= new TextField();
    	ld.getChildren().add(new Label("Costo variabile"));
    	ld.getChildren().add(variableCost);
    	extram3= new TextField();
    	ld.getChildren().add(new Label("Costo extra al m3"));
    	ld.getChildren().add(extram3);

    	HBox tFPane = new HBox(10);
    	tFPane.setSpacing(10);
    	setMargin(tFPane, insets);
    	tFPane.getChildren().addAll(ls, ld);

    	pickerPane = new VBox(10);
    	setMargin(pickerPane, insets);
        pickerMonth = new DatePicker();
    	pickerMonth.setPromptText("Scegli il mese");

    	pickerPane.setVisible(false);
    	pickerPane.getChildren().addAll(new Label("Seleziona un giorno nel mese di riferimento"), pickerMonth);

    	HBox radio = new HBox(10);
    	radioGroup =  new ToggleGroup();
    	radioAnnual = new RadioButton("Annuale");
    	radioMonthly = new RadioButton("Mensile");

    	radioAnnual.setToggleGroup(radioGroup);
    	radioMonthly.setToggleGroup(radioGroup);
    	radio.getChildren().addAll(radioAnnual, radioMonthly);

    	/* 
    	// versione senza lambda expression
    	radioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() 
    	{
    		public void changed(ObservableValue<? extends Toggle> ob, 
    				Toggle o, Toggle n)
    		{

    			RadioButton rb = (RadioButton)radioGroup.getSelectedToggle();

    			if (rb != null) {
    				String s = rb.getText();
    				if(s.equals("Annuale"))
    				{
    					pickerPane.setVisible(false);
    				}
    				else {
    					pickerPane.setVisible(true);
    				}
    			}
    		}
    	});
    	*/
    	
    	/* Versione con lambda expression. */
    	radioGroup.selectedToggleProperty().addListener(
    		(obs,oldValue,newValue) -> {
    			RadioButton rb = (RadioButton)radioGroup.getSelectedToggle();
    			if (rb != null) {
    				String s = rb.getText();
    				if(s.equals("Annuale"))
    				{
    					pickerPane.setVisible(false);
    				}
    				else {
    					pickerPane.setVisible(true);
    				}
    			}
    	});

    	VBox leftPane = new VBox(10);
    	leftPane.setSpacing(10);
    	setMargin(leftPane, insets);
    	calcola = new Button("Calcola");
    	calcola.setOnAction(ev -> computeShare());
    	leftPane.getChildren().addAll(tFPane, radio, pickerPane, calcola);

    	setLeft(leftPane);

    	output = new TextArea();
    	output.setEditable(false);
    	output.setPrefColumnCount(5);
    	output.setPrefRowCount(5);
    	output.setFont(Font.font("Courier New", FontWeight.NORMAL, 12));
    	HBox.setMargin(output, insets);
    	setCenter(output);

    }
    
  private void computeShare() {
	  
	 NumberFormat fN = NumberFormat.getNumberInstance(Locale.ITALY);
	 double  cost, cons, fCost, vCost, cm3, em3; 
	 Number n;
	 try {
		n = fN.parse(totalCost.getText());
		cost = n.doubleValue();
		n = fN.parse(totalCons.getText());
		cons = n.doubleValue();
		n = fN.parse(fixedCost.getText());
		fCost= n.doubleValue();
		n=fN.parse(variableCost.getText());
		vCost= n.doubleValue();
		n=fN.parse(costm3.getText());
		cm3 =  n.doubleValue();
		n=fN.parse(extram3.getText());	
		em3 = n.doubleValue();			
	}  catch (ParseException e) {
		 GasForLifeApp.alert("Errore nei dati","Operazione non consentita" ,"Attenzione uno dei dati inseriti è errato o mancante");
		 return;
	}
	RadioButton sel = ((RadioButton) radioGroup.getSelectedToggle());
	if (sel == null) {
		GasForLifeApp.alert("Mancata selezione","Indicazione annuale/mensile mancante" ,"Indicare se la bolletta sia mensile o annuale.");
		return;
	}
	LocalDate localeDate = pickerMonth.getValue();
	if (localeDate == null) {
		GasForLifeApp.alert("Mancata selezione","Indicazione data mancante" ,"Indicare una data nel mese di riferimento.");
		return;
	}
	Bill bill = new Bill(
		sel.getText().equals("Annuale") ? BillingFrequency.ANNUAL : BillingFrequency.MONTHLY,
		cost,
		fCost,
		vCost,
		cons,
		cm3,
		em3,
		sel.getText().equals("Annuale") ? Optional.empty() : Optional.of(localeDate.getMonth())
	);
	controller.computeShare(bill);
	StringBuilder stringBuilder = new StringBuilder(bill.toString());
	double diff = controller.getDiffCons(bill);
	if (diff != 0) {
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setMaximumFractionDigits(2);
		stringBuilder.append(System.lineSeparator()).append("La differenza tra il conumo presunto nella bolletta e quello reale risulta essere di m3 " + numberFormat.format(diff));
	}
	this.output.setText(stringBuilder.toString());
  }
}
