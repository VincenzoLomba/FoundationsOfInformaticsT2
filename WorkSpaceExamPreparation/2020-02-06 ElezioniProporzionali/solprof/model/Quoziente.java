package dentinia.governor.model;

class Quoziente implements Comparable<Quoziente> {
	
	private Partito partito;
	private long quoziente;	
	
	public Quoziente(Partito partito, long q) {
		this.partito = partito;
		this.quoziente = q;
	}

	public Partito getPartito() {
		return partito;
	}


	public long getQuoziente() {
		return quoziente;
	}

	@Override
	public int compareTo(Quoziente that) {
		return -Long.compare(this.getQuoziente(), that.getQuoziente());
	}
	
	@Override
	public String toString() {
		return "Item [partito=" + partito + ", quoziente=" + quoziente + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((partito == null) ? 0 : partito.hashCode());
		result = prime * result + (int) (quoziente ^ (quoziente >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Quoziente other = (Quoziente) obj;
		if (partito == null) {
			if (other.partito != null) return false;
		} else if (!partito.equals(other.partito)) return false;
		if (quoziente != other.quoziente) return false;
		return true;
	}
}
