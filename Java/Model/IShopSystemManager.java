package Model;

import Shared.TransferObject.Product;
import Shared.Util.Subject;

import java.util.ArrayList;

public interface IShopSystemManager extends Subject {
    void moveToBasket(Product product);

    void moveToBasket(ArrayList<Product> products);

    void buyProduct(Product products);

    void buyProduct(ArrayList<Product> products);


}
