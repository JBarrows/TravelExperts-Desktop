package sample;

import TraveExDB.Customer;
import TraveExDB.CustomerDB;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EventListener;

public class CustomerDetailPane extends TitledPane {
    private Button btnSave, btnCancel;
    private Customer customer;
    private TextField txtCustomerId, txtCustFirstName, txtCustLastName;
    private TextField txtCustAddress, txtCustCity, txtCustProv, txtCustPostal, txtCustCountry;
    private TextField txtCustHomePhone, txtCustBusPhone, txtCustEmail, txtAgentId;

    private ArrayList<myActionListener> updateListeners;
    //private LinkedHashMap<String, TextField> detailFields;

    public CustomerDetailPane() {
        super("Details", new VBox());
        updateListeners = new ArrayList<>();

        //TODO: Add dropdowns
        txtCustomerId = CreateTextField("ID");
        txtCustomerId.setDisable(true);
        txtCustFirstName = CreateTextField("First name");
        txtCustLastName = CreateTextField("Last Name");
        txtCustAddress = CreateTextField("Address");
        txtCustCity = CreateTextField("City");
        txtCustProv = CreateTextField("Province");        // Dropdown
        txtCustPostal = CreateTextField("Postal Code");
        txtCustCountry = CreateTextField("Country");      // Dropdown
        txtCustHomePhone = CreateTextField("Home Phone");
        txtCustBusPhone = CreateTextField("Work Phone");
        txtCustEmail = CreateTextField("Email");
        txtAgentId = CreateTextField("Agent");            // Dropdown

        //Create save and cancel buttons
        btnSave = new Button("Save");
        btnSave.setOnAction(this::onBtnSaveClicked);
        btnCancel = new Button("Cancel");
        btnCancel.setOnAction(event -> setCustomer(customer));
        HBox buttonPane = new HBox();
        buttonPane.getChildren().setAll(btnSave, btnCancel);
        ((VBox)getContent()).getChildren().add(buttonPane);

    }

    private TextField CreateTextField(String lbl) {
        TextField txtF = new TextField();
        txtF.textProperty().addListener((observable, oldValue, newValue) -> onDetailsChanged());
        VBox vbox = (VBox)getContent();
        vbox.getChildren().add(new Label(lbl));
        vbox.getChildren().add(txtF);

        return txtF;
    }

    private void onBtnSaveClicked(ActionEvent event) {
        // Validate Fields
        if (checkFields() != "") {
            return;
        }

        // Update database
        Customer newCustomer = newCustomerFromDetails();
        try {
            //TODO: Check if update fails
            CustomerDB.update(customer, newCustomer);
            disableButtons();

            updateListeners.forEach(myActionListener::onAction);

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

    private Customer newCustomerFromDetails() {

        Customer c = new Customer();

        c.setCustomerId(Integer.parseInt(txtCustomerId.getText()));
        c.setCustFirstName(txtCustFirstName.getText());
        c.setCustLastName(txtCustLastName.getText());
        c.setCustAddress(txtCustAddress.getText());
        c.setCustCity(txtCustCity.getText());
        c.setCustProv(txtCustProv.getText());
        c.setCustPostal(txtCustPostal.getText());
        c.setCustCountry(txtCustCountry.getText());
        c.setCustHomePhone(txtCustHomePhone.getText());
        c.setCustBusPhone(txtCustBusPhone.getText());
        c.setCustEmail(txtCustEmail.getText());
        c.setAgentId(Integer.parseInt(txtAgentId.getText()));

        return c;
    }

    // TODO: Test validation
    private String checkFields() {
        //Check Postal code
        {
            String postal = txtCustPostal.getText();
            boolean isPostal = postal.matches("^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$");
            boolean isZip = postal.matches("^[0-9]{5}$");
            if (!(isPostal || isZip))
                return "Invalid postal or zip code";
        }

        //Check Phone numbers
        if (!validatePhone(txtCustHomePhone.getText())) return "Invalid home phone";
        if (!validatePhone(txtCustBusPhone.getText())) return "Invalid work phone";

        //Check Email
        {
            String email = txtCustEmail.getText();
            if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
                return "Invalid email";
        }

        return "";
    }

    private boolean validatePhone(String phone) {
        if (phone.matches("^\\(?[0-9]{3}\\)?[-. ]?[0-9]{3}[-. ]?[0-9]{4}$"));
        return true;
    }

    private void onDetailsChanged() {
        btnSave.setDisable(false);
        btnCancel.setDisable(false);
    }

    void setCustomer(Customer customer) {
        this.customer = customer;

        txtCustomerId.setText(String.valueOf(customer.getCustomerId()));
        txtCustFirstName.setText(customer.getCustFirstName());
        txtCustLastName.setText(customer.getCustLastName());
        txtCustAddress.setText(customer.getCustAddress());
        txtCustCity.setText(customer.getCustCity());
        txtCustProv.setText(customer.getCustProv());        // Dropdown
        txtCustCountry.setText(customer.getCustCountry());      // Dropdown
        txtCustPostal.setText(customer.getCustPostal());
        txtCustHomePhone.setText(customer.getCustHomePhone());
        txtCustBusPhone.setText(customer.getCustBusPhone());
        txtCustEmail.setText(customer.getCustEmail());
        txtAgentId.setText(String.valueOf(customer.getAgentId())); // Dropdown

        //disable buttons
        disableButtons();
    }

    void addUpdateListener(myActionListener listener) {
        updateListeners.add(listener);
    }
}
