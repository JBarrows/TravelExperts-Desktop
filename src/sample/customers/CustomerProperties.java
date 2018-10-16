package sample.customers;

import TraveExDB.Customer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TitledPane;

import java.util.ArrayList;

public class CustomerProperties extends ArrayList<TitledPane> {

    private CustomerDetailPane pnDetails;
    private SimpleObjectProperty<CustomerPurchasesPane> pnPurchases = new SimpleObjectProperty<>(this, "pnPurchases");

    public CustomerDetailPane getDetailsPanel() {
        return pnDetails;
    }

    public CustomerProperties() {
        super();

        // Add Details pane
        pnDetails = new CustomerDetailPane();
        this.add(pnDetails);

        // Add Purchases pane
        pnPurchases.set(new CustomerPurchasesPane());
        this.add(pnPurchases.get());

    }

    public void setCustomer(Customer customer) {
        pnDetails.setCustomer(customer);
        pnPurchases.get().setCustomer(customer);
    }

}
