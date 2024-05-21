package Server.networking;
import ModelDB.ProductDAO;
import ModelDB.ProductDAOImpl;
import Server.model.ReserveManager;
import Shared.TransferObject.Product;
import Shared.TransferObject.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SocketHandler implements Runnable {
    private Socket socket;
    private ReserveManager reserveManager;
    private ObjectOutputStream outToClient;
    private ObjectInputStream inFromClient;
    private ConnectionPool connectionPool;
    private ProductDAOImpl productDAOImpl;

    // Define a map to map request types to corresponding actions
    private Map<String, RequestHandler> requestHandlers;

    public SocketHandler(Socket socket, ReserveManager reserveManager, ConnectionPool connectionPool, ProductDAOImpl productDAOImpl) {
        this.socket = socket;
        this.reserveManager = reserveManager;
        this.connectionPool = connectionPool;
        this.productDAOImpl = productDAOImpl;

        // Initialize the maps
        requestHandlers = new HashMap<>();
        requestHandlers.put("getProduct", this::handleGetProducts);
        requestHandlers.put("searchProductByID", this::handleSearchProductByID);
        requestHandlers.put("ProductAdded", this::handleProductAdded);
        requestHandlers.put("requestAllProducts", this::handleGetAllProducts);
        requestHandlers.put("requestToReserveProduct", this::handleRequestToReserveProduct);
        requestHandlers.put("requestToBuyAllProducts", this::handleBuyAllProducts);
        requestHandlers.put("requestToRemoveProduct", this::handleRemoveProduct);

        try {
            outToClient = new ObjectOutputStream(socket.getOutputStream());
            outToClient.flush();
            inFromClient = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Request request = (Request) inFromClient.readObject();
                System.out.println("SocketHandler Run received request" + request.getType());
                String requestType = request.getType();
                RequestHandler handler = requestHandlers.get(requestType);
                if (handler != null) {
                    handler.handle(request);
                } else {
                    System.out.println("Request Type not recognized: " + requestType);
                }
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.err.println("Error listening to server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Handler metode for "getProducts" request
    private void handleGetProducts(Request request) throws IOException {
        String ID = (String) request.getArg();
        Product product = productDAOImpl.getProduct(ID);
        System.out.println("Product type being sent: "+ product.getID());
        outToClient.writeObject(new Request("getProduct", product));
        outToClient.flush();
    }

    // Handler metode for "searchProductByID" request
    private void handleSearchProductByID(Request request) throws IOException {
        String ID = (String) request.getArg();
        Product product1 = productDAOImpl.getProduct("1");
        Product product2 = productDAOImpl.getProduct("2");
        Product product3 = productDAOImpl.getProduct("3");
        Product product4 = productDAOImpl.getProduct("4");
        ArrayList<Product> searchResults = new ArrayList<>();
        searchResults.add(product1);
        searchResults.add(product2);
        searchResults.add(product3);
        searchResults.add(product4);
        outToClient.writeObject(new Request("searchResults", searchResults));
        outToClient.flush();
    }

    private void handleGetAllProducts(Request request) throws IOException, SQLException {
        ArrayList<Product> allProducts = productDAOImpl.getAllProducts();
        outToClient.writeObject(new Request("allProductsReturned", allProducts));
        outToClient.flush();
    }

    // Handler metode for "ProductAdded" request
    private void handleProductAdded(Request request) throws IOException {
        Product requestedProduct = (Product) request.getArg();
        Product reservedProduct = reserveManager.reserveProduct(requestedProduct);
        if (reservedProduct != null) {
            outToClient.writeObject(new Request("ProductAdded", reservedProduct));
            outToClient.flush();
        }
    }

    private void handleRequestToReserveProduct(Request request) throws IOException, SQLException {
        Product requestedProductToReserve = (Product) request.getArg();
        Product productDB = productDAOImpl.getProduct(requestedProductToReserve.getID());
        productDB.setQuantity(productDB.getQuantity() - requestedProductToReserve.getQuantity());
        productDAOImpl.update(productDB);
        if (requestedProductToReserve != null){
            outToClient.writeObject(new Request("reservedProduct",requestedProductToReserve));
            outToClient.flush();

        }
        Request updateAllItems = new Request("allProductsReturned", productDAOImpl.getAllProducts());
        outToClient.writeObject(updateAllItems);
    }

    private void handleBuyAllProducts(Request request) throws IOException {
        ArrayList<Product> requestToBuyAllProducts = (ArrayList<Product>) request.getArg();
        System.out.println("Products have been bought");
        outToClient.writeObject(new Request("boughtProducts",requestToBuyAllProducts));
        outToClient.flush();
    }

    private void handleRemoveProduct(Request request) throws SQLException, IOException {
        Product requestedProductToRemove = (Product) request.getArg();
        Product productDB = productDAOImpl.getProduct(requestedProductToRemove.getID());
        productDB.setQuantity(productDB.getQuantity() + requestedProductToRemove.getQuantity());
        productDAOImpl.update(productDB);
        //TODO: Handle if delete throws SQL-error
        outToClient.writeObject(new Request("removedProduct",requestedProductToRemove));
        Request updateAllItems = new Request("allProductsReturned", productDAOImpl.getAllProducts());
        outToClient.writeObject(updateAllItems);
        outToClient.flush();
    }

    public void sendRequest(Request request) {
        try {
            outToClient.writeObject(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Functional interface for request handlers
    private interface RequestHandler {
        void handle(Request request) throws IOException, SQLException;
    }
}
