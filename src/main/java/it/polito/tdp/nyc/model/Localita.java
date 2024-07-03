package it.polito.tdp.nyc.model;

import java.util.Objects;

import com.javadocmd.simplelatlng.LatLng;

public class Localita {
	private String localita;
	private double lat;
	private double lng;
	
	public Localita(String localita, double lat, double lng) {
		super();
		this.localita = localita;
		this.lat = lat;
		this.lng = lng;
	}

	public String getLocalita() {
		return localita;
	}

	public void setLocalita(String localita) {
		this.localita = localita;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	@Override
	public int hashCode() {
		return Objects.hash(lat, lng, localita);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Localita other = (Localita) obj;
		return Double.doubleToLongBits(lat) == Double.doubleToLongBits(other.lat)
				&& Double.doubleToLongBits(lng) == Double.doubleToLongBits(other.lng)
				&& Objects.equals(localita, other.localita);
	}
	
	
	
	

}
