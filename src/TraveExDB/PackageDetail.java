package TraveExDB;

public class PackageDetail extends BookingDetail {
    private final ProductSupplier productSupplier;

    public PackageDetail(ProductSupplier productSupplier) {
        this.productSupplier = productSupplier;
    }

    @Override
    public String toString() {
        return productSupplier.toString();
    }
}
