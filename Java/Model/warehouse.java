package Model;

import Model.Product;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class warehouse  {

    private ArrayList<Product> products;


    public warehouse(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
    
    public void addProducts(Product product){
        products.add(product);
    }
    public void removeProduct(Product product){
        products.remove(product);
    }


    /*
    @Override
    public void buyProduct(Product product) {
        Product product1 = new Product("banan",10.99,20);
        products.add(product1);
        support.firePropertyChange("NewMessage", null, product1);

    }

     */
}
