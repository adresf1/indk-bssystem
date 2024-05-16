package View;

import Model.IShopSystemManager;
import Shared.TransferObject.Product;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class PenguinMartViewModel {
    private StringProperty username, textinput;

    private ObservableList<Product> shoppingCart;
    private IShopSystemManager shopSystemManager;

    public PenguinMartViewModel(IShopSystemManager shopSystemManager) {
        this.shopSystemManager = shopSystemManager;
        this.username = new SimpleStringProperty();
        this.textinput = new SimpleStringProperty();
        shopSystemManager.addListener("GlobalChatUpdate", this::onUpdateGlobalChat);
        shoppingCart = FXCollections.observableList(new ArrayList<Product>());
    }

    private void onUpdateGlobalChat(PropertyChangeEvent propertyChangeEvent) {
    /*
        ArrayList<Product> newGlobalChat = (ArrayList<Product>)propertyChangeEvent.getNewValue();
        globalChat = FXCollections.observableArrayList(newGlobalChat);
        for (int i = 0; i < newGlobalChat.size(); i++) {
            System.out.println(newGlobalChat.get(i).getProduct());
        }
    */

    }

    public void moveToBasket(Product p) {
        shoppingCart.add(p);
    }

    public void allProductsToStackPane(){
        ArrayList<Product> products = shopSystemManager.getAllProducts();
        for(Product item : products){
            shoppingCart.add(item);
        }
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

}
