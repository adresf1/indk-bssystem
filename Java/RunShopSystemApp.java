import Core.ClientFactory;
import Network.SocketClient;
import Shared.TransferObject.Product;
import javafx.application.Application;

public class RunShopSystemApp {
    public static void main(String[] args)
    {

        SocketClient client = new SocketClient();
        client.buyProduct(new Product("Example Product", 10.0, 1));
        client.startClient();
    }
}
