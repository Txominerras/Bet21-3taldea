package exceptions;
public class BetAlreadyCreatedException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public BetAlreadyCreatedException()
  {
    super();
  }
  /**This exception is triggered if the event has already finished
  *@param s String of the exception
  */
  public BetAlreadyCreatedException(String s)
  {
    super(s);
  }
}
