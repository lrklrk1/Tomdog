package Tomdog.http;

import java.util.HashMap;

import static Tomdog.http.HTTPCommon.defaultHttpVersion;

public class HttpRequestImpl extends HttpRequest {

    public HttpRequestImpl() {
        this.headers = new HashMap<String, String>();
        this.cookies = new HashMap<String, String>();
        this.parameters = new HashMap<String, String>();
    }

    @Override
    public void addLine(HttpRequestLine line) {
        if (line == null) {
            return;
        }

        this.line = line;

        HttpMethod m = HttpMethod.get(new String(line.getMethod(), 0, line.getMethodEnd()));

        if (m == null) {
            System.out.println("wrong method");
            this.method = HttpMethod.GET;
        } else {
            this.method = m;
        }

        String uri = new String(line.getUri(), 0, line.getUriEnd());

        if (uri.equals("")) {
            System.out.println("wrong uri");
            this.uri = "/";
        } else {
            this.uri = uri;
        }

        String protocol = new String(line.getProtocol(), 0, line.getProtocolEnd());
        if (!protocol.equals(defaultHttpVersion)) {
            System.out.println("only support HTTP/1.1");
            this.protocol = defaultHttpVersion;
        } else {
            this.protocol = protocol;
        }

        /*只对uri进行解析，得到在uri中的地址，以及参数*/
        parseLine();

    }

    //    目前只支持uri解析，协议不支持其他的协议或者版本，方法支持get，head。
    private void parseLine() {

//        TODO

        parseURI(this.uri);
    }

    private void parseURI(String uri) {
        int questionMark = uri.indexOf("?");

        if (questionMark == -1) {
            return;
        }

        String parameters = uri.substring(questionMark + 1);

        parseParameters(parameters);

        int start = uri.indexOf("://");

        uri = uri.substring(start + 1, questionMark);

        parseAbsAndContextUri(uri);

    }

    private void parseParameters(String parameters) {
        String[] pars = parameters.split("&");
        for (String p : pars) {
            String[] kv = p.split("=");
            if (kv.length == 2) {
                String key = kv[0];
                String value = kv[1];
                this.parameters.put(key, value);
            } else {
                System.out.println("can not parse" + p);
            }
        }
    }

    private void parseAbsAndContextUri(String uri) {
        int contextIndex = uri.indexOf("/", 2);

        this.absUri = uri.substring(0, contextIndex);

        this.contextUri = uri.substring(contextIndex);

    }


    @Override
    public void addHeader(HttpHeader header) {

        if (header == null) {
            System.out.println("this is no header");
            return;
        }

        String name = new String(header.getName(),0, header.getNameEnd());
        String value = new String(header.getValue(), 0, header.getValueEnd());

        this.headers.put(name, value);

        parseHeader(name);
    }

    /*对请求信息进行解析，这里只解析一个Cookie属性*/
    private void parseHeader(String name) {

//        TODO

        parseCookies(name);
    }

    /*只有在Cookie下的，以分号分隔，以等号作为name和value标志的参数才作为cookie*/
    private void parseCookies(String name) {

        if (!name.equals("Cookies")) {
            return;
        }

        String parameters = this.headers.get(name);

        String[] pars = parameters.split(";");

        for (String p : pars) {
            String[] kv = p.split("=");
            if (kv.length == 2) {
                String key = kv[0];
                String value = kv[1];
                this.cookies.put(key, value);
            } else {
                System.out.println("can not parse" + p);
            }
        }

    }

}