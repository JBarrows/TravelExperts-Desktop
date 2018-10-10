package sample;

import TraveExDB.Customer;
import TraveExDB.PackageDB;
import TraveExDB.TravelPackage;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PackagesTable extends TableView {

    TableColumn colPackageId;
    TableColumn colPkgName;
    TableColumn colPkgStartDate;
    TableColumn colPkgEndDate;
    TableColumn colPkgDesc;
    TableColumn colPkgBasePrice;
    TableColumn colPkgAgencyCommission;

    ObservableList<TravelPackage> travelPackages;

    public PackagesTable() {
        this.travelPackages = FXCollections.observableArrayList();

        travelPackages.addListener((ListChangeListener<TravelPackage>) c -> setItems(travelPackages));

        // this.set
        generateColumns();
        fillTable();
    }

    private static Object currencyCellFactory(Object param) {
        TableCell<TravelPackage, Float> cell = new TableCell<>() {
            public void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : getString());
                setGraphic(null);
            }

            private String getString() {
                String ret = "";
                if (getItem() != null) {
                    String gi = getItem().toString();
                    NumberFormat df = DecimalFormat.getInstance();
                    df.setMinimumFractionDigits(2);
                    df.setRoundingMode(RoundingMode.HALF_DOWN);

                    ret = "$" + df.format(Float.parseFloat(gi));
                } else {
                    ret = null;
                }
                return ret;
            }
        };

        cell.setStyle("-fx-alignment: top-right;");
        return cell;
    }

    private void generateColumns() {
        String[] headers = {
                "ID",
                "Name",
                "Description",
                "Start",
                "End",
                "Base Price",
                "Agency Commission"
        };

        String[] propNames = {
                "packageId",
                "pkgName",
                "pkgDesc",
                "pkgStartDate",
                "pkgEndDate",
                "pkgBasePrice",
                "pkgAgencyCommission"
        };

        for (int i = 0; i < headers.length; i++) {
            TableColumn<Customer, Object> col =  new TableColumn<>(headers[i]);
            col.setCellValueFactory(new PropertyValueFactory<>(propNames[i]));
            getColumns().add(col);
        }

        //Special formatting for the currency columns
        for (int i = 5; i < headers.length; i++) {

            TableColumn col =  (TableColumn)this.getColumns().get(i);

            col.setCellFactory(PackagesTable::currencyCellFactory);
        }
    }

    public void fillTable() {
        travelPackages.setAll( PackageDB.getAll() );
    }
}
