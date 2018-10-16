package sample.customers;

import TraveExDB.Customer;
import TraveExDB.CustomerDB;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.myActionListener;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDetailPane extends TitledPane {
    private CustomerForm customerForm;
    private Button btnSave, btnCancel;
    private Customer customer;

    private ArrayList<myActionListener> updateListeners;

    public CustomerDetailPane() {
        super("Details", new VBox());
        updateListeners = new ArrayList<>();
        VBox vbox  = (VBox)getContent();

        customerForm = new CustomerForm();
        vbox.getChildren().add(customerForm);

        //Create save and cancel buttons
        btnSave = new Button("Save");
        btnSave.setOnAction(this::onBtnSaveClicked);
        btnCancel = new Button("Cancel");
        btnCancel.setOnAction(event -> setCustomer(customer));
        btnSave.setStyle("-fx-graphic: url('/icons/small-check.png')");
        btnCancel.setStyle("-fx-graphic: url('/icons/small-cancel.png')");
        HBox buttonPane = new HBox(5);
        buttonPane.getChildren().setAll(btnSave, btnCancel);
        vbox.getChildren().add(buttonPane);

        customerForm.setActionListener(this::onDetailsChanged);
    }

    private void onBtnSaveClicked(ActionEvent event) {

        Customer newCustomer = customerForm.getCustomer();
        if (newCustomer == null)    return;

        // Update database
        try {
            int rowsAffected = CustomerDB.update(customer, newCustomer);
            if (rowsAffected > 0) {
                disableButtons();
                updateListeners.forEach(myActionListener::onAction);
                if (rowsAffected > 1) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Extra rows updated. Please check database", ButtonType.OK);
                    alert.show();
                }
                this.customer = newCustomer;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to update customer in database", ButtonType.OK);
                alert.show();
            }

        } catch (SQLException | ClassNotFoundException e) {

            Alert updateError = new Alert(Alert.AlertType.ERROR, e.getMessage(),ButtonType.OK);
            updateError.setTitle("Error updating customer");
            updateError.show();
            e.printStackTrace();
        }

    }

    private void disableButtons() {
        //disable buttons
        btnSave.setDisable(true);
        btnCancel.setDisable(true);
    }


    private void onDetailsChanged() {
        btnSave.setDisable(false);
        btnCancel.setDisable(false);
    }

    void setCustomer(Customer customer) {
        this.customer = customer; // Keep track of customer in case of update
        customerForm.setCustomer(customer);

        //disable buttons
        disableButtons();
    }

    public void addUpdateListener(myActionListener listener) {
        updateListeners.add(listener);
    }
}
