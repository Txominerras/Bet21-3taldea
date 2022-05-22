package exceptions;
public class DatePassed extends Exception {
 private static final long serialVersionUID = 1L;
 
 public DatePassed()
  {
    super();
  }
  /**This exception is triggered if the event has already finished
  *@param s String of the exception
  */
  public DatePassed(String s)
  {
    super(s);
  }
}