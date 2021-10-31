package serviceTest;

import static org.junit.jupiter.api.Assertions.*;

import DAO.*;
import client.ServerConnectionException;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.FillRequest;
import result.FillResult;
import service.FillService;

import java.io.FileNotFoundException;
import java.sql.Connection;

class FillServiceTest {
  private Database db;

  private User bestUser;
  private UserDAO uDAO;

  private FillService fs;

  @BeforeEach
  public void setUp() throws DataAccessException, ServerConnectionException, FileNotFoundException {
    db = new Database();
    db.openConnection();

    fs = new FillService();

    bestUser= new User("IronLord", "Felspring", "mylie@hawkmoon.net",
            "Lord", "Felwinter", "m", "Warmind_Exo");


    Connection conn = db.getConnection();
    db.clearTables();


    uDAO = new UserDAO(conn);
  }

  @AfterEach
  public void tearDown() throws DataAccessException {
    db.openConnection();
    db.clearTables();
    db.closeConnection(true);
  }

  @Test
  public void fillPass() throws DataAccessException {
    uDAO.insert(bestUser);

    assertEquals(bestUser, uDAO.find(bestUser.getPersonID()));

    FillRequest fr = new FillRequest(bestUser.getUsername(), 1);


    db.closeConnection(true);

    FillResult expected = fs.fill(fr);
    boolean success = expected.isSuccess();

    assertEquals(true, success);

  }

  @Test
  public void fillFail() throws DataAccessException {
    uDAO.insert(bestUser);

    assertEquals(bestUser, uDAO.find(bestUser.getPersonID()));

    FillRequest fr = new FillRequest("Felwinter", 1);

    db.closeConnection(true);

    FillResult expected = fs.fill(fr);
    boolean success = expected.isSuccess();

    assertEquals(false, success);

  }

}