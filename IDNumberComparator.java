/**
 * @author Rohan Dayal
 * @ID_Number 112768867
 * @Recitation 02
 */

package homework7;

import java.util.Comparator;
/**
 * Class defines IDNumberComparator and implements Comparator<Patient>
 * interface
 */
public class IDNumberComparator implements Comparator<Patient> {
	/**
	 * compare method for ID's of Patient o1 and o2
	 * @return 0 if Patient o1 has same ID has Patient o2. Return
	 * 1 if Patient o1 has greater ID than Patient o2. Return -1 if
	 * Patient o1 has smaller ID than Patient o2.
	 */
	public int compare(Patient o1, Patient o2) {
		
		if(o1.getID()>o2.getID())
			return 1;
		else if(o1.getID()==o2.getID())
			return 0;
		else
			return -1;
	}

}
