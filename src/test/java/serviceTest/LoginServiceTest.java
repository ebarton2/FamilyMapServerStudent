package serviceTest;

import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDAO;
import client.ServerConnectionException;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import result.LoginResult;
import service.LoginService;

import java.io.FileNotFoundException;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
  private Database db;

  private User bestUser;
  private UserDAO uDAO;

  private LoginService ls;

  @BeforeEach
  public void setUp() throws DataAccessException, ServerConnectionException, FileNotFoundException {
    db = new Database();
    db.openConnection();

    ls = new LoginService();

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
  public void loginPass() throws DataAccessException {
    uDAO.insert(bestUser);

    assertEquals(bestUser, uDAO.find(bestUser.getPersonID()));

    LoginRequest lr = new LoginRequest(bestUser.getUsername(), bestUser.getPassword());


    db.closeConnection(true);

    LoginResult expected = ls.login(lr);
    boolean success = expected.getSuccess();

    assertEquals(true, success);

  }

  @Test
  public void loginFail() throws DataAccessException {
    uDAO.insert(bestUser);

    assertEquals(bestUser, uDAO.find(bestUser.getPersonID()));

    LoginRequest lr = new LoginRequest("Saladin", "IsA****");


    db.closeConnection(true);

    LoginResult expected = ls.login(lr);
    boolean success = expected.getSuccess();

    assertEquals(false, success);
  }


}