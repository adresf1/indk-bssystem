package Tests;

import ModelDB.ProductDAOImpl;
import Shared.TransferObject.Product;
import Shared.Util.MyDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class ProductDAOImplTest
{
  @BeforeEach
  public void setUp() throws IOException, SQLException {
    ProductDAOImpl productDAO = ProductDAOImpl.getInstance();

    String[][] productsData = {
        {"1", "Brød", "123", "Wholefood", "01/01/2020", "02/08/2020", "234565", "3.95","7.25","5.55","Ttt"},
        {"2", "Mælk", "234", "Grocery", "10/01/2020", "23/02/2020", "345064","7.95", "8.65", "2.25", "L"},
        {"3", "Æg", "132", "Grocery", "11/01/2020", "27/01/2020", "45063","8.95", "9.55", "2.25", "Kg"},
        {"4", "Bacon Æg", "132", "Grocery", "11/01/2020", "27/01/2020", "45063","8.95", "9.55", "2.25", "Kg"}
    };

    for (String[] productData : productsData) {
      String productId = productData[0];
      String productName = productData[1];
      // Check if the primary key exists in the table
      if (productDAO.DoesPrimaryKeyExitsInTable(productDAO.getWarehouseDB(),"products", productId)) {
        // If it exists, update the product
        Product product = new Product(productName, productId, Integer.parseInt(productData[2]), productData[3],
            MyDate.fromString(productData[4]), MyDate.fromString(productData[5]),
            Integer.parseInt(productData[6]), Double.parseDouble(productData[7]),
            Double.parseDouble(productData[8]), Double.parseDouble(productData[9]), productData[10]);
        productDAO.update(product);
      } else {
        // If it doesn't exist, create the product
        Product product = new Product(productName, productId, Integer.parseInt(productData[2]), productData[3],
            MyDate.fromString(productData[4]), MyDate.fromString(productData[5]),
            Integer.parseInt(productData[6]), Double.parseDouble(productData[7]),
            Double.parseDouble(productData[8]), Double.parseDouble(productData[9]), productData[10]);
        productDAO.creatProduct(product);
      }
    }
  }

  /*@Test void creatProductTest() throws SQLException, SQLException
  {
    ProductDAOImpl productDAO = ProductDAOImpl.getInstance();

    Product createdProduct = productDAO.creat("1","Brød",2,"Heee", MyDate.fromString("01/01/2020"),MyDate.fromString("01/01/2020"),123,23.95,10.95,11.86,"DKK");
    assertNotNull(createdProduct, "Created product should not be null");
    assertEquals("Brød", createdProduct.getName(), "Product name should match");
    //assertEquals(10.95, createdProduct.getPrice(), 0.001, "Product price should match");
   // assertEquals(20, createdProduct.getQuantity(), "Product quantity should match");
  }
  @Test void TestIfPrimaryKeyExits() throws SQLException
  {
    ProductDAOImpl productDAO = ProductDAOImpl.getInstance();

    assertEquals(true,productDAO.DoesPrimaryKeyExitsInTable(productDAO.getWarehouseDB(),"products","2"));
  }
//*/
  @Test void deleteProductTest() throws SQLException
  {
      ProductDAOImpl productDAO = ProductDAOImpl.getInstance();
        Product product = new Product();
        product.setID("3");
      productDAO.delete(product);
      product.setID("99");
      assertEquals(false, productDAO.DoesPrimaryKeyExitsInTable(productDAO.getWarehouseDB(),"products","3"));
      assertThrows(SQLException.class,()->productDAO.delete(product));
  }
//
  @Test void reabyNameTest() throws SQLException
  {
    ProductDAOImpl productDAO = ProductDAOImpl.getInstance();
    List<Product> products = new ArrayList<>();
    List<Product> products1 = new ArrayList<>();
    products =  productDAO.readByName("Æg");

  products1 = productDAO.readByName("hejj");

    assertEquals(2, products.size());
    assertEquals(0,products1.size());

  }
//  @Test void FailiaureReabyNameTest() throws SQLException
//  {
//    ProductDAOImpl productDAO = ProductDAOImpl.getInstance();
//    Product productToRead = new Product("Milk",0,0);
//    productDAO.readByName(productToRead.getName());
//    assertEquals("brød", productToRead.getName(), "Product name should match");
//
//  }
//
//  @Test void updateTest() throws SQLException
//  {
//
//    // Insert the product into the database
//    ProductDAOImpl productDAO = ProductDAOImpl.getInstance();
//    Product originalProduct = productDAO.creat("originalName1", 10.0, 5);
//
//    // Update the attributes of the product
//    originalProduct.setPrice(15.0);
//    originalProduct.setQuantity(10);
//
//    // Update the product in the database
//    productDAO.update(originalProduct);
//
//    // Retrieve the updated product from the database
//    List<Product> updatedProducts = productDAO.readByName(originalProduct.getName());
//
//    // Ensure there is only one updated product
//    assertEquals(1, updatedProducts.size(), "Only one product should be updated");
//
//    // Retrieve the updated product
//    Product updatedProduct = updatedProducts.get(0);
//
//    // Compare the updated attributes with the expected values
//    assertEquals(originalProduct.getName(), updatedProduct.getName(), "Product name should match");
//    assertEquals(originalProduct.getPrice(), updatedProduct.getPrice(), "Product price should match");
//    assertEquals(originalProduct.getQuantity(), updatedProduct.getQuantity(), "Product quantity should match");
//  }
//
//  @Test void FailiaureUpdateTest() throws SQLException
//  {
//
//    // Insert the product into the database
//    ProductDAOImpl productDAO = ProductDAOImpl.getInstance();
//    Product originalProduct = productDAO.creat("originalName1", 10.0, 5);
//
//    // Update the attributes of the product
//    originalProduct.setPrice(15.0);
//    originalProduct.setQuantity(10);
//
//    // Update the product in the database
//    productDAO.update(originalProduct);
//
//    // Retrieve the updated product from the database
//    List<Product> updatedProducts = productDAO.readByName(originalProduct.getName());
//
//    // Ensure there is only one updated product
//    assertEquals(1, updatedProducts.size(), "Only one product should be updated");
//
//    // Retrieve the updated product
//    Product updatedProduct = updatedProducts.get(0);
//
//    // Compare the updated attributes with the expected values
//    assertEquals(originalProduct.getName(), updatedProduct.getName(), "Product name should match");
//    assertEquals(originalProduct.getPrice(), updatedProduct.getPrice(), "Product price should match");
//    assertEquals(originalProduct.getQuantity(), updatedProduct.getQuantity(), "Product quantity should match");
  }






