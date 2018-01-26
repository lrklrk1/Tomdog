package Tomdog.http;

import java.util.HashMap;

public abstract class HttpRequest {

    protected HttpRequestLine line;

    protected HttpMethod method;

    protected String uri;

    protected String absUri;

    protected String contextUri;

    protected HashMap<String, String> parameters;

    protected String protocol;

    protected HashMap<String, String> headers;

    protected HashMap<String, String> cookies;

    public abstract void addHeader(HttpHeader header);

    public abstract void addLine(HttpRequestLine line);

}