package Network;

import Shared.TransferObject.Product;
import Shared.TransferObject.Request;
import Shared.Util.Subject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Objects;

public class SocketClient implements Client, Subject
{
    private PropertyChangeSupport support;
    private ObjectOutputStream outToServer;

    private ObjectInputStream inFromServer;

    private ArrayList<Product> shoppingcart;

    public SocketClient() {

        try {
            Socket socket = new Socket("localHost", 2910);
            outToServer = new ObjectOutputStream(socket.getOutputStream());
            inFromServer = new ObjectInputStream(socket.getInputStream());
            shoppingcart = new ArrayList<>();
            support = new PropertyChangeSupport(this);
            new Thread(this::listenToServer).start();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SocketClient(ObjectOutputStream outToServer) {
        this.outToServer = outToServer;
    }

    private void listenToServer() {
        try {
            //this.outToServer.writeObject(new Request("Listener", null));

            while(true){
                Request request = (Request) this.inFromServer.readObject();
                if ("ProductAdded".equals(request.getType())){
                    Product reservedProduct = (Product) request.getArg();
                    shoppingcart.add(reservedProduct); // Add to cart
                    support.firePropertyChange("ProductAdded", null, reservedProduct); // Notify listeners
                    System.out.println("Confirmation received from server");
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
    public void reserveProduct(Product product) {
        try {
            outToServer.writeObject(new Request("ProductAdded", product));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    public void moveToBasket(Request request) {
        if (request.getArg() instanceof Product) {
            Product product = (Product) request.getArg();
            shoppingcart.add(product);
            support.firePropertyChange("ProductAdded", null, product); //
        } else {
            throw new RuntimeException("request.getArg returned null with expected type being product");
        }
    }
    */

    @Override
    public ArrayList<Product> getReservedProducts () {
        return shoppingcart;
    }

    public void requestAllProducts() {
        try {
            outToServer.writeObject(new Request("getAllProducts", null));
            // Handle response from server...
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchProductByID(String ID) {
        try {
            outToServer.writeObject(new Request("searchProductByID", ID));
            // Handle response from server...
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override public void addListener(String eventName,
        PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(eventName, listener);
    }

    @Override public void removeListener(String eventName,
        PropertyChangeListener listener)
    {
        support.removePropertyChangeListener(eventName, listener);

    }
}
