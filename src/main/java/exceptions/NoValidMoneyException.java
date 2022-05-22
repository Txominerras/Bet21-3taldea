package exceptions;
public class NoValidMoneyException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public NoValidMoneyException()
  {
    super();
  }
  /**This exception is triggered if the event has already finished
  *@param s String of the exception
  */
  public NoValidMoneyException(String s)
  {
    super(s);
  }
}