package socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class testServer {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        SocketAddress address = null;
        Socket socket = null;
        InputStream in = null;
        try {
            serverSocket = new ServerSocket();
            address = new InetSocketAddress("127.0.0.1", 8888);
            serverSocket.bind(address);
            socket = serverSocket.accept();
            in = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (null != socket) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != serverSocket) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
