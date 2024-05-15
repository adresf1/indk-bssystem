import Core.ClientFactory;
import Model.ShoppingCart;
import Network.SocketClient;
import Shared.TransferObject.Product;
import Shared.Util.MyDate;

import java.util.ArrayList;


public class RunShopSystemApp {
    public static void main(String[] args) {

        SocketClient client = new SocketClient();
        ShoppingCart shoppingCart = new ShoppingCart();

        client.addListener("ProductAdded", shoppingCart);
        client.addListener("AllProducts", evt -> {
            ArrayList<Product> products = (ArrayList<Product>) evt.getNewValue();
            // Do something with the list of products, e.g., print them or display in the UI
            products.forEach(product -> System.out.println(product.getName()));
        });

        // Request all products from the server
       // client.requestAllProducts();

        client.startClient();
    }
}
