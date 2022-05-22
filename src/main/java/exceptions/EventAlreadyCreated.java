package exceptions;
public class EventAlreadyCreated extends Exception {
 private static final long serialVersionUID = 1L;
 
 public EventAlreadyCreated()
  {
    super();
  }
  /**This exception is triggered if the event has already finished
  *@param s String of the exception
  */
  public EventAlreadyCreated(String s)
  {
    super(s);
  }
}