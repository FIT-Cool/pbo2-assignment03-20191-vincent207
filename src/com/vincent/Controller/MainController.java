package com.vincent.Controller;

import com.vincent.Entity.Category;
import com.vincent.Entity.Item;
import com.vincent.Main;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    @FXML
    private MenuItem showCategoryMenu;
    @FXML
    private MenuItem closeMenu;
    @FXML
    private MenuItem aboutMenu;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtID;
    @FXML
    private ComboBox<Category> comboCategory;
    @FXML
    private DatePicker datePickerBox;
    @FXML
    private Button updateButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button saveButton;
    @FXML
    private TableColumn<Item, String> col01;
    @FXML
    private TableColumn<Item, String> col02;
    @FXML
    private TableColumn<Item, String> col03;
    @FXML
    private TableColumn<Item, String> col04;

    @FXML
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yyyy");

    private ObservableList<Item> items;
    private ObservableList<Category> categories;
    @FXML
    private TableView<Item> tableItem;

    public void close(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void about(ActionEvent actionEvent) {
    }

    public void showCategory(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CategoryManagementForm.fxml"));
            VBox root = loader.load();
            CategoryManagementController controller = loader.getController();
            controller.setMainController(this);

            Stage categoryManagementStage = new Stage();
            categoryManagementStage.setTitle("Category Management");
            categoryManagementStage.setScene(new Scene(root));
            categoryManagementStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void updateAct(ActionEvent actionEvent) {
        int found = 0;
        for (Item item : items) {
            if (item.getName().equals(txtName.getText())) {
                found++;
            }
        }
        if (found >= 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Duplicate item name");
            alert.setTitle("Error");
            alert.show();
        } else {
            if (txtID.getText().isEmpty() || txtName.getText().isEmpty() || comboCategory.getSelectionModel().getSelectedIndex() == -1 || datePickerBox.getEditor().getSelectedText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please fill name/ price/ category");
                alert.show();
            } else {
                Item item = tableItem.getSelectionModel().getSelectedItem();
                item.setName(txtName.getText());
                item.setId(Integer.parseInt(txtID.getText()));
                item.setDate(datePickerBox.getValue());
                item.setCategory(comboCategory.getSelectionModel().getSelectedItem());
            }
        }

        txtID.clear();
        txtName.clear();
        datePickerBox.getEditor().clear();
        comboCategory.getSelectionModel().select(-1);
        updateButton.setDisable(true);
    }

    public void resetAct(ActionEvent actionEvent) {
        txtName.clear();
        txtID.clear();
        datePickerBox.getEditor().clear();
        comboCategory.getSelectionModel().select(-1);
        saveButton.setDisable(false);
        updateButton.setDisable(true);
    }

    public void saveAct(ActionEvent actionEvent) {
        int found = 0;
        for (Item item : items) {
            if (item.getName().equals(txtName.getText())) {
                found++;
            }
        }
        if (found >= 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Duplicate item name");
            alert.setTitle("Error");
            alert.show();
        } else {
            if (txtName.getText().isEmpty() || txtID.getText().isEmpty() || datePickerBox.getEditor().getSelectedText().isEmpty() || comboCategory.getSelectionModel().getSelectedIndex() == -1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please fill name/ price/ category");
                alert.show();
            } else {
                Item i = new Item();
                i.setName(txtName.getText());
                i.setId(Integer.parseInt(txtID.getText()));
                i.setCategory(comboCategory.getSelectionModel().getSelectedItem());
                i.setDate(datePickerBox.getValue());
                getItems().add(i);
            }
        }

        txtID.clear();
        txtName.clear();
        comboCategory.getSelectionModel().select(-1);
        datePickerBox.getEditor().clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableItem.setItems(getItems());
        comboCategory.setItems(getCategories());
        col01.setCellValueFactory(data -> {
            Item i = data.getValue();
            return new SimpleStringProperty(String.valueOf(i.getId()));
        });
        col02.setCellValueFactory(data -> {
            Item i = data.getValue();
            return new SimpleStringProperty(i.getName());
        });
        col03.setCellValueFactory(data -> {
            Item i = data.getValue();
            return new SimpleStringProperty(i.getCategory().toString());
        });
        col04.setCellValueFactory(data -> {
            Item i = data.getValue();
            return new SimpleStringProperty(i.getDate().format(dtf));
        });
    }

    public ObservableList<Category> getCategories() {
        if (categories == null) {
            categories = FXCollections.observableArrayList();
        }
        return categories;
    }

    public ObservableList<Item> getItems() {
        if (items == null) {
            items = FXCollections.observableArrayList();
        }
        return items;
    }

    @FXML
    private void tableAct(MouseEvent mouseEvent) {
        if (tableItem.getSelectionModel().getSelectedIndex() > -1) {
            Item item = tableItem.getSelectionModel().getSelectedItem();
            txtName.setText(item.getName());
            txtID.setText(String.valueOf(item.getId()));
            comboCategory.getSelectionModel().select(item.getCategory());
            datePickerBox.setValue(item.getDate());
            saveButton.setDisable(true);
            updateButton.setDisable(false);
        }
    }
}
