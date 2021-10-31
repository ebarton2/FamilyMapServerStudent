package serviceTest;

import DAO.*;
import client.ServerConnectionException;
import model.Authentication;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.*;
import result.ClearResult;
import result.EventResult;
import service.ClearService;
import service.EventService;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceTest {
  private Database db;

  private Authentication bestAuth;
  private Authentication worstAuth;
  private AuthenticationDAO aDAO;

  private Event bestEvent;
  private EventDAO eDao;

  private EventService es;

  @BeforeEach
  public void setUp() throws DataAccessException, ServerConnectionException {
    db = new Database();

    es = new EventService();

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
  public void eventPass() throws DataAccessException {
    eDao.insert(bestEvent);
    aDAO.insert(bestAuth);

    assertEquals(bestEvent, eDao.find(bestEvent.getEventID(), bestEvent.getUsername()));

    db.closeConnection(true);


    EventResult eventResult = es.event(bestEvent.getEventID(), bestAuth.getAuthToken());
    Boolean success = eventResult.isSuccess();

    assertEquals(true, success);
  }

  @Test
  public void eventFail() throws DataAccessException {
    eDao.insert(bestEvent);
    aDAO.insert(worstAuth);

    assertEquals(bestEvent, eDao.find(bestEvent.getEventID(), bestEvent.getUsername()));

    db.closeConnection(true);


    EventResult eventResult = es.event(bestEvent.getEventID(), worstAuth.getAuthToken());
    Boolean success = eventResult.isSuccess();

    assertEquals(false, success);
  }

}