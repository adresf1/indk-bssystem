package Model;

import Shared.TransferObject.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO
{
  Product creat(String name, double price, int quantity) throws SQLException;
  //Product readByName(String name) throws SQLException;
  List<Product> readByName(String searchString) throws SQLException;
  void update(Product product) throws SQLException;;
  void delete(Product product) throws SQLException;

}
