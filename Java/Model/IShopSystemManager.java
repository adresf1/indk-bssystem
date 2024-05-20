package Model;

import Shared.TransferObject.Product;
import Shared.Util.Subject;

import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;

public interface IShopSystemManager extends Subject {
    void moveToBasket(Product product);

    void moveToBasket(ArrayList<Product> products);

    void buyProduct(Product products);

    void buyProduct(ArrayList<Product> products);

    ArrayList<Product> getAllProducts();

    PropertyChangeSupport getSuppoert();

    void requestAllProducts();

    void requestToReserveProduct(Product p) throws IOException;

    void requestBuyProducts(ArrayList<Product> products) throws IOException;
}
