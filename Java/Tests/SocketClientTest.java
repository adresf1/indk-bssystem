package Tests;

import Network.SocketClient;
import Shared.TransferObject.Product_Legacy;
import Shared.TransferObject.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SocketClientTest {
    private SocketClient socketClient;
    private ObjectOutputStream outToServerStub;
    private ByteArrayOutputStream outputStream;
    private ObjectInputStream inputStream;
    private ByteArrayInputStream inToClientStub;


    @BeforeEach
    public void setUp() throws IOException {
        byte[] Arr = new byte[10];
        // Create a ByteArrayOutputStream to capture the data written to the ObjectOutputStream
        outputStream = new ByteArrayOutputStream();
        // Create ObjectOutputStream using the ByteArrayOutputStream
        outToServerStub = new ObjectOutputStream(outputStream);
        // Create SocketHandler with the stub ObjectOutputStream
        socketClient = new SocketClient(outToServerStub);

    }
    @Test
    public void testGetProduct() {

    }

    @Test
    void testBuyProduct() {
    }

    @Test
    public void testMoveToBasket_invalidInput() {
        Product_Legacy product = new Product_Legacy("Beans", 10.0,1);
        Request request = new Request("BuyProduct", null);

    try {
    socketClient.moveToBasket(request);
    } catch (Exception e) {
        e.printStackTrace();
    }

        String responseString = new String(outputStream.toByteArray());
        System.out.println("Response: " + responseString);
        assertEquals(0,socketClient.getBoughtProducts().size());
    }

    @Test
    public void testMoveToBasket_validInput() {
        Product_Legacy product = new Product_Legacy("Beans", 10.0,1);
        Request request = new Request("BuyProduct", product);

        try {
            socketClient.moveToBasket(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String responseString = new String(outputStream.toByteArray());
        System.out.println("Response: " + responseString);
        assertEquals(1,socketClient.getBoughtProducts().size());
    }

    @Test
    public void testMoveToBasket_manyValidInput() {
        Product_Legacy product = new Product_Legacy("Beans", 10.0,1);
        Request request = new Request("BuyProduct", product);
        Request request1 = new Request("BuyProduct", product);
        Request request2 = new Request("BuyProduct", product);

        try {
            socketClient.moveToBasket(request);
            socketClient.moveToBasket(request1);
            socketClient.moveToBasket(request2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String responseString = new String(outputStream.toByteArray());
        System.out.println("Response: " + responseString);
        assertEquals(3,socketClient.getBoughtProducts().size());
    }

    @Test
    void testGetBoughtProducts() {
    }
}