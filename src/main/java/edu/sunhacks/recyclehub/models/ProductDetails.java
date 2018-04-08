package edu.sunhacks.recyclehub.models;

public class ProductDetails {

    private String pid;
    private String productName;
    private int quantity;
    private double amount;

    public ProductDetails(){}
    public ProductDetails(String pid, String productName, int quantity, double amount) {
        this.pid = pid;
        this.productName = productName;
        this.quantity = quantity;
        this.amount = amount;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ProductDetails{" +
                "pid='" + pid + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", amount=" + amount +
                '}';
    }
}
