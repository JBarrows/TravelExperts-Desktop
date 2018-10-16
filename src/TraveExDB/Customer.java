package TraveExDB;

import java.util.*;

public class Customer {
    private Integer customerId;
    private String custFirstName;
    private String custLastName;
    private String custAddress;
    private String custCity;
    private String custProv;
    private String custPostal;
    private String custCountry;
    private String custHomePhone;
    private String custBusPhone;
    private String custEmail;
    private Integer agentId;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustFirstName() {
        return custFirstName;
    }

    public void setCustFirstName(String custFirstName) {
        this.custFirstName = custFirstName;
    }

    public String getCustLastName() {
        return custLastName;
    }

    public void setCustLastName(String custLastName) {
        this.custLastName = custLastName;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustCity() {
        return custCity;
    }

    public void setCustCity(String custCity) {
        this.custCity = custCity;
    }

    public String getCustProv() {
        return custProv;
    }

    public void setCustProv(String custProv) {
        this.custProv = custProv;
    }

    public String getCustPostal() {
        return custPostal;
    }

    public void setCustPostal(String custPostal) {
        this.custPostal = custPostal;
    }

    public String getCustCountry() {
        return custCountry;
    }

    public void setCustCountry(String custCountry) {
        this.custCountry = custCountry;
    }

    public String getCustHomePhone() {
        return custHomePhone;
    }

    public void setCustHomePhone(String custHomePhone) {
        this.custHomePhone = custHomePhone;
    }

    public String getCustBusPhone() {
        return custBusPhone;
    }

    public void setCustBusPhone(String custBusPhone) {
        this.custBusPhone = custBusPhone;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return customerId == customer.customerId &&
                Objects.equals(custFirstName, customer.custFirstName) &&
                Objects.equals(custLastName, customer.custLastName) &&
                Objects.equals(custAddress, customer.custAddress) &&
                Objects.equals(custCity, customer.custCity) &&
                Objects.equals(custProv, customer.custProv) &&
                Objects.equals(custPostal, customer.custPostal) &&
                Objects.equals(custCountry, customer.custCountry) &&
                Objects.equals(custHomePhone, customer.custHomePhone) &&
                Objects.equals(custBusPhone, customer.custBusPhone) &&
                Objects.equals(custEmail, customer.custEmail) &&
                Objects.equals(agentId, customer.agentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, custFirstName, custLastName, custAddress, custCity, custProv, custPostal, custCountry, custHomePhone, custBusPhone, custEmail, agentId);
    }

    public ArrayList<String> getPurchases() {
        ArrayList<String> purchases = new ArrayList<>();
        purchases.add(getCustFirstName());
        purchases.add(getCustLastName());

        return purchases;
    }
}
