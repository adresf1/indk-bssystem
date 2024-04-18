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
        while(true){
            try {
                Request request = (Request) inFromClient.readObject();

                if ("BuyProduct".equals(request.getType())) {
                    System.out.println("Message Recieved of type: BuyProduct");
                    buyProduct(request);
                }
                else {
                    System.out.println("Request Type not recognized: " + request.getType());
                }
            } catch (RuntimeException|IOException|ClassNotFoundException e){
                throw new RuntimeException(e);
            }
        }


    }

    public void buyProduct(Request request) throws IOException {
        Product productToBuy = (Product) request.getArg();
        // Confirm the purchase to the client
        //TODO: vil du sende et response eller en request tilbage til client som  bekræftelse? Sockets kan ikke lide din klasse respons
        outToClient.writeObject(new Response("ProductBought", null));
        // Print a message for testing purposes
        System.out.println("SocketHandler speaking: buyProduct");
    }
}
