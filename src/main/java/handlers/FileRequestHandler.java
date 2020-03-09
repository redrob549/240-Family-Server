package handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.file.Files;

public class FileRequestHandler extends Handler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
                String urlPath = exchange.getRequestURI().toString();
                if (urlPath == null || urlPath.equals("/")) {
                    urlPath = "/index.html";
                }
                String filePath = "web" + urlPath;
                File webPage = new File(filePath);
                if (webPage.exists()) {
                    System.out.println("serving homepage");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    Files.copy(webPage.toPath(), respBody);
                    exchange.getResponseBody().close();
                    System.out.println("homepage served");
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    File notFound = new File("web/HTML/404.html");
                    OutputStream respBody = exchange.getResponseBody();
                    Files.copy(notFound.toPath(), respBody);
                    exchange.getResponseBody().close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
