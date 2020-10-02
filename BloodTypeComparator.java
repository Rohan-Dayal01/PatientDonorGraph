/**
 * @author Rohan Dayal
 * @ID_Number 112768867
 * @Recitation 02
 */

package homework7;

import java.util.Comparator;
/**
 * Class defines BloodTypeComparator object and implements
 * Comparator<Patient> interface
 *
 */
public class BloodTypeComparator implements Comparator<Patient> {

	/**
	 * int compare method to compare two Patient objects
	 * Comparison of BloodType is alphabetical order 
	 * @return int 0 if same rank. int 1 if o1>o2. int -1 if o1<o2
	 */
	public int compare(Patient o1, Patient o2) {
		
		if(o1.getBloodType().getBloodType().compareTo(o2.getBloodType().
				getBloodType())==0)
			return 0;
		else if(o1.getBloodType().getBloodType().compareTo(o2.getBloodType()
				.getBloodType())>0)
			return 1;
		else
			return -1;
	}

}
