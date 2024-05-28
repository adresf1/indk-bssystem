package Model;

import Network.Client;
import Shared.TransferObject.Product;

import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;

public class ShopSystemManager implements IShopSystemManager {

    private PropertyChangeSupport support;
    private Client client;

    public ShopSystemManager(Client client) {
        this.client = client;
        support = client.getSupport();
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        return client.searchProductByID("1");
    }

    @Override
    public PropertyChangeSupport getSuppoert() {
        return support;
    }

    @Override
    public void requestAllProducts() {
        client.requestAllProducts();
    }

    @Override
    public void requestToReserveProduct(Product p) throws IOException {
        client.requestToReserveProduct(p);
    }

    @Override
    public void requestBuyProducts(ArrayList<Product> products) throws IOException {
        client.requestBuyAllProducts(products);
    }

    @Override
    public void requestRemoveProduct(Product product) throws IOException {
        client.requestRemoveProduct(product);
    }
}
