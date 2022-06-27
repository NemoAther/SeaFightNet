/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seafightnet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import network.Client;
import network.Server;

/**
 *
 * @author ksmnote
 */
public class SeaFightNet extends Application {

    private Socket clientSocket; //сокет для общения
    private ServerSocket server; // серверсокет
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток записи в сокет

    @Override
    public void start(Stage primaryStage) {
        Button serverButton = new Button();
        serverButton.setText("Sever start");
        serverButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Server server = new Server(6666);
                Thread threadServer = new Thread(server);
                threadServer.start();
            }
        });
        Button clientButton = new Button();
        clientButton.setText("Client connect");
        clientButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                startClient("localhost", 6666);
            }
        });

        VBox root = new VBox();

        root.getChildren().add(serverButton);
        root.getChildren().add(clientButton);
        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void startServer(int port) {
        //Server server = new Server();
        //server.startServer(port);
    }

    public void startClient(String address, int port) {
        Client client = new Client();
        client.Connect(address, port);
    }
}
