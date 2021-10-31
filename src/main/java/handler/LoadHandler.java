package handler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

import DAO.DataAccessException;
import UtilityFunctions.ResponseBody;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.*;
import request.LoadRequest;
import result.LoadResult;
import service.LoadService;

public class LoadHandler implements HttpHandler {
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    boolean success = false;

    try {
      System.out.println("Test if it is a POST method in Load");
      if (exchange.getRequestMethod().toLowerCase().equals("post")) {

        InputStreamReader inputStreamReader = new InputStreamReader(exchange.getRequestBody());

        LoadRequest loadRequest = composeRequest(inputStreamReader);

        System.out.println("Load Request composed.");

        LoadService loadService = new LoadService();
        LoadResult loadResult = loadService.load(loadRequest);
        System.out.println("Load Result received");

        String response = generateJson(loadResult);
        System.out.println(response);

        success = loadResult.isSuccess();

        ResponseBody responseBody = new ResponseBody();
        responseBody.respond(exchange, success, response);
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

  private LoadRequest composeRequest(InputStreamReader inputStreamReader) {
    Gson gson = new Gson();
    LoadRequest loadRequest = gson.fromJson(inputStreamReader, LoadRequest.class);
    return loadRequest;
  }

  private String generateJson (LoadResult loadResult) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(loadResult);
  }
}
