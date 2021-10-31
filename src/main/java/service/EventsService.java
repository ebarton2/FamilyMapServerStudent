package service;

import DAO.AuthenticationDAO;
import DAO.DataAccessException;
import DAO.Database;
import DAO.EventDAO;
import model.Authentication;
import model.Event;
import result.EventsResult;

import java.sql.Connection;
import java.util.ArrayList;

public class EventsService {

  /**
   *  Returns ALL events for ALL family members of the current user. The current user is determined from the provided auth token.
   * @param authToken auth token of user
   * @return
   */
  public EventsResult events(String authToken) throws DataAccessException {
    EventsResult eventsResult = null;
    Database db = new Database();

    try {
      db.openConnection();
      Connection conn = db.getConnection();

      AuthenticationDAO authDAO = new AuthenticationDAO(conn);
      Authentication auth = authDAO.findAuth(authToken);

      if (auth == null) {
        eventsResult = new EventsResult("Error: Unable to authenticate event in EventsService", false);
        db.closeConnection(false);
        return eventsResult;
      }

      String username = auth.getUsername();
      EventDAO eventDAO = new EventDAO(conn);
      ArrayList<Event> events = eventDAO.getAll(username);

      if (events == null) {
        eventsResult = new EventsResult("Error: Unable to find events in EventsService", false);
        db.closeConnection(false);
        return eventsResult;
      }

      eventsResult = new EventsResult(events, true);

      db.closeConnection(true);

    } catch (DataAccessException e) {
      e.printStackTrace();
      eventsResult = new EventsResult("Error: failed to access database during EventsService", false);
      db.closeConnection(false);
      return eventsResult;
    }

    return eventsResult;
  }
}
