package zannopoll.model;

public class Intervista {

	private String nome;
	private int eta;
	private Sesso sex;

	public Intervista(String nome, int eta, Sesso sex) {
		if (nome == null || nome.isEmpty())
			throw new IllegalArgumentException("nome scelta non valido");	
		if (eta<18)
			throw new IllegalArgumentException("età intervistato non valida");	
		if (sex == null)
			throw new IllegalArgumentException("sesso non valido");
		this.nome = nome;
		this.eta=eta;
		this.sex=sex;
	}

	public String getNome() {
		return nome;
	}

	public int getEta() {
		return eta;
	}
	
	public Sesso getSesso() {
		return sex;
	}

	public String toString() {
		return nome + " (" + eta + ", " + sex + ")";
	}

}
