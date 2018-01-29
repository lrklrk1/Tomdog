package Tomdog.http;

/**
 * Created by l15598 on 2018/1/23.
 */
public class HttpRequestHeader {

    private static final int INITIAL_NAME_SIZE = 32;
    private static final int INITIAL_VALUE_SIZE = 64;
    private static final int MAX_NAME_SIZE = 128;
    private static final int MAX_VALUE_SIZE = 4096;

    public HttpRequestHeader() {
        this(new char[INITIAL_NAME_SIZE], 0, new char[INITIAL_VALUE_SIZE], 0);
    }

    public HttpRequestHeader(char[] name, int nameEnd, char[] value, int valueEnd) {
        this.name = name;
        this.nameEnd = nameEnd;
        this.value = value;
        this.valueEnd = valueEnd;
    }

    public HttpRequestHeader(String name, String value) {
        this.name = name.toCharArray();
        this.nameEnd = name.length();
        this.value = value.toCharArray();
        this.valueEnd = value.length();
    }

    private char[] name;
    private int nameEnd;
    private char[] value;
    private int valueEnd;

}