package io.github.ardonplay.normalserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class FileHandler implements HttpHandler {
    private static final int BUFF_SIZE = 1000;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("static")).getPath() + (exchange.getRequestURI().toString().equals("/") ? "" : exchange.getRequestURI());
        File file = new File(path);

        if (file.isDirectory()) {
            file = new File(path + "/index.html");
        }
        exchange.getResponseHeaders().set("Content-Type", getType(file));
        exchange.sendResponseHeaders(200, file.length());
        FileInputStream stream = new FileInputStream(file);
        long writted = 0;
        while (writted != file.length()) {
            byte [] buffer = stream.readNBytes(BUFF_SIZE);
            exchange.getResponseBody().write(buffer);
            writted += buffer.length;
        }
        exchange.getResponseBody().close();
    }

    private static String getType(File file) {
        String contentType;
        String fileName = file.getName();
        if (fileName.endsWith("html")) {
            contentType = "text/html";
        } else if (fileName.endsWith("css")) {
            contentType = "text/css";
        } else if (fileName.endsWith("svg") || fileName.endsWith("xml")) {
            contentType = "image/svg+xml";
        } else if (fileName.endsWith("js")) {
            contentType = "text/javascript; charset=utf-8";
        } else if (fileName.endsWith("json")) {
            contentType = "application/json";
        } else if (fileName.endsWith("jpg")) {
            contentType = "image/jpeg";
        } else if (fileName.endsWith("mp4")) {
            contentType = "video/mp4";
        }
        else if(fileName.endsWith("ico")){
            contentType = "image/x-icon";
        } else {
            contentType = "text/plain";
        }

        return contentType;
    }
}
