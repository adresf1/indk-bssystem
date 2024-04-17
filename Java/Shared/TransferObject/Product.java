package Shared.TransferObject;

import java.util.ArrayList;

public class Product {

    private String name;
    private double price;
    private int quantity;


    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }


    public String getName() {
        return name;
    }


    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setter methods
    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


   /* public double calculateTotalPrice(int quantity) {
        return price * quantity;
    }

    public void decreaseQuantity(int quantityPurchased) {
        if (quantityPurchased <= quantity) {
            quantity -= quantityPurchased;
        } else {
            System.out.println("Insufficient quantity available.");
        }
    }


    public void increaseQuantity(int quantityRestocked) {
        quantity += quantityRestocked;
    }

    */



}
