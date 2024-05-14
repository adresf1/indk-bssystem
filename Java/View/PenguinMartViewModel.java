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

    private ObservableList<Product> globalChat;
    private IShopSystemManager IShopSystemManager;

    public PenguinMartViewModel(IShopSystemManager shopSystemManager) {
        this.IShopSystemManager = shopSystemManager;
        this.username = new SimpleStringProperty();
        this.textinput = new SimpleStringProperty();
        IShopSystemManager.addListener("GlobalChatUpdate", this::onUpdateGlobalChat);
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

    public void sendProduct() {
        //        String result = IShopSystemManager.sendProduct(textinput.getValue(),username.getValue());
    }

    //UsernameTextfield
    public StringProperty ProductProperty() {
        return textinput;
    }
    public StringProperty usernameProperty() {
        return username;
    }
}
