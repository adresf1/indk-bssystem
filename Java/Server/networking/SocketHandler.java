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

        // Initialize the map
        requestHandlers = new HashMap<>();
        requestHandlers.put("getAllProducts", this::handleGetAllProducts);
        requestHandlers.put("searchProductByName", this::handleSearchProductByName);
        requestHandlers.put("ProductAdded", this::handleProductAdded);

        try {
            outToClient = new ObjectOutputStream(socket.getOutputStream());
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
                String requestType = request.getType();
                RequestHandler handler = requestHandlers.get(requestType);
                if (handler != null) {
                    handler.handle(request);
                } else {
                    System.out.println("Request Type not recognized: " + requestType);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Handler metode for "getAllProducts" request
    private void handleGetAllProducts(Request request) throws IOException {
        ArrayList<Product> allProducts = productDAOImpl.getAllProducts();
        outToClient.writeObject(new Request("allProducts", allProducts));
    }

    // Handler metode for "searchProductByName" request
    private void handleSearchProductByName(Request request) throws IOException {
        String ID = (String) request.getArg();
        ArrayList<Product> searchResults = productDAOImpl.searchProductByID(ID);
        outToClient.writeObject(new Request("searchResults", searchResults));
    }

    // Handler method for "ProductAdded" request
    private void handleProductAdded(Request request) throws IOException {
        Product requestedProduct = (Product) request.getArg();
        Product reservedProduct = reserveManager.reserveProduct(requestedProduct);
        if (reservedProduct != null) {
            outToClient.writeObject(new Request("ProductAdded", reservedProduct));
        }
    }

    // Functional interface for request handlers
    private interface RequestHandler {
        void handle(Request request) throws IOException;
    }
}