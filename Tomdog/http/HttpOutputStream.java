package Tomdog.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import static Tomdog.http.HTTPCommon.CR;
import static Tomdog.http.HTTPCommon.LF;
import static Tomdog.http.HTTPCommon.SCOLON;
import static Tomdog.http.HTTPCommon.SCR;
import static Tomdog.http.HTTPCommon.SLF;
import static Tomdog.http.HTTPCommon.SSP;

public class HttpOutputStream {

    private OutputStream out;

    public HttpOutputStream(OutputStream out) {
        this.out = out;
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

        try {
            byte[] sc = write(line);
            byte[] hs = write(headers);
            out.write(sc);
            out.write(hs);
            String entity = response.getEntity();
            if (entity != null) {
                out.write(response.getEntity().getBytes());
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] write(HttpResponseLine line) throws IOException {
        if (line == null) {
            return new byte[]{};
        }

        String version = line.getHttpVersion();
        Integer code = line.getCode();
        String phrase = line.getReasonPhrase();

        if (version == null || version.equals("")
                || code == null
                || phrase == null || phrase.equals("")) {
            System.out.println("the response line is missing something " + version + "銆€" + code + " " + " " + phrase);
            return new byte[]{};
        }

        String responseLine = version + SSP + code + SSP + phrase + SCR + SLF;

        return responseLine.getBytes();
    }

    private byte[] write(HashMap<String, HttpHeader> headers) throws IOException {

        StringBuilder sb = new StringBuilder();

        for (HttpHeader header : headers.values()) {

            if (header == null
                    || header.getNameEnd() == 0
                    || header.getValueEnd() == 0) {
                continue;
            }

            sb.append(write(header));
            sb.append(SCR);
            sb.append(SLF);
        }
        sb.append(SCR);
        sb.append(SLF);
        return sb.toString().getBytes();
    }

    private String write(HttpHeader header) throws IOException {
        if (header == null) {
            return "";
        }

        String name = new String(header.getName(), 0, header.getNameEnd());
        String value = new String(header.getValue(), 0, header.getValueEnd());
        return name + SCOLON + value;
    }

}