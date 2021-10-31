package request;

public final class LoginRequest {
  private final String username;
  private final String password;

  /**
   * Constructs a login request
   * @param username
   * @param password
   */

  public LoginRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }
}