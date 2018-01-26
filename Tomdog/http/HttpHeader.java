package Tomdog.http;

public class HttpHeader {

    protected static final int INITIAL_NAME_SIZE = 32;

    protected static final int INITIAL_VALUE_SIZE = 64;

    protected static final int MAX_NAME_LENGTH = 128;

    protected static final int MAX_VALUE_LENGTH = 4096;

    public HttpHeader() {

        this(new char[INITIAL_NAME_SIZE], 0, new char[INITIAL_VALUE_SIZE], 0);

    }

    public HttpHeader(char[] name, int nameEnd, char[] value, int valueEnd) {

        this.name = name;

        this.nameEnd = nameEnd;

        this.value = value;

        this.valueEnd = valueEnd;

    }

    public HttpHeader(String name, String value) {

        this.name = name.toCharArray();

        this.nameEnd = name.length();

        this.value = value.toCharArray();

        this.valueEnd = value.length();

    }

    private char[] name;

    private int nameEnd;

    private char[] value;

    private int valueEnd;


    public char[] getName() {
        return name;
    }

    public void setName(char[] name) {
        this.name = name;
    }

    public int getNameEnd() {
        return nameEnd;
    }

    public void setNameEnd(int nameEnd) {
        this.nameEnd = nameEnd;
    }

    public char[] getValue() {
        return value;
    }

    public void setValue(char[] value) {
        this.value = value;
    }

    public int getValueEnd() {
        return valueEnd;
    }

    public void setValueEnd(int valueEnd) {
        this.valueEnd = valueEnd;
    }

    public void recycle() {

        this.nameEnd = 0;

        this.valueEnd = 0;

    }

}