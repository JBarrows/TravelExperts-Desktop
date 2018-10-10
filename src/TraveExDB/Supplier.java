package TraveExDB;

public class Supplier {
    private int supplierId;
    private String supName;

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    public Supplier(int supplierId, String supName) {
        this.supplierId = supplierId;
        this.supName = supName;
    }
}
