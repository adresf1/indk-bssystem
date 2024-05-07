package Model;

import Shared.TransferObject.Product;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class ShoppingCart implements PropertyChangeListener {
  private ArrayList<Product> products;
  private PropertyChangeSupport support;

  public ShoppingCart() {
    products = new ArrayList<>();
    support = new PropertyChangeSupport(this);
  }


  public void reserveProduct(Product product) {
    products.add(product);
    support.firePropertyChange("ReservedProduct", null, product);
  }

  private void addProduct(Product product) {
    support.firePropertyChange("ProductAdded", null, product);
  }

  public void removeProduct(Product product) {
    products.remove(product);
    support.firePropertyChange("ProductRemoved", null, product);
  }

  public ArrayList<Product> getProducts() {
    return new ArrayList<>(products);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if ("ProductAdded".equals(evt.getPropertyName())) {
      Product product = (Product) evt.getNewValue();
      reserveProduct(product);
      System.out.println("Product added to shopping cart: " + product.getName());
    } else if ("ReservedProduct".equals(evt.getPropertyName())) {
      Product product = (Product) evt.getNewValue();
      System.out.println("Product reserved to shopping cart: " + product.getName());
    } else if ("ProductRemoved".equals(evt.getPropertyName())) {
      Product product = (Product) evt.getNewValue();
      System.out.println("Product removed from shopping cart: " + product.getName());
      removeProduct(product);
    }
  }
}
