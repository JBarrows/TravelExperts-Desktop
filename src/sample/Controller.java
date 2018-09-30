package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private Accordion propAccordion;
    @FXML private ListView<String> lvEntities;

    private final ObservableList<String> listEntities = FXCollections.observableArrayList("Packages", "Customers");


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lvEntities.setItems(listEntities);

        lvEntities.getSelectionModel().selectedItemProperty().addListener( new SelectedEntityChangedListener() );

    }

    private void loadCustomerUI() {
        //Show customer table and properties
        System.out.println("Loading Customer UI");

        //Fill properties accordion
        //TODO: Move this to happen when a table row is selected
        propAccordion.getPanes().setAll(getCustomerPropertyPanes());
    }

    private ArrayList<TitledPane> getCustomerPropertyPanes() {

        ArrayList<TitledPane> panes = new ArrayList<>();

        // Add Purchases pane
        ListView<String> purchasesContent = new ListView<>();
        purchasesContent.setItems(FXCollections.observableArrayList("Caribbean Cruise [December 5]", "Antarctic Expedition [July 6]"));
        TitledPane purchases = new TitledPane("Packages Bought", purchasesContent);
        panes.add(purchases);
        
        // Add Details pane
        VBox detailsContent = new VBox(new TextField("First Name"), new TextField("Last Name"));
        TitledPane details = new TitledPane("Customer Details", detailsContent);
        panes.add(details);

        return panes;

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
