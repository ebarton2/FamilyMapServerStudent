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
import result.PersonResult;
import result.PersonsResult;
import service.PersonService;
import service.PersonsService;

public class PersonHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    System.out.println("In the PersonHandler");
    boolean success = false;

    try {
      System.out.println("Test if this is a GET method");
      if (exchange.getRequestMethod().toLowerCase().equals("get")) {
        Headers reqHeaders = exchange.getRequestHeaders();

        if (reqHeaders.containsKey("Authorization")) {

          String authToken = reqHeaders.getFirst("Authorization");

          String urlPath = exchange.getRequestURI().toString();
          String[] parameters = urlPath.split("/");
          String response;

          if (parameters.length > 2) {
            String personID = parameters[2];
            PersonService personService = new PersonService();
            PersonResult personResult = personService.person(personID, authToken);

            response = generateSingleJson(personResult);
            System.out.println(response);
            success = personResult.isSuccess();

            ResponseBody responseBody = new ResponseBody();
            responseBody.respond(exchange, success, response);

          } else {
            PersonsService personsService = new PersonsService();
            PersonsResult personsResult = personsService.persons(authToken);

            response = generateArrayJson(personsResult);
            System.out.println(response);
            success = personsResult.isSuccess();

            ResponseBody responseBody = new ResponseBody();
            responseBody.respond(exchange, success, response);
          }

        }

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

  private String generateSingleJson (PersonResult personResult) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(personResult);
  }

  private String generateArrayJson (PersonsResult personsResult) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(personsResult);
  }

}
