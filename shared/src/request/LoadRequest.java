package request;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import model.Event;
import model.Person;
import model.User;

public final class LoadRequest {
  private final ArrayList<User> users;
  private final ArrayList<Person> persons;
  private final ArrayList<Event> events;

  /**
   * Constructs a load request
   * @param users an ArrayList of users
   * @param persons an ArrayList of persons
   * @param events an ArrayList of events
   */

  public LoadRequest(ArrayList<User> users, ArrayList<Person> persons, ArrayList<Event> events) {
    this.users = users;
    this.persons = persons;
    this.events = events;
  }

  /**
   * Gets a single event from a given ID
   * @param eventID ID of the event
   * @return
   */

  public Event getEvent(String eventID) {
    Iterator iter = this.events.iterator();

    Event event;
    do {
      if (!iter.hasNext()) {
        return null;
      }

      event = (Event)iter.next();
    } while(!event.getEventID().equals(eventID));

    return event;
  }

  /**
   * Gets a person from a given ID
   * @param personID ID for a specific person
   * @return
   */

  public Person getPerson(String personID) {
    Iterator iter = this.persons.iterator();

    Person person;
    do {
      if (!iter.hasNext()) {
        return null;
      }

      person = (Person)iter.next();
    } while(!person.getPersonID().equals(personID));

    return person;
  }

  /**
   * Gets a list of events connected to an associated user
   * @param associatedUser username of associated user
   * @return
   */

  public Set<Event> getEvents(String associatedUser) {
    HashSet set = new HashSet();
    Iterator iter = this.events.iterator();

    while(iter.hasNext()) {
      Event event = (Event)iter.next();
      if (event.getUsername().equals(associatedUser)) {
        set.add(event);
      }
    }

    return set;
  }

  /**
   * Gets a list of persons associated with a user
   * @param associatedUser username assocaited with a given person
   * @return
   */
  public Set<Person> getPersons(String associatedUser) {
    HashSet set = new HashSet();
    Iterator iter = this.persons.iterator();

    while(iter.hasNext()) {
      Person person = (Person)iter.next();
      if (person.getAssociatedUsername().equals(associatedUser)) {
        set.add(person);
      }
    }
    return set;
  }

  public ArrayList<User> getUsers() {
    return this.users;
  }

  public ArrayList<Person> getPersons() {
    return this.persons;
  }

  public ArrayList<Event> getEvents() {
    return this.events;
  }
}
