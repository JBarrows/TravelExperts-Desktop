package sample.customers;

import TraveExDB.Customer;
import TraveExDB.CustomerDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.PropertiesPaneListener;

import java.io.IOException;
import java.util.ArrayList;

public class CustomerMain extends VBox {
    @FXML private CustomersTable tabCustomers;
    @FXML private Button btnAddCustomer;
    @FXML private Button btnDeleteCustomer;

    PropertiesPaneListener propertiesListListener;

    public CustomerMain() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("customerMain.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        btnAddCustomer.setOnAction(event -> loadNewCustomerForm());

        btnDeleteCustomer.setOnAction(event -> {
            Customer customer = (Customer) tabCustomers.getSelectionModel().getSelectedItem();
            CustomerDB.delete(customer);
            tabCustomers.fillTable();
        });
    }

    public CustomersTable getCustomersTable() {
        return tabCustomers;
    }

    public Button getAddButton() { return btnAddCustomer; }
    public Button getDeleteButton() { return btnDeleteCustomer; }

    @FXML
    private ArrayList<TitledPane> loadNewCustomerForm() {
        CustomerForm custForm = new CustomerForm();

        Button[] buttons = {
                new Button("Save"),
                new Button("Clear")
        };
        buttons[0].setStyle("-fx-graphic: url('/icons/small-check.png')");
        buttons[1].setStyle("-fx-graphic: url('/icons/small-cancel.png')");

        for (Button btn : buttons) btn.setDisable(true);
        custForm.setActionListener(() -> {
            for (Button btn : buttons) btn.setDisable(false);
        });

        buttons[0].setOnAction(event -> onNewSaveButtonClicked(custForm));
        buttons[1].setOnAction(event -> custForm.clear());

        HBox buttonBox = new HBox(5, buttons[0], buttons[1]);
        custForm.getChildren().add(buttonBox);

        TitledPane formPane = new TitledPane("New Customer", custForm);
        formPane.setExpanded(true);
        ArrayList<TitledPane> alPanes = new ArrayList<>();
        alPanes.add(formPane);

        if (this.propertiesListListener != null) {
            propertiesListListener.setPropertiesPane(alPanes, "NewCustomer");
        }

        return alPanes;
    }

    private void onNewSaveButtonClicked(CustomerForm custForm) {
        Customer cust = custForm.getCustomer();
        if (cust == null) {
            new Alert(Alert.AlertType.ERROR, "Validation failed", ButtonType.OK).show();
            return;
        }

        if (CustomerDB.insert(cust)) {
            Alert alSuccess = new Alert(Alert.AlertType.CONFIRMATION, "Customer successfully created", ButtonType.OK);
            alSuccess.show();
            custForm.clear();
            tabCustomers.fillTable();
        } else {
            Alert alFailed = new Alert(Alert.AlertType.ERROR, "Customer creation failed", ButtonType.OK);
            alFailed.show();
        }
    }

    public void setPropertiesListListener(PropertiesPaneListener propertiesListListener) {
        this.propertiesListListener = propertiesListListener;
    }
}
