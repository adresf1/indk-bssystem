import Core.ClientFactory;
import Model.ShoppingCart;
import Network.SocketClient;
import Shared.TransferObject.Product;
import Shared.Util.MyDate;


public class RunShopSystemApp {
    public static void main(String[] args)
    {

        SocketClient client = new SocketClient();
        ShoppingCart shoppingCart = new ShoppingCart();
        MyDate MyDate = new MyDate(4,5,2024);
        client.reserveProduct(new Product("name", "1",2, "Iiii", MyDate, MyDate,
                2, 1.2, 4.4, 1.5, "Hej"));
        client.addListener("ProductAdded", shoppingCart);
        client.startClient();
    }
}
