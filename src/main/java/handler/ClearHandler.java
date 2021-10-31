package handler;

import java.io.*;
import java.net.*;

import DAO.DataAccessException;
import UtilityFunctions.JsonOutput;
import UtilityFunctions.ResponseBody;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.*;
import result.ClearResult;
import service.ClearService;

public class ClearHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    boolean success = false;
    try {
      System.out.println("Test if it is a POST method for Clear");
      if (exchange.getRequestMethod().toLowerCase().equals("post")) {

        ClearService clearService = new ClearService();
        ClearResult clearResult = clearService.clear();

        String response = generateJson(clearResult);

        System.out.println(response);
        success = clearResult.getSuccess();

        ResponseBody responseBody = new ResponseBody();
        responseBody.respond(exchange, success, response);

        // TODO: Remove extra code
/*        JsonOutput jsonOutput = new JsonOutput();

        if (!success) {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
          OutputStream respBody = exchange.getResponseBody();
          jsonOutput.writeOutput(response, respBody);
          respBody.close();
        } else {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
          OutputStream respBody = exchange.getResponseBody();
          jsonOutput.writeOutput(response, respBody);
          respBody.close();
        }*/
      }

      exchange.getResponseBody().close();
    } catch (IOException e) {
      e.printStackTrace();
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
      exchange.getResponseBody().close();

    } catch (DataAccessException e) {
      e.printStackTrace();
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
      exchange.getResponseBody().close();
    }
  }

    private String generateJson (ClearResult clearResult) {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      return gson.toJson(clearResult);
    }

}
