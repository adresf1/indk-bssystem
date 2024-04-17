package Network;

import Shared.TransferObject.Product;

import java.util.ArrayList;

public interface Client {
    ArrayList<Product> getProduct();

    void buyProduct(Product product);

    ArrayList<Product> getBoughtProducts();
    void startClient();

}
