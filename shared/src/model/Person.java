package model;

public class Person {
  private String personID;
  private String associatedUsername;
  private String firstName;
  private String lastName;
  private String gender;
  private String fatherID;
  private String motherID;
  private String spouseID;

  /**
   * Public constructor for person
   *
   * @param personID Unique identifier for this person (non-empty string)
   * @param associatedUsername User (Username) to which this person belongs
   * @param firstName Person’s first name (non-empty string)
   * @param lastName Person’s last name (non-empty string)
   * @param gender User’s gender, f or m
   * @param fatherID Person ID of person’s father (possibly null)
   * @param motherID Person ID of person’s mother (possibly null)
   * @param spouseID Person ID of person’s spouse (possibly null)
   */

  public Person(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
    this.personID=personID;
    this.associatedUsername=associatedUsername;
    this.firstName=firstName;
    this.lastName=lastName;
    this.gender=gender;
    this.fatherID=fatherID;
    this.motherID=motherID;
    this.spouseID=spouseID;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID=personID;
  }

  public String getAssociatedUsername() {
    return associatedUsername;
  }

  public void setAssociatedUsername(String associatedUsername) {
    this.associatedUsername=associatedUsername;
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

  public String getFatherID() {
    return fatherID;
  }

  public void setFatherID(String fatherID) {
    this.fatherID=fatherID;
  }

  public String getMotherID() {
    return motherID;
  }

  public void setMotherID(String motherID) {
    this.motherID=motherID;
  }

  public String getSpouseID() {
    return spouseID;
  }

  public void setSpouseID(String spouseID) {
    this.spouseID=spouseID;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null)
      return false;
    if (o instanceof Person) {
      Person oPerson = (Person) o;
      return oPerson.getAssociatedUsername().equals(getAssociatedUsername()) &&
              oPerson.getFatherID().equals(getFatherID()) &&
              oPerson.getPersonID().equals(getPersonID()) &&
              oPerson.getMotherID().equals(getMotherID()) &&
              oPerson.getFirstName().equals(getFirstName()) &&
              oPerson.getLastName().equals(getLastName()) &&
              oPerson.getGender().equals(getGender()) &&
              oPerson.getSpouseID().equals(getSpouseID());
    } else {
      return false;
    }
  }
}
