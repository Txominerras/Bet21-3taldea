package exceptions;
public class UserAlreadyInTheGroup extends Exception {
 private static final long serialVersionUID = 1L;
 
 public UserAlreadyInTheGroup()
  {
    super();
  }
  /**This exception is triggered if the event has already finished
  *@param s String of the exception
  */
  public UserAlreadyInTheGroup(String s)
  {
    super(s);
  }
}