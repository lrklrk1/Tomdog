package Tomdog.http;

import java.util.HashMap;

public enum HttpStatusCode {

    OK(200, "OK"),
    BAD_REQUEST(400, "Bad Request");

    private static HashMap<HttpStatusCode, String> phrMap = new HashMap<HttpStatusCode, String>();
    private static HashMap<HttpStatusCode, Integer> codeMap = new HashMap<HttpStatusCode, Integer>();

    static {
        HttpStatusCode[] codes = values();
        for (HttpStatusCode code : codes) {
            phrMap.put(code, code.return_pharse);
            codeMap.put(code, code.code);
        }
    }

    private String return_pharse;
    private int code;

    HttpStatusCode(Integer code, String return_pharse) {
        this.code = code;
        this.return_pharse = return_pharse;
    }

    public static String getPhr(HttpStatusCode code) {
        return phrMap.get(code);
    }

    public static Integer getCode(HttpStatusCode code) {
        return codeMap.get(code);
    }

}