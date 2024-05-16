package Model;

import Network.Client;
import Shared.TransferObject.Product;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class ShopSystemManager implements IShopSystemManager {

    private PropertyChangeSupport support;
    private Client client;
    private ArrayList<Product> searchResult;
    private ShoppingCart shoppingCart;

    public ShopSystemManager(Client client) {
        support = new PropertyChangeSupport(this);
        searchResult = new ArrayList<>();
        shoppingCart = new ShoppingCart();
        this.client = client;
    }


    @Override
    public void moveToBasket(Product product) {
        shoppingCart.reserveProduct(product);
    }

    @Override
    public void moveToBasket(ArrayList<Product> products) {
        for(Product item:products) {
            shoppingCart.reserveProduct(item);
        }
    }

    @Override
    public void buyProduct(Product products) {


    }

    @Override
    public void buyProduct(ArrayList<Product> products) {

    }

    @Override
    public ArrayList<Product> getAllProducts() {
        return client.searchProductByID("1");
    }

    @Override
    public void addListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName, listener);
    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(eventName,listener);
    }
}
