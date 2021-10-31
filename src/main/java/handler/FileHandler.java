package handler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.file.Files;

import com.sun.net.httpserver.*;

public class FileHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    try {
      if (exchange.getRequestMethod().toLowerCase().equals("get")) {

        String urlPath = exchange.getRequestURI().toString();

        if (urlPath == null || urlPath.equals("/")) {
          urlPath = "/index.html";
        }

        String filePath = "web" + urlPath;

        File file = new File(filePath);

        if (file.exists()) {

          OutputStream respBody = exchange.getResponseBody();
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
          Files.copy(file.toPath(), respBody);
          exchange.getResponseBody().close();

        } else {
          filePath = "web/HTML/404.html";
          file = new File(filePath);

          OutputStream respBody = exchange.getResponseBody();
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
          Files.copy(file.toPath(), respBody);
          exchange.getResponseBody().close();
        }

      } else {

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
        exchange.getResponseBody().close();
      }

    } catch (IOException e) {

      exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
      exchange.getResponseBody().close();
      e.printStackTrace();
    }

  }

}
