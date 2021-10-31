package result;


public final class LoginResult {
  private String authtoken;
  private String username;
  private String personID;
  private String message;
  private boolean success;

  /**
   * Success constructor
   * @param authtoken authtoken needed
   * @param username username needed
   * @param personID personID needed
   * @param success success needed
   */

  public LoginResult(String authtoken, String username, String personID, boolean success) {
    this.authtoken = authtoken;
    this.username = username;
    this.personID = personID;
    message = null;
    this.success = success;
  }

  /**
   * Error constructor
   * @param message error message
   * @param success error boolean
   */

  public LoginResult(String message, boolean success) {
    this.message = message;
    authtoken = null;
    username = null;
    personID = null;
    this.success = success;
  }

  public String getAuthtoken() {
    return this.authtoken;
  }

  public String getUsername() {
    return this.username;
  }

  public String getPersonID() {
    return this.personID;
  }

  public String getMessage() {
    return this.message;
  }

  public boolean getSuccess() {
    return this.success;
  }
}
