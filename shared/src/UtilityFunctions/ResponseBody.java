package UtilityFunctions;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class ResponseBody {
  public static void respond(HttpExchange exchange, boolean success, String response) throws IOException {
    JsonOutput jsonOutput = new JsonOutput();
    if (!success) {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
      OutputStream respBody = exchange.getResponseBody();
      jsonOutput.writeOutput(response, respBody);
      respBody.close();
    } else {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
      OutputStream respBody = exchange.getResponseBody();
      jsonOutput.writeOutput(response, respBody);
      respBody.close();
    }
  }

}
