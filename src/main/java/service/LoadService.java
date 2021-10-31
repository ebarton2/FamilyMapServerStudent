package service;

import DAO.*;
import request.LoadRequest;
import result.LoadResult;

import java.sql.Connection;

public class LoadService {

  private int users = 0;
  private int people = 0;
  private int events = 0;

  /**
   * Clears all data from the database (just like the /clear API),
   * and then loads the posted user, person, and event data into the database.
   *
   * @param l load request submitted
   * @return
   */
  public LoadResult load(LoadRequest l) throws DataAccessException {
    LoadResult loadResult = null;
    Database db = new Database();

    if (!checkValidity(l)) {
      loadResult = new LoadResult("Error: Invalid loadRequest", false);
      return loadResult;
    }

    try {
      db.openConnection();
      Connection conn = db.getConnection();
      UserDAO userDAO = new UserDAO(conn);
      PersonDAO personDAO = new PersonDAO(conn);
      EventDAO eventDAO = new EventDAO(conn);
      AuthenticationDAO authenticationDAO = new AuthenticationDAO(conn);

      wipeDatabase(userDAO, personDAO, eventDAO, authenticationDAO);
      loadUsers(l, userDAO);
      loadPersons(l, personDAO);
      loadEvents(l, eventDAO);

      loadResult = new LoadResult("Successfully added " + users + " users, "
                                  + people + " persons, and "
                                  + events + " events to the database.", true);

      db.closeConnection(true);

    } catch (DataAccessException e) {
      e.printStackTrace();
      loadResult = new LoadResult("Error: unable to load into the database", false);
      db.closeConnection(false);
      return loadResult;
    }

    return loadResult;
  }

  private boolean checkValidity(LoadRequest l)  {
    if (l == null) {
      return false;
    }
    if (l.getUsers() == null) {
      return false;
    }
    if (l.getPersons() == null) {
      return false;
    }
    if (l.getEvents() == null) {
      return false;
    }
    return true;
  }

  private void wipeDatabase(UserDAO u, PersonDAO p, EventDAO e, AuthenticationDAO a) throws DataAccessException {
    u.clear();
    p.clear();
    e.clear();
    a.clear();
  }

  private void loadUsers(LoadRequest l, UserDAO userDAO) throws DataAccessException {
    for (int i = 0; i < l.getUsers().size(); ++i) {
      userDAO.insert(l.getUsers().get(i));
      ++users;
    }
  }

  private void loadPersons(LoadRequest l, PersonDAO personDAO) throws DataAccessException {
    for (int i = 0; i < l.getPersons().size(); ++i) {
      personDAO.insert(l.getPersons().get(i));
      ++people;
    }
  }

  private void loadEvents(LoadRequest l, EventDAO eventDAO) throws DataAccessException {
    for (int i = 0; i < l.getEvents().size(); ++i) {
      eventDAO.insert(l.getEvents().get(i));
      ++events;
    }
  }
}
