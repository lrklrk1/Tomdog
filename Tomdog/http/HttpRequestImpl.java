package Tomdog.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        /*�Û�uri菴�茵�茹ｆ��鐚�緇��医��uri筝㊦���医��鐚�篁ュ�����*/
        parseLine();

    }

    //    �����Ŭ����uri茹ｆ��鐚��顒�������銀����顒�����������号�����get鐚�head��
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

        if (contextIndex == -1) {
            this.absUri = uri;
            this.contextUri = "";
        } else {
            this.absUri = uri.substring(0, contextIndex);
            this.contextUri = uri.substring(contextIndex);
        }
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

    /*絲壕�羆�篆≧���茵�茹ｆ��鐚�菴����ŰВ��筝�筝�Cookie絮���*/
    private void parseHeader(String name) {

//        TODO

        parseCookies(name);
    }

    /*�Ŭ����Cookie筝���鐚�篁ュ���垸����鐚�篁ョ��垬�筝�name��value��綽������井��篏�筝�cookie*/
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

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String getUri() {
        return this.uri;
    }

    @Override
    public String getAbsUri() {
        return this.absUri;
    }

    @Override
    public String getContextUri() {
        return null;
    }

    @Override
    public String getMethod() {
        return this.method.getValue();
    }

    @Override
    public Map<String, String> getPara() {
        return this.parameters;
    }
}