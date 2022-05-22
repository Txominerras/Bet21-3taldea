package exceptions;
public class NoValidDate extends Exception {
 private static final long serialVersionUID = 1L;
 
 public NoValidDate()
  {
    super();
  }
  /**This exception is triggered if the event has already finished
  *@param s String of the exception
  */
  public NoValidDate(String s)
  {
    super(s);
  }
}