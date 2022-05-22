package exceptions;
public class NonPositiveNum extends Exception {
 private static final long serialVersionUID = 1L;
 
 public NonPositiveNum()
  {
    super();
  }
  /**This exception is triggered if the event has already finished
  *@param s String of the exception
  */
  public NonPositiveNum(String s)
  {
    super(s);
  }
}