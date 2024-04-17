package Server;

import Server.model.BuyingManager;
import Server.model.BuyingManagerImpl;
import Server.networking.ConnectionPool;
import Server.networking.SocketHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RunServer {

    public static void main(String[] args){
        try {


            System.out.println("Starting socket server");
            //SocketServer ss = new SocketServer(new MessageManagerImpl());
            ServerSocket welcomeSocket = new ServerSocket(2910);
            ConnectionPool connectionPool = new ConnectionPool();
            BuyingManager buyingManager = new BuyingManagerImpl();

            while (true){
                Socket socket = welcomeSocket.accept();
                SocketHandler socketHandler = new SocketHandler(socket, buyingManager ,connectionPool);
                connectionPool.add(socketHandler);
                System.out.println("Network.Client connected");
                new Thread(socketHandler).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
