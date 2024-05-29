package Network;

import Shared.TransferObject.Product;

import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;

public interface Client {
    ArrayList<Product> searchProductByID(String ID);
    PropertyChangeSupport getSupport();
    void requestAllProducts();

    void requestToReserveProduct(Product p) throws IOException;

    void requestBuyAllProducts(ArrayList<Product> products) throws IOException;

    void requestRemoveProduct(Product product) throws IOException;
}
