package com.vincent.Controller;

import com.vincent.Entity.Category;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryManagementController implements Initializable {
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtName;
    @FXML
    private Button saveButton;
    @FXML
    private TableColumn<Category, String> col01;
    @FXML
    private TableColumn<Category, String> col02;
    @FXML
    private TableView<Category> tableCategory;
    @FXML
    private MainController mainController;

    @FXML
    private void saveAct(ActionEvent actionEvent) {
        int found = 0;
        for(Category c : mainController.getCategories())
        {
            if(c.getName().equals(txtName.getText())){
                found++;
            }
        }
        if (found >= 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Duplicate category name");
            alert.setTitle("Error");
            alert.show();
        } else {
            if (txtName.getText().isEmpty() || txtID.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please fill category name");
                alert.show();
            } else {
                Category c = new Category();
                c.setId(Integer.parseInt(txtID.getText()));
                c.setName(txtName.getText());
                mainController.getCategories().add(c);
            }
        }
        txtID.clear();
        txtName.clear();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col01.setCellValueFactory(data ->{
            Category c = data.getValue();
            return new SimpleStringProperty(String.valueOf(c.getId()));
        });
        col02.setCellValueFactory((data -> {
            Category c = data.getValue();
            return new SimpleStringProperty(c.getName());
        }));
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        tableCategory.setItems(mainController.getCategories());
    }
}
