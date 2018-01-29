package Tomdog.http;

import java.util.HashMap;

public abstract class HttpResponse {

    HttpResponseLine line;

    HashMap<String, HttpHeader> headers;

    HttpOutputStream out;

    String entity;

    public HttpResponseLine getLine() {
        return line;
    }

    public HashMap<String, HttpHeader> getHeaders() {
        return headers;
    }

    /*make 200 response*/
    public abstract HttpResponse ok();

    /*make 400 response*/
    public abstract HttpResponse bad();

    /*add header*/
    public abstract void addHeader(HttpHeader header);

    /*write to front*/
    public abstract void build();

    public abstract void setEntity(String entity);

    public abstract String getEntity();

}