package Server.networking;
import Shared.TransferObject.Request;
import java.io.IOException;
import java.util.ArrayList;

public class ConnectionPool {
    private final ArrayList<SocketHandler> serverConnections;

    public ConnectionPool() {
        this.serverConnections = new ArrayList<>();

    }

    public void add(SocketHandler serverConnection){
        serverConnections.add(serverConnection);
    }

    public void broadcast(Request request, SocketHandler serverConnectionIgnore) throws IOException {
        for (SocketHandler connection: serverConnections){
            if (connection != serverConnectionIgnore){
                connection.buyProduct(request);
            }
        }
    }
}
