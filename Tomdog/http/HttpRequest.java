package Tomdog.http;

import java.util.HashMap;
import java.util.Map;

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

    public abstract Map<String, String> getHeaders();

    public abstract String getUri();

    public abstract String getAbsUri();

    public abstract String getContextUri();

    public abstract String getMethod();

    public abstract Map<String, String> getPara();

}