package service;

import DAO.*;
import UtilityFunctions.IDGenerator;
import com.google.gson.Gson;
import jsonClasses.FNameData;
import jsonClasses.LocationData;
import jsonClasses.MNameData;
import jsonClasses.SNameData;
import model.Event;
import model.Person;
import model.User;
import request.FillRequest;
import result.FillResult;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.Struct;
import java.util.Random;

public class FillService {

  private static int YEAR = 2021;
  private static int YEAR_DIFF = 22;

  private int total_people_added = 0;
  private int total_events_added = 0;

  private LocationData locData;
  private FNameData fNameData;
  private MNameData mNameData;
  private SNameData sNameData;

  public FillService() throws FileNotFoundException { // TODO: Add a try/catch statement
    Reader loc = new FileReader("json/locations.json");
    Reader fName = new FileReader("json/fnames.json");
    Reader mName = new FileReader("json/mnames.json");
    Reader sName = new FileReader("json/snames.json");
    Gson gson = new Gson();
    locData = gson.fromJson(loc, LocationData.class);
    fNameData = gson.fromJson(fName, FNameData.class);
    mNameData = gson.fromJson(mName, MNameData.class);
    sNameData = gson.fromJson(sName, SNameData.class);
  }

  /**
   *Populates the server's database with generated data for the specified user name.
   *
   * @param f
   * @return
   */
  public FillResult fill(FillRequest f) throws DataAccessException {
    FillResult fillResult = null;
    Database db = new Database();

    if (!checkValidity(f)) {
      fillResult = new FillResult("Error: Invalid fill request.", false);
      return fillResult;
    }
    System.out.println("Valid request");

    try{
      boolean success = fillGenerations(f.getUsername(), f.getGenerations(), db);

      if (success) {
        String message = "Successfully added " + total_people_added + " persons and " + total_events_added + " events to the database.";
        fillResult = new FillResult(message, true);
        db.closeConnection(true);
      } else {
        fillResult = new FillResult("Error: fill was unsuccessful.", false);
        db.closeConnection(false);
      }
      return fillResult;

    } catch (DataAccessException e) {
      e.printStackTrace();
      fillResult = new FillResult("Error: Failure to fill database", false);
      db.closeConnection(false);
      return fillResult;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      fillResult = new FillResult("Error: Unable to find Json files", false);
      db.closeConnection(false);
      return fillResult;
    }
  }

  private static boolean checkValidity(FillRequest f) {
    if (f == null) {
      return false;
    }
    if (f.getGenerations() < 0) {
      return false;
    }
    return true;
  }

  private boolean fillGenerations(String username, int generations, Database db) throws FileNotFoundException, DataAccessException {
    db.openConnection();
    Connection conn = db.getConnection();


    UserDAO userDAO = new UserDAO(conn);
    PersonDAO personDAO = new PersonDAO(conn);
    EventDAO eventDAO = new EventDAO(conn);
    System.out.println("DAOs are open");

    User user = userDAO.findUsername(username);
    if (user == null) {
      System.out.println("User not found in Fill");
      return false;
    }
    eventDAO.removeUser(username);
    personDAO.removeUser(username);
    System.out.println("User cleared from database, ready for fill.");

    fillDatabase(generations, user, personDAO, eventDAO);
    System.out.println("Database filled");
    return true;
  }

  private void fillDatabase(int maxGenerations, User user, PersonDAO personDAO, EventDAO eventDAO) throws DataAccessException {
    IDGenerator generator = new IDGenerator();
    String fatherID = generator.createID();
    String motherID = generator.createID();
    Person person = new Person(user.getPersonID(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getGender(), fatherID, motherID, null);
    personDAO.insert(person);
    System.out.println("User added to persons table");

    Random num = new Random();
    int index = locData.getData().length;
    index = num.nextInt(index - 1);
    int year = YEAR - YEAR_DIFF;
    IDGenerator idGenerator = new IDGenerator();
    String eventID = idGenerator.createID();
    Event event = new Event(eventID, user.getUsername(), user.getPersonID(),
            locData.getData()[index].getLatitude(), locData.getData()[index].getLongitude(),
            locData.getData()[index].getCountry(), locData.getData()[index].getCity(), "birth", year);
    eventDAO.insert(event);
    System.out.println("User birth added to events table");

    ++total_people_added;
    ++total_events_added;


    if (maxGenerations > 0) {
      int currGeneration = 1;
      generateParent(currGeneration, maxGenerations, user.getUsername(),
              fatherID, motherID, "m", year, personDAO, eventDAO, index);
      generateParent(currGeneration, maxGenerations, user.getUsername(),
              fatherID, motherID, "f", year, personDAO, eventDAO, index);
    }

  }

 private void generateParent(int currGeneration, int maxGeneration, String username,
                                    String fatherID, String motherID, String gender, int year,
                                    PersonDAO personDAO, EventDAO eventDAO, int locIndex) throws DataAccessException {
   String fatherParentID = null;
   String motherParentID = null;
   IDGenerator generator = new IDGenerator();
    if (currGeneration < maxGeneration) {
      fatherParentID = generator.createID();
      motherParentID = generator.createID();
    }

   int index;
   String firstName;
   String lastName;
   String parentID;
   String spouseID;

   if (gender.equals("m")) {
     index = rand(mNameData.getData().length);
     firstName = mNameData.getData()[index];
     index = rand(sNameData.getData().length);
     parentID = fatherID;
     spouseID = motherID;
   } else {
     index = rand(fNameData.getData().length);
     firstName = fNameData.getData()[index];
     index = rand(sNameData.getData().length);
     parentID = motherID;
     spouseID = fatherID;
   }

   int travelDataOne = (locIndex + rand(locData.getData().length)) % locData.getData().length;
   int travelDataTwo = (locIndex + rand(locData.getData().length)) % locData.getData().length;

   lastName = sNameData.getData()[index];
   Person person = new Person(parentID, username, firstName, lastName, gender, fatherParentID, motherParentID, spouseID);
   personDAO.insert(person);
   ++total_people_added;

   Event birth = new Event(generator.createID(), username, parentID,
           locData.getData()[travelDataOne].getLatitude(), locData.getData()[travelDataOne].getLongitude(),
           locData.getData()[travelDataOne].getCountry(), locData.getData()[travelDataOne].getCity(), "birth", year - YEAR_DIFF);
   eventDAO.insert(birth);
   ++total_events_added;

   Event marriage = new Event(generator.createID(), username, parentID,
           locData.getData()[locIndex].getLatitude(), locData.getData()[locIndex].getLongitude(),
           locData.getData()[locIndex].getCountry(), locData.getData()[locIndex].getCity(), "marriage", year - 1);
   eventDAO.insert(marriage);
   ++total_events_added;

   Event death = new Event(generator.createID(), username, parentID,
           locData.getData()[travelDataTwo].getLatitude(), locData.getData()[travelDataTwo].getLongitude(),
           locData.getData()[travelDataTwo].getCountry(), locData.getData()[travelDataTwo].getCity(), "death", year + YEAR_DIFF);
   eventDAO.insert(death);
   ++total_events_added;

   if (currGeneration < maxGeneration) {
     generateParent(currGeneration + 1, maxGeneration, username,
             fatherParentID, motherParentID, "m", year - YEAR_DIFF, personDAO, eventDAO, locIndex);
     generateParent(currGeneration + 1, maxGeneration, username,
             fatherParentID, motherParentID, "f", year - YEAR_DIFF, personDAO, eventDAO, locIndex);
   }
  }

  private int rand (int length) {
    Random num = new Random();
    return num.nextInt(length-1);
  }

}
