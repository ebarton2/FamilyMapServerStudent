package DAO;

import model.Person;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

public class UserDAO {
  private final Connection conn;

  /**
   * Establishes a connection
   * @param conn
   */

  public UserDAO(Connection conn)
  {
    this.conn = conn;
  }

  /**
   * Inserts a user object into the user database
   * @param user
   * @throws DataAccessException
   */
  public void insert(User user) throws DataAccessException {
    //We can structure our string to be similar to a sql command, but if we insert question
    //marks we can change them later with help from the statement
    String sql = "INSERT INTO users (username, password, email, first_name, last_name, " +
            "gender, person_id) VALUES(?,?,?,?,?,?,?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      //Using the statements built-in set(type) functions we can pick the question mark we want
      //to fill in and give it a proper value. The first argument corresponds to the first
      //question mark found in our sql String
      stmt.setString(1, user.getUsername());
      stmt.setString(2, user.getPassword());
      stmt.setString(3, user.getEmail());
      stmt.setString(4, user.getFirstName());
      stmt.setString(5, user.getLastName());
      stmt.setString(6, user.getGender());
      stmt.setString(7, user.getPersonID());

      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error encountered while inserting into the database");
    }
  }

  /**
   * Locates a specific user in the user database
   * @param personID
   * @return
   * @throws DataAccessException
   */
  public User find(String personID) throws DataAccessException {
    User user;
    ResultSet rs = null;
    String sql = "SELECT * FROM users WHERE person_id = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, personID);
      rs = stmt.executeQuery();
      if (rs.next()) {
        user = new User(rs.getString("username"), rs.getString("password"),
                rs.getString("email"), rs.getString("first_name"), rs.getString("last_name"),
                rs.getString("gender"), rs.getString("person_id"));
        return user;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error: encountered while finding user with personID");
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

  public User findUsername(String username) throws DataAccessException {
    User user;
    ResultSet rs = null;
    String sql = "SELECT * FROM users WHERE username = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      rs = stmt.executeQuery();
      if (rs.next()) {
        user = new User(rs.getString("username"), rs.getString("password"),
                rs.getString("email"), rs.getString("first_name"), rs.getString("last_name"),
                rs.getString("gender"), rs.getString("person_id"));
        return user;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error: encountered while finding user with username");
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

  public User login(String username, String password) throws DataAccessException {
    User user;
    ResultSet rs = null;
    String sql = "SELECT * FROM users WHERE username = ? AND password = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      stmt.setString(2, password);
      rs = stmt.executeQuery();
      if (rs.next()) {
        user = new User(rs.getString("username"), rs.getString("password"),
                rs.getString("email"), rs.getString("first_name"), rs.getString("last_name"),
                rs.getString("gender"), rs.getString("person_id"));
        return user;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error: encountered while finding user with username and password");
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
   * Clears the user database
   * @throws DataAccessException
   */
  public void clear() throws DataAccessException {
    String sql = "DELETE FROM users";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error: while clearing users table");
    }
  }


  /**
   * Removes a specific user from the user database
   *
   * @param userID
   * @throws DataAccessException
   */
  public void remove(String userID) throws DataAccessException {
    // Will implement
  }

  /**
   * Gets all users from the user database
   *
   * @return
   * @throws DataAccessException
   */
  public ArrayList<User> getAll() throws DataAccessException {
    // Probably won't ever use
    return null;
  }
}

