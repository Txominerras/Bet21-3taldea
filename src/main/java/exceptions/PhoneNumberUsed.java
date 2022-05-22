package exceptions;
public class PhoneNumberUsed extends Exception {
 private static final long serialVersionUID = 1L;
 
 public PhoneNumberUsed()
  {
    super();
  }
  /**This exception is triggered if the event has already finished
  *@param s String of the exception
  */
  public PhoneNumberUsed(String s)
  {
    super(s);
  }
}