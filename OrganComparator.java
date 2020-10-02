/**
 * @author Rohan Dayal
 * @ID_Number 112768867
 * @Recitation 02
 */

package homework7;

import java.util.Comparator;
/**
 * Class defines OrganComparator. Implements Comparator<Patient> interface
 *
 */
public class OrganComparator implements Comparator<Patient>{
	/**
	 * compare method for alphabetical order of organs of two
	 * Patients o1 and o2
	 * @return 1 if o1 has higher alphabetical organ than o2. 0 if the same
	 * -1 if o1 has lower alphabetical organ than o2.
	 */
	public int compare(Patient o1, Patient o2) {
		if(o1.getOrgan().compareTo(o2.getOrgan())==0)
			return 0;
		else if(o1.getOrgan().compareTo(o2.getOrgan())>0)
			return 1;
		else
			return -1;
	}

}
