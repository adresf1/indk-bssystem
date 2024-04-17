package Core;

import Network.Client;
import Network.SocketClient;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ClientFactory {
    private Client client;
    private static ClientFactory instance;
    private static final Lock lock = new ReentrantLock(){};

    public ClientFactory(){};

    public static ClientFactory getInstance(){
        if(instance == null){
            synchronized (lock){
                if(instance==null){
                    instance = new ClientFactory();
                }
            }
        }
        return instance;
    }

    public Client getClient() {
        if(client == null) {
            client = new SocketClient();
        }
        return client;
    }
}