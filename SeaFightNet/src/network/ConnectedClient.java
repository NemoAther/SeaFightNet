package network;

import java.io.*;
import java.net.Socket;

public class ConnectedClient implements Runnable {

    private Socket clientSocket; //сокет для общения
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток записи в сокет
    private boolean isConnected = false;
    private int ClientId;

    @Override
    public void run() {
        try {
            startConversation();
        } catch (IOException ex) {
            System.out.println("Не удалось начать общение");
        }
    }

    ConnectedClient(Socket clientSocket, int ClientId, Server server) {
        this.ClientId = ClientId;
        isConnected = true;
        try {
            createStream(); // к созданию потоков ввода/вывода. теперь мы можем принимать сообщения и отправлять
        } catch (IOException ex) {
            System.out.println("Не удалось создать потоки ввода/вывода");
        }

    }

    private void createStream() throws IOException {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }

    private void startConversation() throws IOException {
        while (isConnected) {
            String word = in.readLine();
            System.out.println(word);
            out.write("Привет, это Сервер! Подтверждаю, вы написали : " + word + "\n");
            out.flush(); // выталкиваем все из буфера
            if (word.equalsIgnoreCase("exit")) {
                isConnected = false;
            }
        }
        in.close();
        out.close();
        clientSocket.close();
        
        //server.close(); //если клиент ушел, сервер остается, пропадает только 1 клиент
    }

    public int getClientId() {
        return ClientId;
    }
    
}
