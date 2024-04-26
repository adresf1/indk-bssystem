package Model;

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
    try(Connection connection = getConnection()){
     PreparedStatement statement = connection.prepareStatement("insert into " + warehouseDB + ".products(name, ID, category, productDescription, productionDate, expirationDate, barcode, price, quantity, lowStock, unitType ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

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
   try(Connection connection = getConnection()){
     PreparedStatement statement = connection.prepareStatement("select * from products where name like ?");
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

       Product product = new Product(name, ID, category, productDescription, productionDate, expirationDate, barcode, price, quantity, lowStock, unitType);
       products.add(product);
     }
     return products;
   }
  }

  private static Connection getConnection() throws SQLException
  {
    return DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres?currentSchema=WareHouse",
        "postgres", "KarlDen12.");
  }

  @Override public void update(Product product) throws SQLException
  {
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("update products set name=?, price=?, quantity=? where name=?");
      statement.setString(1, product.getName());
      statement.setDouble(2, product.getPrice());
      statement.setInt(3, product.getQuantity());
      statement.setString(4, product.getName());
      statement.executeUpdate();
    }

  }

  @Override public void delete(Product product) throws SQLException
  {
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("delete from products where name like ?");
      statement.setString(1, product.getName());
      statement.executeUpdate();
    }
  }

}
