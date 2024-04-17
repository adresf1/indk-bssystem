package Server.networking;

import Server.model.BuyingManager;
import Shared.TransferObject.Product;
import Shared.TransferObject.Request;
import Shared.Util.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketHandler implements Runnable{
    private Socket socket;

    private BuyingManager buyingManager;
    private ObjectOutputStream outToClient;

    private ObjectInputStream inFromClient;

    private ConnectionPool connectionPool;

    public SocketHandler(Socket socket, BuyingManager buyingManager, ConnectionPool connectionPool) {
        this.socket = socket;
        this.buyingManager = buyingManager;
        this.connectionPool = connectionPool;


        try {
            outToClient = new ObjectOutputStream(socket.getOutputStream());
            inFromClient = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {

    }

    public void buyProduct(Request request) throws IOException {
        Product productToBuy = (Product) request.getArg();
        // Confirm the purchase to the client
        outToClient.writeObject(new Response("ProductBought", null));
        // Print a message for testing purposes
        System.out.println("SocketHandler speaking: buyProduct");
    }
}
