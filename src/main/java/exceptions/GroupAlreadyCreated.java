package exceptions;
public class GroupAlreadyCreated extends Exception {
 private static final long serialVersionUID = 1L;
 
 public GroupAlreadyCreated()
  {
    super();
  }
  /**This exception is triggered if the event has already finished
  *@param s String of the exception
  */
  public GroupAlreadyCreated(String s)
  {
    super(s);
  }
}