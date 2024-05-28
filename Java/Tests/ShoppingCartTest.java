package Tests;

import Model.ShoppingCart;
import Shared.TransferObject.Product;
import Shared.Util.MyDate;

import org.junit.jupiter.api.Test;

import java.util.List;

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

  @Test void removeProduct()
  {
    ShoppingCart sc = new ShoppingCart();
    MyDate myDate = new MyDate(20, 20, 2020);
    Product product2 = new Product("2", "Sample Product", 1, "Sample Description", myDate, myDate, 2, 2.0, 2.0, 2.0, "SKU123");
    Product product3 = new Product("3", "Sample Product", 2, "Sample Description", myDate, myDate, 2, 3.0, 2.0, 2.0, "SKU223");
    sc.reserveProduct(product2);
    sc.reserveProduct(product3);

    // Variable to capture if the event is triggered
    final boolean[] eventTriggered = {false};

    // Add a listener to capture property change events
    sc.addListener("ProductRemoved", evt -> {
      eventTriggered[0] = true;
    });

    sc.removeProduct(product2);

    // Assertions
    assertFalse(sc.getProducts().contains(product2), "Product should not be in the shopping cart");
    assertTrue(sc.getProducts().contains(product3), "Product should be in the shopping cart");

    // Check if the "ReservedProduct" event was triggered
    assertTrue(eventTriggered[0], "RemovedProduct event should have been triggered");

  }

  @Test void getProducts()
  {
    ShoppingCart sc = new ShoppingCart();
    MyDate myDate = new MyDate(20, 20, 2020);
    Product product4 = new Product("2", "Sample Product", 1, "Sample Description", myDate, myDate, 7, 2.0, 2.0, 2.0, "SKmmm");
    Product product5 = new Product("3", "Sample Product", 2, "Sample Description", myDate, myDate, 2, 5.0, 2.0, 2.0, "SKppp3");
    Product product6 = new Product("7", "Sample Product", 2, "Sample Description", myDate, myDate, 2, 5.0, 2.0, 2.0, "SKppp3");
    sc.reserveProduct(product4);
    sc.reserveProduct(product5);

    List<Product> reservedProducts = sc.getProducts();

    assertEquals(2, reservedProducts.size(), "Expected two products in the shopping cart");

    assertTrue(reservedProducts.contains(product4), "Product should be in the shopping cart");
    assertTrue(reservedProducts.contains(product5), "Product should be in the shopping cart");
    assertFalse(reservedProducts.contains(product6), "product should not be in the shopping cat");

  }
}