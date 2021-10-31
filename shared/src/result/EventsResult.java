package result;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import model.Event;

public final class EventsResult {
  private ArrayList<Event> data;
  private String message;
  private boolean success;

  /**
   * Constructor for a successful events result
   * @param data
   * @param success
   */
  public EventsResult(ArrayList<Event> data, boolean success) {
    this.data = data;
    this.success = success;
  }

  /**
   * Constructor for an error events result
   * @param message
   * @param success
   */
  public EventsResult(String message, boolean success) {
    this.message = message;
    this.success = success;
  }

  /**
   * Gets an event of a specified type connected to a specific person.
   * @param personID ID of specific person
   * @param eventType type of event
   * @return
   */
  public Event getEvent(String personID, String eventType) {
    Iterator iter = this.data.iterator();

    Event event;
    do {
      if (!iter.hasNext()) {
        return null;
      }

      event = (Event)iter.next();
    } while(!event.getPersonID().equals(personID) || !event.getEventType().toLowerCase().contains(eventType.toLowerCase()));

    return event;
  }

  public ArrayList<Event> getData() {
    return this.data;
  }

  public Set<Event> getDataAsSet() {
    return new HashSet(this.data);
  }

  public String getMessage() {
    return this.message;
  }

  public boolean isSuccess() {
    return this.success;
  }
}
