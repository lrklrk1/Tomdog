package Tomdog.core;

import Tomdog.http.HttpHeader;
import Tomdog.http.HttpInputStream;
import Tomdog.http.HttpOutputStream;
import Tomdog.http.HttpRequest;
import Tomdog.http.HttpRequestImpl;
import Tomdog.http.HttpRequestLine;
import Tomdog.http.HttpResponse;
import Tomdog.http.HttpResponseImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Processor implements Runnable {

    private Socket socket;

    private InputStream in;

    private OutputStream out;

    private HttpResponse response;

    private HttpRequest request;

    Processor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        if (socket !=null && socket.isConnected()) {

            try {

                in = socket.getInputStream();
                out = socket.getOutputStream();

                HttpInputStream his = new HttpInputStream(in, 16);

                HttpOutputStream hos = new HttpOutputStream(out);

                request = new HttpRequestImpl();

                response = new HttpResponseImpl(hos);

                parseRequest(his);
                parseHeaders(his);

                HttpHeader httpHeader = new HttpHeader("form", "luurunkai's tomdog");

                response.addHeader(httpHeader);

                ServletPool pool = new ServletPool();

                Servlet servlet = pool.getServlet(request.getAbsUri());

                servlet.process(request, response);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void start() {
        Thread thread = new Thread(this);
        thread.run();
    }

    private void parseRequest(HttpInputStream his) throws IOException {

        HttpRequestLine line = new HttpRequestLine();

        his.readRequestLine(line);

        this.request.addLine(line);
    }

    private void parseHeaders(HttpInputStream his) throws IOException{

        List<HttpHeader> headers = new ArrayList<HttpHeader>();

        boolean hasHeader = true;

        while (hasHeader) {
            HttpHeader header = new HttpHeader();
            his.readHeader(header);
            if (header.getNameEnd() == 0 && header.getNameEnd() == 0) {
                hasHeader = false;
                continue;
            }
            headers.add(header);
        }

        for (HttpHeader header : headers) {
            request.addHeader(header);
        }

    }

}