package Network;

import Shared.TransferObject.Product;

import java.util.ArrayList;

public interface Client {
    Product getProduct();

    void reserveProduct(Product product);

    ArrayList<Product> getReservedProducts();
    void startClient();

}
