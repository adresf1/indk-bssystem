package Model;

import Shared.TransferObject.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO
{

  private static ProductDAOImpl instance;

  private ProductDAOImpl()
  {
    try
    {
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




  @Override public Product creat(String name, double price, int quantity) throws SQLException
  {
    try(Connection connection = getConnection()){
     PreparedStatement statement = connection.prepareStatement("insert into product (name, price, quantity) values (?, ?, ?)");

     statement.setString(1, name);
     statement.setDouble(2, price);
     statement.setInt(3, quantity);
     statement.executeUpdate();
     return  new Product(name, price, quantity);
    }
  }

  @Override public List<Product> readByName(String searchString)
      throws SQLException
  {
   try(Connection connection = getConnection()){
     PreparedStatement statement = connection.prepareStatement("select * from product where name like ?");
     statement.setString(1,"%"+searchString+"%");
     ResultSet resultSet = statement.executeQuery();
     ArrayList<Product> products = new ArrayList<>();
     while(resultSet.next()){
       String name = resultSet.getString("name");
       double price = resultSet.getDouble("price");
       int quantity = resultSet.getInt("quantity");

       Product product = new Product(name, price, quantity);
       products.add(product);
     }
     return products;
   }
  }

  private static Connection getConnection() throws SQLException
  {
    return DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/products?currentSchema?jdbc",
        "postgres", "1234");
  }

  @Override public void update(Product product) throws SQLException
  {
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("update product set name=?, price=?, quantity=? where id=?");
      statement.setString(1, product.getName());
      statement.setDouble(2, product.getPrice());
      statement.setInt(3, product.getQuantity());
      statement.executeUpdate();
    }

  }

  @Override public void delete(Product product) throws SQLException
  {
    try(Connection connection = getConnection()){
      PreparedStatement statement = connection.prepareStatement("delete from product where id=?");
      statement.setString(1, product.getName());
      statement.executeUpdate();
    }
  }
}
