package service;

import DAO.AuthenticationDAO;
import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDAO;
import model.Authentication;
import model.User;
import request.FillRequest;
import request.RegisterRequest;
import result.FillResult;
import result.RegisterResult;
import UtilityFunctions.IDGenerator;

import java.io.FileNotFoundException;
import java.sql.Connection;

public class RegisterService {

  /**
   * Creates a new user account, generates 4 generations of ancestor data for the new user, logs the user in, and returns an auth token.
   * @param r register request provided
   * @return
   */
  public RegisterResult register(RegisterRequest r) throws DataAccessException {
    RegisterResult registerResult = null;
    Database db = new Database();

    if (!checkValidity(r)) {
      registerResult = new RegisterResult("Error: Invalid input. Check that no values are null, and gender is either m or f.", false);
      return registerResult;
    }
    System.out.println("Valid request");

    try {
      db.openConnection();
      Connection conn = db.getConnection();
      UserDAO userDAO = new UserDAO(conn);

      System.out.println("UserDAO open");
      User user = createUser(r);
      userDAO.insert(user);
      System.out.println("User inserted");

      AuthenticationDAO authenticationDAO = new AuthenticationDAO(conn);

      Authentication authToken = createAuthToken(r);
      authenticationDAO.insert(authToken);
      db.closeConnection(true);

      FillRequest fillRequest = new FillRequest(user.getUsername());
      FillService fillService = new FillService();
      FillResult fillResult =  fillService.fill(fillRequest);

      if (fillResult.isSuccess()) {
        registerResult = new RegisterResult(authToken.getAuthToken(), authToken.getUsername(), user.getPersonID(), true);
      } else {
        registerResult = new RegisterResult("Error: unable to fill the database during registration", false);
      }

    } catch (DataAccessException | FileNotFoundException e) {
      e.printStackTrace();
      registerResult = new RegisterResult("Error: Failure to register user in database", false);
      db.closeConnection(false);
      return registerResult;
    }


    return registerResult;
  }

  private static boolean checkValidity(RegisterRequest r) {
    if (r.getEmail() == null
    || r.getUsername() == null
    || r.getPassword() == null
    || r.getFirstName() == null
    || r.getLastName() == null
    || r.getGender() == null) {
      return false;
    }

    if (!r.getGender().equals("m") && !r.getGender().equals("f")) {
      return false;
    }

    return true;
  }

  private static User createUser(RegisterRequest r) {
    IDGenerator generator = new IDGenerator();
    String personID = generator.createID();
    User user = new User(r.getUsername(),r.getPassword(),r.getEmail(),r.getFirstName(),r.getLastName(),r.getGender(), personID);
    return user;
  }

  private static Authentication createAuthToken(RegisterRequest r) {
    IDGenerator generator = new IDGenerator();
    String authToken = generator.createID();
    Authentication authentication = new Authentication(r.getUsername(), authToken);
    return authentication;
  }

}
