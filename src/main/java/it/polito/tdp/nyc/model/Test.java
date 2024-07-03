package it.polito.tdp.nyc.model;

import java.util.List;

public class Test {

	public static void main(String[] args) {
		Model model = new Model();
		model.creaGrafo("AT&T", 2);
		List<Vicini> vi = model.getVicini();
		List<String> g = model.calcolaCammino("FRONT OF STAGE INSIDE PARK SW/O/ INDIAN LAKE", "l");

	}

}
