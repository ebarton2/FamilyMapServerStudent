package model;

public class User {

  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;
  private String gender;
  private String personID;

  /**
   * Public constructor for users
   *
   * @param username Unique username (non-empty string)
   * @param password User’s password (non-empty string)
   * @param email User’s email address (non-empty string)
   * @param firstName User’s first name (non-empty string)
   * @param lastName User’s last name (non-empty string)
   * @param gender User’s gender (string: f or m)
   * @param personID Unique Person ID assigned to this user’s generated Person object (non-empty string)
   */

  public User(String username, String password, String email, String firstName, String lastName, String gender, String personID) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.personID = personID;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username=username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email=email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName=firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName=lastName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender=gender;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID=personID;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password=password;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null)
      return false;
    if (o instanceof User) {
      User oUser = (User) o;
      return oUser.getUsername().equals(getUsername()) &&
              oUser.getPassword().equals(getPassword()) &&
              oUser.getPersonID().equals(getPersonID()) &&
              oUser.getEmail().equals(getEmail()) &&
              oUser.getFirstName().equals(getFirstName()) &&
              oUser.getLastName().equals(getLastName()) &&
              oUser.getGender().equals(getGender());
    } else {
      return false;
    }
  }
}
