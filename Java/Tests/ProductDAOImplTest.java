package Tests;

import Model.ProductDAOImpl;
import Shared.TransferObject.Product;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDAOImplTest
{
  @Test void creatProductTest() throws SQLException
  {
    ProductDAOImpl productDAO = ProductDAOImpl.getInstance();

    Product createdProduct = productDAO.creat("brød",10.95,20);
    assertNotNull(createdProduct, "Created product should not be null");
    assertEquals("brød", createdProduct.getName(), "Product name should match");
    assertEquals(10.95, createdProduct.getPrice(), 0.001, "Product price should match");
    assertEquals(20, createdProduct.getQuantity(), "Product quantity should match");
  }

  @Test void deleteProductTest() throws SQLException
  {
    ProductDAOImpl productDAO = ProductDAOImpl.getInstance();
    Product producttoDelete = new Product("brød",0,0);
    productDAO.delete(producttoDelete);

  }

  @Test void reabyNameTest() throws SQLException
  {
    ProductDAOImpl productDAO = ProductDAOImpl.getInstance();
    Product productToRead = new Product("brød",0,0);
    productDAO.readByName(productToRead.getName());
    assertEquals("brød", productToRead.getName(), "Product name should match");

  }
  @Test void FailiaureReabyNameTest() throws SQLException
  {
    ProductDAOImpl productDAO = ProductDAOImpl.getInstance();
    Product productToRead = new Product("Milk",0,0);
    productDAO.readByName(productToRead.getName());
    assertEquals("brød", productToRead.getName(), "Product name should match");

  }

  @Test void updateTest() throws SQLException
  {

    // Insert the product into the database
    ProductDAOImpl productDAO = ProductDAOImpl.getInstance();
    Product originalProduct = productDAO.creat("originalName1", 10.0, 5);

    // Update the attributes of the product
    originalProduct.setPrice(15.0);
    originalProduct.setQuantity(10);

    // Update the product in the database
    productDAO.update(originalProduct);

    // Retrieve the updated product from the database
    List<Product> updatedProducts = productDAO.readByName(originalProduct.getName());

    // Ensure there is only one updated product
    assertEquals(1, updatedProducts.size(), "Only one product should be updated");

    // Retrieve the updated product
    Product updatedProduct = updatedProducts.get(0);

    // Compare the updated attributes with the expected values
    assertEquals(originalProduct.getName(), updatedProduct.getName(), "Product name should match");
    assertEquals(originalProduct.getPrice(), updatedProduct.getPrice(), "Product price should match");
    assertEquals(originalProduct.getQuantity(), updatedProduct.getQuantity(), "Product quantity should match");
  }

  @Test void FailiaureUpdateTest() throws SQLException
  {

    // Insert the product into the database
    ProductDAOImpl productDAO = ProductDAOImpl.getInstance();
    Product originalProduct = productDAO.creat("originalName1", 10.0, 5);

    // Update the attributes of the product
    originalProduct.setPrice(15.0);
    originalProduct.setQuantity(10);

    // Update the product in the database
    productDAO.update(originalProduct);

    // Retrieve the updated product from the database
    List<Product> updatedProducts = productDAO.readByName(originalProduct.getName());

    // Ensure there is only one updated product
    assertEquals(1, updatedProducts.size(), "Only one product should be updated");

    // Retrieve the updated product
    Product updatedProduct = updatedProducts.get(0);

    // Compare the updated attributes with the expected values
    assertEquals(originalProduct.getName(), updatedProduct.getName(), "Product name should match");
    assertEquals(originalProduct.getPrice(), updatedProduct.getPrice(), "Product price should match");
    assertEquals(originalProduct.getQuantity(), updatedProduct.getQuantity(), "Product quantity should match");
  }






}