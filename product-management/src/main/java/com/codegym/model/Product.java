package com.codegym.model;

public class Product {
    private int id;
    private String name;
    private String price;
    private String quantity;
    private String image;

    public Product(String name, String price, String quantity, String image) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }

    public Product(int id, String name, String price, String quantity, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }

    public Product() {

    }

    public int getId() {
        return id;
    }

    public void setId(int  id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", image='" + image + '\'' +
                '}';
    }
}
