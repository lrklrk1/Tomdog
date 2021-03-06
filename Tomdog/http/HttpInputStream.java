package Tomdog.http;

import java.io.IOException;

import java.io.InputStream;

import java.util.Arrays;

import static Tomdog.http.HTTPCommon.COLON;
import static Tomdog.http.HTTPCommon.CR;
import static Tomdog.http.HTTPCommon.HT;
import static Tomdog.http.HTTPCommon.LF;
import static Tomdog.http.HTTPCommon.SP;
import static Tomdog.http.HttpHeader.MAX_NAME_LENGTH;
import static Tomdog.http.HttpHeader.MAX_VALUE_LENGTH;
import static Tomdog.http.HttpRequestLine.MAX_METHOD_LENGTH;
import static Tomdog.http.HttpRequestLine.MAX_PROTOCOL_LENGTH;
import static Tomdog.http.HttpRequestLine.MAX_URI_LENGTH;

public class HttpInputStream {

    private static final int DEFAULT_BUFFER_SIZE = 16;

    private int pos;

    private InputStream in;

    private byte[] buffer;

    private int current;

    public HttpInputStream(InputStream in, int size) {

        this.in = in;

        if (size > 0) {
            this.buffer = new byte[size];
        } else {
            this.buffer = new byte[DEFAULT_BUFFER_SIZE];
        }

    }

    public void readRequestLine(HttpRequestLine line) throws IOException {
        if (line == null) {
            return;
        }

        if (line.getMethodEnd() != 0) {
            line.recyle();
        }

//        skipSpace(line);

        readMethod(line);

        readURI(line);

        readProtocol(line);

    }

    private void readMethod(HttpRequestLine line) throws IOException {

        int maxLength = line.getMethod().length;
        int currentLength = 0;
        boolean space = false;

        while (!space) {
            if (currentLength >= maxLength) {
                if (2 * currentLength > MAX_METHOD_LENGTH) {
                    throw new IOException("method is too long");
                }

                char[] newBuffer = Arrays.copyOf(line.getMethod(), 2 * currentLength);
                line.setMethod(newBuffer);
                maxLength = line.getMethod().length;
            }

            isNeedRefill();

            if (buffer[pos] == SP) {
                space = true;
            }

            line.getMethod()[currentLength++] = (char) buffer[pos++];
        }

        line.setMethodEnd(currentLength - 1);

    }

    private void readURI(HttpRequestLine line) throws IOException {

        int maxLength = line.getUri().length;
        int currentLneght = 0;
        boolean space = false;

        while (!space) {
            if (currentLneght > maxLength) {
                if (2 * currentLneght > MAX_URI_LENGTH) {
                    throw new IOException("URI too long");
                }

                char[] newBuffer = Arrays.copyOf(line.getUri(), 2 * currentLneght);
                line.setUri(newBuffer);
                maxLength = line.getUri().length;
            }

            isNeedRefill();

            if (buffer[pos] == SP) {
                space = true;
            }

            line.getUri()[currentLneght++] = (char) buffer[pos++];
        }

        line.setUriEnd(currentLneght - 1);

    }

    private void readProtocol(HttpRequestLine line) throws IOException {

        int maxLength = line.getProtocol().length;

        int currentLength = 0;

        /*end of line*/
        boolean eol = false;

        while (!eol) {

            if (currentLength >= maxLength) {
                if (2 * currentLength > MAX_PROTOCOL_LENGTH) {
                    throw new IOException("too long protocol");
                }

                char[] newBuffer = Arrays.copyOf(line.getProtocol(), 2 * currentLength);
                line.setProtocol(newBuffer);
                maxLength = line.getProtocol().length;
            }

            isNeedRefill();

            if (buffer[pos] == CR) {
                pos++;
                continue;
            } else if (buffer[pos] == LF) {
                pos++;
                eol = true;
                continue;
            }

            line.getProtocol()[currentLength++] = (char) buffer[pos++];
        }

        line.setProtocolEnd(currentLength);

    }

    public void readHeader(HttpHeader header) throws IOException {
        if (header == null) {
            return;
        }

        if (header.getNameEnd() != 0) {
            header.recycle();
        }

        readName(header);

        readValue(header);

    }

    private void readName(HttpHeader header) throws IOException {
        int maxLength = header.getName().length;
        int currentLength = 0;

        boolean colon = false;

        while (!colon) {

            if (currentLength >= maxLength) {
                if (2 * currentLength > MAX_NAME_LENGTH) {
                    throw new IOException("name is too long");
                }
                char[] newBuffer = Arrays.copyOf(header.getName(), 2 * currentLength);
                header.setName(newBuffer);
                maxLength = header.getName().length;
            }

            isNeedRefill();

            if (buffer[pos] == COLON) {
                colon = true;
                pos++;
                continue;
            } else if (buffer[pos] == CR) {
                pos++;
                continue;
            } else if (buffer[pos] == LF){
                return;
            }

            header.getName()[currentLength++] = (char) buffer[pos++];
        }
        header.setNameEnd(currentLength);
    }

    private void readValue(HttpHeader header) throws IOException {
        int maxLength = header.getValue().length;
        int currentLength = 0;

        boolean newLine = true;
        boolean eov = false;

        while (newLine) {
            while (!eov) {
                if (currentLength >= maxLength) {
                    if (2 * currentLength > MAX_VALUE_LENGTH) {
                        throw new IOException("value too long");
                    }
                    char[] newBuffer = Arrays.copyOf(header.getValue(), 2 * currentLength);
                    header.setValue(newBuffer);
                    maxLength = header.getValue().length;
                }

                isNeedRefill();

//System.out.println(pos + "   " + current);

                if (buffer[pos] == LF) {
                    eov = true;
                    pos++;
                    continue;
                } else if (buffer[pos] == CR) {
                    return;
                }

                header.getValue()[currentLength++] = (char) buffer[pos++];
            }

            isNeedRefill();

            int i = buffer[pos];

            if (i != SP && i != HT) {
                newLine = false;
            } else {
                eov = false;
                header.getValue()[currentLength++] = (char) buffer[pos++];
            }
        }
        header.setValueEnd(currentLength);
    }

//    private void skipSpace(HttpRequestLine line) throws IOException {
//        int b = read();
//        while (b == CR || b == LF) {
//            b = read();
//        }
//
//        pos--;
//    }

    private void isNeedRefill() throws IOException {
        if (pos >= current) {
            fill();
        } else {
            return;
        }

        int i = read();
        if (i == -1) {

            throw new IOException("end of stream");
        } else {
            pos--;
        }
    }

    private void fill() throws IOException {
        pos = 0;
        current = 0;

        int c = in.read(buffer, 0, buffer.length);
        if (c > 0) {
            current = c;
        }
    }

    public void close() {

        this.pos = 0;
        this.current = 0;
        this.buffer = null;

        if (null != in) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                in = null;
            }
        }
    }

    private int read() throws IOException {

        if (pos >= current) {
            fill();
            if (pos >= current) {
                return -1;
            }
        }

        return buffer[pos++] & 0xFF;
    }

}