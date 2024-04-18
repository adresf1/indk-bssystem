package Tests;

import Server.model.BuyingManager;
import Server.model.BuyingManagerImpl;
import Server.networking.ConnectionPool;
import Server.networking.SocketHandler;
import Shared.TransferObject.Product;
import Shared.TransferObject.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.ByteArrayOutputStream;
import static org.junit.jupiter.api.Assertions.*;

public class SocketHandlerTest {

    private SocketHandler socketHandler;
    private ObjectOutputStream outToClientStub;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() throws IOException {
        // Create a ByteArrayOutputStream to capture the data written to the ObjectOutputStream
        outputStream = new ByteArrayOutputStream();
        // Create ObjectOutputStream using the ByteArrayOutputStream
        outToClientStub = new ObjectOutputStream(outputStream);
        // Create SocketHandler with the stub ObjectOutputStream
        socketHandler = new SocketHandler(outToClientStub);
    }

    @Test
    public void testBuyProduct_InvalidInput() throws IOException {
        // Create a request with a product to be bought
        Product product = new Product("Beans", 10.0,1);
        Request request = new Request("BuyProduct", null);

        // Call the method under test
        socketHandler.buyProduct(request);

        // Verify that the correct response was written to the ObjectOutputStream
        // We convert the captured bytes to a string for verification
        String responseString = new String(outputStream.toByteArray());
        System.out.println("Response: " + responseString);
        assert(responseString.contains("ProductBought"));
        assert(!responseString.contains("Beans")); // Assuming you are sending null as the data part of the response
    }


    @Test
    public void testBuyProduct_ValidInput() throws IOException {
        // Create a request with a product to be bought
        Product product = new Product("Beans", 10.0,1);
        Request request = new Request("BuyProduct", product);

        // Call the method under test
        socketHandler.buyProduct(request);

        // Verify that the correct response was written to the ObjectOutputStream
        // We convert the captured bytes to a string for verification
        String responseString = new String(outputStream.toByteArray());
        System.out.println("Response: " + responseString);
        assert(responseString.contains("ProductBought"));
        assert(responseString.contains("Beans")); // Assuming you are sending null as the data part of the response
    }
}
