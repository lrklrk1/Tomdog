package Tomdog.core;

import java.util.HashMap;

public class ServletPool {

    static HashMap<String, Servlet> servlets = new HashMap<String, Servlet>();

    static {
        servlets.put("a", new Servlet1());
        servlets.put("b", new Servlet2());
        servlets.put("axiba", new Servlet3());
    }

    public static Servlet getServlet(String absUri) {

        if (absUri == null || absUri.equals("/")) {
            return servlets.get("axiba");
        }

        String key = absUri.substring(1);

        if (servlets.get(key) == null) {
            return servlets.get("axiba");
        }

        return servlets.get(key);

    }

}