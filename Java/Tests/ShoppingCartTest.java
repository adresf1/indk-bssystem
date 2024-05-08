package Tests;

import Model.ShoppingCart;
import Network.SocketClient;
import Server.model.ReserveManagerImpl;
import Shared.TransferObject.Product;
import Shared.Util.MyDate;

import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest
{

  @Test
  void reserveProduct()
  {
    ShoppingCart sc = new ShoppingCart();
    MyDate myDate = new MyDate(20, 20, 2020);
    Product product1 = new Product("1", "Sample Product", 1, "Sample Description", myDate, myDate, 2, 2.0, 2.0, 2.0, "SKU123");

    // Variable to capture if the event is triggered
    final boolean[] eventTriggered = {false};

    // Add a listener to capture property change events
    sc.addListener("ReservedProduct", evt -> {
      eventTriggered[0] = true;
    });


    sc.reserveProduct(product1);

    // Assertions
    assertTrue(sc.getProducts().contains(product1), "Product should be in the shopping cart");

    // Check if the "ReservedProduct" event was triggered
    assertTrue(eventTriggered[0], "ReservedProduct event should have been triggered");

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