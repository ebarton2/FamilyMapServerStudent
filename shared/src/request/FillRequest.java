package request;

public final class FillRequest {
  private final String username;
  private final int generations;

  /**
   * Construct a fill request WITH a specified generation number
   * @param username
   * @param generations
   */

  public FillRequest(String username, int generations) {
    this.username = username;
    this.generations = generations;
  }

  /**
   * Construct a fill request WITHOUT a specified generation number
   * @param username
   */
  public FillRequest(String username) {
    this.username=username;
    this.generations=4;
  }

  public final String getUsername() {
    return this.username;
  }

  public final int getGenerations() {
    return this.generations;
  }
}

