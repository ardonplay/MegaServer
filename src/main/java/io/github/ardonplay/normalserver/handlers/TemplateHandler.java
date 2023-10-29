package io.github.ardonplay.normalserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import io.github.ardonplay.javatronilizer.AttributeNotFoundException;
import io.github.ardonplay.javatronilizer.templater.Templater;
import io.github.ardonplay.normalserver.controllers.AbstractTemplateController;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

@Slf4j
public class TemplateHandler extends FileHandler {

    List<AbstractTemplateController> routes;

    public TemplateHandler(List<AbstractTemplateController> routes){
        this.routes = routes;
    }
    @Override
    protected void getRequest(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if (path.contains(".")) {
            super.getRequest(exchange);
        }

        String response;
        for (AbstractTemplateController p : routes) {
            if (p.getRoute().equals(path)) {
                response = String.join("\n", Files.readAllLines(getFile(p.onGet()).toPath(), Charset.defaultCharset()));

                Templater templater = new Templater(response, p.getModel());

                byte[] bytes;
                try {
                    bytes = templater.transform().getBytes();
                } catch (AttributeNotFoundException e) {
                    log.warn(e.getMessage());
                    bytes = e.getMessage().getBytes();
                }
                InputStream stream = new ByteArrayInputStream(bytes);
                exchange.getResponseHeaders().set("Content-Type", "text/html");
                exchange.sendResponseHeaders(200, bytes.length);
                write(exchange.getResponseBody(), stream, bytes.length);
                exchange.getResponseBody().close();
                break;
            }
        }
    }
}
