package handler;

import java.io.IOException;
import java.net.*;

import DAO.DataAccessException;
import UtilityFunctions.ResponseBody;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.*;

import result.EventResult;
import result.EventsResult;
import service.EventService;
import service.EventsService;

public class EventHandler implements HttpHandler {
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    System.out.println("In the EventHandler");
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
            // TODO: Get a specific event
            String eventID = parameters[2];
            EventService eventService = new EventService();
            EventResult eventResult = eventService.event(eventID, authToken);

            response = generateSingleJson(eventResult);
            System.out.println(response);
            success = eventResult.isSuccess();

            ResponseBody responseBody = new ResponseBody();
            responseBody.respond(exchange, success, response);

          } else {
            // TODO: Get all events with authKey
            EventsService eventsService = new EventsService();
            EventsResult eventsResult = eventsService.events(authToken);

            response = generateArrayJson(eventsResult);
            System.out.println(response);
            success = eventsResult.isSuccess();

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

  private String generateSingleJson (EventResult eventResult) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(eventResult);
  }

  private String generateArrayJson (EventsResult eventsResult) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(eventsResult);
  }
}
