package io.github.ardonplay.normalserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import io.github.ardonplay.normalserver.handlers.AbstractHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Objects;

@Slf4j
public class FileHandler extends AbstractHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        log.info("Request: {} {}", exchange.getRequestMethod(), exchange.getRequestURI());
        log.info("Headers: {}", exchange.getRequestHeaders().entrySet());
        switch (exchange.getRequestMethod()) {
            case "GET" -> getRequest(exchange);
            case "POST" -> postRequest(exchange);
        }
    }

    protected void postRequest(HttpExchange exchange) throws IOException {
        String fileName = "test.zip";
        String directoryPath = System.getProperty("user.dir");
        String filePath = directoryPath + File.separator + fileName;
        File file = new File(filePath);

        InputStream stream = exchange.getRequestBody();
        long size = Long.parseLong(exchange.getRequestHeaders().get("Content-Length").get(0));
        FileOutputStream outputStream = new FileOutputStream(file);
        write(outputStream, stream, size);
        log.info("File size: {}", file.length());

        exchange.sendResponseHeaders(200, 0);
        exchange.getResponseBody().close();
    }

    protected void getRequest(HttpExchange exchange) throws IOException {

        File file = getFile(exchange.getRequestURI().getPath());

        String fileType = getType(file);

        exchange.getResponseHeaders().set("Content-Type", fileType);

        exchange.sendResponseHeaders(200, file.length());

        write(exchange.getResponseBody(), new FileInputStream(file), file.length());

        exchange.getResponseBody().close();
    }


    protected File getFile(String path) {
        String route = getPath(path);
        File file = new File(route);
        if (file.isDirectory()) {
            file = new File(route + "/index.html");
        }
        return file;
    }

    protected String getPath(String route) {
        return Objects.requireNonNull(ClassLoader.getSystemClassLoader()
                .getResource("static")).getPath() + (route.equals("/") ? "" : route);
    }
}
