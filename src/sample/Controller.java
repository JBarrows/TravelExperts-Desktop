package sample;

import TraveExDB.Customer;
import TraveExDB.TravelPackage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import sample.customers.CustomerMain;
import sample.customers.CustomerProperties;
import sample.customers.CustomersTable;
import sample.packages.PackageMainController;
import sample.packages.PackageProperties;
import sample.packages.PackagesTable;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public BorderPane borderPane;
    @FXML private Accordion propAccordion;
    @FXML private ListView<String> lvEntities;

    private final ObservableList<String> listEntities = FXCollections.observableArrayList("CUSTOMERS", "PACKAGES", "Products", "Product Offerings", "Suppliers", "Bookings", "Flights", "Secrets", "Rewards", "Regions");

    private CustomerProperties customerProperties = null;
    private PackageProperties packageProperties = null;
    private String propertiesMode = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lvEntities.setItems(listEntities);

        lvEntities.getSelectionModel().selectedItemProperty().addListener( new SelectedEntityChangedListener() );
        //lvEntities.getSelectionModel().select(0);
    }

    private void loadCustomerUI() {
        //Show customer table and properties
        System.out.println("Loading Customer UI");

        //Load CustomerMain
        CustomerMain custMain = new CustomerMain();
        borderPane.setCenter(custMain);
        custMain.setPropertiesListListener((paneList, modeName) -> {
            propAccordion.getPanes().setAll(paneList);
            propAccordion.getPanes().get(0).setExpanded(true);
            propertiesMode = modeName;
        });

        //Generate and fill table
        CustomersTable custTable = custMain.getCustomersTable();

        // Add selection listeners to table
        custTable.getSelectionModel().selectedItemProperty().addListener(new custSelectionListener(custTable));
    }

    private void loadPackageUI() {
        //Show Package table and properties
        System.out.println("Loading Package UI");

        // Load PackageMainView
        PackageMainController ctrlPkgMain = new PackageMainController();
        borderPane.setCenter(ctrlPkgMain);
        ctrlPkgMain.setPropertiesListListener((paneList, modeName) -> {
            propAccordion.getPanes().setAll(paneList);
            propAccordion.getPanes().get(0).setExpanded(true);
            propertiesMode = modeName;
        });

        // Generate and fill table
        PackagesTable pkgTable = ctrlPkgMain.getPackagesTable();

        //Add Selection listeners to table
        pkgTable.getSelectionModel().selectedItemProperty().addListener(new pkgSelectionListener(pkgTable));

    }
    private class custSelectionListener implements ChangeListener<Customer> {
        private CustomersTable custTable;

        public custSelectionListener(CustomersTable custTable) {

            this.custTable = custTable;
        }

        @Override
        public void changed(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue) {
            if (newValue == null)   return;

            // Make sure that the right properties are showing
            if (!propertiesMode.equals("Customer")) {
                System.out.println("Showing Customer Properties");
                //Fill properties accordion
                if (customerProperties == null) customerProperties = new CustomerProperties();
                propAccordion.getPanes().setAll(customerProperties);
                customerProperties.getDetailsPanel().addUpdateListener(custTable::fillTable);

                //propAccordion.getPanes().get(1).setExpanded(true);

                propertiesMode = "Customer";
            }
            // Send customer to properties module
            Customer selectedCustomer = (Customer)newValue;
            customerProperties.setCustomer(selectedCustomer);

        }
    }

    private class pkgSelectionListener implements ChangeListener<TravelPackage> {
        private PackagesTable pkgTable;

        public pkgSelectionListener(PackagesTable pkgTable) {

            this.pkgTable = pkgTable;
        }

        @Override
        public void changed(ObservableValue<? extends TravelPackage> observable, TravelPackage oldValue, TravelPackage newValue) {
            if (newValue == null)   return;

            //Make sure the right properties are showing
            if (!propertiesMode.equals("Package")) {
                System.out.println("Loading Package Properties...");
                //Fill properties accordion
                if (packageProperties == null) packageProperties = new PackageProperties();
                propAccordion.getPanes().setAll(packageProperties);
                packageProperties.addUpdateListener(pkgTable::fillTable);

                propertiesMode = "Package";
            }
            // Send package to properties module
            packageProperties.setPackage(newValue);
        }
    }

    // Listens for a change in entity selection (In the left-side listview)
    class SelectedEntityChangedListener implements ChangeListener<String> {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            switch(newValue.toLowerCase()){
                case"packages":
                    loadPackageUI();
                    break;
                case"customers":
                    loadCustomerUI();
                    break;
            }
        }
    }

}
