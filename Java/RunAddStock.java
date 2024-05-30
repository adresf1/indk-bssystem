import ModelDB.ProductDAOImpl;
import Shared.TransferObject.Product;
import Shared.Util.MyDate;

import java.sql.SQLException;

public class RunAddStock {
    public static void main(String[] args) throws SQLException {

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
}
