package DAO;

import model.Authentication;
import model.Event;
import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthenticationDAO {

  private final Connection conn;

  /**
   * Establishes a connection
   * @param conn
   */

  public AuthenticationDAO(Connection conn)
  {
    this.conn = conn;
  }

  /**
   * Inserts an authentication object into the authentication database
   * @param authentication
   * @throws DataAccessException
   */
  public void insert(Authentication authentication) throws DataAccessException {
    //We can structure our string to be similar to a sql command, but if we insert question
    //marks we can change them later with help from the statement
    String sql = "INSERT INTO auth_key (username, authentication_key) VALUES(?,?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      //Using the statements built-in set(type) functions we can pick the question mark we want
      //to fill in and give it a proper value. The first argument corresponds to the first
      //question mark found in our sql String
      stmt.setString(1, authentication.getUsername());
      stmt.setString(2, authentication.getAuthToken());

      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error encountered while inserting into the database");
    }
  }

  /**
   * Locates a specific person in the person database
   * @param username
   * @return
   * @throws DataAccessException
   */
  public Authentication find(String username) throws DataAccessException {
    Authentication authentication;
    ResultSet rs = null;
    String sql = "SELECT * FROM auth_key WHERE username = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      rs = stmt.executeQuery();
      if (rs.next()) {
        authentication = new Authentication(rs.getString("username"), rs.getString("authentication_key"));
        return authentication;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error: encountered while finding auth_key with username");
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

  public Authentication findAuth(String authKey) throws DataAccessException {
    Authentication authentication;
    ResultSet rs = null;
    String sql = "SELECT * FROM auth_key WHERE authentication_key = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, authKey);
      rs = stmt.executeQuery();
      if (rs.next()) {
        authentication = new Authentication(rs.getString("username"), rs.getString("authentication_key"));
        return authentication;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error: encountered while finding auth_key with authKey");
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
   * Removes a specific AuthKey from the authentication database
   *
   * @param userID
   * @throws DataAccessException
   */
  public void remove(String userID) throws DataAccessException {
    // Will implement
  }

  /**
   * Clears the authentication database
   * @throws DataAccessException
   */
  public void clear() throws DataAccessException {
    String sql = "DELETE FROM auth_key";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error while clearing auth_key table");
    }
  }
}
