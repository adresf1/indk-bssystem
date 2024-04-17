package Shared.Util;

import Shared.TransferObject.Product;

public class Response {
    private String message;
    private Product product; // You might need to adjust this based on your response

    public Response(String message, Product product) {
        this.message = message;
        this.product = product;
    }

    public String getMessage() {
        return message;
    }

    public Product getProduct() {
        return product;
    }
}
