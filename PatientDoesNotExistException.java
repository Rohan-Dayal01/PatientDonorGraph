/**
 * @author Rohan Dayal
 * @ID_Number 112768867
 * @Recitation 02
 */

package homework7;
/**
 * Class defines PatientDoesNotExistException. Extends Exception class
 * This is thrown when seeking to access a non-existent Patient in the
 * donors or recipients lists of the TransplantGraph
 */
public class PatientDoesNotExistException extends Exception{
	/**
	 * Constructor for PatientDoesNotExistException
	 * @param message is the message passed to super constructor
	 */
	public PatientDoesNotExistException(String message) {
		super(message);
	}
}
