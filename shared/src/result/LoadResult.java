package result;


public final class LoadResult {
  private String message;
  private boolean success;

  /**
   * Constructor for a load result
   * @param message
   * @param success
   */
  public LoadResult(String message, boolean success) {
    this.message = message;
    this.success = success;
  }

  public String getMessage() {
    return this.message;
  }

  public boolean isSuccess() {
    return this.success;
  }
}
