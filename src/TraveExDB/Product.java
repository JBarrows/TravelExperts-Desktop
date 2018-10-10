package TraveExDB;

public class Product {
    private int productId;
    private String prodName;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Product(int productId, String prodName) {
        this.productId = productId;
        this.prodName = prodName;
    }
}
