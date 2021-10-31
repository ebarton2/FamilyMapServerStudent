package result;

public final class ClearResult {
  private String message;
  private boolean success;

  /**
   * Constructs a result for a clear call
   * @param message
   * @param success
   */

  public ClearResult(String message, boolean success) {
    this.message=message;
    this.success=success;
  }

  public String getMessage() {
    return this.message;
  }

  public boolean getSuccess() {
    return this.success;
  }
}
