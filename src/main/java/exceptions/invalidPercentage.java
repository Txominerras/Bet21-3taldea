package exceptions;
public class invalidPercentage extends Exception {
	private static final long serialVersionUID = 1L;

	public invalidPercentage()
	{
		super();
	}
	/**This exception is triggered if the event has already finished
	 *@param s String of the exception
	 */
	public invalidPercentage(String s)
	{
		super(s);
	}
}