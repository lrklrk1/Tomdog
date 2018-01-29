package Tomdog.http;

import java.util.HashMap;

public enum HttpMethod {

    GET("GET"),
    HEAD("HEAD");

    private static HashMap<String, HttpMethod> methods = new HashMap<String, HttpMethod>();
    static {
        HttpMethod[] ms = values();
        for (HttpMethod m : ms) {
            methods.put(m.getValue(), m);
        }
    }

    private String value;

    HttpMethod(String value) {
        this.value = value;
    }

    public static HttpMethod get(String s) {
        HttpMethod m = methods.get(s);
        if (m != null) {
            return m;
        } else {
            return null;
        }
    }

    public String getValue() {
        return this.value;
    }


}