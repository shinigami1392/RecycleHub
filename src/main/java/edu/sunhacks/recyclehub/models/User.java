package edu.sunhacks.recyclehub.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection="User")
public class User {
    @Id
    private String id;
    private String username;
    private String password;

    private List<ProductDetails> stackedProdDetails;

    private List<ProductDetails> recycledProdDetails;

    public User(String username, String password, List<ProductDetails> stackedProdDetails, List<ProductDetails> recycledProdDetails) {
        this.username = username;
        this.password = password;
        this.stackedProdDetails = stackedProdDetails;
        this.recycledProdDetails = recycledProdDetails;
    }

    public User(){
        stackedProdDetails = new ArrayList<ProductDetails>();
        recycledProdDetails = new ArrayList<ProductDetails>();
    }


    public List<ProductDetails> getStackedProdDetails() {
        return stackedProdDetails;
    }

    public void setStackedProdDetails(List<ProductDetails> stackedProdDetails) {
        this.stackedProdDetails = stackedProdDetails;
    }

    public List<ProductDetails> getRecycledProdDetails() {
        return recycledProdDetails;
    }

    public void setRecycledProdDetails(List<ProductDetails> recycledProdDetails) {
        this.recycledProdDetails = recycledProdDetails;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String id) {
        this.username = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
