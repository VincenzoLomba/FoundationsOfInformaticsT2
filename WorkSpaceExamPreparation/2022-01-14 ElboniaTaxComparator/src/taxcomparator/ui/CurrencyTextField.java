package taxcomparator.ui;

import java.text.DecimalFormat;
import java.text.ParseException;

import javafx.scene.control.TextField;

public class CurrencyTextField extends TextField {

	public CurrencyTextField() {
		
		this.textProperty().addListener((observable, oldValue, newValue) -> {
			
			DecimalFormat formatter = new DecimalFormat("¤ #,##0.##");
			if (newValue.matches("\\d*\\.?\\d*")) {
				//System.out.print("caso A: " + newValue);
				String newValueStr = formatter.format(Double.parseDouble(newValue));
				this.setText(newValueStr);
				//System.out.println(", " + newValueStr);
			}
			else if (newValue.matches("€ \\s+\\d*\\.?\\d*")) {
				String newValueStr;
				try {
					//System.out.print("caso B: " + newValue);
					newValueStr = formatter.format(formatter.parse(newValue));
					//System.out.println(", " + newValueStr);
				} catch (ParseException e) {
					newValueStr = oldValue;
				}
				this.setText(newValueStr);
			}
		 });
	}
}
