package UtilityFunctions;

import java.util.UUID;

public class IDGenerator {

  public static String createID() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }

}
