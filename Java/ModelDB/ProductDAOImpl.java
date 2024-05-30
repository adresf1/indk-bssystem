package ModelDB;

import Shared.TransferObject.Product;
import Shared.Util.MyDate;
import com.sun.jdi.Value;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;
import static sun.security.x509.PolicyInformation.ID;

public class ProductDAOImpl implements ProductDAO
{

  private static ProductDAOImpl instance;
  private String warehouseDB;

  private ProductDAOImpl()
  {
    try
    {
      //warehouseDB = "warehouse"
      warehouseDB = "testWarehouse";
      DriverManager.registerDriver(new org.postgresql.Driver());
    }
    catch (SQLException e)
    {
      throw new RuntimeException(e);
    }
  }

  public String getWarehouseDB()
  {
    return warehouseDB;
  }

  public static synchronized ProductDAOImpl getInstance() throws SQLException
  {
    if (instance == null)
    {
      instance = new ProductDAOImpl();
    }
    return instance;
  }

  public void creatProduct(Product product) throws SQLException
  {
    try (Connection connection = getConnection(warehouseDB))
    {
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO " + warehouseDB + ".products(products_ID, products_name, products_category, products_productDescription, products_productionDate, products_expirationDate, products_barcode, products_price, products_quantity, products_lowStock, products_unitType ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

      statement.setString(1, product.getID());
      statement.setString(2, product.getName());
      statement.setInt(3, product.getCategory());
      statement.setString(4, product.getProductDescription());
      statement.setString(5, product.getProductionDate().toString());
      statement.setString(6, product.getExpirationDate().toString());
      statement.setInt(7, product.getBarcode());
      statement.setDouble(8, product.getPrice());
      statement.setDouble(9, product.getQuantity());
      statement.setDouble(10, product.getLowStock());
      statement.setString(11, product.getUnitType());
      statement.executeUpdate();
    }
  }

  @Override public Product creat(String ID, String name, int category, String productDescription, MyDate productionDate, MyDate expirationDate,
      int barcode, double price, double quantity, double lowStock, String unitType) throws SQLException
  {
    try (Connection connection = getConnection(warehouseDB))
    {
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO " + warehouseDB + ".products(products_ID, products_name, products_category, products_productDescription, products_productionDate, products_expirationDate, products_barcode, products_price, products_quantity, products_lowStock, products_unitType ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

      statement.setString(1, ID);
      statement.setString(2, name);
      statement.setInt(3, category);
      statement.setString(4, productDescription);
      statement.setString(5, productionDate.toString());
      statement.setString(6, expirationDate.toString());
      statement.setInt(7, barcode);
      statement.setDouble(8, price);
      statement.setDouble(9, quantity);
      statement.setDouble(10, lowStock);
      statement.setString(11, unitType);
      statement.executeUpdate();
      return new Product(name, ID, category, productDescription, productionDate,
          expirationDate, barcode, price, quantity, lowStock, unitType);
    }
  }

  @Override public List<Product> readByName(String searchString)
      throws SQLException
  {
    try (Connection connection = getConnection(warehouseDB))
    {
      PreparedStatement statement = connection.prepareStatement(
          "SELECT * FROM products WHERE products_name LIKE ?");
      statement.setString(1, "%" + searchString + "%");
      ResultSet resultSet = statement.executeQuery();
      ArrayList<Product> products = new ArrayList<>();
      while (resultSet.next())
      {
        String name = resultSet.getString("products_name");
        String ID = resultSet.getString("products_id");
        double price = resultSet.getDouble("products_price");
        double quantity = resultSet.getDouble("products_quantity");
        int category = resultSet.getInt("products_category");
        String productDescription = resultSet.getString(
            "products_productDescription");
        String productionDate = resultSet.getString("products_productionDate");
        String expirationDate = resultSet.getString("products_expirationDate");
        int barcode = resultSet.getInt("products_barcode");
        double lowStock = resultSet.getDouble("products_lowStock");
        String unitType = resultSet.getString("products_unitType");

        Product product = new Product(name, ID, category, productDescription,
            MyDate.fromString(productionDate), MyDate.fromString(expirationDate), barcode, price, quantity,
            lowStock, unitType);
        products.add(product);
      }
      return products;
    }
  }

  private static Connection getConnection(String database) throws SQLException
  {
    String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=" + database;
    System.out.println(url);
    return DriverManager.getConnection(url, "postgres", "Kika12345");
  }

  @Override public void update(Product product) throws SQLException
  {
    try (Connection connection = getConnection(warehouseDB))
    {
      PreparedStatement statement = connection.prepareStatement(
          "UPDATE products SET  products_ID=?,products_name=?,products_category=?, products_productDescription=?, products_productionDate=?, products_expirationDate=?, products_barcode=?, products_price=?, products_quantity=?, products_lowStock=?, products_unitType=? where products_ID=?");
      statement.setString(1, product.getID());
      statement.setString(2, product.getName());
      statement.setInt(3, product.getCategory());
      statement.setString(4, product.getProductDescription());
      statement.setString(5, product.getProductionDate().toString());
      statement.setString(6, product.getExpirationDate().toString());
      statement.setInt(7, product.getBarcode());
      statement.setDouble(8, product.getPrice());
      statement.setDouble(9, product.getQuantity());
      statement.setDouble(10, product.getLowStock());
      statement.setString(11, product.getUnitType());
      statement.setString(12, product.getID());
      statement.executeUpdate();
    }

  }

  @Override public void delete(Product product) throws SQLException
  {
    try (Connection connection = getConnection(warehouseDB))
    {
      if (DoesPrimaryKeyExitsInTable(warehouseDB, "products", product.getID()))
      {
        PreparedStatement statement = connection.prepareStatement(
            "DELETE FROM products WHERE products_id LIKE ?");
        statement.setString(1, product.getID());
        statement.executeUpdate();
      }
      else
      {
        throw new SQLException("Element does not exits");
      }
    }
  }

  public static boolean DoesPrimaryKeyExitsInTable(String warehouseDB, String table, String value) throws SQLException
  {
    try (Connection connection = getConnection(warehouseDB))
    {
      PreparedStatement statement = connection.prepareStatement(
          "SELECT COUNT(*) AS count FROM " + table + " WHERE products_ID = '"
              + value + "'");
      ResultSet resultSet = statement.executeQuery();
      resultSet.next();
      int count = resultSet.getInt("count");
      // statement.executeUpdate();
      if (count > 0)
      {
        return true;
      }
      else
      {
        return false;
      }
    }
  }

  public static int numberOfElementsInTable(String warehouseDB, String table, String value) throws SQLException
  {
    try (Connection connection = getConnection(warehouseDB))
    {
      PreparedStatement statement = connection.prepareStatement(
              "SELECT COUNT(*) AS count FROM " + table);
      ResultSet resultSet = statement.executeQuery();
      resultSet.next();
      return resultSet.getInt("count");
      // statement.executeUpdate();
    }
  }

  public ArrayList<Product> getAllProducts()
          throws SQLException{

    try (Connection connection = getConnection(warehouseDB))
    {
      PreparedStatement statement = connection.prepareStatement(
              "SELECT * FROM products ");
      ResultSet resultSet = statement.executeQuery();
      ArrayList<Product> products = new ArrayList<>();
      while (resultSet.next())
      {
        String name = resultSet.getString("products_name");
        String ID = resultSet.getString("products_id");
        double price = resultSet.getDouble("products_price");
        double quantity = resultSet.getDouble("products_quantity");
        int category = resultSet.getInt("products_category");
        String productDescription = resultSet.getString(
                "products_productDescription");
        String productionDate = resultSet.getString("products_productionDate");
        String expirationDate = resultSet.getString("products_expirationDate");
        int barcode = resultSet.getInt("products_barcode");
        double lowStock = resultSet.getDouble("products_lowStock");
        String unitType = resultSet.getString("products_unitType");

        Product product = new Product(name, ID, category, productDescription,
                MyDate.fromString(productionDate), MyDate.fromString(expirationDate), barcode, price, quantity,
                lowStock, unitType);
        products.add(product);
      }
      return products;
    }
  }
  public Product getProduct(String id)
  {
    try (Connection connection = getConnection(warehouseDB))
    {
      PreparedStatement statement = connection.prepareStatement(
          "SELECT * FROM products WHERE products_id LIKE ?");
      statement.setString(1, id);

      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next())
      {
        String ID = resultSet.getString("products_id");
        String name = resultSet.getString("products_name");
        int category = resultSet.getInt("products_category");
        String productDescription = resultSet.getString(
            "products_productDescription");
        String productionDate = resultSet.getString("products_productionDate");
        String expirationDate = resultSet.getString("products_expirationDate");
        int barcode = resultSet.getInt("products_barcode");
        double price = resultSet.getDouble("products_price");
        double quantity = resultSet.getDouble("products_quantity");
        double lowStock = resultSet.getDouble("products_lowStock");
        String unitType = resultSet.getString("products_unitType");

        return new Product(name, ID, category, productDescription,
            MyDate.fromString(productionDate), MyDate.fromString(expirationDate),
            barcode, price, quantity, lowStock, unitType);
      }
    }
    catch (SQLException e)
    {
      throw new RuntimeException("Error fetching product by ID", e);
    }
    return null;
  }





  public ArrayList<Product> searchProductByID(String id) {
    try (Connection connection = getConnection(warehouseDB)) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM products WHERE products_id = ?");
      statement.setString(1, id);
      ResultSet resultSet = statement.executeQuery();
      ArrayList<Product> products = new ArrayList<>();
      while (resultSet.next()) {
        Product product = new Product(
                resultSet.getString("products_name"),
                resultSet.getString("products_id"),
                resultSet.getInt("products_category"),
                resultSet.getString("products_productDescription"),
                MyDate.fromString(resultSet.getString("products_productionDate")),
                MyDate.fromString(resultSet.getString("products_expirationDate")),
                resultSet.getInt("products_barcode"),
                resultSet.getDouble("products_price"),
                resultSet.getDouble("products_quantity"),
                resultSet.getDouble("products_lowStock"),
                resultSet.getString("products_unitType")
        );
        products.add(product);
      }
      return products;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
