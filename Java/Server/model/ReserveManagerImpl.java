package Server.model;

import Shared.TransferObject.Product;

import java.util.ArrayList;

public class ReserveManagerImpl implements ReserveManager{

    private ArrayList<Product> products;

    public ReserveManagerImpl(){
        products = new ArrayList<>();
        System.out.println("ReserveManagerImpl added");
    }

    @Override
    public ArrayList<Product> getProducts() {
        return new ArrayList<>(products);
    }

    @Override
    public Product reserveProduct(Product requestedProduct) {
        return requestedProduct;
    }

}
