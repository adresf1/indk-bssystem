package Model;

import Shared.TransferObject.Product;

import java.util.ArrayList;

public interface ShoppingCartInterface
{
  public ArrayList<Product> getProducts();
  public void reserve(Product product);
  public void unreserve(Product product);

}
