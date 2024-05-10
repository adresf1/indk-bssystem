package Model;

import Shared.TransferObject.Product;

import java.util.ArrayList;

public class UnreservedState implements ShoppingCartInterface {

  private ArrayList<Product> products;

  public UnreservedState() {
    products = new ArrayList<>();
  }

  @Override
  public void reserve(Product product) {
    products.add(product);
  }

  @Override public void unreserve(Product product)
  {
    //nothing
  }

  @Override
  public ArrayList<Product> getProducts() {
    return products;
  }
}




