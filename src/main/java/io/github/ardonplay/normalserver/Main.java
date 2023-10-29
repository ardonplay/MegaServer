package io.github.ardonplay.normalserver;

import com.sun.net.httpserver.HttpServer;
import io.github.ardonplay.normalserver.controllers.MainController;
import io.github.ardonplay.normalserver.handlers.TemplateHandler;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.Executors;

@Slf4j
public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
        server.createContext("/", new TemplateHandler(List.of(new MainController())));
        server.setExecutor(Executors.newFixedThreadPool(10));
        log.info("Server started!");
        server.start();
    }
}