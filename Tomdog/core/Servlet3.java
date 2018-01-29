package Tomdog.core;

import Tomdog.http.HttpRequest;
import Tomdog.http.HttpResponse;

public class Servlet3 implements Servlet {

    @Override
    public void process(HttpRequest request, HttpResponse response) {
        response.ok().build();
    }
}