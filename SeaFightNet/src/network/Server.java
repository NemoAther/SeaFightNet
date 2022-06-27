package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ListIterator;

public class Server implements Runnable {

    private ServerSocket server; // серверсокет
    private Socket clientSocket; //сокет для общения
    //private BufferedReader in; // поток чтения из сокета
    //private BufferedWriter out; // поток записи в сокет
    private final ArrayList<ConnectedClient> clients; //клиенты на сервере
    private int port = 6666;
    int idPool = 0;

    @Override
    public void run() {
        startServer(port);
    }

    public Server(int port) {
        this.port = port;
        clients = new ArrayList<>();
    }

    public void startServer(int port) {

        try {
            server = new ServerSocket(port); // серверсокет прослушивает порт 
        } catch (IOException ex) {
            System.out.println("Ошибка связывания с портом " + port);
            System.exit(-1);
        }
        System.out.println("Сервер запущен!");
        try {
            clientSocket = server.accept(); // accept() будет ждать пока кто-нибудь не захочет подключиться
            int ClientId = idPool++;
            ConnectedClient client = new ConnectedClient(clientSocket, ClientId, this);
            clients.add(client);
        } catch (IOException ex) {
            System.out.println("Не удалось установить соединение");
            System.exit(-1);

        }

    }

    void clientDisconnect(ConnectedClient client) {
        for (int i = 0; i < clients.size(); i++) {
            if (client.getClientId() == clients.get(i).getClientId()) {
                clients.remove(i);
                break;
            }
        }
    }
}
