package model;

import java.security.InvalidParameterException;

/**
 * Created by svindler on 19.01.2017.
 */
public class Product {

    private String user;
    private String name;
    private String category;
    private String price;
    private int quantity;

    public Product(String user, String name, String category, String price, int quantity) {

        this.user = user;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;

    }
    public String getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }


}
