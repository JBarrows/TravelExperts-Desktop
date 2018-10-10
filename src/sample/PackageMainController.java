package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class PackageMainController extends Node implements Initializable {
    @FXML private PackagesTable tabPackages;
    @FXML private VBox vbPackageMain;
    @FXML private Button btnAddPackage;
    @FXML private Button btnDeletePackage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabPackages = new PackagesTable();
        vbPackageMain.getChildren().add(0, tabPackages);

        btnAddPackage.setOnAction(this::addNewPackage);
        btnDeletePackage.setOnAction(this::deleteSelectedPackage);
    }

    private void deleteSelectedPackage(ActionEvent actionEvent) {
        //TODO: Delete selected package
    }

    private void addNewPackage(ActionEvent actionEvent) {
        //TODO: Add new package
    }
}
