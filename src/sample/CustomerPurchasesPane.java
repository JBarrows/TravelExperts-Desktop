package sample;

import TraveExDB.Booking;
import TraveExDB.BookingDB;
import TraveExDB.BookingDetail;
import TraveExDB.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class CustomerPurchasesPane extends javafx.scene.control.TitledPane {

    private Customer customer;
    private VBox vbox;
    private TreeView<String> tvPurchases;
    private Button btnAdd, btnDelete;


    public CustomerPurchasesPane() {
        super("Purchases", new VBox());
        vbox = (VBox)getContent();

        //Add ListView
        tvPurchases = new TreeView<String>();
        vbox.getChildren().add(tvPurchases);

        //Add buttons
        btnAdd = new Button("Add");
        btnAdd.setOnAction(this::onAddButtonClicked);
        btnDelete = new Button("Delete");
        btnDelete.setOnAction(this::onDeleteButtonClicked);
    }

    private void onDeleteButtonClicked(ActionEvent actionEvent) {

    }

    private void onAddButtonClicked(ActionEvent actionEvent) {

    }

    void setCustomer(Customer newCust) {
        this.customer = newCust;

        // Add customer name as root
        TreeItem<String> root = new TreeItem<>(customer.getCustFirstName() + " " + customer.getCustLastName());

        root.setExpanded(true);
        tvPurchases.setRoot(root);

        //Load Packages from DB
        ArrayList<Booking> bookings = BookingDB.getBookings(newCust);
        if (bookings.isEmpty()) root.setValue("<None>");
        for (Booking b: bookings) {
            //Create tree item from booking
            TreeItem<String> branch = new TreeItem<>(b.toString());
            //Create detail leaves
            ArrayList<BookingDetail> bookingDetails = BookingDB.getDetails(b);
            for (BookingDetail detail : bookingDetails) {
                TreeItem<String> leaf = new TreeItem<>(detail.toString());
                branch.getChildren().add(leaf);
            }
            //Add booking to root
            root.getChildren().add(branch);
        }

    }
}
