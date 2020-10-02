/**
 * @author Rohan Dayal
 * @ID_Number 112768867
 * @Recitation 02
 */

package homework7;

import java.util.Comparator;
/**
 * Class defines NumConnectionsComparator. Implements the Comparator<Patient>
 * interface
 */
public class NumConnectionsComparator implements Comparator<Patient>{
	/**
	 * Compare method for the Number of connections between two Patients
	 * @return 1 if o1 has more connections than o2. 0 if equal number of
	 * connections. -1 if o1 has less connections than o2
	 */
	public int compare(Patient o1, Patient o2) {
		if(o1.getNumConnects()>o2.getNumConnects())
			return 1;
		else if(o1.getNumConnects()==o2.getNumConnects())
			return 0;
		else
			return -1;
	}

}
