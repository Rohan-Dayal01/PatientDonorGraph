/**
 * @author Rohan Dayal
 * @ID_Number 112768867
 * @Recitation 02
 */

package homework7;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
/**
 * Class defines TransplantGraph object and implements Serializable interface
 * TransplantGraph has data fields: private ArrayList<Patient> donors,
 * private ArrayList<Patient> recipients, public static final int MAX_PATIENTS,
 * private boolean[][] connections, private int curMaxDonorIndex, private int
 * curMaxRecipIndex
 * 
 */
public class TransplantGraph implements Serializable{
	private ArrayList<Patient> donors= new ArrayList<>();;
	private ArrayList<Patient> recipients = new ArrayList<>();
	public static final int MAX_PATIENTS = 100;
	private boolean[][] connections = new boolean[MAX_PATIENTS][MAX_PATIENTS];
	private int curMaxDonorIndex=0;//value should indicate the smallest unused donor index in connections
	private int curMaxRecipIndex=0;//value should indicate the smallest UNUSED recipient index in connections
	//each row index represents the ID of a donor
	//each column index represents the ID of a recipient
	/**
	 * No arg constructor for TransplantGraph object
	 */
	public TransplantGraph() {
		
	}
	/**
	 * Method to update the curMaxDonorIndex by getting the donors list size
	 */
	public void updateDonorIndex() {
		curMaxDonorIndex = donors.size();
	}
	/**
	 * Getter method for curMaxDonorIndex
	 * @return int curMaxDonorIndex
	 */
	public int getMaxDonorInd() {
		return curMaxDonorIndex;
	}
	/**
	 * Method to update curMaxRecipIndex by getting recipients list size
	 */
	public void updateRecipIndex() {
		curMaxRecipIndex = recipients.size();
	}
	/**
	 * Getter method for curMaxRecipIndex
	 * @return int curMaxRecipIndex
	 */
	public int getMaxRecipInd() {
		return curMaxRecipIndex;
	}
	/**
	 * Method to build a TransplantGraph object with data from txt files for
	 * donorFile and recipientFile according to pre-defined structure
	 * @param donorFile is String name of donorFile
	 * @param recipientFile is String name of recipientFile
	 * @return a TransplantGraph object with data initialized in accordance 
	 * with the information from the files
	 * @throws FileNotFoundException if sought after cannot be found
	 * @throws TooManyPatientsException if seeking to add more Patients
	 * to one of the lists than is allowed
	 */
	public static TransplantGraph buildFromFiles(String donorFile, 
			String recipientFile) 
			throws FileNotFoundException, TooManyPatientsException {
		File donorFila = new File(donorFile);
		TransplantGraph patientGraph = new TransplantGraph();
		if(donorFila.exists()==false)
			throw new FileNotFoundException("Donor file not found.");
		File recipientFila = new File(recipientFile);
		if(recipientFila.exists()==false)
			throw new FileNotFoundException("Recipient file not found.");
		Scanner readDonors = new Scanner(donorFila);
		Patient tempDonor;
		String linea;
		String []comps;
		String organ;
		while(readDonors.hasNextLine()) {
			linea = readDonors.nextLine();
			comps = linea.split(", ");
			organ = comps[3];
			organ = organ.substring(0,1).toUpperCase()+organ.substring(1)
			.toLowerCase();
			tempDonor = new Patient(Integer.parseInt(comps[0]), 
					comps[1],Integer.parseInt(comps[2]),organ,
					new BloodType(comps[4]), true);
			patientGraph.addDonor(tempDonor);
			patientGraph.updateDonorIndex();//increasing curMaxDonorIndex
		}
		readDonors.close();
		Patient tempRecip;
		Scanner readRecip = new Scanner(recipientFila);
		while(readRecip.hasNextLine()) {
			linea = readRecip.nextLine();
			comps = linea.split(", ");
			organ = comps[3];
			organ = organ.substring(0,1).toUpperCase()+organ.substring(1).
					toLowerCase();
			tempRecip = new Patient(Integer.parseInt(comps[0]), 
					comps[1],Integer.parseInt(comps[2]),organ,
					new BloodType(comps[4]), false);
			patientGraph.addRecipient(tempRecip);
			patientGraph.updateRecipIndex();;//increasing curMaxRecipIndex
		}
		readRecip.close();
		return patientGraph;
		
	}
	/**
	 * Method to add a donor to the donors list and update adjacency matrix
	 * @param name is String name for new donor Patient
	 * @param age is int age for new donor Patient 
	 * @param organ is String organ for new donor Patient
	 * @param b is BloodType bloodType for new donor Patient
	 * @throws TooManyPatientsException if seeking to add more donors than
	 * the specified MAX_PATIENTS constant
	 */
	public void addDonor(String name, int age, String organ, BloodType b)
			throws TooManyPatientsException {
		if(donors.size()>=MAX_PATIENTS)
			throw new TooManyPatientsException("Cannot have more than "
		+ MAX_PATIENTS+" in the donor list.");
		organ = organ.substring(0,1).toUpperCase()+organ.substring(1);
		Patient tempP =new Patient(curMaxDonorIndex, name, age, organ, b, true);
		donors.add(tempP);//adding to ArrayList
		for(int x=0;x<recipients.size();x++) {
			if(recipients.get(x).getOrgan().equals(tempP.getOrgan())&&
					BloodType.isCompatible(recipients.get(x).getBloodType(),
							tempP.getBloodType())) {
				connections[tempP.getID()][recipients.get(x).getID()] = true;//donor is row, recipient is column
			}
		}//Go through adjacency matrix and update
		updateAllConnections();
		this.updateDonorIndex();
	}
	/**
	 * Overloaded method for adding a donor to donors list.
	 * @param patient is Patient object for the donor to be added to the list
	 * @throws TooManyPatientsException is thrown if trying to add donor to 
	 * the donors list when already at the capacity
	 * 
	 */
	public void addDonor(Patient patient) throws TooManyPatientsException {
		if(donors.size()>=MAX_PATIENTS)
			throw new TooManyPatientsException("Cannot have more than " +
		MAX_PATIENTS+" in the donor list.");
		donors.add(patient);
		for(int x=0;x<recipients.size();x++) {
			if(recipients.get(x).getOrgan().equals(patient.getOrgan())&&
					BloodType.isCompatible(recipients.get(x).getBloodType(), 
							patient.getBloodType())) {
				connections[patient.getID()][recipients.get(x).getID()] = true;
			}
		}
		updateAllConnections();
		this.updateDonorIndex();
	}
	/**
	 * Method to add a recipient Patient to the recipients list and update
	 * the adjacency matrix accordingly
	 * @param name is String name of new recipient Patient
	 * @param age is int age of new recipient Patient
	 * @param organ is String organ of new recipient Patient
	 * @param b is BloodType bloodType of new recipient patient
	 * @throws TooManyPatientsException is thrown if trying to add a recipient
	 * when recipients list is already at MAX_PATIENTS capacity
	 */
	public void addRecipient(String name, int age, String organ, BloodType b)
			throws TooManyPatientsException {
		if(recipients.size()>=MAX_PATIENTS)
			throw new TooManyPatientsException("Cannot have more than " + 
		MAX_PATIENTS+" in the recipients list.");
		organ = organ.substring(0,1).toUpperCase()+organ.substring(1).
				toLowerCase();
		Patient tempP = new Patient(curMaxRecipIndex, name, age, organ, b, 
				false);
		recipients.add(tempP);
		for(int x=0;x<donors.size();x++) {//this part here updates the adjacency matrix
			if(donors.get(x).getOrgan().equals(tempP.getOrgan())&&
					BloodType.isCompatible(tempP.getBloodType(), 
							donors.get(x).getBloodType()))
				connections[donors.get(x).getID()][tempP.getID()] = true;
		}
		updateAllConnections();
		this.updateRecipIndex();
	}
	/**
	 * Overloaded method to add a new recipient Patient to recipients list.
	 * Also updates adjacency matrix
	 * @param patient is Patient object seeking to be added to recipients list 
	 * @throws TooManyPatientsException is thrown if trying to add a recipient
	 * when recipients list is already at MAX_PATIENTS capacity
	 */
	public void addRecipient(Patient patient) throws TooManyPatientsException{
		if(donors.size()>=MAX_PATIENTS)
			throw new TooManyPatientsException("Cannot have more than " 
		+ MAX_PATIENTS+" in the donor list.");
		recipients.add(patient);
		for(int x=0;x<donors.size();x++) {//this part here updates the adjacency matrix
			if(donors.get(x).getOrgan().equals(patient.getOrgan())&&
					BloodType.isCompatible(patient.getBloodType(), 
							donors.get(x).getBloodType()))
				connections[donors.get(x).getID()][patient.getID()] = true;
		}
		updateAllConnections();
		this.updateRecipIndex();
	}
	/**
	 * Getter method for retrieving Patient object from donors list with a 
	 * specific index value
	 * @param index is int index of desired Patient object
	 * @return reference to the Patient object from donors list that matches
	 * specifications (if it exists). If it does not exist, returns null
	 */
	public Patient getDonor(int index) {
		for(int x=0;x<donors.size();x++) {
			if(donors.get(x).getID()==index)
				return donors.get(x);
		}
		return null;
	}
	/**
	 * Method to retrieve the index of a Patient in donors list of a 
	 * given name
	 * @param name is String of desired patient
	 * @return int index where the Patient is located in the donors list.
	 * If they do not exist in the list, return -1
	 */
	public int getDonorIndex(String name) {// USED ONLY BY REMOVE METHOD
		for(int x=0;x<donors.size();x++) {
			if(donors.get(x).getName().equalsIgnoreCase(name))
				return x;
		}
		return -1;
	}
	/**
	 * Overloaded method to retrieve index of a Patient in donors list
	 * of given name and ID
	 * @param name is String name of the desired Patient
	 * @param ID is int ID of the desired Patient
	 * @return int index of the desired donor (if it exists). Return -1
	 * otherwise 
	 */
	public int getDonorIndex(String name, int ID) {
		for(int x=0;x<donors.size();x++) {
			if(donors.get(x).getName().equalsIgnoreCase(name)&&
					donors.get(x).getID()==ID)
				return x;
		}
		return -1;
	}
	/**
	 * Getter method for Patient object from recipients list of given index
	 * @param index is int index of the desired Patient
	 * @return reference to Patient object of the desired index (if one exists)
	 * . Otherwise, return null
	 */
	public Patient getRecipient(int index) {
		for(int x=0;x<recipients.size();x++) {
			if(recipients.get(x).getID()==index)
				return recipients.get(x);
		}
		return null;
	}
	/**
	 * Method to return index in recipients list of Patient with given name
	 * @param name is String name of desired patient
	 * @return the index in recipients list if the Patient is found. Otherwise,
	 * return -1
	 */
	public int getRecipientIndex(String name) {//USED ONLY IN REMOVE METHOD
		for(int x=0;x<recipients.size();x++) {
			if(recipients.get(x).getName().equalsIgnoreCase(name))
				return x;
		}
		return -1;
	}
	/**
	 * Overloaded method to return index in recipients list of a Patient with
	 * given name and ID
	 * @param name is String name of the desired Patient
	 * @param ID is int ID of the desired Patient
	 * @return int index of the Patient in recipients list. If does not exist
	 * in the list, return -1
	 */
	public int getRecipientIndex(String name, int ID) {
		for(int x=0;x<recipients.size();x++) {
			if(recipients.get(x).getName().equalsIgnoreCase(name)&&
					recipients.get(x).getID()==ID)
				return x;
		}
		return -1;
	}
	/**
	 * Method to remove Patient of given name from recipients list
	 * @param name is String name of the Patient desired to be removed
	 * @throws PatientDoesNotExistException if no Patient of the 
	 * given name exists in recipients list
	 */
	public void removeRecipient(String name) throws 
	PatientDoesNotExistException{
		int removedIndx = this.getRecipientIndex(name);//getRecipientIndex returns the index ID of the recipient with given name
		if(removedIndx==-1)
			throw new PatientDoesNotExistException(name+" is not a member of"
					+ " the organ waitlist");
		recipients.remove(removedIndx);
		//Will shift all the booleans values using shiftArray and going down the 
		//connections array. 
		//Start at 
		for(int x=0;x<connections.length;x++) {
			this.shiftArray(removedIndx,connections[x]);
		}
		for(int x=removedIndx;x<curMaxRecipIndex;x++) {
			if(getRecipient(x+1)!=null)
				getRecipient(x+1).setID(getRecipient(x+1).getID()-1);
		}
		updateAllConnections();
		this.updateRecipIndex();
	}
	/**
	 * Method to remove Patient donor from donors list given specific name 
	 * @param name is String name of the donor seeking to be removed
	 * @throws PatientDoesNotExistException if Patient of the given name
	 * does not exist in the donors list
	 */
	public void removeDonor(String name) throws PatientDoesNotExistException {
		int removedIndx = this.getDonorIndex(name);//gets the index ID of the donor with name
		if(removedIndx==-1)
			throw new PatientDoesNotExistException("No donor of name "+name+
					" exists.");
		donors.remove(removedIndx);
		for(int x=removedIndx;x<curMaxDonorIndex;x++) {
			connections[x] = connections[x+1];
			if(getDonor(x+1)!=null)
				getDonor(x+1).setID(getDonor(x+1).getID()-1);
		}
		updateAllConnections();
		this.updateDonorIndex();
	}
	/**
	 * Method to shift array certain number of positions
	 * @param startInd is the index from which to begin swapping
	 * @param arr is the array object which is being modified with the
	 * swapping
	 */
	public void shiftArray(int startInd, boolean[] arr) {
		for(int x=startInd;x<curMaxRecipIndex;x++) {
			arr[x] = arr[x+1]; 
		}
	}
	/**
	 * Method to print all the recipient Patient objects in the specified
	 * output formatting 
	 */
	public void printAllRecipients() {
		String fin = String.format("%-15s | %-20s | %-15s | %-15s | %-15s "
				+ "| %-15s\n", "Index",
				"Recipient Name", "Age", "Organ Needed", "Blood Type", "Donor"
						+ " ID");
		fin+="---------------------------------------------------------------"
				+ "------"
				+ "---------------------------------------\n";
		for(int x=0;x<recipients.size();x++) {
			fin+=recipients.get(x).toString()+" "+getMatchingDonors(
					recipients.get(x))+"\n";
		}
		System.out.println(fin);	
	}
	/**
	 * Method to print all the donor Patient objects in the specified output
	 * formatting.
	 */
	public void printAllDonors() {
		String fin = String.format("%-15s | %-20s | %-15s | %-15s | %-15s "
				+ "| %-15s\n", "Index",
				"Donor Name", "Age", "Organ Donated", "Blood Type", 
				"Recipient ID");
		fin+="-------------------------------------------------------------"
				+ "--------"
				+ "---------------------------------------\n";
		for(int x=0;x<donors.size();x++) {
			fin+=donors.get(x).toString()+" "+getMatchingRecipients(
					donors.get(x))+"\n";
		}
		System.out.println(fin);
		
	}
	/**
	 * Method to get a String representation of the matching donor IDs of a 
	 * given Patient recipient
	 * @param receiver is the Patient from recipients list for whom the
	 * matching donors are being searched for
	 * @return String donorNums which is formatted String of the IDs for the
	 * donors which work for the input receiver Patient 
	 */
	public String getMatchingDonors(Patient receiver){
		//ArrayList<Patient> theDonors = new ArrayList<>();
		//int indx = getRecipientIndex(receiver.getName(), receiver.getID());
		int indx = receiver.getID();
		String donorNums = "";
		for(int x=0;x<donors.size();x++) {
			if(connections[donors.get(x).getID()][indx]==true)
				donorNums = donorNums+donors.get(x).getID()+", ";
		}
		if(donorNums.length()>2) {
			donorNums = donorNums.substring(0,donorNums.length()-2);
		}
		return donorNums;	
	}
	/**
	 * Method to get a String representation of the matching recipient IDs of a
	 * given Patient donor
	 * @param donor is the Patient from donors list for whom the matching
	 * recipients are being searched for
	 * @return String recipNums which is formatted String of the IDs for the
	 * recipients which work for the input Patient donor
	 */
	public String getMatchingRecipients(Patient donor) {
		//int indx = getDonorIndex(donor.getName(), donor.getID());
		int indx = donor.getID();
		String recipNums="";
		for(int x=0;x<recipients.size();x++) {
			if(connections[indx][recipients.get(x).getID()]==true)
				recipNums = recipNums+recipients.get(x).getID()+", ";
		}
		if(recipNums.length()>2) {
			recipNums = recipNums.substring(0, recipNums.length()-2);
		}
		return recipNums;
	}
	/**
	 * Method to update the number of matching donors for each recipient Object
	 * @param receiver is the recipient Object which is having its number of
	 * matching donors updated.
	 */
	public void updateNumMatchingDonors(Patient receiver){
		//ArrayList<Patient> theDonors = new ArrayList<>();
		//int indx = getRecipientIndex(receiver.getName(), receiver.getID());
		int indx = receiver.getID();
		int counter=0;
		for(int x=0;x<donors.size();x++) {
			if(connections[x][indx]==true)
				counter++;
		}
		receiver.setNumConnections(counter);
		//return counter;	
	}
	/**
	 * Method to update the number of matching recipients for each donor Object
	 * @param donor is the donor Object which is having its number of matching
	 * recipients updated.
	 */
	public void updateNumMatchingRecipients(Patient donor){
		//ArrayList<Patient> theDonors = new ArrayList<>();
		//int indx = getDonorIndex(donor.getName(), donor.getID());
		int indx = donor.getID();
		int counter=0;
		for(int x=0;x<recipients.size();x++) {
			if(connections[indx][x]==true)
				counter++;
		}
		donor.setNumConnections(counter);
	}
	/**
	 * Method that runs updateNumMatchingRecipients and updateNumMatchingDonors
	 * for all the donors and recipients in the TransplantGraph
	 */
	public void updateAllConnections() {
		for(int x=0;x<recipients.size();x++) {//updating matching donor connections for each recipient
			updateNumMatchingDonors(recipients.get(x));
		}
		for(int x=0;x<donors.size();x++) {//updating matching recipient connections for each donor
			updateNumMatchingRecipients(donors.get(x));
		}
	}
	/**
	 * Method to sort the donors list by ID number
	 */
	public void sortDonorsID() {
		Collections.sort(donors, new IDNumberComparator());
	}
	/**
	 * Method to sort the donors list by BloodType
	 */
	public void sortDonorsBloodType() {
		Collections.sort(donors, new BloodTypeComparator());
	}
	/**
	 * Method to sort the donors list by the Number of Connections of each 
	 * donor Patient
	 */
	public void sortDonorsNumConnections() {
		Collections.sort(donors, new NumConnectionsComparator());
	}
	/**
	 * Method to sort the donors list by the organs of each donor Patient
	 */
	public void sortDonorsOrgan() {
		Collections.sort(donors, new OrganComparator());
	}
	/**
	 * Method to sort the recipients list by the ID of each recipient
	 */
	public void sortRecipientsID() {
		Collections.sort(recipients, new IDNumberComparator());
	}
	/**
	 * Method to sort recipients list by BloodType (alphabetically)
	 */
	public void sortRecipientsBloodType() {
		Collections.sort(recipients, new BloodTypeComparator());
	}
	/**
	 * Method to sort the recipients list by the Number of Connections 
	 */
	public void sortRecipientsNumConnections() {
		Collections.sort(recipients, new NumConnectionsComparator());
	}
	/**
	 * Method to sort the recipients list by the organ (alphabetically)
	 */
	public void sortRecipientsOrgan() {
		Collections.sort(recipients, new OrganComparator());
	}
	/*public void printBoolArr() {
		for(int x=0;x<donors.size();x++) {
			for(int y=0;y<recipients.size();y++) {
				System.out.print(connections[x][y]+"  ");
			}
			System.out.println();
		}
	}*/
	
	
}
