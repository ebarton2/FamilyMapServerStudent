package result;


public final class PersonResult {
  private String personID;
  private String associatedUsername;
  private String firstName;
  private String lastName;
  private String gender;
  private String fatherID;
  private String motherID;
  private String spouse;
  private String message;
  private boolean success;

  /**
   * Constructor for a successful person result
   * @param personID
   * @param associatedUsername
   * @param firstName
   * @param lastName
   * @param gender
   * @param fatherID
   * @param motherID
   * @param spouse
   * @param success
   */

  public PersonResult(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouse, boolean success) {
    this.personID = personID;
    this.associatedUsername = associatedUsername;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.fatherID = fatherID;
    this.motherID = motherID;
    this.spouse = spouse;
    this.success = success;
    this.message = null;
  }

  /**
   * Constructor for an error person result
   * @param message
   * @param success
   */
  public PersonResult(String message, boolean success) {
    this.message = message;
    this.success = success;
    this.personID = null;
    this.associatedUsername = null;
    this.firstName = null;
    this.lastName = null;
    this.gender = null;
    this.fatherID = null;
    this.motherID = null;
    this.spouse = null;
  }

  public String getPersonID() {
    return this.personID;
  }

  public String getAssociatedUsername() {
    return this.associatedUsername;
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

  public String getFatherID() {
    return this.fatherID;
  }

  public String getMotherID() {
    return this.motherID;
  }

  public String getSpouse() {
    return this.spouse;
  }

  public String getMessage() {
    return this.message;
  }

  public boolean isSuccess() {
    return this.success;
  }
}
