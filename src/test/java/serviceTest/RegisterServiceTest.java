package serviceTest;

import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDAO;
import client.ServerConnectionException;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;
import result.RegisterResult;
import service.RegisterService;

import java.io.FileNotFoundException;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest {
  private Database db;

  private User bestUser;
  private UserDAO uDAO;

  private RegisterService rs;

  @BeforeEach
  public void setUp() throws DataAccessException, ServerConnectionException, FileNotFoundException {
    db = new Database();
    db.openConnection();

    rs = new RegisterService();

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
  public void registerPass() throws DataAccessException {
    RegisterRequest rr = new RegisterRequest(bestUser.getUsername(), bestUser.getPassword(), bestUser.getEmail(), bestUser.getFirstName(), bestUser.getLastName(), bestUser.getGender());


    db.closeConnection(true);

    RegisterResult expected = rs.register(rr);
    boolean success = expected.getSuccess();

    assertEquals(true, success);
  }

  @Test
  public void registerFail() throws DataAccessException {
    RegisterRequest rr = new RegisterRequest(null, bestUser.getPassword(), bestUser.getEmail(), bestUser.getFirstName(), bestUser.getLastName(), bestUser.getGender());


    db.closeConnection(true);

    RegisterResult expected = rs.register(rr);
    boolean success = expected.getSuccess();

    assertEquals(false, success);

  }


}