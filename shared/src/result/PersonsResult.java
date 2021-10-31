package result;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import model.Person;

public final class PersonsResult {
  private ArrayList<Person> data;
  private String message;
  private boolean success;

  /**
   * Constructor for a successful persons result
   * @param data
   * @param success
   */
  public PersonsResult(ArrayList<Person> data, boolean success) {
    this.data = data;
    this.success = success;
    this.message = null;
  }

  /**
   * Constructor for an error persons result
   * @param message
   * @param success
   */
  public PersonsResult(String message, boolean success) {
    this.message = message;
    this.success = success;
    this.data = null;
  }

  /**
   * Gets a person based on a given person's ID
   * @param personID ID of a specific person
   * @return
   */
  public Person getPerson(String personID) {
    Iterator iter = this.data.iterator();

    Person person;
    do {
      if (!iter.hasNext()) {
        return null;
      }

      person = (Person)iter.next();
    } while(!person.getPersonID().equals(personID));

    return person;
  }

  public ArrayList<Person> getData() {
    return this.data;
  }

  public Set<Person> getDataAsSet() {
    return new HashSet(this.data);
  }

  public String getMessage() {
    return this.message;
  }

  public boolean isSuccess() {
    return this.success;
  }
}
