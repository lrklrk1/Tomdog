package Tomdog.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import static Tomdog.http.HTTPCommon.COLON;
import static Tomdog.http.HTTPCommon.CRLF;
import static Tomdog.http.HTTPCommon.SP;

public class HttpOutputStream {

    private byte[] buffer;

    private int current;

    private OutputStream out;

    public HttpOutputStream(OutputStream out) {
        this.out = out;
        this.current = 0;
    }

    public void writeResponse(HttpResponse response) {
        if (response == null) {
            return;
        }

        HttpResponseLine line = response.getLine();
        if (line == null) {
            return;
        }

        HashMap<String, HttpHeader> headers = response.getHeaders();

        StringBuilder sb = new StringBuilder();

        try {
            write(line, sb);
            write(headers, sb);
            out.write(sb.toString().getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(HttpResponseLine line, StringBuilder sb) throws IOException {
        if (line == null) {
            return;
        }

        String version = line.getHttpVersion();
        Integer code = line.getCode();
        String phrase = line.getResonPhrase();

        if (version == null || version.equals("")
                || code == null
                || phrase == null || phrase.equals("")) {
            System.out.println("the response line is missing something " + version + "　" + code + " " + " " + phrase);
            return;
        }

        sb.append(version.getBytes());
        sb.append(SP);
        sb.append(code);
        sb.append(SP);
        sb.append(phrase.getBytes());
        sb.append(CRLF);
//        out.write(CR);
//        out.write(LF);
    }

    private void write(HashMap<String, HttpHeader> headers, StringBuilder sb) throws IOException {

        for (HttpHeader header : headers.values()) {

            if (header == null
                    || header.getNameEnd() == 0
                    || header.getValueEnd() == 0) {
                continue;
            }

            write(header, sb);
        }
    }

    private void write(HttpHeader header, StringBuilder sb) throws IOException {
        if (header == null) {
            return;
        }

        String name = new String(header.getName(), 0, header.getNameEnd());
        String value = new String(header.getValue(), 0, header.getValueEnd());

        sb.append(name.getBytes());
        sb.append(COLON);
        sb.append(SP);
        sb.append(value.getBytes());
    }

}