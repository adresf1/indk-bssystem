package Server.networking;

import Server.model.BuyingManager;
import Shared.TransferObject.Product;
import Shared.TransferObject.Request;

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

    public SocketHandler(ObjectOutputStream outToClient) {
        this.outToClient = outToClient;
    }

    @Override
    public void run() {
        while(true){
            try {
                Request request = (Request) inFromClient.readObject();

                if ("BuyProduct".equals(request.getType())) {
                    System.out.println("Message Received of type: BuyProduct");
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
        outToClient.writeObject(new Request("ProductBought", productToBuy));
        System.out.println("SocketHandler speaking: buyProduct");
    }
}
