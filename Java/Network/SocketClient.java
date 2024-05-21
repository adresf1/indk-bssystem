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
            Socket socket = new Socket("localhost", 2910);
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
            while(true){
                System.out.println("Entering while loop");
                Request request = (Request) this.inFromServer.readObject();
                System.out.println("Listen to server request from server " + request.getType());
                if ("ProductAdded".equals(request.getType())){
                    Product reservedProduct = (Product) request.getArg();
                    support.firePropertyChange("ProductAdded", null, reservedProduct); // Notify listeners
                    System.out.println("Confirmation received from server");
                }
                else if ("getProduct".equals(request.getType())) {
                  String product = (String) request.getArg();
                  System.out.println("Product received: " + product);
                }
                else if ("searchProductByID".equals(request.getType())){
                    System.out.println("listenToServer: searchProductByID");
                }
                else if ("allProductsReturned".equals(request.getType())) {
                    System.out.println("Entered allProductsReturned");
                    support.firePropertyChange("allProductsReturned_event",null, request.getArg());
                }
                else if ("reservedProduct".equals(request.getType())) {
                    System.out.println("entered reservedProduct");
                    support.firePropertyChange("reservedProduct_event",null, request.getArg());
                }
                else if ("boughtProducts".equals(request.getType())) {
                    System.out.println("Entered boughtProducts");
                    support.firePropertyChange("allProductsBought_event",null, request.getArg());
                }
                else if ("removedProduct".equals(request.getType())) {
                    System.out.println("Entered removedProduct");
                    support.firePropertyChange("removedProduct_event", null, request.getArg());
                } else {
                  System.out.println("Request type not recognized ");
                }
            }
        } catch (IOException|ClassNotFoundException e) {
          System.err.println("Error listening to server: " + e.getMessage());
          e.printStackTrace();
        }

    }

    public PropertyChangeSupport getSupport(){
        return this.support;
    }

    @Override
    public void startClient() {
    }

   @Override
    public String getProduct(String id) {
        try {
          while(true)
          {
            outToServer.writeObject(new Request("getProduct", id));
            outToServer.flush();
            Request response = (Request) inFromServer.readObject();
            System.out.println("getProduct: " + response.getArg().toString());
            return  (String) response.getArg();
          }

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

  public void reserveProductByID(String id) {
    try {
      outToServer.writeObject(new Request("ProductAdded", id));
      outToServer.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

    @Override
    public ArrayList<Product> getReservedProducts () {
        return shoppingcart;
    }

        public void requestAllProducts() {
        try {
            outToServer.writeObject(new Request("requestAllProducts", null));
            outToServer.flush();
        }
        catch (IOException e)
        {
          throw new RuntimeException(e);
        }
    }

    @Override
    public void requestToReserveProduct(Product p) throws IOException {
        outToServer.writeObject((new Request("requestToReserveProduct",p)));
        outToServer.flush();
    }

    @Override
    public void requestBuyAllProducts(ArrayList<Product> products) throws IOException {
        outToServer.writeObject(new Request("requestToBuyAllProducts", products));
        outToServer.flush();
    }

    @Override
    public void requestRemoveProduct(Product product) throws IOException {
        outToServer.writeObject(new Request("requestToRemoveProduct", product));
        outToServer.flush();
    }

    public ArrayList<Product> searchProductByID(String ID) {
        try {
            outToServer.writeObject(new Request("searchProductByID", ID));
            outToServer.flush();
            Request response = (Request) inFromServer.readObject();
            if ("searchResults".equals(response.getType())) {
                 return (ArrayList<Product>) response.getArg();

            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
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
