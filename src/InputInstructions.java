import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.*;
import java.lang.reflect.Array;

/**
 * To put the input information into the 
 * data structures that represent the memory and 
 * registers of the machine.
 * @author BSchuck
 * 
 *
 */
public class InputInstructions {
	/**
	 * A magic number to be used within the program. 
	 */
	private final static int eleven = 11;
	/**
	 * The map will store an integer and the string of hex for the registers.
	 */
	public static final Map<Integer, String> myMap = new HashMap<Integer, String>();
	
	/**
	 * 
	 * @param args
	 * @throws IOException
	 * Takes in the Input file and makes an array of all the memory.
	 */
	public static void main(String[] args) throws IOException {
		
		int i = 0;
		Scanner keyboard = new Scanner(System.in);
		String input = "";
		
		File inputFile = new File(input);
		boolean fileExists = inputFile.exists();
		//gets the file and makes sure it exist before moving on.
		while (fileExists == false) {
			System.out.println("The file does not exist. Try another one.");
			
			input = keyboard.nextLine();
			
			inputFile = new File(input);
			fileExists = inputFile.exists();
			
		}
		
		String read = "";
		String line = "";		
		FileReader reader = new FileReader(input);
		BufferedReader file = new BufferedReader(reader);		
		read = file.readLine();
		//gets just the first line.
		String firstLine = read.toString();
		//cuts off the last four hex characters.
		String memorySize = firstLine.substring(eleven);
		//converts the hex to figure out how many memory spaces there are.
		int hexValue = Integer.valueOf(memorySize, 16).intValue();
		 //creates an array of string to the length.
		 String[] memoryLength = new String[hexValue];
		 //while the file still has input read every line and put it into an array
		 while (read != null) {
				read = file.readLine();
				if (read != null) {
					String textInstructions = read.toString();
					String memoryPos = textInstructions.substring(2, 4);
					String textData = textInstructions.substring(4);
					int pos = Integer.valueOf(memoryPos, 16).intValue();
					
					//convert memory to pos in array then add it
					MachineMain.machineModel.memoryArray[pos]=textData;
					i++;
				} else {
					break;
				}
		 }
		
	}

}
