package exceptions;

public class CuoteHasNotQuestion extends Exception{

		 private static final long serialVersionUID = 1L;
		 
		 public CuoteHasNotQuestion()
		  {
		    super();
		  }
		  /**This exception is triggered if the event has already finished
		  *@param s String of the exception
		  */
		  public CuoteHasNotQuestion(String s)
		  {
		    super(s);
		  }
		
}