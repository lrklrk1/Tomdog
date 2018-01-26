package Tomdog.http;

import static Tomdog.http.HTTPCommon.defaultHttpVersion;

public class HttpResponseLine {

    private String httpVersion;

    private HttpStatusCode statusCode;

    private Integer code;

    private String resonPhrase;

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String version) {

        if (!version.equals(defaultHttpVersion)) {
            System.out.println("only support http/1.1");
            this.httpVersion = defaultHttpVersion;
        } else {
            this.httpVersion = version;
        }

    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatusCode statusCode) {

        if (statusCode == null) {
            return;
        }

        String phrase = HttpStatusCode.getPhr(statusCode);
        Integer code = HttpStatusCode.getCode(statusCode);
        if (phrase != null || !phrase.equals("")
                || code != null) {
            this.statusCode = statusCode;
            this.resonPhrase = phrase;
            this.code = code;
        }
    }

    public Integer getCode() {
        return this.code;
    }

    public String getResonPhrase() {
        return this.resonPhrase;
    }
}