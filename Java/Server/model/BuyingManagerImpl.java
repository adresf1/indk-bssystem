package Server.model;

import Shared.TransferObject.Product;

import java.util.ArrayList;

public class BuyingManagerImpl implements BuyingManager{

    private ArrayList<Product> products;

    public BuyingManagerImpl(){
        products = new ArrayList<>();
        System.out.println("BuyingManagerImpl added");
    }

    @Override
    public ArrayList<Product> getProducts() {
        return new ArrayList<>(products);
    }

}
