package sample;

import TraveExDB.Customer;
import TraveExDB.CustomerDB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomerProperties extends ArrayList<TitledPane> {

    Customer customer;
    CustomerDetailPane pnDetails;
    CustomerPurchasesPane pnPurchases;

    CustomerProperties() {
        super();

        // Add Details pane
        pnDetails = new CustomerDetailPane();
        this.add(pnDetails);

        // Add Purchases pane
        pnPurchases = new CustomerPurchasesPane();
        this.add(pnPurchases);

    }

    void setCustomer(Customer customer) {
        this.customer = customer;

        pnDetails.setCustomer(customer);
        pnPurchases.setCustomer(customer);
    }

    void addUpdateListener(myActionListener listener) {
        pnDetails.addUpdateListener(listener);
    }
}
