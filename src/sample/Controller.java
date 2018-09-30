package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private ListView<String> lvEntities;

    final ObservableList<String> listEntities = FXCollections.observableArrayList("Packages", "Customers");


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lvEntities.setItems(listEntities);

        lvEntities.getSelectionModel().selectedItemProperty().addListener( new SelectedEntityChangedListener() );

    }

    private void loadCustomerUI() {
        //Show customer table and properties
        System.out.println("Loading Customer UI");
    }

    private void loadPackageUI() {
        //Show Package table and properties
        System.out.println("Loading Package UI");
    }

    class SelectedEntityChangedListener implements ChangeListener<String> {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            switch(newValue){
                case"Packages":
                    loadPackageUI();
                    break;
                case"Customers":
                    loadCustomerUI();
                    break;
            }
        }
    }

}
