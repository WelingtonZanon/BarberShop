package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.entities.Product;
import model.entities.Service;
import model.exceptions.ValidationException;
import model.services.ProductService;
import model.services.ServiceService;

public class ProductFormController implements Initializable {

	private Product entity;
	
	private ProductService product;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtQtd;
	
	@FXML
	private TextField txtValueIn;
	
	@FXML
	private TextField txtValueOut;
	
	@FXML
	private TextArea AreaDescription;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	public void setProduct(Product entity) {
		this.entity = entity;
	}
	
	public void setProductService(ProductService product) {
		this.product = product;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (product == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			product.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}
		catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	private Product getFormData() {
		Product obj = new Product();
		
		ValidationException exception = new ValidationException("Validation error");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if (txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		}
		obj.setName(txtName.getText());
		
		if (txtQtd.getText() == null || txtQtd.getText().trim().equals("")) {
			exception.addError("baseSalary", "Field can't be empty");
		}
		obj.setQtd(Utils.tryParseToInt(txtQtd.getText()));
				
		if (txtValueIn.getText() == null || txtValueIn.getText().trim().equals("")) {
			exception.addError("baseSalary", "Field can't be empty");
		}
		obj.setValuein(Utils.tryParseToDouble(txtValueIn.getText()));
		
		if (txtValueOut.getText() == null || txtValueOut.getText().trim().equals("")) {
			exception.addError("baseSalary", "Field can't be empty");
		}
		obj.setValueout(Utils.tryParseToDouble(txtValueOut.getText()));
		
		if (AreaDescription.getText() == null || AreaDescription.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		}
		obj.setDescription(AreaDescription.getText());
		
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		
		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
		
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		txtQtd.setText(String.valueOf(entity.getQtd()));
		txtValueIn.setText(String.valueOf(entity.getValuein()));
		txtValueOut.setText(String.valueOf(entity.getValueout()));
		AreaDescription.setText(entity.getDescription());
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if (fields.contains("name")) {
			labelErrorName.setText(errors.get("name"));
		}
	}
}
