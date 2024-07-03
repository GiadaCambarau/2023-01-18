/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.nyc;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.nyc.model.Model;
import it.polito.tdp.nyc.model.Vicini;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbProvider"
    private ComboBox<String> cmbProvider; // Value injected by FXMLLoader

    @FXML // fx:id="txtDistanza"
    private TextField txtDistanza; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtStringa"
    private TextField txtStringa; // Value injected by FXMLLoader
    
    @FXML // fx:id="txtTarget"
    private ComboBox<String> txtTarget; // Value injected by FXMLLoader

    @FXML
    void doAnalisiGrafo(ActionEvent event) {
    	txtResult.appendText("I VERTICI CON PIU VICINI SONO: "+"\n");
    	List<Vicini> v = model.getVicini();
    	for (Vicini a : v) {
    		txtResult.appendText(a.getVertice()+ " --> "+ a.getVicini()+"\n");
    		txtTarget.getItems().add(a.getVertice());
    	}
    	
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	String arrivo = txtTarget.getValue();
    	if (arrivo.compareTo("")==0) {
    		txtResult.appendText("Seleziona un vertice di partenza");
    		return;
    	}
    	String s = txtStringa.getText();
    	if (s.compareTo("")==0) {
    		txtResult.appendText("Inserisci una stringa");
    		return;
    	}
    	txtResult.appendText("\n IL PERCORSO MIGLIORE è: "+"\n");
    	List<String> percorso = model.calcolaCammino(arrivo, s);
    	for (String st: percorso) {
    		txtResult.appendText(st+"\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	String provider = cmbProvider.getValue();
    	if (provider.compareTo("")==0) {
    		txtResult.setText("Seleziona un provider");
    		return;
    	}
    	String input= txtDistanza.getText();
    	double distanza = 0;
    	if (input.compareTo("")==0) {
    		txtResult.setText("Inserisci una distanza");
    		return;
    	}
    	try {
    		distanza = Double.parseDouble(input);
    	}catch(NumberFormatException e) {
    		txtResult.setText("La distanza deve essere un numero");
    		return;
    	}
    	model.creaGrafo(provider, distanza);
    	txtResult.appendText("Verici: "+ model.getV()+"\n");
    	txtResult.appendText("Archi: "+ model.getA()+"\n");
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProvider != null : "fx:id=\"cmbProvider\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDistanza != null : "fx:id=\"txtDistanza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStringa != null : "fx:id=\"txtStringa\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    public void setModel(Model model) {
    	this.model = model;
    	cmbProvider.getItems().addAll(model.getProviders());
    }
}
