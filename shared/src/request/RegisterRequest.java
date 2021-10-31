package request;

public final class RegisterRequest {
  private final String username;
  private final String password;
  private final String email;
  private final String firstName;
  private final String lastName;
  private final String gender;

  /**
   * Constructs a register request
   * @param username
   * @param password
   * @param email
   * @param firstName
   * @param lastName
   * @param gender
   */

  public RegisterRequest(String username, String password, String email, String firstName, String lastName, String gender) {
    this.username = username;
    this.password=  password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public String getGender() {
    return this.gender;
  }

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }

  public String getEmail() {
    return this.email;
  }
}

