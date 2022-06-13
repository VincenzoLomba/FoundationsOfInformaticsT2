package dentinia.governor.persistence;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import dentinia.governor.model.Elezioni;

public class MySeggiWriter implements SeggiWriter {

	private String outputFileName;
	
	public MySeggiWriter (String outputFileName) {
		if (outputFileName == null || outputFileName.isBlank()) throw new IllegalArgumentException(
			"Nell'istanziare un oggetto della classe " + MySeggiWriter.class.getSimpleName() + " si e' fornito al suo costruttore un parametro nullo o vuoto."
		);
		this.outputFileName = outputFileName;
	}
	
	@Override
	public void stampaSeggi(Elezioni elezioni, String msg) throws IOException {
		
		if (elezioni == null || msg == null || msg.isBlank()) throw new IllegalArgumentException(
			"Nel chiamare il metodo \"stampaSeggi\" di un oggetto di classe " + MySeggiWriter.class.getSimpleName() + " sono stati forniti parametri non validi."
		);
		PrintWriter printWriter = new PrintWriter(outputFileName);
		printWriter.println(LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM).withLocale(Locale.ITALY)));
		printWriter.println(msg);
		printWriter.print(elezioni.toString());
		printWriter.flush();
		printWriter.close();
	}

}
