package Tomdog.http;

import java.io.OutputStream;
import java.util.HashMap;

import static Tomdog.http.HTTPCommon.defaultHttpVersion;

public class HttpResponseImpl extends HttpResponse {

    public HttpResponseImpl(HttpOutputStream out) {
        this.line = new HttpResponseLine();
        this.headers = new HashMap<String, HttpHeader>();
        this.out = out;
    }

    public void addHeader(HttpHeader header) {
        if (header == null) {
            return;
        }

        String name = new String(header.getName(), 0, header.getNameEnd());
        this.headers.put(name, header);
    }

    @Override
    public void build() {
        out.writeResponse(this);
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
    public HttpResponse ok() {
        this.addVersion(defaultHttpVersion);
        this.addStatusCode(HttpStatusCode.OK);

        return this;
    }

    @Override
    public HttpResponse bad() {
        this.addVersion(defaultHttpVersion);
        this.addStatusCode(HttpStatusCode.BAD_REQUEST);

        return this;
    }

    @Override
    public void setEntity(String entity) {
        this.entity = entity;
    }

    @Override
    public String getEntity() {
        return this.entity;
    }
}