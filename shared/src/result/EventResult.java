package result;

import model.Event;

public final class EventResult {
  private String eventID;
  private String associatedUsername;
  private String personID;
  private float latitude;
  private float longitude;
  private String country;
  private String city;
  private String eventType;
  private int year;
  private String message;
  private boolean success;

  /**
   * Constructor for a successful result
   * @param eventID
   * @param associatedUsername
   * @param personID
   * @param latitude
   * @param longitude
   * @param country
   * @param city
   * @param eventType
   * @param year
   * @param success
   */

  public EventResult(String eventID, String associatedUsername, String personID, float latitude, float longitude, String country, String city, String eventType, int year, boolean success) {
    this.eventID = eventID;
    this.associatedUsername = associatedUsername;
    this.personID = personID;
    this.latitude = latitude;
    this.longitude = longitude;
    this.country = country;
    this.city = city;
    this.eventType = eventType;
    this.year = year;
    this.success = success;
  }

  /**
   * Constructor for an error result
   * @param message
   * @param success
   */
  public EventResult(String message, boolean success) {
    this.message = message;
    this.success = success;
  }

  /**
   * Creates new event object based on success. Returns object if true, else returns null
   * @return
   */
  public Event toEvent() {
    return this.success ? new Event(this.eventID, this.associatedUsername, this.personID, this.latitude, this.longitude, this.country, this.city, this.eventType, this.year) : null;
  }

  public String getEventID() {
    return this.eventID;
  }

  public String getAssociatedUsername() {
    return this.associatedUsername;
  }

  public String getPersonID() {
    return this.personID;
  }

  public String getCountry() {
    return this.country;
  }

  public String getCity() {
    return this.city;
  }

  public String getEventType() {
    return this.eventType;
  }

  public String getMessage() {
    return this.message;
  }

  public boolean isSuccess() {
    return this.success;
  }
}
