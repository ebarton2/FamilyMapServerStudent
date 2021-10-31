package handler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.*;

import DAO.DataAccessException;
import UtilityFunctions.ResponseBody;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.*;
import request.LoginRequest;
import request.RegisterRequest;
import result.LoginResult;
import result.RegisterResult;
import service.LoginService;

public class LoginHandler implements HttpHandler {
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    System.out.println("In the LoginHandler");
    boolean success = false;

    try {
      System.out.println("Test if it is a POST method");
      if (exchange.getRequestMethod().toLowerCase().equals("post")) {

        InputStreamReader inputStreamReader = new InputStreamReader(exchange.getRequestBody());

        LoginRequest loginRequest = composeRequest(inputStreamReader);

        System.out.println("Login Request composed");
        LoginService loginService = new LoginService();
        LoginResult loginResult = loginService.login(loginRequest);
        System.out.println("Login Result received");

        String response = generateJson(loginResult);

        System.out.println(response);
        success = loginResult.getSuccess();

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


  private LoginRequest composeRequest(InputStreamReader inputStreamReader) {
    Gson gson = new Gson();
    LoginRequest loginRequest = gson.fromJson(inputStreamReader, LoginRequest.class);
    System.out.println("Composing Login Request:");
    System.out.println(loginRequest.getUsername());
    System.out.println(loginRequest.getPassword());
    return loginRequest;
  }

  private String generateJson (LoginResult loginResult) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(loginResult);
  }

}
