package ModelDB;

import Shared.TransferObject.Product;
import Shared.Util.MyDate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

  public static synchronized ProductDAOImpl getInstance() throws SQLException{
    if (instance == null){
      instance = new ProductDAOImpl();
    }
    return instance;
  }

  @Override
  public Product creat(String name, String ID, int category, String productDescription, MyDate productionDate, MyDate expirationDate,
                                 int barcode, double price, double quantity, double lowStock, String unitType) throws SQLException
  {
    try(Connection connection = getConnection(warehouseDB)){
     PreparedStatement statement = connection.prepareStatement("INSERT INTO " + warehouseDB + ".products(name, ID, category, productDescription, productionDate, expirationDate, barcode, price, quantity, lowStock, unitType ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

     statement.setString(1, name);
     statement.setString(2,ID);
     statement.setInt(3,category);
     statement.setString(4,productDescription);
     statement.setString(5,productionDate.toString());
     statement.setString(6,expirationDate.toString());
     statement.setInt(7,barcode);
     statement.setDouble(8, price);
     statement.setDouble(9, quantity);
     statement.setDouble(10, lowStock);
     statement.setString(11,unitType);
     statement.executeUpdate();
     return  new Product(name, ID, category, productDescription, productionDate, expirationDate, barcode, price, quantity, lowStock, unitType);
    }
  }

  @Override public List<Product> readByName(String searchString)
      throws SQLException
  {
   try(Connection connection = getConnection(warehouseDB)){
     PreparedStatement statement = connection.prepareStatement("SELECT * FROM products WHERE name LIKE ?");
     statement.setString(1,"%"+searchString+"%");
     ResultSet resultSet = statement.executeQuery();
     ArrayList<Product> products = new ArrayList<>();
     while(resultSet.next()){
       String name = resultSet.getString("name");
       String ID = resultSet.getString("ID");
       double price = resultSet.getDouble("price");
       double quantity = resultSet.getDouble("quantity");
       int category = resultSet.getInt("category");
       String productDescription = resultSet.getString("productDescription");
       String productionDate = resultSet.getString("productionDate");
       String expirationDate = resultSet.getString("expirationDate");
       int barcode = resultSet.getInt("barcode");
       double lowStock = resultSet.getDouble("lowStock");
       String unitType = resultSet.getString("unitType");

       Product product = new Product(name, ID, category, productDescription, MyDate.fromString(productionDate), MyDate.fromString(expirationDate), barcode, price, quantity, lowStock, unitType);
       products.add(product);
     }
     return products;
   }
  }

  private static Connection getConnection(String database) throws SQLException
  {
    String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=" + database;
    return DriverManager.getConnection(
        url,
        "postgres", "KarlDen12.");
  }

  @Override public void update(Product product) throws SQLException
  {
    try(Connection connection = getConnection(warehouseDB)){
      PreparedStatement statement = connection.prepareStatement("UPDATE products SET name=?, ID=?, category=?, productDescription=?, productionDate=?, expirationDate=?, barcode=?, price=?, quantity=?, lowStock=?, unitType=? where ID=?");
      statement.setString(1, product.getName());
      statement.setString(2,product.getID());
      statement.setInt(3,product.getCategory());
      statement.setString(4,product.getProductDescription());
      statement.setString(5,product.getProductionDate().toString());
      statement.setString(6,product.getExpirationDate().toString());
      statement.setInt(7,product.getBarcode());
      statement.setDouble(8, product.getPrice());
      statement.setDouble(9, product.getQuantity());
      statement.setDouble(10, product.getLowStock());
      statement.setString(11, product.getUnitType());
      statement.executeUpdate();
    }

  }

  @Override public void delete(Product product) throws SQLException
  {
    try(Connection connection = getConnection(warehouseDB)){
      PreparedStatement statement = connection.prepareStatement("DELETE FROM products WHERE ID LIKE ?");
      statement.setString(1, product.getID());
      statement.executeUpdate();
    }
  }

}
