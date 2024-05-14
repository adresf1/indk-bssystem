    package Server.model;

    import Shared.TransferObject.Product;

    import java.util.ArrayList;

    public interface ReserveManager {

        ArrayList<Product> getProducts();

        Product reserveProduct(Product requestedProduct);
    }
