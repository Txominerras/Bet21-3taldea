package exceptions;
public class EmailUsed extends Exception {
 private static final long serialVersionUID = 1L;
 
 public EmailUsed()
  {
    super();
  }
  /**This exception is triggered if the event has already finished
  *@param s String of the exception
  */
  public EmailUsed(String s)
  {
    super(s);
  }
}