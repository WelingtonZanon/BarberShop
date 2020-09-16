package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.ProductService;
import model.services.ServiceService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem service;

	@FXML
	private MenuItem product;

	@FXML
	private MenuItem order;

	@FXML
	public void onButtonServiceAction() {
		/**conexao Conexao = new conexao();
		Conexao.conecta();**/
		loadView("/gui/ServiceList.fxml", (ServiceListController controller) -> {
			controller.setServiceService(new ServiceService());
			controller.updateTableView();
		});
	}
	
	public void onButtonProductAction() {
		/**conexao Conexao = new conexao();
		Conexao.conecta();**/
		loadView("/gui/ProductList.fxml", (ProductListController controller) -> {
			controller.setProductService(new ProductService());
			controller.updateTableView();
		});
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}

	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();

			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());

			T controller = loader.getController();
			initializingAction.accept(controller);
			
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
}
