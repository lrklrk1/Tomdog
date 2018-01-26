package Tomdog.http;

import java.util.HashMap;

import static Tomdog.http.HTTPCommon.defaultHttpVersion;

public class HttpResponseImpl extends HttpResponse {

    public HttpResponseImpl() {
        this.line = new HttpResponseLine();
        this.headers = new HashMap<String, HttpHeader>();
    }

    private void addHeader(HttpHeader header) {
        if (header == null) {
            return;
        }

        String name = new String(header.getName(), 0, header.getNameEnd());
        this.headers.put(name, header);
    }

    private void addVersion(String version) {
        if (this.line != null) {
            line.setHttpVersion(version);
        }
    }

    private void addStatusCode(HttpStatusCode code) {
        if (this.line != null) {
            line.setStatusCode(code);
        }
    }

    @Override
    public void ok() {
        this.addVersion(defaultHttpVersion);
        this.addStatusCode(HttpStatusCode.OK);
//  TODO
//this.addHeaders(headers);
    }

    @Override
    public void bad() {
        this.addVersion(defaultHttpVersion);
        this.addStatusCode(HttpStatusCode.BAD_REQUEST);
//  TODO
//response.addHeaders(headers);
    }
}