package mediaesami.model;

import java.util.Objects;

public class AttivitaFormativa {
	private String nome;
	private double cfu;
	private long id;
	
	public AttivitaFormativa(long id, String nome, double cfu) {
		if(id<1) throw new IllegalArgumentException("id illegale in AF:" + id);
		if(nome==null || nome.equals("")) throw new IllegalArgumentException("nome vuoto o nullo in AF:" + nome);
		if(cfu<1.0) throw new IllegalArgumentException("cfu non valido in AF: "+ cfu);
		this.nome = nome;
		this.cfu = cfu;
		this.id=id;
	}

	public String getNome() {
		return nome;
	}

	public double getCfu() {
		return cfu;
	}

	public long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cfu, id, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		AttivitaFormativa other = (AttivitaFormativa) obj;
		return cfu == other.cfu && id == other.id && Objects.equals(nome, other.nome);
	}
	
}
