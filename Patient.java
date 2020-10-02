/**
 * @author Rohan Dayal
 * @ID_Number 112768867
 * @Recitation 02
 */

package homework7;

import java.io.Serializable;
/**
 * Class defines a Patient object. Implements Comparable and Serializable
 * interfaces. Patient object has data fields private String name, private
 * String organ, private int age, private BloodType bloodType, private int ID,
 * private boolean isDonor, private int numConnects (for the number of 
 * connections this Patient object has in the TransplantGraph)
 */
public class Patient implements Comparable, Serializable{
	private String name;
	private String organ;
	private int age;
	private BloodType bloodType;
	private int ID;
	private boolean isDonor;//true if patient is a donor, false if they are a recipient
	private int numConnects;
	/**
	 * Default constructor for Patient Object
	 */
	public Patient() {
		
	}
	/**
	 * Setter method for numConnections
	 * @param connections is what this.numConnections is set equal to
	 */
	public void setNumConnections(int connections) {
		numConnects = connections;
	}
	/**
	 * Getter method for numConnects
	 * @return numConnects data field
	 */
	public int getNumConnects() {
		return numConnects;
	}
	/**
	 * Overloaded constructor for Patient
	 * @param ID is what this.ID is set to
	 * @param name is what this.name is set to
	 * @param age is what this.age is set to
	 * @param organ is what this.organ is set to
	 * @param bloodType is what this.bloodType is set to
	 * @param isDonor is what this.isDonor is set to
	 */
	public Patient(int ID, String name, int age, 
			String organ, BloodType bloodType, boolean isDonor) {
		this.ID = ID;
		this.name = name;
		this.age = age;
		this.organ = organ;
		this.bloodType = bloodType;
		this.isDonor = isDonor;
	}
	/**
	 * Setter method for name
	 * @param name is what this.name is set to
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Setter method for organ
	 * @param organ is what this.organ is set to
	 */
	public void setOrgan(String organ) {
		this.organ = organ;
	}
	/**
	 * Setter method for age
	 * @param age is what this.age is set to
	 */
	public void setAge(int age) {
		this.age = age;
	}
	/**
	 * Setter method for bloodType
	 * @param b is what bloodType is set to
	 */
	public void setBloodType(BloodType b) {
		bloodType = b;
	}
	/**
	 * Setter method for ID
	 * @param ID is what this.ID is set to
	 */
	public void setID(int ID) {
		this.ID = ID;	
	}
	/**
	 * Setter method for isDonor
	 * @param isDonor is what this.isDonor is set to
	 */
	public void setIsDonor(boolean isDonor) {
		this.isDonor = isDonor;
	}
	/**
	 * Getter method for this.name
	 * @return String this.name
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * Getter method for organ
	 * @return String this.organ
	 */
	public String getOrgan() {
		return this.organ;
	}
	/**
	 * Getter method for age
	 * @return int this.age
	 */
	public int getAge() {
		return this.age;
	}
	/**
	 * Getter method for bloodType
	 * @return BloodType this.bloodType
	 */
	public BloodType getBloodType() {
		return this.bloodType;
	}
	/**
	 * Getter method for ID
	 * @return int this.ID
	 */
	public int getID() {
		return this.ID;
	}
	/**
	 * Getter method for isDonor
	 * @return boolean this.isDonor
	 */
	public boolean getIsDonor() {
		return this.isDonor;
	}
	/**
	 * compareTo method for Patient object 
	 * @param Object o is the Patient object that is compared with calling 
	 * Patient
	 * @return int 0 if this.ID == otherPatient.getID(). int 1 if
	 * this.ID> otherPatient.getID(). int -1 otherwise 
	 * 
	 */
	public int compareTo(Object o) {
		Patient otherPatient = (Patient)o;
		if(otherPatient.getID()==this.getID())
			return 0;
		else if(this.getID()>otherPatient.getID())
			return 1;
		else 
			return -1;
	}
	/**
	 * toString() method for the Patient class
	 * @return a String representation of the current Patient object
	 */
	public String toString() {
		String fin = String.format("%-15d | %-20s | %-15d | %-15s | %-15s |"
				, ID, name, age, organ,
				bloodType.getBloodType() );
		return fin;
	}
}
