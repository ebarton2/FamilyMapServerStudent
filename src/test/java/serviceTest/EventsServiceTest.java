package serviceTest;

import DAO.AuthenticationDAO;
import DAO.DataAccessException;
import DAO.Database;
import DAO.EventDAO;
import client.ServerConnectionException;
import model.Authentication;
import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import result.EventResult;
import result.EventsResult;
import service.EventService;
import service.EventsService;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class EventsServiceTest {
  private Database db;

  private Authentication bestAuth;
  private Authentication worstAuth;
  private AuthenticationDAO aDAO;

  private Event bestEvent;
  private EventDAO eDao;

  private EventsService es;

  @BeforeEach
  public void setUp() throws DataAccessException, ServerConnectionException {
    db = new Database();

    es = new EventsService();

    bestEvent = new Event("Fall_of_Torobatl", "Caiatl", "CaiatlCalus",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Death", 2015);

    bestAuth = new Authentication("Caiatl", "Torobatl");
    worstAuth = new Authentication("Crota", "Helmouth");



    db.openConnection();
    Connection conn = db.getConnection();

    db.clearTables();

    eDao = new EventDAO(conn);
    aDAO = new AuthenticationDAO(conn);
  }

  @AfterEach
  public void tearDown() throws DataAccessException {
    db.openConnection();
    db.clearTables();
    db.closeConnection(true);
  }


  @Test
  public void eventsPass() throws DataAccessException {
    eDao.insert(bestEvent);
    aDAO.insert(bestAuth);

    assertEquals(bestEvent, eDao.find(bestEvent.getEventID(), bestEvent.getUsername()));

    db.closeConnection(true);


    EventsResult eventsResult = es.events(bestAuth.getAuthToken());
    Boolean success = eventsResult.isSuccess();

    assertEquals(true, success);
  }

  @Test
  public void eventsFail() throws DataAccessException {
    eDao.insert(bestEvent);
    aDAO.insert(worstAuth);

    assertEquals(bestEvent, eDao.find(bestEvent.getEventID(), bestEvent.getUsername()));

    db.closeConnection(true);


    EventsResult eventsResult = es.events(worstAuth.getAuthToken());
    Boolean success = eventsResult.isSuccess();

    assertEquals(false, success);
  }

}