package exceptions;
public class NotEnoughUsers extends Exception {
 private static final long serialVersionUID = 1L;
 
 public NotEnoughUsers()
  {
    super();
  }
  /**This exception is triggered if the event has already finished
  *@param s String of the exception
  */
  public NotEnoughUsers(String s)
  {
    super(s);
  }
}