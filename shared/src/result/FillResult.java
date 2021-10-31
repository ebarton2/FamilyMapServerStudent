package result;


public final class FillResult {
  private String message;
  private boolean success;

  /**
   * constructor for a fill result
   * @param message
   * @param success
   */
  public FillResult(String message, boolean success) {
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
