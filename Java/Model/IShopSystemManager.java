package Model;

import Shared.TransferObject.Product;

import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;

public interface IShopSystemManager {

    ArrayList<Product> getAllProducts();

    PropertyChangeSupport getSuppoert();

    void requestAllProducts();

    void requestToReserveProduct(Product p) throws IOException;

    void requestBuyProducts(ArrayList<Product> products) throws IOException;

    void requestRemoveProduct(Product product) throws IOException;
}
