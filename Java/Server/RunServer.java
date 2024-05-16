package Server;

import ModelDB.ProductDAOImpl;
import Server.model.ReserveManager;
import Server.model.ReserveManagerImpl;
import Server.networking.ConnectionPool;
import Server.networking.SocketHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class RunServer {

    public static void main(String[] args){
        try {
            System.out.println("Starting socket server");
            ServerSocket welcomeSocket = new ServerSocket(2910);
            ConnectionPool connectionPool = new ConnectionPool();
            ReserveManager reserveManager = new ReserveManagerImpl();
            ProductDAOImpl productDAOImpl = ProductDAOImpl.getInstance();

            while (true) {
                Socket socket = welcomeSocket.accept();
                SocketHandler socketHandler = new SocketHandler(socket, reserveManager, connectionPool, productDAOImpl); // Pass ProductDAOImpl instance
                connectionPool.add(socketHandler);
                System.out.println("Network.Client connected");
                new Thread(socketHandler).start();
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
