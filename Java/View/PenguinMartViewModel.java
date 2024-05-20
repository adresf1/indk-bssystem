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
        this.username = new SimpleStringProperty();
        this.textinput = new SimpleStringProperty();

        shoppingCart = FXCollections.observableList(new ArrayList<Product>());
        productList = FXCollections.observableList(new ArrayList<Product>());
        support.addPropertyChangeListener("allProductsReturned_event",this::handleAllProducts);
        support.addPropertyChangeListener("reservedProduct_event",this::handleReservedProducts);
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
        System.out.println(productReserved + " !!!!!! ");
        shoppingCart.add(productReserved);
        support.firePropertyChange("updateStackpaneItems",null,null);
        support.firePropertyChange("refreshTableView",null,null);
    }

    public void moveToBasket(Product p) throws IOException {
        Product temp = p.copy();
        temp.setQuantity(1);
        shopSystemManager.requestToReserveProduct(temp);
    }

    public void allProductsToStackPane(){
        ArrayList<Product> products = shopSystemManager.getAllProducts();
        for(Product item : products){
            shoppingCart.add(item);
        }
    }
    public ObservableList<Product> getProductList()
    {
        return productList;
    }
    //UsernameTextfield
    public StringProperty ProductProperty() {
        return textinput;
    }
    public StringProperty usernameProperty() {
        return username;
    }

    public ObservableList<Product> getShoppingCart() {
        return shoppingCart;
    }

    public ObservableList<Product> getAllProducts(){
        ObservableList<Product> templist = FXCollections.observableList(shopSystemManager.getAllProducts());
        return templist;
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

    public PropertyChangeSupport getSupport()
    {
        return support;
    }

}
