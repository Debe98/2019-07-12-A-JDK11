/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Arco;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPorzioni"
    private TextField txtPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalorie"
    private Button btnCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="boxFood"
    private ComboBox<Food> boxFood; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...\n");
    	
    	String raw = txtPorzioni.getText();
    	int n;
    	try {
    		n = Integer.parseInt(raw);
    		
    	}
    	catch (NumberFormatException e) {
    		txtResult.appendText("Il parametro inserito non è un numero\n");
    		return;
    	}
    	
    	model.creaGrafo(n);
    	boxFood.getItems().clear();
    	boxFood.getItems().addAll(model.getVertex());
    	btnCalorie.setDisable(false);
    	btnSimula.setDisable(false);
    	txtResult.setText("Grafo creato");
    }
    
    @FXML
    void doCalorie(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Analisi calorie...");
    	
    	if (boxFood.getItems().isEmpty()) {
    	    txtResult.setText("Prima crea il grafo!");
    	    return;
    	}
    	if (boxFood.getValue() == null) {
    		txtResult.setText("Devi scegliere un opzione");
    	    return;
    	}
    	Food f = boxFood.getValue();
    	
    	List <Arco> viciniMax = model.getVicini(f);
    	txtResult.setText("Trovati "+viciniMax.size()+" archi vicini:\n");
    	int cnt = 0;
    	for (Arco a : viciniMax) {
    		if (cnt < 5) {
    			txtResult.appendText(a+"\n");
    			cnt++;
    		}
    		else {
    			break;
    		}
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Simulazione...");
    	
    	if (boxFood.getItems().isEmpty()) {
    	    txtResult.setText("Prima crea il grafo!");
    	    return;
    	}
    	if (boxFood.getValue() == null) {
    		txtResult.setText("Devi scegliere un opzione");
    	    return;
    	}
    	Food f = boxFood.getValue();
    	
    	String raw = txtK.getText();
    	int n;
    	try {
    		n = Integer.parseInt(raw);
    		
    	}
    	catch (NumberFormatException e) {
    		txtResult.appendText("Il parametro inserito non è un numero\n");
    		return;
    	}
    	if (n < 1 || n > 10) {
    		txtResult.appendText("Il numero inserito deve essere compreso tra 1 e 10\n");
    		return;
    	}
    	model.simula(n, f);
    	
    	List <Food> cibiPreparati = model.getLista();
    	Double tempo = model.getTempo();
    	
    	txtResult.setText("Impeagati "+tempo+" minuti\nPreparati:\n");
    	
    	for (Food cibo : cibiPreparati) {
    		txtResult.appendText(cibo+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCalorie != null : "fx:id=\"btnCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	btnCalorie.setDisable(true);
    	btnSimula.setDisable(true);
    }
}
