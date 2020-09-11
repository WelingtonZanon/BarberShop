package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.Main;
import gui.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Service;
import model.services.ServiceService;

public class ServiceListController implements Initializable {
	
	private ServiceService service;

	@FXML
	private TableView<Service> tableViewService;

	@FXML
	private TableColumn<Service, Integer> tableColumnId;

	@FXML
	private TableColumn<Service, String> tableColumnName;

	@FXML
	private TableColumn<Service, String> tableColumnValor;
	
	@FXML
	private TableColumn<Service, Service> tableColumnEDIT;

	@FXML
	private TableColumn<Service, Service> tableColumnREMOVE;

	@FXML
	private Button btNew;
	
	private ObservableList<Service> obsList;
	
	public void setServiceService(ServiceService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnValor.setCellValueFactory(new PropertyValueFactory<>("value"));
				
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewService.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		
		if (service == null) {

			throw new IllegalStateException("Service was null");
		}
		
		List<Service> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewService.setItems(obsList);
		
	}

	private void createDialogForm(Service obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Service data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
			
		} catch (IOException e) {
			e.printStackTrace();
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

}
