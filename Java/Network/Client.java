package Network;

import Shared.TransferObject.Product;

import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;

public interface Client {
    String getProduct(String id);

    void reserveProduct(Product product);

    ArrayList<Product> getReservedProducts();

    ArrayList<Product> searchProductByID(String ID);
    void startClient();

    PropertyChangeSupport getSupport();
    void requestAllProducts();

    void requestToReserveProduct(Product p) throws IOException;

    void requestBuyAllProducts(ArrayList<Product> products) throws IOException;
}
