package Model;

import Shared.TransferObject.Product;

import java.util.ArrayList;

public class ShoppingCart {

  private ShoppingCartInterface currentState;

  public ShoppingCart() {
    currentState = new UnreservedState();
  }

  public void reserve(Product product) {
    currentState.reserve(product);
  }

  public ArrayList<Product> getProducts() {
    return currentState.getProducts();
  }

  public void setCurrentState(ShoppingCartInterface newState) {
    currentState = newState;
  }
}
