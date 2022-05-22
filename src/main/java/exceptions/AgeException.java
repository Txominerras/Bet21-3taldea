package exceptions;
public class AgeException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public AgeException()
  {
    super();
  }
  /**This exception is triggered if the event has already finished
  *@param s String of the exception
  */
  public AgeException(String s)
  {
    super(s);
  }
}