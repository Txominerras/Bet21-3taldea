package exceptions;
public class UserNotExist extends Exception {
 private static final long serialVersionUID = 1L;
 
 public UserNotExist()
  {
    super();
  }
  /**This exception is triggered if the event has already finished
  *@param s String of the exception
  */
  public UserNotExist(String s)
  {
    super(s);
  }
}
