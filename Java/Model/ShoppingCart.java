package Model;

import Shared.TransferObject.Product;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class ShoppingCart implements PropertyChangeListener {
  private PropertyChangeSupport support;

  public ShoppingCart() {

    support = new PropertyChangeSupport(this);
  }

  public void reserve(Product product) {

    support.firePropertyChange("Reserved the product", null, product);
  }

  public ArrayList<Product> getProducts() {
    return getProducts();
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if ("ProductAdded".equals(evt.getPropertyName())) {
      Product product = (Product) evt.getNewValue();
      System.out.println("Product added to shopping cart: " + product.getName());
    }
  }
}
