package dentinia.governor.model;

public class Partito implements Comparable<Partito> {

	private String nome;

	public Partito(String nome) {
		if (nome == null)
			throw new IllegalArgumentException("nome");
		
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public String toString() {
		return nome;
	}

	public int compareTo(Partito that) {
		if (that == null)
			throw new IllegalArgumentException("that");
		
		return this.getNome().compareTo(that.getNome());
	}
	
	@Override
	public boolean equals(Object that) {
		if (that == null)
			return false;
		
		if (this == that)
			return true;
		
		if (that.getClass() != this.getClass())
			return false;
		
		Partito thatPartito = (Partito) that;
		return getNome().equals(thatPartito.getNome());
	}
	
	@Override
	public int hashCode() {
		return nome.hashCode() ^ 37 + getClass().hashCode();
	}

}
