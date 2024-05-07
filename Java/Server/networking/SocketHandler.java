package Server.networking;
import Server.model.ReserveManager;
import Shared.TransferObject.Product;
import Shared.TransferObject.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;



public class SocketHandler implements Runnable{
    private Socket socket;

    private ReserveManager reserveManager;
    private ObjectOutputStream outToClient;

    private ObjectInputStream inFromClient;

    private ConnectionPool connectionPool;

    public SocketHandler(Socket socket, ReserveManager reserveManager, ConnectionPool connectionPool) {
        this.socket = socket;
        this.reserveManager = reserveManager;
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

                if ("ProductAdded".equals(request.getType())) {
                    Product requestedProduct = (Product) request.getArg();
                    Product reservedProduct = reserveManager.reserveProduct(requestedProduct);
                    if (reservedProduct != null) {
                        outToClient.writeObject(new Request("ProductAdded", reservedProduct));
                    }
                } else {
                    System.out.println("Request Type not recognized: " + request.getType());
                }
            } catch (RuntimeException|IOException|ClassNotFoundException e){
                throw new RuntimeException(e);
            }
        }
    }

    public void reserveProduct(Request request) throws IOException {
        Product productToReserve = (Product) request.getArg();
        outToClient.writeObject(new Request("ProductAdded", productToReserve));
        System.out.println("SocketHandler speaking: reserveProduct");
    }
}
