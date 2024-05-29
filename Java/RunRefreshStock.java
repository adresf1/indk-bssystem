import ModelDB.ProductDAOImpl;
import Shared.TransferObject.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public class RunRefreshStock {

    public static void main(String[] args) throws SQLException {
        //Denne klasse tilføjer til quantity af varer.
        ProductDAOImpl DB = ProductDAOImpl.getInstance();
        ArrayList<Product> stock = DB.getAllProducts();
        for (Product product: stock) {
            product.setQuantity(product.getQuantity()+10);
            DB.update(product);
        }
    }

}
