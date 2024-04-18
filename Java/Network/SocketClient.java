package Network;

import Shared.TransferObject.Product;
import Shared.TransferObject.Request;

import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Objects;

public class SocketClient implements Client {
    private PropertyChangeSupport support;
    private ObjectOutputStream outToServer;

    private ObjectInputStream inFromServer;

    private ArrayList<Product> boughtProducts;

    public SocketClient() {

        support = new PropertyChangeSupport(this);
        boughtProducts = new ArrayList<>();

        try {
            Socket socket = new Socket("localHost", 2910);
            outToServer = new ObjectOutputStream(socket.getOutputStream());
            inFromServer = new ObjectInputStream(socket.getInputStream());
            new Thread(() -> listenToServer(null, null)).start();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SocketClient(ObjectOutputStream outToServer) {
        this.outToServer = outToServer;
        boughtProducts = new ArrayList<>();
    }

    private void listenToServer(ObjectOutputStream outToServer, ObjectInputStream inFromServer) {
        try {
            //this.outToServer.writeObject(new Request("Listener", null));

            while(true){
                Request request = (Request) this.inFromServer.readObject();
                if ("ProductBought".equals(request.getType())){
                    System.out.println("Confirmation received from server");
                    moveToBasket(request);
                    System.out.println(boughtProducts);
                }
                else {
                    System.out.println("Request type not recognized ");
                }

            }
        } catch (IOException|ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void startClient() {
    }

    @Override
    public ArrayList<Product> getProduct() {
        try {
            outToServer.writeObject(new Request("getProductList", null));
            Request response = (Request) inFromServer.readObject();
            return (ArrayList<Product>) response.getArg();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void buyProduct(Product product) {
        try {
            Request buyRequest = new Request("BuyProduct", product);
            outToServer.writeObject(buyRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void moveToBasket(Request request) {
        if (request.getArg() instanceof Product) {
            boughtProducts.add((Product) request.getArg());
        } else {
            throw new RuntimeException("request.getArg returned null with expected type being product");
        }
    }
    @Override
    public ArrayList<Product> getBoughtProducts () {
        return boughtProducts;
    }
}
