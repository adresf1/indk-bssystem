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

public class SocketClient implements Client {
    private PropertyChangeSupport support;
    private ObjectOutputStream outToServer, outToServer1;

    private ObjectInputStream inFromServer, inFromServer1;

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

    private void listenToServer(ObjectOutputStream outToServer, ObjectInputStream inFromServer) {
        try {
            this.outToServer.writeObject(new Request("Listener", null));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startClient() {
    }

    @Override
    public ArrayList<Product> getProduct() {
        try {
            outToServer.writeObject(new Request("Products", null));
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
            boughtProducts.add(product);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public ArrayList<Product> getBoughtProducts () {
        return boughtProducts;
    }
}
