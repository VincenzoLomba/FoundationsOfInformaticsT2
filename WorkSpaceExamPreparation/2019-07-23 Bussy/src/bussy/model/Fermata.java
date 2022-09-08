package bussy.model;

public class Fermata {
	private String id, nome;

	public String getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public Fermata(String id, String nome) {
		if (id==null || nome==null || id.equals("") || nome.equals("")) throw new IllegalArgumentException("arg invalidi in ctor fermata");
		this.id = id;
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Fermata [id=" + id + ", nome=" + nome + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Fermata other = (Fermata) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	


}
