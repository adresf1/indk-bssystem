package ModelDB;

import Shared.TransferObject.Product;
import Shared.Util.MyDate;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO
{
  //Todo: Should this be Product class? would be more scalable instead of using primitive types as parameters
  Product creat(String name, String ID, int category, String productDescription, MyDate productionDate, MyDate expirationDate,
                int barcode, double price, double quantity, double lowStock, String unitType) throws SQLException;
  //Product readByName(String name) throws SQLException;
  List<Product> readByName(String searchString) throws SQLException;
  void update(Product product) throws SQLException;;
  void delete(Product product) throws SQLException;

}
