package Tomdog.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class Connector implements Runnable{

    private boolean stop = false;

    @Override
    public void run() {

        try (ServerSocket serverSocket = new ServerSocket()) {

            SocketAddress socketAddress =new InetSocketAddress("127.0.0.1", 8080);

            serverSocket.bind(socketAddress);

            Socket socket;

            while(!stop) {

                socket = serverSocket.accept();

                Processor process = new Processor(socket);

                process.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.run();
    }

}