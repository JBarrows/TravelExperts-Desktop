package sample;

import TraveExDB.Customer;
import TraveExDB.TravelPackage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public BorderPane borderPane;
    @FXML private Accordion propAccordion;
    @FXML private ListView<String> lvEntities;

    private final ObservableList<String> listEntities = FXCollections.observableArrayList("Customers", "Packages");

    private CustomerProperties customerProperties = null;
    private PackageProperties packageProperties = null;
    private String propertiesMode = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lvEntities.setItems(listEntities);

        lvEntities.getSelectionModel().selectedItemProperty().addListener( new SelectedEntityChangedListener() );
        lvEntities.getSelectionModel().select(0);
    }

    private void loadCustomerUI() {
        //Show customer table and properties
        System.out.println("Loading Customer UI");

        //Generate and fill table
        CustomersTable custTable = new CustomersTable();
        borderPane.setCenter(custTable);
        custTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null)   return;

                // Make sure that the right properties are showing
                if (!propertiesMode.equals("Customer")) {
                    System.out.println("Showing Customer Properties");
                    //Fill properties accordion
                    if (customerProperties == null) customerProperties = new CustomerProperties();
                    propAccordion.getPanes().setAll(customerProperties);
                    customerProperties.addUpdateListener(custTable::fillTable);
                    propAccordion.getPanes().get(1).setExpanded(true);
                    propertiesMode = "Customer";
                }
                // Send customer to properties module
                Customer selectedCustomer = (Customer)newValue;
                customerProperties.setCustomer(selectedCustomer);

            });

    }

    private void loadPackageUI() {
        //Show Package table and properties
        System.out.println("Loading Package UI");

        // Load PackageMainView
        try {
            Pane pnPkgMain = FXMLLoader.load(getClass().getResource("/sample/PackageMainView.fxml"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Generate and fill table
        PackagesTable pkgTable = new PackagesTable();
        borderPane.setCenter(pkgTable);
        pkgTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)   return;

            //Make sure the right properties are showing
            if (!propertiesMode.equals("Package")) {
                System.out.println("Showing Package Properties");
                //Fill properties accordion
                if (packageProperties == null) packageProperties = new PackageProperties();
                propAccordion.getPanes().setAll(packageProperties);
                packageProperties.addUpdateListener(pkgTable::fillTable);
                propAccordion.getPanes().get(2).setExpanded(true);
                propertiesMode = "Package";
            }
            // Send package to properties module
            TravelPackage selectedPackage = (TravelPackage)newValue;
            packageProperties.setPackage(selectedPackage);
        });
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
