package Network;

import Shared.TransferObject.Product;

import java.util.ArrayList;

public interface Client {
    ArrayList<Product> getProduct();

    void reserveProduct(Product product);

    ArrayList<Product> getReservedProducts();
    void startClient();

}
