package sample.customers;

import TraveExDB.Booking;
import TraveExDB.BookingDB;
import TraveExDB.BookingDetail;
import TraveExDB.Customer;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

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
        btnDelete = new Button("Delete");
    }

    void setCustomer(Customer newCust) {
        this.customer = newCust;

        // Add customer name as root
        TreeItem<String> root = new TreeItem<>(customer.getCustFirstName() + " " + customer.getCustLastName());

        root.setExpanded(true);
        tvPurchases.setRoot(root);

        //Load packages from DB
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
