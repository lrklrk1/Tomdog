package Tomdog.http;



public class HttpRequestLine {

    protected static final int INITIAL_METHOD_LENGTH  = 8;

    protected static final int INITIAL_URI_LENGTH  = 64;

    protected static final int INITIAL_PROTOCOL_LENGTH  = 8;

    protected static final int MAX_METHOD_LENGTH = 1024;

    protected static final int MAX_URI_LENGTH = 32768;

    protected static final int MAX_PROTOCOL_LENGTH = 1024;

    public HttpRequestLine() {

        this(new char[INITIAL_METHOD_LENGTH], 0,

                new char[INITIAL_URI_LENGTH], 0,

                new char[INITIAL_PROTOCOL_LENGTH], 0);

    }

    public HttpRequestLine(char[] method, int methodEnd,

                           char[] uri, int uriEnd,

                           char[] protocol, int protocolEnd) {

        this.method = method;

        this.methodEnd = methodEnd;

        this.uri = uri;

        this.uriEnd = uriEnd;

        this.protocol = protocol;

        this.protocolEnd = protocolEnd;

    }

    private char[] method;

    private int methodEnd;

    private char[] uri;

    private int uriEnd;

    private char[] protocol;

    private int protocolEnd;

    public char[] getMethod() {

        return method;

    }

    public void setMethod(char[] method) {

        this.method = method;

    }

    public int getMethodEnd() {

        return methodEnd;

    }

    public void setMethodEnd(int methodEnd) {

        this.methodEnd = methodEnd;

    }

    public char[] getUri() {

        return uri;

    }

    public void setUri(char[] uri) {

        this.uri = uri;

    }

    public int getUriEnd() {

        return uriEnd;

    }

    public void setUriEnd(int uriEnd) {

        this.uriEnd = uriEnd;

    }

    public char[] getProtocol() {

        return protocol;

    }

    public void setProtocol(char[] protocol) {

        this.protocol = protocol;

    }

    public int getProtocolEnd() {

        return protocolEnd;

    }

    public void setProtocolEnd(int protocolEnd) {

        this.protocolEnd = protocolEnd;

    }

    public void recyle() {

        this.methodEnd = 0;

        this.protocolEnd = 0;

        this.uriEnd = 0;

    }

}