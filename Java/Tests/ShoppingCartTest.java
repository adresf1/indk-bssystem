package Tests;

import Model.ShoppingCart;
import Network.SocketClient;
import Server.model.ReserveManagerImpl;
import Shared.TransferObject.Product;
import Shared.Util.MyDate;
import junit.framework.TestListener;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest
{

  @Test void reserveProduct()
  {
    ReserveManagerImpl reserveManager = new ReserveManagerImpl();
    SocketClient socketClient = new SocketClient();

    socketClient.addListener("ReservedProduct",evt -> reserveResponse());

    ShoppingCart sc = new ShoppingCart();
    MyDate myDate = new MyDate(20, 20, 20);
    Product product1 = new Product("", "sa", 1, "asd", myDate, myDate, 2, 2.0, 2.0, 2.0, "2");

    reserveManager.reserveProduct(product1);



  }

  private void reserveResponse()
  {

  }

  @Test void removeProduct()
  {
  }

  @Test void getProducts()
  {
  }
}