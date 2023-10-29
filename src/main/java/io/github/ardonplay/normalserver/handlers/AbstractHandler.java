package io.github.ardonplay.normalserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AbstractHandler implements HttpHandler {
    protected static final int BUFF_SIZE = 10000;
    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }

    protected void write(OutputStream writtable, InputStream readable, long length) throws IOException {
        long writted = 0;
        while (writted != length) {
            byte[] buffer = readable.readNBytes(BUFF_SIZE);
            writtable.write(buffer);
            writted += buffer.length;
        }
        readable.close();
    }

    protected static String getType(File file) {
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
        } else if (fileName.endsWith("ico")) {
            contentType = "image/x-icon";
        } else {
            contentType = "text/plain";
        }
        return contentType;
    }
}
