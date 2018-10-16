package sample.customers;

import TraveExDB.Agent;
import TraveExDB.Customer;
import TraveExDB.CustomerDB;
import TraveExDB.NamedPair;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import sample.myActionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerForm extends VBox {

    @FXML private TextField txtCustomerId;
    @FXML private TextField txtCustFirstName;
    @FXML private TextField txtCustLastName;
    @FXML private ChoiceBox<NamedPair> drpCustCountry;
    @FXML private ChoiceBox<NamedPair> drpCustProv;
    @FXML private TextField txtCustCity;
    @FXML private TextField txtCustPostal;
    @FXML private TextField txtCustAddress;
    @FXML private TextField txtCustHomePhone;
    @FXML private TextField txtCustBusPhone;
    @FXML private TextField txtCustEmail;
    @FXML private ChoiceBox<Agent> drpAgentId;

    TextInputControl[] textControls;
    private myActionListener actionListener;


    public CustomerForm() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("customerForm.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set Country dropdown (Sets province dropdown too)
        drpCustCountry.getItems().setAll(new NamedPair("CAN", "Canada"), new NamedPair("USA", "United States"));
        drpCustCountry.getSelectionModel().selectedItemProperty().addListener(this::onCountrySelect);
        drpCustCountry.getSelectionModel().selectFirst(); // Sets province dropdown

        // Set Agent dropdown
        drpAgentId.getItems().setAll(CustomerDB.getAgents());
        drpAgentId.getItems().add(0, null);
    }

    private void onFieldChanged(ObservableValue observable, Object oldValue, Object newValue) {
        if (actionListener != null)
            actionListener.onAction();
    }

    private void onCountrySelect(ObservableValue<? extends Pair<String, String>> observable, Pair<String, String> oldValue, Pair<String, String> newValue) {
        ArrayList<NamedPair> provinces = CustomerDB.getProvinces(newValue.getKey());
        drpCustProv.getItems().setAll(provinces);
        drpCustProv.getSelectionModel().selectFirst();
    }

    public void setActionListener(myActionListener actionListener) {
        this.actionListener = actionListener;

        TextInputControl[] textControls = {txtCustomerId, txtCustFirstName, txtCustLastName, txtCustCity, txtCustPostal, txtCustAddress, txtCustHomePhone, txtCustBusPhone, txtCustEmail};
        for (TextInputControl txt : textControls) {
            txt.textProperty().addListener(this::onFieldChanged);
        }

        ChoiceBox[] dropdowns = {drpCustCountry, drpCustProv, drpAgentId};
        for (ChoiceBox drp : dropdowns) {
            ReadOnlyObjectProperty selectedItemProperty = drp.getSelectionModel().selectedItemProperty();
            selectedItemProperty.addListener(this::onFieldChanged);
        }
    }

    public myActionListener getActionListener() {
        return actionListener;
    }

    public void clear() {
        TextInputControl[] textControls = {txtCustomerId, txtCustFirstName, txtCustLastName, txtCustCity, txtCustPostal, txtCustAddress, txtCustHomePhone, txtCustBusPhone, txtCustEmail};
        for (TextInputControl txt : textControls) {
            if (!txt.isDisabled())
                txt.clear();
        }

        drpCustCountry.getSelectionModel().selectFirst(); // Will also set provinces
        drpAgentId.getSelectionModel().selectFirst(); // Should be blank
    }

    public Customer getCustomer() {

        String testResult = checkFields();
        if (!testResult.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, testResult, ButtonType.OK);
            alert.show();
            return null;
        }

        Customer customer = new Customer();

        Integer id = txtCustomerId.getText().isEmpty() ? null : Integer.parseInt(txtCustomerId.getText()) ;
        customer.setCustomerId(id);
        customer.setCustFirstName(txtCustFirstName.getText());
        customer.setCustLastName(txtCustLastName.getText());
        customer.setCustCountry(drpCustCountry.getValue().getValue());
        customer.setCustProv(drpCustProv.getValue().getKey());
        customer.setCustCity(txtCustCity.getText());
        customer.setCustPostal(txtCustPostal.getText().toUpperCase());
        customer.setCustAddress(txtCustAddress.getText());
        customer.setCustHomePhone(txtCustHomePhone.getText());
        customer.setCustBusPhone(txtCustBusPhone.getText());
        customer.setCustEmail(txtCustEmail.getText().trim());
        Agent selectedAgent = drpAgentId.getValue();
        customer.setAgentId(selectedAgent == null ? null : selectedAgent.getAgentId());

        return customer;
    }

    public void setCustomer(Customer customer) {
        txtCustomerId.setText(String.valueOf(customer.getCustomerId()));
        txtCustFirstName.setText(customer.getCustFirstName());
        txtCustLastName.setText(customer.getCustLastName());
        txtCustAddress.setText(customer.getCustAddress());
        txtCustCity.setText(customer.getCustCity());
        txtCustPostal.setText(customer.getCustPostal());
        txtCustHomePhone.setText(customer.getCustHomePhone());
        txtCustBusPhone.setText(customer.getCustBusPhone());
        txtCustEmail.setText(customer.getCustEmail());

        //Set dropdowns
        //Set Country
        for (int i = 0; i < drpCustCountry.getItems().size(); i++) {
            if (drpCustCountry.getItems().get(i).getValue().equals(customer.getCustCountry())) {
                drpCustCountry.getSelectionModel().select(i);
            }
        }
        //Set Province
        for (int i = 0; i < drpCustProv.getItems().size(); i++) {
            //Set Country
            if (drpCustProv.getItems().get(i).getKey().equals(customer.getCustProv())) {
                drpCustProv.getSelectionModel().select(i);
            }
        }
        //Set Agent
        if (customer.getAgentId() == null)
            drpAgentId.getSelectionModel().selectFirst();
        else {
            //Find agent in list
            for (int i = 1; i < drpAgentId.getItems().size(); i++) {
                if (drpAgentId.getItems().get(i).getAgentId() == customer.getAgentId()) {
                    drpAgentId.getSelectionModel().select(i);
                }
            }
        }
    }

    private String checkFields() {
        //Check First and last names
        if (txtCustFirstName.getText().isEmpty()) return "First name cannot be empty";
        if (txtCustLastName.getText().isEmpty()) return "Last name cannot be empty";
        //Check address and city
        if (txtCustAddress.getText().isEmpty()) return "Address cannot be empty";
        if (txtCustCity.getText().isEmpty()) return "City cannot be empty";
        //Check Postal code
        {
            String postal = txtCustPostal.getText().toUpperCase();
            //boolean isPostal = postal.matches("^((?!.*[DFIOQU])[A-VXY][0-9][A-Z]) ?([0-9][A-Z][0-9])$");
            Pattern postalRegEx =  Pattern.compile("^((?!.*[DFIOQU])[A-VXY][0-9][A-Z]) ?([0-9][A-Z][0-9])$");
            Matcher postalMatcher = postalRegEx.matcher(postal);
            if (postalMatcher.find()) {
                String newPostal;
                newPostal = postalMatcher.group(1) + " " + postalMatcher.group(2);
                txtCustPostal.setText(newPostal);
            }
            else {
                // Not postal
                boolean isZip = postal.matches("^[0-9]{5}$");
                if (!isZip)
                    //Not postal, not zip
                    return "Invalid postal or zip code";
            }
        }

        //Check Home phone
        String newPhone;
        newPhone = validatePhone(txtCustHomePhone.getText());
        if (newPhone == null)
            return "Invalid home phone";
        else
            txtCustHomePhone.setText(newPhone);
        //Check Work phone
        newPhone = validatePhone(txtCustBusPhone.getText());
        if (newPhone == null)
            return "Invalid work phone";
        else
            txtCustBusPhone.setText(newPhone);

        //Check Email
        {
            String email = txtCustEmail.getText().trim();
            if (!(email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$") || email.isEmpty()))
                return "Invalid email";
        }

        return "";
    }

    private String validatePhone(String phone) {
        Pattern pattern = Pattern.compile("^\\(?([0-9]{3})\\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$");
        Matcher matcher = pattern.matcher(phone);

        if (matcher.find()) {
            String newPhone;
            newPhone = String.format("(%s) %s-%s", matcher.group(1), matcher.group(2), matcher.group(3));

            return newPhone;
        }

        return null;
    }

}
