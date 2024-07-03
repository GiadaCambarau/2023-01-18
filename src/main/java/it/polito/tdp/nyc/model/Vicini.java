package it.polito.tdp.nyc.model;

import java.util.List;
import java.util.Objects;

public class Vicini  implements Comparable<Vicini>{
	private String vertice;
	private int vicini;
	public Vicini(String vertice, int vicini) {
		super();
		this.vertice = vertice;
		this.vicini = vicini;
	}
	public String getVertice() {
		return vertice;
	}
	public void setVertice(String vertice) {
		this.vertice = vertice;
	}
	public int getVicini() {
		return vicini;
	}
	public void setVicini(int vicini) {
		this.vicini = vicini;
	}
	@Override
	public int hashCode() {
		return Objects.hash(vertice, vicini);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vicini other = (Vicini) obj;
		return Objects.equals(vertice, other.vertice) && vicini == other.vicini;
	}
	@Override
	public int compareTo(Vicini o) {
		// TODO Auto-generated method stub
		return o.vicini-this.vicini;
	}
	
	
}
