/**
 * @author Rohan Dayal
 * @ID_Number 112768867
 * @Recitation 02
 */

package homework7;

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
/**
 * Class serves as driver class for the TransplantGraph.
 * TransplantDriver has public static final String DONOR_FILE
 * and public static final String RECIPIENT_FILE. Also main method
 */
public class TransplantDriver {
	public static final String DONOR_FILE = "donors.txt";
	public static final String RECIPIENT_FILE = "recipients.txt";
	/**
	 * Main driver class method of the TransplantGraph
	 * @param args
	 * @exception IOException e is caught if an IO exception occurs
	 * while serialization process is occurring
	 * @exception FileNotFoundException e is caught if a file is sought
	 * but cannot be found
	 * @exception InputMismatchException e is caught when error in entering
	 * information through the Scanner
	 * @exception TooManyPatientsException is caught if donors list or
	 * recipients list is at capacity and seeking to add more Patients
	 * @exception PatientDoesNotExistException is caught if trying to
	 * access a Patient with specific data field values that does not 
	 * exist in the TransplantGraph
	 * @exception ClassNotFoundException e is caught when error obtaining
	 * the class for TransplantGraph
	 */
	public static void main(String[]args) {
		Scanner rod = new Scanner(System.in);
		// NEED TO TRY AND LOAD A PREVIOUSLY CREATED SERIALIZABLE
		TransplantGraph deGraphy;
		try {
			File fila = new File("transplant.obj");
			if(fila.exists()) {
				FileInputStream transplantobj = new FileInputStream(fila);
				ObjectInputStream fileIn = new ObjectInputStream(transplantobj);
				deGraphy = (TransplantGraph)fileIn.readObject();
				fileIn.close();
				System.out.println("Loading data from transplant.obj... ");
			}
			else {
				System.out.println("transplant.obj not found. Creating new Tran"
						+ "splantGraph object...");
				System.out.println("Loading data from '"+DONOR_FILE+"'...");
				System.out.println("Loading data from '"+RECIPIENT_FILE+"'...");
				deGraphy = new TransplantGraph();
				deGraphy = TransplantGraph.buildFromFiles(DONOR_FILE, 
						RECIPIENT_FILE);
			}
			String entd="";
			while(entd.equals("Q")==false) {
				try {
				String menu = "(LR) - List all recipients\n"
						+ "(LO) - List all donors\n"
						+ "(AO) - Add new donor\n"
						+ "(AR) - Add new recipient\n"
						+ "(RO) - Remove donor\n"
						+ "(RR) - Remove recipient\n"
						+ "(SR) - Sort recipients\n"
						+ "(SO) - Sort donors\n"
						+ "(Q) - Quit";
				System.out.println("Menu:");
				System.out.println(menu);
				System.out.println("Please select an option:");
				entd = rod.next();
				entd = entd.toUpperCase();
				rod.nextLine();
				if(entd.equals("LR")) {
					deGraphy.printAllRecipients();
				}
				else if(entd.equals("LO")) {
					deGraphy.printAllDonors();
				}
				else if(entd.equals("AO")) {
					System.out.println("Please enter the organ donor name: ");
					String nam = rod.nextLine();
					System.out.println("Please enter the organ(s) "+nam+" is "
							+ "donating: ");
					String orgos = rod.next();
					System.out.println("Please enter the blood type of " + nam+
							":");
					String bt = rod.next();
					System.out.println("Please enter the age of "+nam+":");
					int age = rod.nextInt();
					deGraphy.addDonor(nam, age, orgos, new BloodType(bt.
							toUpperCase()));
					System.out.println("\nThe organ donor with ID "
							+ (deGraphy.getMaxDonorInd()-1)+" was "
									+ "successfully added "
									+ "to the donor list!");
				}
				else if(entd.equals("AR")) {
					System.out.println("Please enter the new recipient's "
							+ "name: ");
					String name = rod.nextLine();
					System.out.println("Please enter the recipient's blood "
							+ "type: ");
					String bt = rod.next();
					System.out.println("Please enter the recipient's age: ");
					int age = rod.nextInt();
					System.out.println("Please enter the organ needed: ");
					String organ = rod.next();
					deGraphy.addRecipient(name, age, organ, 
							new BloodType(bt.toUpperCase()));
					System.out.println(name +" is now on the organ transplant "
							+ "waiting list!");
				}
				else if(entd.equals("RO")) {
					System.out.println("Please enter the name of the organ "
							+ "donor"
							+ " to remove:");
					String name = rod.nextLine();
					deGraphy.removeDonor(name);
					System.out.println(name +" was removed from the organ donor"
							+ " list.");

				}
				else if(entd.equals("RR")) {
					System.out.println("Please enter the name of the recipient"
							+ " to remove:");
					String name = rod.nextLine();
					deGraphy.removeRecipient(name);
					System.out.println(name+" has been removed from the "
							+ "organ transplant"
							+ " waitlist.");
				}
				else if(entd.equals("SR")) {
					String enttwo="";
					while(enttwo.equals("Q")==false) {
						System.out.println("(I) Sort by ID\n" + 
								"(N) Sort by Number of Donors\n" + 
								"(B) Sort by Blood Type\n" + 
								"(O) Sort by Organ Needed\n" + 
								"(Q) Back to Main Menu");
						System.out.println("Please select an option.");
						enttwo = rod.next();
						enttwo = enttwo.toUpperCase();
						if(enttwo.equals("I")) {
							deGraphy.sortRecipientsID();
							deGraphy.printAllRecipients();
						}
						else if(enttwo.equals("N")) {//connections
							deGraphy.sortRecipientsNumConnections();
							deGraphy.printAllRecipients();
							deGraphy.sortRecipientsID();
						}
						else if(enttwo.equals("B")) {
							deGraphy.sortRecipientsBloodType();
							deGraphy.printAllRecipients();
							deGraphy.sortRecipientsID();
						}
						else if(enttwo.equals("O")) {
							deGraphy.sortRecipientsOrgan();
							deGraphy.printAllRecipients();
							deGraphy.sortRecipientsID();
						}
						else if(enttwo.equals("Q")) {
							System.out.println("Returning to main menu.");
							break;
						}
					}
					}
				else if(entd.equals("SO")) {
					String entthree="";
					while(entthree.equals("Q")==false) {
						System.out.println("(I) Sort by ID\n" + 
								"(N) Sort by Number of Recipients\n" + 
								"(B) Sort by Blood Type\n" + 
								"(O) Sort by Organ Donated\n" + 
								"(Q) Back to Main Menu");
						System.out.println("Please select an option:");
						entthree = rod.next();
						entthree = entthree.toUpperCase();
						if(entthree.equals("I")) {
							deGraphy.sortDonorsID();
							deGraphy.printAllDonors();
						}
						else if(entthree.equals("N")) {
							deGraphy.sortDonorsNumConnections();
							deGraphy.printAllDonors();
							deGraphy.sortDonorsID();
						}
						else if(entthree.equals("B")) {
							deGraphy.sortDonorsBloodType();
							deGraphy.printAllDonors();
							deGraphy.sortDonorsID();
						}
						else if(entthree.equals("O")) {
							deGraphy.sortDonorsOrgan();
							deGraphy.printAllDonors();
							deGraphy.sortDonorsID();
						}
						else if(entthree.equals("Q")) {
							System.out.println("Returning to main menu.");
							break;
						}
					}
				}
				else if(entd.equals("Q")){
					
					FileOutputStream output = new FileOutputStream
							("transplant.obj");
					try {
						ObjectOutputStream objOut = new ObjectOutputStream
								(output);
						objOut.writeObject(deGraphy);
						objOut.close();
						System.out.println("Writing data "
								+ "to transplant.obj...");
					}
					catch(IOException e) {
						System.out.println("Error serializing the data.");
						e.printStackTrace();
					}
					System.out.println("Program terminating successfully.");
					break;
				}
			}
				catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
				catch(InputMismatchException e) {
					rod.nextLine();
					System.out.println("Please enter a valid data type. "
							+ "If asking for "
							+ "an integer, double, boolean, String, please "
							+ "enter accordingly.");
				}
				catch(TooManyPatientsException e) {
					System.out.println(e.getMessage());
				}
				catch(PatientDoesNotExistException e) {
					System.out.println(e.getMessage());
				}
		} 
		}
		catch(IOException e) {
			System.out.println("Error in obtaining the files.");
			
		}
		catch (ClassNotFoundException e) {
			System.out.println("Class not found for TransplantGraph.");
		}
		catch(TooManyPatientsException e) {
			System.out.println(e.getMessage());
		}
			
			//deGraphy.printBoolArr();
			
		
	}
}
