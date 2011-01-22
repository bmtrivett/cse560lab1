import java.util.HashMap;
import java.util.Map;

import java.io.*;


/**
 * To put the input information into the data structures that represent the
 * memory and registers of the machine.
 * 
 * @author BSchuck
 * 
 * 
 */
public class InputInstructions {
	/**
	 * A magic number to be used within the program.
	 */
	private final static int posOfInstr = 11;

	/**
	 * 
	 * @param args
	 * @throws IOException
	 *             Takes in the Input file and makes an array of all the memory.
	 */
	public static String FindFile(String input) throws IOException {

		int count = 0;

		File inputFile = new File(input);
		boolean fileExists = inputFile.exists();
		// make catch throws in catch part return stamtment
		if (fileExists == false) {
			return "The file does not exist. Try another one.";
		}

		String read = "";
		FileReader reader = new FileReader(input);
		BufferedReader file = new BufferedReader(reader);
		read = file.readLine();
		while(count < 15)
		{
			int i = 0;
			char ch = read.charAt(i);
			if(Character.isLowerCase(ch) || ch == ' ')
			{
				return "The header record is incorrect in the file please try a new file.";
			}
			i++;
			count++;
		}
		if (read.length() != 15) {
			return "The header record is incorrect in the file please try a new file.";
		}
		// gets just the first line.
		String firstLine = read.toString();
		// Stores the program name.
		MachineMain.machineModel.programName = firstLine.substring(1, 7);
		// cuts off the last four hex characters.

		String startValue = firstLine.substring(7, 11);
		String memorySize = firstLine.substring(posOfInstr, 15);
		
		if(memorySize == "0000")
		{
			return "The header record is incorrect in the file please try a new file.";
		}

		// converts the hex to figure out how many memory spaces there are.
		int decValueMem = Integer.parseInt(memorySize, 16);
		int decValueStart = Integer.parseInt(startValue, 16);
		int decTotalMem = decValueMem + decValueStart;

		// while the file still has input read every line and put it into an
		// array
		while (read != null) {
			int counter = 2;
			count = 0;
			read = file.readLine();
			while(count < 9)
			{
				int i = 0;
				char ch = read.charAt(i);
				if(Character.isLowerCase(ch) || ch == ' ')
				{
					return "The text record is incorrect in the file please try a new file.";
				}
				i++;
				count++;
			}
			if(read.charAt(0) == '\n')
			{
				return "The text record is incorrect in the file please try a new file.";
			}
			// if it E blow up
			if (read.substring(0, 1).equals("E")) {
				break;
			}

			if (read.length() != 9) {
				return "The text record has an error on line " + counter;
			}
			if (counter > decValueMem) {
				return "There are more text record than expected.";
			}
			String textInstructions = read.toString();
			String memoryPos = textInstructions.substring(1, 5);
			int decMemPos = Integer.parseInt(memoryPos, 16);

			if (decMemPos >= decTotalMem || decMemPos < decValueStart) 
			{
				return "The value is out of the allocated memory.";
			}
			
			String textData = textInstructions.substring(5, 9);

			// convert memory to pos in array then add it
			MachineMain.machineModel.memoryArray[decMemPos] = textData;
			counter++;
		}
		// Set the program counter to the first line to be executed.
		MachineMain.machineModel.programCounter = read.substring(1,5);
		return null;
	}

}
