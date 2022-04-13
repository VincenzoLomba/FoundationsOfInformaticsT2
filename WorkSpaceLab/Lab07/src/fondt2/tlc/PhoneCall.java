package fondt2.tlc;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.LocalDateTime;

public class PhoneCall {
	private LocalDateTime callStart;
	private LocalDateTime callEnd;
	private String destNumber;

	public PhoneCall(LocalDateTime callStart, LocalDateTime callEnd, String destNumber) {
		this.callStart = callStart;
		this.callEnd = callEnd;
		this.destNumber = destNumber;
	}

	public LocalDateTime getStart() {
		return callStart;
	}

	public LocalDateTime getEnd() {
		return callEnd;
	}

	public String getDestNumber() {
		return destNumber;
	}

	public String toString() {
		DateTimeFormatter shortFormatter = DateTimeFormatter
				.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.MEDIUM);
		return shortFormatter.format(callStart) + " -- "
				+ shortFormatter.format(callEnd) + " --> " + destNumber;
	}
}
