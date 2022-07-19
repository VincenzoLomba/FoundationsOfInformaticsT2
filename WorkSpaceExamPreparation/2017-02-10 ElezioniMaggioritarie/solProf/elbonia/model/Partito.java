package elbonia.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Partito implements Comparable<Partito> {

	private String nome;
	private int voti;
	
	public Partito(String nome, int voti) {
		this.nome = nome;
		this.voti = voti;
	}
	
	public int getVoti() {
		return voti;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String toString(){
		return nome + " " + voti;
	}

	public int compareTo(Partito that) {
		return that.getVoti()-this.getVoti(); 
	}

	public boolean equals(Object that) {
		if (!(that instanceof Partito)) return false;
		return ((Partito)that).getVoti()==this.getVoti(); 
	}
	
	public int hashCode(){
		return nome.hashCode() + 31* voti;
	} 
	
	public static Set<String> getNomiPartiti(List<Partito> listaPartiti){
		// considera anche il caso in cui un partito sia presente solo in alcuni collegi
		Set<String> nomiPartiti = new HashSet<String>();
		for (Partito p : listaPartiti){
			nomiPartiti.add(p.getNome());
		}
		return nomiPartiti;
	}
	public static Set<String> getNomiPartiti(Collegio c){
		return getNomiPartiti(new ArrayList<>(c.getPartiti()));
	}

}
