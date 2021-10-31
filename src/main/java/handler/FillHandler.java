package handler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

import DAO.DataAccessException;
import UtilityFunctions.JsonOutput;
import UtilityFunctions.ResponseBody;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.*;
import request.FillRequest;
import result.FillResult;
import service.FillService;

public class FillHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    boolean success = false;
    try {
      System.out.println("Test if it is a POST method in Fill");
      if (exchange.getRequestMethod().toLowerCase().equals("post")) {

        String urlPath = exchange.getRequestURI().toString();
        String[] parameters = urlPath.split("/");

        FillRequest fillRequest = fillCreate(parameters);
        FillService fillService = new FillService();
        FillResult fillResult = fillService.fill(fillRequest);

        String response = generateJson(fillResult);

        System.out.println(response);
        success = fillResult.isSuccess();

        ResponseBody responseBody = new ResponseBody();
        responseBody.respond(exchange, success, response);
      }

    } catch (DataAccessException e) {
      e.printStackTrace();
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
      exchange.getResponseBody().close();
    } catch (IOException e) {
      e.printStackTrace();
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
      exchange.getResponseBody().close();
    }
  }

  private static FillRequest fillCreate (String[] parameters) {
    FillRequest fillRequest;

    if (parameters.length == 3) {
      fillRequest = new FillRequest(parameters[2]);
      return fillRequest;
    } else if (parameters.length == 4) {
      int generations=Integer.parseInt(parameters[3]);
      fillRequest=new FillRequest(parameters[2], generations);
      return fillRequest;
    }

    return null;
  }

  private String generateJson (FillResult fillResult) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(fillResult);
  }


}
