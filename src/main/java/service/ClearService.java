package service;

import DAO.*;
import result.ClearResult;

import java.sql.Connection;

public class ClearService {
  /**
   * Deletes ALL data from the database, including user accounts, auth tokens, and generated person and event data.
   * @return
   */

  public ClearResult clear() throws DataAccessException {

    ClearResult result = null;
    Database db = new Database();

    try {

      db.openConnection();
      Connection conn = db.getConnection();

      UserDAO userDAO = new UserDAO(conn);
      PersonDAO personDAO = new PersonDAO(conn);
      AuthenticationDAO authenticationDAO = new AuthenticationDAO(conn);
      EventDAO eventDAO = new EventDAO(conn);

      userDAO.clear();
      personDAO.clear();
      authenticationDAO.clear();
      eventDAO.clear();

    } catch (DataAccessException e) {
      e.printStackTrace();
      result = new ClearResult("Error: Bad data access. Could not clear.", false);
      db.closeConnection(false);
      return result;
    }

    result = new ClearResult("Clear succeeded.", true);
    db.closeConnection(true);
    return result;
  }

}
