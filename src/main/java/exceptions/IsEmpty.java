package exceptions;
public class IsEmpty extends Exception {
 private static final long serialVersionUID = 1L;
 
 public IsEmpty()
  {
    super();
  }
  /**This exception is triggered if the event has already finished
  *@param s String of the exception
  */
  public IsEmpty(String s)
  {
    super(s);
  }
}