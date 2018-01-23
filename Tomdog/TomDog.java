package Tomdog;

import java.io.OutputStream;

import java.net.InetSocketAddress;

import java.net.ServerSocket;

import java.net.Socket;

import java.net.SocketAddress;

import java.io.InputStream;



public class TomDog {

    public static void main(String[] args) {

        ServerSocket serverSocket = null;

        SocketAddress socketAddress = null;

        Socket socket = null;

        InputStream in = null;

        OutputStream out = null;

        try {

            socketAddress = new InetSocketAddress("127.0.0.1", 8080);

            serverSocket = new ServerSocket();

            serverSocket.bind(socketAddress);

            socket = serverSocket.accept();

            if (socket != null && socket.isConnected()) {

                in = socket.getInputStream();

                out = socket.getOutputStream();

                StringBuilder sb = new StringBuilder();

                int c = 0;

                byte[] buffer = new byte[1024];

                while ((c = in.read(buffer)) != -1) {

                    for (byte bb : buffer) {
                        System.out.print(bb + " ");
                    }
System.out.println();
                    sb.append(new String(buffer));

                }

                System.out.println(sb.toString());

            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

        }

    }

}