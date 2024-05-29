package View;

import Model.IShopSystemManager;
import Shared.TransferObject.Product;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;

public class PenguinMartViewModel {
    private StringProperty username, textinput;

    private ObservableList<Product> shoppingCart;

    private ObservableList<Product> productList;
    private PropertyChangeSupport support;
    private IShopSystemManager shopSystemManager;

    public PenguinMartViewModel(IShopSystemManager shopSystemManager) {
        this.shopSystemManager = shopSystemManager;
        support = shopSystemManager.getSuppoert();

        shoppingCart = FXCollections.observableList(new ArrayList<Product>());
        productList = FXCollections.observableList(new ArrayList<Product>());
        support.addPropertyChangeListener("allProductsReturned_event",this::handleAllProducts);
        support.addPropertyChangeListener("reservedProduct_event",this::handleReservedProducts);
        support.addPropertyChangeListener("removedProduct_event", this::handleRemovedProduct);
    }



    private void handleAllProducts(PropertyChangeEvent propertyChangeEvent) {
        System.out.println("Entered event handler: handleAllProducts");
        ArrayList<Product> newProductList = (ArrayList<Product>) propertyChangeEvent.getNewValue();
        productList.setAll(newProductList);  // Update the list
        support.firePropertyChange("refreshTableView", null, null);
    }

    private void handleReservedProducts(PropertyChangeEvent propertyChangeEvent) {
        System.out.println("Enters event handler : handleReservedProducts");
        Product productReserved = (Product)propertyChangeEvent.getNewValue();
        shoppingCart.add(productReserved);
        support.firePropertyChange("updateStackpaneItems",null,null);
        support.firePropertyChange("refreshTableView",null,null);
    }

    private void handleRemovedProduct(PropertyChangeEvent propertyChangeEvent) {
        System.out.println("Entered event handler: handleRemovedProduct");
        Product productRemoved = (Product)propertyChangeEvent.getNewValue();
        shoppingCart.removeIf(product -> productRemoved.getID().equals(product.getID()));
        System.out.println(shoppingCart.size());
        support.firePropertyChange("updateStackpaneItems",null,null);
    }

    public void moveToBasket(Product p) throws IOException {
        Product temp = p.copy();
        shopSystemManager.requestToReserveProduct(temp);
    }
    public ObservableList<Product> getProductList()
    {
        return productList;
    }

    public ObservableList<Product> getShoppingCart() {
        return shoppingCart;
    }

    public void requestAllProducts()
    {
        shopSystemManager.requestAllProducts();
    }

    public void requestBuyProducts() throws IOException {
        ArrayList<Product> tempP = new ArrayList<>();
        for (Product temp : shoppingCart) {
            tempP.add(temp);
        }
        shopSystemManager.requestBuyProducts(tempP);
    }
    public void requestRemoveProduct(Product product) throws IOException {
        shopSystemManager.requestRemoveProduct(product);
    }

    public PropertyChangeSupport getSupport()
    {
        return support;
    }

}
