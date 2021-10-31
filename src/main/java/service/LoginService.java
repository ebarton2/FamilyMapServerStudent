package service;

import DAO.AuthenticationDAO;
import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDAO;
import UtilityFunctions.IDGenerator;
import model.Authentication;
import model.User;
import result.LoginResult;
import request.LoginRequest;

public class LoginService {

  /**
   * Logs in the user and returns an auth token.
   * @param l login request provided
   * @return
   */
  public LoginResult login(LoginRequest l) throws DataAccessException {
    LoginResult loginResult = null;
    Database db = new Database();
    if (!checkValidity(l)) {
      loginResult = new LoginResult("ERROR: Invalid input", false);
      return loginResult;
    }
    System.out.println("Valid");

    try {
      db.openConnection();
      UserDAO userDAO = new UserDAO(db.getConnection());

      User user = userDAO.login(l.getUsername(), l.getPassword());
      if (user == null) {
        loginResult = new LoginResult("Error: Failure to locate user in database", false);
        db.closeConnection(false);
        return loginResult;
      }

      System.out.println("Username: " + user.getUsername());
      System.out.println("Password: " + user.getPassword());
      System.out.println("PersonID: " + user.getPersonID());

      AuthenticationDAO authenticationDAO = new AuthenticationDAO(db.getConnection());
      Authentication authToken = createAuthToken(user);
      authenticationDAO.insert(authToken);

      loginResult = new LoginResult(authToken.getAuthToken(), user.getUsername(), user.getPersonID(), true);


    } catch (DataAccessException e) {
      e.printStackTrace();
      loginResult = new LoginResult("Error: Failure to locate user in database", false);
      db.closeConnection(false);
      return loginResult;
    }
    db.closeConnection(true);
    return loginResult;
  }

  private static boolean checkValidity(LoginRequest r) {
    if (r.getUsername() == null || r.getPassword() == null) {
      return false;
    }
    return true;
  }

  private static Authentication createAuthToken(User u) throws DataAccessException {
    Authentication authentication;

    IDGenerator generator = new IDGenerator();

    authentication = new Authentication(u.getUsername(), generator.createID());
    return authentication;
  }
}
