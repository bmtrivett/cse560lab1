import java.util.Map;

/**
 * This class interprets the instructions and executes them.
 * Will only stop execution if HALT instruction is encountered.
 * @author GerardLouis
 *
 */
public class Interpreter {

	/**
	 * The map will store an integer denoting the register number and the string
	 * of hex for the registers.
	 */
	public Map<Integer, String> registerMap;

	/**
	 * The entire abstract machine's memory. Each string in the array will have
	 * a length of 4, representing the hex value stored there.
	 */
	public String[] memoryArray;

	/**
	 * This array of integers will have a length of 3 and each integer
	 * may have a value of 0 or 1.
	 */
	public Integer[] conditionCodeRegisters;

	/**
	 * This string of four characters will have the start value
	 * of the instructions in hex.
	 */
	public String hexStartValue;

	/**
	 * This integer will have the location in the array of the
	 * next instruction.
	 */
	public int programCounter;

	public instructionParser parserOfInstructions;
	
	/**
	 * This constructor initiates an Instructor object.
	 * @param memory This String array will contain the list of
	 * instructions to be executed 
	 * @param hexStartValue This value will have the starting point 
	 */
	public Interpreter (
			Map<Integer, String> registerMap,
			String[] memoryArray,
			Integer[] conditionCodeRegisters,
			String hexStartValue
	) {
		this.registerMap = registerMap;
		this.memoryArray = memoryArray;
		this.conditionCodeRegisters = conditionCodeRegisters;
		this.hexStartValue = hexStartValue;
		this.programCounter = Utility.HexToDecimalValue(hexStartValue);
		this.parserOfInstructions = new instructionParser();
	}

	public void ExecuteAllInstructions() {
		while (this.memoryArray[this.programCounter].charAt(0) != 'F' && 
				this.memoryArray[this.programCounter].charAt(2) != '2' && 
				this.memoryArray[this.programCounter].charAt(3) != '5') {
			String instruction = this.memoryArray[this.programCounter];
			this.programCounter++;
			this.parserOfInstructions.parse(
					this.registerMap,
					this.memoryArray,
					this.conditionCodeRegisters,
					this.programCounter,
					instruction);
		}
	}
}