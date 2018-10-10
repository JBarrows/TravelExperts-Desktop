package sample;

import TraveExDB.Customer;
import TraveExDB.CustomerDB;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class CustomersTable extends TableView {

    TableColumn colCustomerId;
    TableColumn colCustFirstName;
    TableColumn colCustLastName;
    TableColumn colCustAddress;
    TableColumn colCustCity;
    TableColumn colCustProv;
    TableColumn colCustPostal;
    TableColumn colCustCountry;
    TableColumn colCustHomePhone;
    TableColumn colCustBusPhone;
    TableColumn colCustEmail;
    TableColumn colAgentId;

    private ObservableList<Customer> customers;

    CustomersTable() {
        this.customers = FXCollections.observableArrayList();

        customers.addListener((ListChangeListener<Customer>) c -> setItems(customers));

       // this.set
        generateColumns();
        fillTable();
    }

    private void generateColumns() {
        String[] headers = {
                "ID",
                "First Name",
                "Last Name",
                "Address",
                "City",
                "Prov",
                "Postal",
                "Country",
                "Home Phone",
                "Work Phone",
                "Email",
                "Agent"
            };

        String[] propNames = {
                "CustomerId",
                "CustFirstName",
                "CustLastName",
                "CustAddress",
                "CustCity",
                "CustProv",
                "CustPostal",
                "CustCountry",
                "CustHomePhone",
                "CustBusPhone",
                "CustEmail",
                "AgentId"
            };

        for (int i = 0; i < headers.length; i++) {
            TableColumn col =  new TableColumn(headers[i]);
            col.setCellValueFactory(new PropertyValueFactory<Customer, String>(propNames[i]));
            getColumns().add(col);
        }
    }

    void fillTable() {
        customers.setAll( CustomerDB.getAll() );
    }


}
