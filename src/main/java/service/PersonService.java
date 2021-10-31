package service;

import DAO.*;
import model.Authentication;
import model.Person;
import result.PersonResult;

import java.sql.Connection;

public class PersonService {

  /**
   * Returns the single Person object with the specified ID.
   * @param personID ID of a given person
   * @param authToken auth token of the user
   * @return
   */
  public PersonResult person(String personID, String authToken) throws DataAccessException {
    PersonResult personResult = null;
    Database db = new Database();

    try {
      db.openConnection();
      Connection conn = db.getConnection();
      AuthenticationDAO authDAO = new AuthenticationDAO(conn);
      Authentication auth = authDAO.findAuth(authToken);

      if (auth == null) {
        personResult = new PersonResult("Error: Unable to authenticate person in PersonService", false);
        db.closeConnection(false);
        return personResult;
      }

      String username = auth.getUsername();
      PersonDAO personDAO = new PersonDAO(conn);
      Person person = personDAO.find(personID, username);

      if (person == null) {
        personResult = new PersonResult("Error: Unable to find person in PersonService", false);
        db.closeConnection(false);
        return personResult;
      }

      personResult = new PersonResult(person.getPersonID(), person.getAssociatedUsername(), person.getFirstName(), person.getLastName(),
              person.getGender(), person.getFatherID(), person.getMotherID(), person.getSpouseID(), true);

      db.closeConnection(true);

    } catch (DataAccessException e) {
      e.printStackTrace();
      personResult = new PersonResult("Error: failed to access database during PersonService", false);
      db.closeConnection(false);
      return personResult;
    }

    return personResult;
  }
}
