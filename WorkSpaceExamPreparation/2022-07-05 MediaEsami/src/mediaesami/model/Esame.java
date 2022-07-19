package mediaesami.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class Esame {
	private AttivitaFormativa af;
	private LocalDate date;
	private Voto voto;
	
	public Esame(AttivitaFormativa af, LocalDate date, Voto voto) {
		if(af==null) throw new IllegalArgumentException("Attività formativa nulla in esame" + af);
		if(date==null) throw new IllegalArgumentException("Data nulla in esame" + date);
		if(date.isAfter(LocalDate.now())) throw new IllegalArgumentException("Data nel futuro inammissibile" + date);
		if(voto==null) throw new IllegalArgumentException("Voto nullo in esame" + voto);		
		this.af = af;
		this.date = date;
		this.voto = voto;
	}

	public AttivitaFormativa getAf() {
		return af;
	}

	public LocalDate getDate() {
		return date;
	}

	public Voto getVoto() {
		return voto;
	}

	@Override
	public String toString() {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy").withLocale(Locale.ITALY);
		return String.format("%1$-35s", af.getNome()) + "\t" + "Cfu: " + af.getCfu() + "\t" + formatter.format(getDate()) + "\t" + getVoto();
	}

	@Override
	public int hashCode() {
		return Objects.hash(af, date, voto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Esame other = (Esame) obj;
		return Objects.equals(af, other.af) && Objects.equals(date, other.date) && voto == other.voto;
	}
	
}
