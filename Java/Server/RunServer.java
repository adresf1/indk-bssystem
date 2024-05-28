package Server;

import ModelDB.ProductDAOImpl;
import Server.networking.ConnectionPool;
import Server.networking.SocketHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class RunServer {

    public static void main(String[] args){
        try {
            System.out.println("Starting ServerSocket");
            ServerSocket welcomeSocket = new ServerSocket(2910);
            ConnectionPool connectionPool = new ConnectionPool();
            ProductDAOImpl productDAOImpl = ProductDAOImpl.getInstance();

            while (true) {
                //Create new socket connection
                Socket socket = welcomeSocket.accept();


                SocketHandler socketHandler = new SocketHandler(socket, connectionPool, productDAOImpl); // Pass ProductDAOImpl instance
                //Added to ConnectionPool so broadcast function is available
                connectionPool.add(socketHandler);
                System.out.println("Network Client connected");
                new Thread(socketHandler).start();
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
