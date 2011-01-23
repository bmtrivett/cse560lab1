
/**
 * This class interprets the instructions and executes them.
 * Will only stop execution if HALT instruction is encountered.
 * @author GerardLouis
 *
 */
public class Interpreter {
	
	/**
	 * This instructionParser object will have the instruction that
	 * is to be executed passed in to it in order to be parsed.
	 */
	public static instructionParser parserOfInstructions;
	
	/**
	 * This array will have the memory locations that are changed
	 * within an instruction (if any).
	 */
	public static String[] memoryChanges;
	
	/**
	 * This array will have the registers that have been modified
	 * within an instruction (if any).
	 */
	public static Integer[] registerChanges;
	
	/**
	 * This string will have the current instruction being executed.
	 */
	public static String instruction;
	
	/**
	 * This constructor initiates an Instructor object.
	 * @param memory This String array will contain the list of
	 * instructions to be executed 
	 * @param hexStartValue This value will have the starting point 
	 */
	public Interpreter () 
	{
		memoryChanges = new String[5];
		registerChanges = new Integer[5];
		parserOfInstructions = new instructionParser();
	}

	public String ExecuteAnInstruction() {
		// The decimal value of the programCounter.
		Integer programCounterDecimal = Utility.HexToDecimalValue(MachineMain.machineModel.programCounter);
		
		// Blank string to notify of potential errors. If not blank and longer
		// than 4 characters, then an error has been encountered.
		String error = null;
		
		// String instruction set to hex value at programCounter position
		// in memoryArray.
		instruction = MachineMain.machineModel.memoryArray[programCounterDecimal];
		
		// Then programCounter is incremented and decimal value is updated.
		MachineMain.machineModel.programCounter = interpreterUtility.IncrementHexValue(MachineMain.machineModel.programCounter); 
		programCounterDecimal = Utility.HexToDecimalValue(MachineMain.machineModel.programCounter);
		
		// Then, parse instruction is run with its return value being stored
		// in error. Then, error is returned.
		error = parserOfInstructions.parse(
				instruction,
				memoryChanges,
				registerChanges);
		return error;
		
	}
}