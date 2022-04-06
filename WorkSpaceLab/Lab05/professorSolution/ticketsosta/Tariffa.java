package ticketsosta;

public class Tariffa {
	private String nome;
	private double tariffaOraria;
	private int minutiFranchigia, durataMinima;
	
	public Tariffa(String nome, double tariffaOraria, int minutiFranchigia, int durataMinima) {
		super();
		this.nome = nome;
		this.tariffaOraria = tariffaOraria;
		this.minutiFranchigia = minutiFranchigia;
		this.durataMinima = durataMinima;
	}
	
	public String getNome() {
		return nome;
	}
	public double getTariffaOraria() {
		return tariffaOraria;
	}
	public int getMinutiFranchigia() {
		return minutiFranchigia;
	}
	
	public int getDurataMinima() {
		return durataMinima;
	}

	@Override
	public String toString() {
		return "Tariffa " + nome + ", tariffa oraria=" + tariffaOraria + ", minuti franchigia=" + minutiFranchigia + ", durata minima=" + durataMinima;
	}

}
