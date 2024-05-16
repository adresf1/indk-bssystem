package Tests;

import Model.ShoppingCart;
import Network.SocketClient;
import Shared.TransferObject.Product;
import Shared.Util.MyDate;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class ShoopingCartIntegrationTest
{
  private SocketClient socketClient;
  private ShoppingCart cart;

  @BeforeEach public void setUp()
  {
    // Opret en instans af indkøbskurvklasse og socket klient
    socketClient = new SocketClient(); // Du kan også bruge en mock SocketClient her, hvis det er nødvendigt
    cart = new ShoppingCart();
  }

  @Test void testTest()
  {
    socketClient.test();
    socketClient.test();
  }

  @Test void testAddProductToShoppingCart()
  {
    // Hent produkter fra databasen
    //socketClient.requestAllProducts();

    // Simulér valg af et produkt fra den hentede liste
    String product =  socketClient.getProduct("1");
    //Product selectedProduct = allProducts.get(0); // Antager at det første produkt vælges

    // Reserver det valgte produkt
    socketClient.reserveProductByID("1");

    // Vent lidt tid for at tillade behandling af serverbeskeder
    // Dette afhænger af, hvordan din klient og server er implementeret

    // Kontroller om produktet er tilføjet til indkøbskurven
    ArrayList<Product> shoppingCart = socketClient.getReservedProducts();
    assertTrue(shoppingCart.contains(product),
        "Product should be added to shopping cart");
  }
}