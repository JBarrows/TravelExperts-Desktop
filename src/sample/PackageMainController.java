package sample;

import TraveExDB.PackageDB;
import TraveExDB.TravelPackage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class PackageMainController extends VBox {
    @FXML private PackagesTable tabPackages;
    @FXML private Button btnAddPackage;
    @FXML private Button btnDeletePackage;

    PropertiesPaneListener propertiesListListener;

    PackageMainController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("packageMainView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        btnAddPackage.setOnAction(event -> loadNewPackageForm());

        btnDeletePackage.setOnAction(event -> {
            TravelPackage travelPackage = (TravelPackage) tabPackages.getSelectionModel().getSelectedItem();
            PackageDB.delete(travelPackage);
            tabPackages.fillTable();
        });
    }

    PackagesTable getPackagesTable() {
        return tabPackages;
    }

    public Button getAddButton() { return btnAddPackage; }
    public Button getDeleteButton() { return btnDeletePackage; }

    @FXML
    private ArrayList<TitledPane> loadNewPackageForm() {
        PackageForm pkgForm = new PackageForm();

        //TODO: Disable buttons until data is edited
        Button[] buttons = {
                new Button("Save"),
                new Button("Clear")
        };
        buttons[0].setStyle("-fx-graphic: url('/icons/small-check.png')");
        buttons[1].setStyle("-fx-graphic: url('/icons/small-cancel.png')");

        for (Button btn : buttons) btn.setDisable(true);
        pkgForm.setActionListener(() -> {
            for (Button btn : buttons) btn.setDisable(false);
        });

        buttons[0].setOnAction(event -> onNewSaveButtonClicked(pkgForm));
        buttons[1].setOnAction(event -> pkgForm.clear());

        HBox buttonBox = new HBox(5, buttons[0], buttons[1]);
        pkgForm.getChildren().add(buttonBox);

        TitledPane formPane = new TitledPane("New Package", pkgForm);
        formPane.setExpanded(true);
        ArrayList<TitledPane> al = new ArrayList<>();
        al.add(formPane);

        if (this.propertiesListListener != null) {
            propertiesListListener.setPropertiesPane(al);
        }

        return al;
    }

    private void onNewSaveButtonClicked(PackageForm pkgForm) {
        TravelPackage pkg = pkgForm.getTravelPackage();
        if (pkg == null) {
            new Alert(Alert.AlertType.ERROR, "Validation failed", ButtonType.OK).show();
            return;
        }

        if (PackageDB.insert(pkg)) {
            Alert alSuccess = new Alert(Alert.AlertType.CONFIRMATION, "Package successfully created", ButtonType.OK);
            alSuccess.show();
            pkgForm.clear();
            tabPackages.fillTable();
        } else {
            Alert alFailed = new Alert(Alert.AlertType.ERROR, "Package creation failed", ButtonType.OK);
            alFailed.show();
        }
    }

    public void setPropertiesListListener(PropertiesPaneListener propertiesListListener) {
        this.propertiesListListener = propertiesListListener;
    }
}
