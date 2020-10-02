/**
 * @author Rohan Dayal
 * @ID_Number 112768867
 * @Recitation 02
 */

package homework7;
/**
 * Class defines TooManyPatientsException and extends Exception
 * This exception is thrown when seeking to add more than the
 * pre-defined maximum number of donors or recipients to their
 * respective lists
 */
public class TooManyPatientsException extends Exception{
	/**
	 * Constructor for TooManyPatientsException object
	 * @param message is String passed to super constructor
	 */
	public TooManyPatientsException(String message) {
		super(message);
	}
}
