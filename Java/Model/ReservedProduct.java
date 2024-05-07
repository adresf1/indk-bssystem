package Model;

import Shared.TransferObject.Product;

import java.util.ArrayList;

public class ReservedProduct implements ShoppingCartInterface {

  private ArrayList<Product> products;

  public ReservedProduct() {
    products = new ArrayList<>();
  }

  @Override
  public void reserve(Product product) {
   //do nothing
  }

  @Override public void unreserve(Product product)
  {
    products.remove(product);
  }

  @Override
  public ArrayList<Product> getProducts() {
    return products;
  }
}
