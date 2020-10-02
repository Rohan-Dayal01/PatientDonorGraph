/**
 * @author Rohan Dayal
 * @ID_Number 112768867
 * @Recitation 02
 */

package homework7;
import java.io.Serializable;
/**
 * Class defines the BloodType object. Class implements Serializable.
 * BloodType object has private String bloodType as a datafield
 */
public class BloodType implements Serializable{
	private String bloodType;
	/**
	 * Default constructor for BloodType
	 */
	public BloodType() {
		
	}
	/**
	 * Constructor for BloodType with String bloodType as parameter
	 * @param bloodType is what this.bloodType is set equal to
	 */
	public BloodType(String bloodType) {
		this.bloodType = bloodType;
	}
	/**
	 * Void method to set this.bloodType to bloodType String
	 * @param bloodType is what this.bloodType is set equal to
	 */
	public void setBloodType(String bloodType) {
		this.bloodType= bloodType;
	}
	/**
	 * Getter method for this.bloodType
	 * @return String data field bloodType
	 */
	public String getBloodType() {
		return bloodType;
	}
	/**
	 * Method to determine whether one bloodType is compatible with another
	 * @param recipient is the BloodType of the recipient Patient
	 * @param donor is the BloodType of the donor Patient
	 * @return boolean true if two BloodTypes are compatible. Otherwise, false
	 */
	public static boolean isCompatible(BloodType recipient, BloodType donor) {
		String recipType = recipient.getBloodType();
		String donorType = donor.getBloodType();
		if(recipType.equals("O")&&donorType.equals("O"))
			return true;
		if((recipType.equals("A")&&donorType.equals("O"))||(recipType.equals(
				"A")&&donorType.equals("A")))
			return true;
		if((recipType.equals("B")&&donorType.equals("O"))||(recipType.equals(
				"B")&&donorType.equals("B")))
			return true;
		if((recipType.equals("AB")&&donorType.equals("O"))
				||(recipType.equals("AB")&&donorType.equals("A"))
				||(recipType.equals("AB")&&donorType.equals("B"))
				||(recipType.equals("AB")&&donorType.equals("AB")))
			return true;
		else
			return false;
	}
}
