package Tomdog.core;

import Tomdog.http.HttpHeader;
import Tomdog.http.HttpRequest;
import Tomdog.http.HttpResponse;

import java.util.Map;

public class Servlet1 implements Servlet {

    @Override
    public void process(HttpRequest request, HttpResponse response) {
        Map<String, String> para = request.getPara();

        int a = 0;
        int b = 0;

        for (Map.Entry<String, String> entry : para.entrySet()) {
            if (entry.getKey().equals("a")) {
                a = Integer.parseInt(entry.getValue());
            } else if (entry.getKey().equals("b")) {
                b = Integer.parseInt(entry.getValue());
            }
        }

        response.setEntity(a * b + "");

        response.ok().build();

    }
}