package Network;

import Shared.TransferObject.Product;

import java.util.ArrayList;

public interface Client {
    String getProduct(String id);

    void reserveProduct(Product product);

    ArrayList<Product> getReservedProducts();
    void startClient();

}
