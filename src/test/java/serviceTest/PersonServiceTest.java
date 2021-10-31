package serviceTest;

import DAO.AuthenticationDAO;
import DAO.DataAccessException;
import DAO.Database;
import DAO.PersonDAO;
import client.ServerConnectionException;
import model.Authentication;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import result.PersonResult;
import service.PersonService;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {
  private Database db;

  private Authentication bestAuth;
  private Authentication worstAuth;
  private AuthenticationDAO aDAO;

  private Person bestPerson;
  private PersonDAO pDAO;

  private PersonService ps;

  @BeforeEach
  public void setUp() throws DataAccessException, ServerConnectionException {
    db = new Database();

    ps = new PersonService();

    bestPerson = new Person("Kells_Scourge", "Atraks_1", "Taniks",
            "theScarred", "m", "ClovisBray", "Eramiskel", "HeavyShank");

    bestAuth = new Authentication("Atraks_1", "Riis");
    worstAuth = new Authentication("Crota", "Helmouth");



    db.openConnection();
    Connection conn = db.getConnection();

    db.clearTables();

    pDAO = new PersonDAO(conn);
    aDAO = new AuthenticationDAO(conn);
  }

  @AfterEach
  public void tearDown() throws DataAccessException {
    db.openConnection();
    db.clearTables();
    db.closeConnection(true);
  }


  @Test
  public void personPass() throws DataAccessException {
    pDAO.insert(bestPerson);
    aDAO.insert(bestAuth);

    assertEquals(bestPerson, pDAO.find(bestPerson.getPersonID(), bestPerson.getAssociatedUsername()));

    db.closeConnection(true);


    PersonResult personResult = ps.person(bestPerson.getPersonID(), bestAuth.getAuthToken());
    Boolean success = personResult.isSuccess();

    assertEquals(true, success);
  }

  @Test
  public void personFail() throws DataAccessException {
    pDAO.insert(bestPerson);
    aDAO.insert(bestAuth);

    assertEquals(bestPerson, pDAO.find(bestPerson.getPersonID(), bestPerson.getAssociatedUsername()));

    db.closeConnection(true);


    PersonResult personResult = ps.person(bestPerson.getPersonID(), worstAuth.getAuthToken());
    Boolean success = personResult.isSuccess();

    assertEquals(false, success);
  }


}