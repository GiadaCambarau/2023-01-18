package it.polito.tdp.nyc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.nyc.model.Hotspot;
import it.polito.tdp.nyc.model.Localita;

public class NYCDao {
	
	public List<Hotspot> getAllHotspot(){
		String sql = "SELECT * FROM nyc_wifi_hotspot_locations";
		List<Hotspot> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Hotspot(res.getInt("OBJECTID"), res.getString("Borough"),
						res.getString("Type"), res.getString("Provider"), res.getString("Name"),
						res.getString("Location"),res.getDouble("Latitude"),res.getDouble("Longitude"),
						res.getString("Location_T"),res.getString("City"),res.getString("SSID"),
						res.getString("SourceID"),res.getInt("BoroCode"),res.getString("BoroName"),
						res.getString("NTACode"), res.getString("NTAName"), res.getInt("Postcode")));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public List<String> getProviders(){
		String sql= "SELECT distinct n.Provider "
				+ "FROM nyc_wifi_hotspot_locations n "
				+ "ORDER BY n.Provider asc ";
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("Provider"));
				}
			
			conn.close();
			return result;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
	}
	
	public List<String> getLocations (String p){
		String sql = "SELECT DISTINCT n.Location "
				+ "FROM nyc_wifi_hotspot_locations n "
				+ "WHERE n.Provider = ?";
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, p);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("Location"));
				}
			
			conn.close();
			return result;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		
	}
	
	public List<Localita> getPosizioneMedia(String provider){
		String sql = "SELECT SUM(n.Latitude)/COUNT(*) AS totLat, SUM(n.Longitude)/COUNT(*) AS totLon, n.Location "
				+ "FROM nyc_wifi_hotspot_locations n "
				+ "WHERE n.Provider = ?  "
				+ "group BY  n.Location";
		List<Localita> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, provider);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Localita l = new Localita(res.getString("n.Location"), res.getDouble("totLat"), res.getDouble("totLon"));
				result.add(l);
				}
			
			conn.close();
			return result;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
	}

}
