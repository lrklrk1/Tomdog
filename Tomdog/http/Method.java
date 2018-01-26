package Tomdog.http;

import java.util.HashMap;

public enum Method {

    GET("GET"),
    HEAD("HEAD");

    private static HashMap<String, Method> methods = new HashMap();

    static {

        Method[] ms = values();

        for (Method m : ms) {

            methods.put(m.getValue(), m);

        }

    }

    String value;

    Method(String value) {

        this.value = value;

    }

    public static Method get(String s) {

        Method m = methods.get(s);

        if (m != null) {

            return m;

        } else {

            return null;

        }

    }

    private String getValue() {

        return this.value;

    }

}