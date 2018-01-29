package Tomdog.core;

import Tomdog.http.HttpRequest;
import Tomdog.http.HttpResponse;

public interface Servlet {

    void process(HttpRequest request, HttpResponse response);

}