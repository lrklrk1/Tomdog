package Tomdog;

import Tomdog.http.HttpInputStream;
import Tomdog.http.HttpHeader;
import Tomdog.http.HttpRequestImpl;
import Tomdog.http.HttpRequestLine;

import java.io.IOException;
import java.io.OutputStream;

import java.net.InetSocketAddress;

import java.net.ServerSocket;

import java.net.Socket;

import java.net.SocketAddress;

import java.io.InputStream;
import java.util.List;

public class TomDog {

    public static void main(String[] args) {

        ServerSocket serverSocket =null;

        SocketAddress socketAddress =null;

        Socket socket =null;

        InputStream in =null;

        OutputStream out =null;

        try {

            socketAddress =new InetSocketAddress("127.0.0.1", 8080);

            serverSocket =new ServerSocket();

            serverSocket.bind(socketAddress);

            socket = serverSocket.accept();

            if (socket !=null && socket.isConnected()) {

                in = socket.getInputStream();

                out = socket.getOutputStream();

                socket.getOutputStream().write('a');

                StringBuilder sb =new StringBuilder();

//int c =0;
//
//byte[] buffer =new byte[1024];
//
//while ((c = in.read(buffer)) != -1) {
//
//    for (byte bb : buffer) {
//
//        System.out.print(bb +" ");
//
//    }
//
//    System.out.println();
//
//    sb.append(new String(buffer));
//
//}
//
//System.out.println(sb.toString());

                HttpRequestLine line = new HttpRequestLine();
                HttpInputStream his = new HttpInputStream(in, 16);
                List<HttpHeader> headers = null;
                try {
                    his.readRequestLine(line);
//                    headers = new ArrayList<HttpHeader>();
//                    boolean hasHeader = true;
//                    while (hasHeader) {
//                        HttpHeader header = new HttpHeader();
//                        his.readHeader(header);
//                        if (header.getNameEnd() == 0 && header.getNameEnd() == 0) {
//                            break;
//                        }
//                        headers.add(header);
//                    }

                    HttpRequestImpl impl = new HttpRequestImpl();

                    impl.addLine(line);
//
//                    for (HttpHeader header : headers) {
//                        impl.addHeader(header);
//                    }

//                    HttpOutputStream hos = new HttpOutputStream(out);
//                    HttpResponse response = new HttpResponseImpl();
//                    response.ok();
//                    hos.writeResponse(response);

                    out.write('d');
                    out.flush();

                } catch (IOException e) {
                    ;
                }


            }

        }catch (Exception e) {

            e.printStackTrace();

        }finally {

        }

    }

}