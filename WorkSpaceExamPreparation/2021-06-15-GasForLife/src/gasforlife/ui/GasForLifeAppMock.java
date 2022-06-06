package gasforlife.ui;



import gasforlife.controller.Controller;
import gasforlife.controller.MyController;
import gasforlife.persistence.ConsumptionReader;
import gasforlife.persistence.FlatReader;
import gasforlife.persistence.test.MyConsumptionReaderMock;
import gasforlife.persistence.test.MyFlatReaderMock;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GasForLifeAppMock extends Application  {
		private Controller controller;

		public void start(Stage stage) {		
			
				FlatReader flatReader = new MyFlatReaderMock();
				ConsumptionReader consReader = new MyConsumptionReaderMock();
				controller = new MyController(flatReader,consReader);
				System.out.println("Configuration read");
			stage.setTitle("GasForLife");
			MainPane root = new MainPane(controller, stage);
			Scene scene = new Scene(root, 950, 450, Color.ALICEBLUE);
			stage.setScene(scene);
			stage.show();
		}


		public static void main(String[] args) {
			launch(args);
		}

		 public static void alert(String title, String headerMessage, String contentMessage) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle(title);
				alert.setHeaderText(headerMessage);
				alert.setContentText(contentMessage);
				alert.showAndWait();
			}
	}
