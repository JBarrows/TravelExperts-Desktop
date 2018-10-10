package TraveExDB;

import javafx.beans.property.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TravelPackage {

    private SimpleIntegerProperty packageId = new SimpleIntegerProperty(this, "packageId");
    private SimpleStringProperty pkgName = new SimpleStringProperty(this, "pkgName");
    private SimpleStringProperty pkgDesc = new SimpleStringProperty(this, "pkgDesc");
    private SimpleObjectProperty<Date> pkgStartDate = new SimpleObjectProperty<>(this, "pkgStartDate");
    private SimpleObjectProperty<Date> pkgEndDate = new SimpleObjectProperty<>(this, "pkgEndDate");
    private SimpleFloatProperty pkgBasePrice = new SimpleFloatProperty(this, "pkgBasePrice");
    private Float pkgAgencyCommission = null;

    public int getPackageId() {
        return packageId.get();
    }

    public void setPackageId(int packageId) {
        this.packageId.set(packageId);
    }

    public String getPkgName() {
        return pkgName.get();
    }

    public void setPkgName(String pkgName) {
        this.pkgName.set(pkgName);
    }

    public String getPkgDesc() {
        return pkgDesc.get();
    }

    public void setPkgDesc(String pkgDesc) {
        this.pkgDesc.set(pkgDesc);
    }

    public Date getPkgStartDate() {
        return pkgStartDate.get();
    }

    public void setPkgStartDate(Date pkgStartDate) {
        this.pkgStartDate.set(pkgStartDate);
    }

//    public void setPkgStartDate(String pkgStartDate) {
//
//        if (pkgStartDate.trim().equals("")) {
//            this.pkgStartDate = null;
//            return;
//        }
//
//        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:MM:mm");
//
//        try {
//            this.pkgStartDate = ft.parse(pkgStartDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            this.pkgStartDate = null;
//        }
//    }

    public Date getPkgEndDate() {
        return pkgEndDate.get();
    }

    public void setPkgEndDate(Date pkgEndDate) {
        this.pkgEndDate.set(pkgEndDate);
    }

//    public void setPkgEndDate(String pkgEndDate) {
//        if (pkgEndDate.trim().equals("")) {
//            this.pkgEndDate = null;
//            return;
//        }
//
//        SimpleDateFormat ft = new SimpleDateFormat();
//
//        try {
//            this.pkgEndDate = ft.parse(pkgEndDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            this.pkgEndDate = null;
//        }
//    }

    public float getPkgBasePrice() {
        return pkgBasePrice.get();
    }

    public void setPkgBasePrice(float pkgBasePrice) {
        this.pkgBasePrice.set(pkgBasePrice);
    }

    public Float getPkgAgencyCommission() {
        return pkgAgencyCommission;
    }

    public void setPkgAgencyCommission(Float pkgAgencyCommission) {
        this.pkgAgencyCommission = pkgAgencyCommission;
    }
}
