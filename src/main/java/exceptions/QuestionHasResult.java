package exceptions;

public class QuestionHasResult extends Exception{

	 private static final long serialVersionUID = 1L;
			 
	 public QuestionHasResult()
	 {
		 super();
	 }
	/**This exception is triggered if the event has already finished
	*@param s String of the exception
	 */
	 public QuestionHasResult(String s)
	{
		 super(s);
	}
			
	}
