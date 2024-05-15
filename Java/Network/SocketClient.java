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
            outToServer.flush();
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
           // this.outToServer.writeObject(new Request("Listener", null));

            while(true){
                Request request = (Request) this.inFromServer.readObject();
                System.out.println("Listen to server request from server " + request.getType());
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
    public Product getProduct() {
        try {
            outToServer.writeObject(new Request("getProduct", null));
            outToServer.flush();
            Request response = (Request) inFromServer.readObject();
            return  (Product) response.getArg();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void reserveProduct(Product product) {
        try {
            outToServer.writeObject(new Request("ProductAdded", product));
            outToServer.flush();
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
    private void displayProducts(ArrayList<Product> products)
    {
        System.out.println("All Products:");
        for (Product product : products)
        {
            System.out.println(product.getName() + " - " + product.getPrice());
        }
    }


//        public void requestAllProducts() {
//        try {
//            outToServer.writeObject(new Request("getAllProducts", null));
//            outToServer.flush();
////            Request response = (Request) inFromServer.readObject();
////            if ("allProducts".equals(response.getType())) {
////                ArrayList<Product> allProducts = (ArrayList<Product>) response.getArg();
////                // Do something with the list of products
////                displayProducts(allProducts);
////            }
//        }
//        catch (IOException e)
//        {
//          throw new RuntimeException(e);
//        }
//        }

    public void searchProductByID(String ID) {
        try {
            outToServer.writeObject(new Request("searchProductByID", ID));
            outToServer.flush();
            Request response = (Request) inFromServer.readObject();
            if ("searchResults".equals(response.getType())) {
                ArrayList<Product> searchResults = (ArrayList<Product>) response.getArg();
                // Do something with the search results
                //displaySearchResults(searchResults);
            }
        } catch (IOException | ClassNotFoundException e) {
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
