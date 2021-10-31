package serviceTest;

import DAO.*;
import client.ServerConnectionException;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoadRequest;
import result.LoadResult;
import service.LoadService;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LoadServiceTest {
  private Database db;

  private User bestUser;
  private UserDAO uDAO;
  private ArrayList<User> users;

  private Event bestEvent;
  private EventDAO eDao;
  private ArrayList<Event> events;

  private Person bestPerson;
  private PersonDAO pDao;
  private ArrayList<Person> persons;

  private LoadService ls;
  private LoadRequest lrq;

  @BeforeEach
  public void setUp() throws DataAccessException, ServerConnectionException, FileNotFoundException {
    db = new Database();
    db.openConnection();

    ls = new LoadService();

    bestUser= new User("IronLord", "Felspring", "mylie@hawkmoon.net",
            "Lord", "Felwinter", "m", "Warmind_Exo");

    bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Biking_Around", 2016);

    bestPerson = new Person("Kells_Scourge", "Atraks_1", "Taniks",
            "theScarred", "m", "ClovisBray", "Eramiskel", "HeavyShank");

    users = new ArrayList<>();
    events = new ArrayList<>();
    persons = new ArrayList<>();

    users.add(bestUser);
    events.add(bestEvent);
    persons.add(bestPerson);

    lrq = new LoadRequest(users, persons, events);


    Connection conn = db.getConnection();
    db.clearTables();


    eDao = new EventDAO(conn);
    pDao = new PersonDAO(conn);
    uDAO = new UserDAO(conn);
  }

  @AfterEach
  public void tearDown() throws DataAccessException {
    db.openConnection();
    db.clearTables();
    db.closeConnection(true);
  }

  @Test
  public void loadPass() throws DataAccessException {
    db.closeConnection(true);

    LoadResult expected = ls.load(lrq);
    boolean success = expected.isSuccess();

    assertEquals(true, success);

  }

  @Test
  public void loadFail() throws DataAccessException {
    db.closeConnection(true);

    LoadResult expected = ls.load(null);
    boolean success = expected.isSuccess();

    assertEquals(false, success);
  }
}