package service;

import DAO.AuthenticationDAO;
import DAO.DataAccessException;
import DAO.Database;
import DAO.EventDAO;
import model.Authentication;
import model.Event;
import result.EventResult;

import java.sql.Connection;

public class EventService {

  /**
   *  Returns the single Event object with the specified ID.
   * @param eventID ID of the given event
   * @param authToken auth token of user
   * @return
   */
  public EventResult event(String eventID, String authToken) throws DataAccessException {
    EventResult eventResult = null;
    Database db = new Database();

    try {
      db.openConnection();
      Connection conn = db.getConnection();
      AuthenticationDAO authDAO = new AuthenticationDAO(conn);
      Authentication auth = authDAO.findAuth(authToken);

      if (auth == null) {
        eventResult = new EventResult("Error: Unable to authenticate event in EventService", false);
        db.closeConnection(false);
        return eventResult;
      }

      String username = auth.getUsername();
      EventDAO eventDAO = new EventDAO(conn);
      Event event = eventDAO.find(eventID, username);

      if (event == null) {
        eventResult = new EventResult("Error: Unable to locate event in EventService", false);
        db.closeConnection(false);
        return eventResult;
      }

      eventResult = new EventResult(event.getEventID(), event.getUsername(), event.getPersonID(),
              event.getLatitude(), event.getLongitude(), event.getCountry(), event.getCity(),
              event.getEventType(), event.getYear(), true);

      db.closeConnection(true);


    } catch (DataAccessException e) {
      e.printStackTrace();
      eventResult = new EventResult("Error: Failed to access event database in EventService", false);
      db.closeConnection(false);
      return eventResult;
    }


    return eventResult;
  }
}
