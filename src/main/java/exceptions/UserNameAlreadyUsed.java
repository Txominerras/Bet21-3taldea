package exceptions;
public class UserNameAlreadyUsed extends Exception {
 private static final long serialVersionUID = 1L;
 
 public UserNameAlreadyUsed()
  {
    super();
  }
  /**This exception is triggered if the event has already finished
  *@param s String of the exception
  */
  public UserNameAlreadyUsed(String s)
  {
    super(s);
  }
}
