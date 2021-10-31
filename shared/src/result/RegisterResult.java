package result;


public final class RegisterResult {
  private String authtoken;
  private String username;
  private String personID;
  private String message;
  private boolean success;

  /**
   * Constructs a successful register result
   * @param authtoken
   * @param username
   * @param personID
   * @param success
   */

  public RegisterResult(String authtoken, String username, String personID, boolean success) {
    this.authtoken = authtoken;
    this.username = username;
    this.personID = personID;
    this.success = success;
    message = null;
  }

  /**
   * Constructs an error register result
   * @param message
   * @param success
   */

  public RegisterResult(String message, boolean success) {
    this.message = message;
    this.success = success;
    authtoken = null;
    username = null;
    personID = null;
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

  public void setPersonID(String personID) {
    this.personID = personID;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean getSuccess() { return this.success; }
}
