package Tomdog.http;

import java.util.HashMap;

public abstract class HttpResponse {

    HttpResponseLine line;

    HashMap<String, HttpHeader> headers;

    public HttpResponseLine getLine() {
        return line;
    }

    public HashMap<String, HttpHeader> getHeaders() {
        return headers;
    }

    public abstract void ok();

    public abstract void bad();

}