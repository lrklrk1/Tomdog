package Tomdog.http;

import jdk.vm.ci.hotspot.HotSpotResolvedPrimitiveType;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static Tomdog.http.HttpRequestLine.MAX_METHOD_LENGTH;
import static Tomdog.http.HttpRequestLine.MAX_PROTOCOL_LENGTH;
import static Tomdog.http.HttpRequestLine.MAX_URI_LENGTH;

public class HttpInputStream extends InputStream {

    private static final byte CR = (byte) '\r';

    private static final byte LF = (byte) '\n';

    private static final byte SP = (byte) ' ';

    private static final byte HT = (byte) '\t';

    private static final byte COLON = (byte) ':';

    private static final int LC_OFFSET = 'A' - 'a';

    private static final int DEFAULT_BUFFER_SIZE = 16;

    /*当前在buffer中的位置*/
    private int pos;

    /*输入的流*/
    private InputStream in;

    /*存储字节的buffer*/
    private byte[] buffer;

    /*当前的buffer的最大长度*/
    private int current;

    public HttpInputStream(InputStream in, int size) {
        this.in = in;
        if (size > 0) {
            this.buffer = new byte[size];
        } else {
            this.buffer = new byte[DEFAULT_BUFFER_SIZE];
        }
    }

    /*解析 第一行 请求行*/
    public void readRequest(HttpRequestLine line) throws IOException {

        if (line == null) {
            return;
        }
        if (line.getMethodEnd() != 0) {
            line.recyle();
        }
//
//        /*跳过起始的空格*/
//        skipSpace(line);

        /*读入请求行中的方法*/
        readMethod(line);

        /*读入请求行中的URI*/
        readURI(line);

        /*读入请求行中的协议*/
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

            /*标志位，标识method字段结束，进入uri字段*/
            if (buffer[pos] == SP) {
                space = true;
            }

            line.getMethod()[currentLength++] = (char)buffer[pos++];
        }

        /*包括最后的空格*/
        line.setMethodEnd(currentLength - 1);
    }

    private void readURI(HttpRequestLine line) throws IOException {
        int maxLength = line.getUri().length;
        int currentLneght = 0;

        boolean space = false;

        while(!space) {

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

            line.getUri()[currentLneght++] = (char)buffer[pos++];
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
                line.setUri(newBuffer);
                maxLength = line.getProtocol().length;
            }

            isNeedRefill();

            if (buffer[pos] == CR) {
                if (buffer[++pos] == LF) {
                    eol = true;
                } else {
                    throw new IOException("CRLF must be the end of line");
                }
            } else {
                line.getUri()[currentLength++] = (char)buffer[pos++];
            }

        }
        line.setUriEnd(currentLength - 1);
    }

    public void readHeader(HttpRequestLine line) {

    }

    private void skipSpace(HttpRequestLine line) throws IOException {
        int b = read();
        while (b == CR || b == LF) {
            b = read();
        }
        pos--;
    }

    private void isNeedRefill() throws IOException{
        if (pos >= current) {
            fill();
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

    @Override
    /*首先判断是否需要refill，然后再读取一个字节*/
    public int read() throws IOException {
        if (pos >= current) {
            fill();
            if (pos >= current) {
                return -1;
            }
        }
        return buffer[pos++] & 0xFF;
    }
}
