package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import com.javadocmd.simplelatlng.window.SortWrapper.DistanceComparator;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	private NYCDao dao;
	private List<String> providers;
	private Graph<String, DefaultWeightedEdge> grafo;
	private List<String> best;
	private int max;
	
	public Model() {
		this.dao = new NYCDao();
		this.providers = dao.getProviders();
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	}
	
	public List<String> getProviders(){
		return this.providers;
	}
	
	public void creaGrafo(String p, double d) {
		List<String> localita = dao.getLocations(p);
		Graphs.addAllVertices(this.grafo,localita);
		List<Localita> posizioni = dao.getPosizioneMedia(p);
		for (Localita l1 : posizioni) {
			for (Localita l2: posizioni) {
				if (!l1.equals(l2)) {
					LatLng coordinate1 = new LatLng(l1.getLat(),l1.getLng());
					LatLng coordinate2 = new LatLng(l2.getLat(),l2.getLng());
					double distanza = LatLngTool.distance(coordinate1, coordinate2, LengthUnit.KILOMETER);
					if (distanza <= d) {
						Graphs.addEdgeWithVertices(this.grafo, l1.getLocalita(), l2.getLocalita(), d);
					}
					
				}
			}
		}
		System.out.println("V: "+ this.grafo.vertexSet().size());
		System.out.println("A: "+ this.grafo.edgeSet().size());
		
	}
	
	public int getV() {
		return this.grafo.vertexSet().size();
		
	}
	public int getA() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Vicini> getVicini(){
		List<Vicini> result = new ArrayList<>();
		for (String l : this.grafo.vertexSet()) {
			List<String> vicini = Graphs.neighborListOf(this.grafo, l);
			int numV = vicini.size();
			Vicini v = new Vicini(l, numV);
			result.add(v);
		}
		Collections.sort(result);
		List<Vicini> copia = new ArrayList<>(result);
		for (Vicini v: copia) {
			if (v.getVicini()<copia.get(0).getVicini()) {
				result.remove(v);
			}
		}
		return result;
	}
	
	public List<String> calcolaCammino(String target, String s){
		List<String > parziale = new ArrayList<>();
		this.best = new ArrayList<>();
		this.max = 0;
		 Set<String> vertici = new HashSet<>(grafo.vertexSet());
		//dato che non posso passare nei vertici che contengono la stringa 
		//elimino in partenza questi vetici
		for (String nome : this.grafo.vertexSet()) {
			if (nome.contains(s)) {
				vertici.remove(nome);
			}
		}
		
		//scelgo casualmente la localita di partenza
		List<Vicini> lista = getVicini();
		double random = Math.random();
		int indice = (int)(random * lista.size()-1);
		String partenza = lista.get(indice).getVertice();
		//la aggiungo a parziale 
		parziale.add(partenza);
		vertici.remove(partenza);
		//RIAGGIUGO IL TARGET NEL CASO L'AVESSI ELIMINATA
		if (!vertici.contains(target)) {
			vertici.add(target);
		}
		//chiamo la ricorsione 
		ricorsione(parziale, vertici, target, partenza);
		
		return best;
		
	}

	 private void ricorsione(List<String> parziale, Set<String> vertici, String target, String partenza) {
	        // Condizione di uscita: l'ultimo elemento di parziale deve essere il target
	        if (parziale.get(parziale.size() - 1).equals(target)) {
	            if (parziale.size() >= max) {
	                this.max = parziale.size();
	                this.best = new ArrayList<>(parziale);
	            }
	            return;
	        }

	        Set<String> vicini = new HashSet<>(Graphs.neighborSetOf(this.grafo,partenza));
	        vicini.retainAll(vertici);

	        for (String v : vicini) {
	            parziale.add(v);
	            vertici.remove(v);

	            ricorsione(parziale, vertici, target,v);

	            parziale.remove(parziale.size() - 1);
	            vertici.add(v);
	        }
	    }
	
}
