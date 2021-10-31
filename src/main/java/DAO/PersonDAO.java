package DAO;

import model.Event;
import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

public class PersonDAO {

  private final Connection conn;

  /**
   * Establishes a connection
   * @param conn
   */

  public PersonDAO(Connection conn)
  {
    this.conn = conn;
  }

  /**
   * Inserts a person object into the person database
   * @param person
   * @throws DataAccessException
   */
  public void insert(Person person) throws DataAccessException {
    //We can structure our string to be similar to a sql command, but if we insert question
    //marks we can change them later with help from the statement
    String sql = "INSERT INTO person (person_id, associated_username, first_name, last_name, gender, " +
            "father_id, mother_id, spouse_id) VALUES(?,?,?,?,?,?,?,?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      //Using the statements built-in set(type) functions we can pick the question mark we want
      //to fill in and give it a proper value. The first argument corresponds to the first
      //question mark found in our sql String
      stmt.setString(1, person.getPersonID());
      stmt.setString(2, person.getAssociatedUsername());
      stmt.setString(3, person.getFirstName());
      stmt.setString(4, person.getLastName());
      stmt.setString(5, person.getGender());
      stmt.setString(6, person.getFatherID());
      stmt.setString(7, person.getMotherID());
      stmt.setString(8, person.getSpouseID());

      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error encountered while inserting into the database");
    }
  }

  /**
   * Locates a specific person in the person database
   * @param personID
   * @return
   * @throws DataAccessException
   */
  public Person find(String personID, String username) throws DataAccessException {
    Person person;
    ResultSet rs = null;
    String sql = "SELECT * FROM person WHERE person_id = ? AND associated_username = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, personID);
      stmt.setString(2, username);
      rs = stmt.executeQuery();
      if (rs.next()) {
        person = new Person(rs.getString("person_id"), rs.getString("associated_username"),
                rs.getString("first_name"), rs.getString("last_name"), rs.getString("gender"),
                rs.getString("father_id"), rs.getString("mother_id"), rs.getString("spouse_id"));
        return person;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding event");
    } finally {
      if(rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

    }
    return null;
  }

  /**
   * Removes persons connected to a given username
   *
   * @param username
   * @throws DataAccessException
   */
  public void removeUser(String username) throws DataAccessException {
    String sql = "DELETE FROM person WHERE associated_username = ?;";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      stmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error: while removing user persons from table");
    }
  }


  /**
   * Gets all persons related to an associated user
   *
   * @param username ID of associated user
   * @return
   * @throws DataAccessException
   */
  public ArrayList<Person> getAll(String username) throws DataAccessException {
    ArrayList<Person> personArray = new ArrayList<>();

    ResultSet rs = null;
    String sql = "SELECT * FROM person WHERE associated_username = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      rs = stmt.executeQuery();
      while (rs.next()) {
        personArray.add(new Person(rs.getString("person_id"), rs.getString("associated_username"),
                rs.getString("first_name"), rs.getString("last_name"), rs.getString("gender"),
                rs.getString("father_id"), rs.getString("mother_id"), rs.getString("spouse_id")));
      }
      if (personArray.isEmpty()) {
        return null;
      }

      return personArray;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding event");
    } finally {
      if(rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

    }
  }

  /**
   * Clears the person database
   * @throws DataAccessException
   */
  public void clear() throws DataAccessException {
    String sql = "DELETE FROM person";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error while clearing person table");
    }
  }
}
