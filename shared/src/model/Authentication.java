package model;

public class Authentication {
  private String username;
  private String authToken;

  /**
   * Public constructor for authentication tokens
   *
   * @param username Unique username
   * @param authToken Unique authentication token for a given user
   */

  public Authentication(String username, String authToken) {
    this.username=username;
    this.authToken=authToken;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username=username;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken=authToken;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null)
      return false;
    if (o instanceof Authentication) {
      Authentication oAuthentication = (Authentication) o;
      return oAuthentication.getUsername().equals(getUsername()) &&
              oAuthentication.getAuthToken().equals(getAuthToken());
    } else {
      return false;
    }
  }
}
