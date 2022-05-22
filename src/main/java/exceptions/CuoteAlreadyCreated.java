package exceptions;
public class CuoteAlreadyCreated extends Exception {
 private static final long serialVersionUID = 1L;
 
 public CuoteAlreadyCreated()
  {
    super();
  }
  /**This exception is triggered if the event has already finished
  *@param s String of the exception
  */
  public CuoteAlreadyCreated(String s)
  {
    super(s);
  }
}
