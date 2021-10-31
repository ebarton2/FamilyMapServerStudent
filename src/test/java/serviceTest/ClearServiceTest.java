package serviceTest;

import DAO.*;
import client.ServerConnectionException;
import model.Authentication;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import result.ClearResult;
import service.ClearService;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {
  private Database db;

  private Authentication bestAuth;
  private AuthenticationDAO aDAO;

  private Event bestEvent;
  private EventDAO eDao;

  private Person bestPerson;
  private PersonDAO pDao;

  private User bestUser;
  private UserDAO uDAO;

  private ClearService cs;

  @BeforeEach
  public void setUp() throws DataAccessException, ServerConnectionException {
    db = new Database();
    db.openConnection();

    cs = new ClearService();

    bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Biking_Around", 2016);

    bestPerson = new Person("Kells_Scourge", "Atraks_1", "Taniks",
            "theScarred", "m", "ClovisBray", "Eramiskel", "HeavyShank");

    bestUser = new User("IronLord", "Felspring", "mylie@hawkmoon.net",
            "Lord", "Felwinter", "m", "Warmind_Exo");

    bestAuth = new Authentication("Caiatl", "Torobatl");

    Connection conn = db.getConnection();
    db.clearTables();

    eDao = new EventDAO(conn);
    pDao = new PersonDAO(conn);
    uDAO = new UserDAO(conn);
    aDAO = new AuthenticationDAO(conn);

  }

  @AfterEach
  public void tearDown() throws DataAccessException {
    db.openConnection();
    db.clearTables();
    db.closeConnection(true);
  }

  @Test
  public void clearPass() throws DataAccessException {
    uDAO.insert(bestUser);
    eDao.insert(bestEvent);
    pDao.insert(bestPerson);
    aDAO.insert(bestAuth);

    assertEquals(bestUser, uDAO.find(bestUser.getPersonID()));

    db.closeConnection(true);

    ClearResult expected = new ClearResult("Clear succeeded.", true);

    cs.clear();

    db.openConnection();
    uDAO = new UserDAO(db.getConnection());
    eDao = new EventDAO(db.getConnection());
    pDao = new PersonDAO(db.getConnection());
    aDAO = new AuthenticationDAO(db.getConnection());

    assertNotEquals(bestUser, uDAO.find(bestUser.getPersonID()));
    assertNotEquals(bestPerson, pDao.find(bestPerson.getPersonID(), bestPerson.getAssociatedUsername()));
    assertNotEquals(bestEvent, eDao.find(bestEvent.getEventID(), bestEvent.getUsername()));
    assertNotEquals(bestAuth, aDAO.find(bestAuth.getAuthToken()));
    db.closeConnection(false);
  }

  @Test
  public void clearPassAgain() throws DataAccessException {
    /*db.openConnection();
    uDAO = new UserDAO(db.getConnection());
    eDao = new EventDAO(db.getConnection());
    pDao = new PersonDAO(db.getConnection());
    aDAO = new AuthenticationDAO(db.getConnection());*/

    uDAO.insert(bestUser);
    eDao.insert(bestEvent);
    pDao.insert(bestPerson);
    aDAO.insert(bestAuth);

    db.closeConnection(true);

    ClearResult fail = new ClearResult("Error: Bad data access. Could not clear.", false);

    ClearResult ex = cs.clear();

    assertNotEquals(fail, ex);
  }



}