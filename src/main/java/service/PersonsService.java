package service;

import DAO.AuthenticationDAO;
import DAO.DataAccessException;
import DAO.Database;
import DAO.PersonDAO;
import model.Authentication;
import model.Person;
import result.PersonResult;
import result.PersonsResult;

import java.sql.Connection;
import java.util.ArrayList;

public class PersonsService {
  /**
   * Returns ALL family members of the current user.
   * The current user is determined from the provided auth token.
   *
   * @param authToken auth token of user
   * @return
   */
  public PersonsResult persons(String authToken) throws DataAccessException {
    PersonsResult personsResult = null;
    Database db = new Database();

    try {
      db.openConnection();
      Connection conn = db.getConnection();
      AuthenticationDAO authDAO = new AuthenticationDAO(conn);
      Authentication auth = authDAO.findAuth(authToken);

      if (auth == null) {
        personsResult = new PersonsResult("Error: Unable to authenticate person in PersonsService", false);
        db.closeConnection(false);
        return personsResult;
      }

      String username = auth.getUsername();
      PersonDAO personDAO = new PersonDAO(conn);
      ArrayList<Person> persons = personDAO.getAll(username);

      if (persons == null) {
        personsResult = new PersonsResult("Error: Unable to find people in PersonsService", false);
        db.closeConnection(false);
        return personsResult;
      }

      personsResult = new PersonsResult(persons, true);

      db.closeConnection(true);

    } catch (DataAccessException e) {
      e.printStackTrace();
      personsResult = new PersonsResult("Error: failed to access database during PersonsService", false);
      db.closeConnection(false);
      return personsResult;
    }

    return personsResult;
  }
}
