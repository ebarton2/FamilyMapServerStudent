package handler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

import DAO.DataAccessException;
import UtilityFunctions.JsonOutput;
import UtilityFunctions.ResponseBody;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.*;
import request.RegisterRequest;
import result.RegisterResult;
import service.RegisterService;

public class RegisterHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange exchange) throws IOException {

    boolean success = false;

    try {

      if (exchange.getRequestMethod().toLowerCase().equals("post")) {

        InputStreamReader inputStreamReader = new InputStreamReader(exchange.getRequestBody());
        RegisterRequest registerRequest = composeRequest(inputStreamReader);
        RegisterService registerService = new RegisterService();
        RegisterResult registerResult = registerService.register(registerRequest);

        String response = generateJson(registerResult);

        System.out.println(response);
        success = registerResult.getSuccess();

        ResponseBody responseBody = new ResponseBody();
        responseBody.respond(exchange, success, response);
      }

      exchange.getResponseBody().close();
    } catch (IOException e) {
      e.printStackTrace();
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
      exchange.getResponseBody().close();

    } catch (DataAccessException e) {
      e.printStackTrace();
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
      exchange.getResponseBody().close();
    }
  }

  private RegisterRequest composeRequest(InputStreamReader inputStreamReader) {
    Gson gson = new Gson();
    RegisterRequest registerRequest = gson.fromJson(inputStreamReader, RegisterRequest.class);
    return registerRequest;
  }

  private String generateJson (RegisterResult registerResult) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(registerResult);
  }

}
