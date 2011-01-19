// CODE IN PROGRESS	

	/**
	 * This class interprets the instructions and executes them.
	 * Will only stop execution if HALT instruction is encountered.
	 * @author GerardLouis
	 *
	 */
	public class Interpreter {
		/**
		 * This array will hold the entire memory of the machine.
		 * As such, it will have a length of 65,536.
		 */
		public String[] memoryLength;
		
		/**
		 * This array of strings will hold the contents of the General 
		 * Purpose Registers. Each string will have a length of four 
		 * and will be formatted as a hex value. Since there are 8 
		 * general purpose registers, this array will have a length of 8.
		 */
		public String[] gpRegisters;
		
		/**
		 * This array of integers will have a length of 3 and each integer
		 * may have a value of 0 or 1.
		 */
		public int[] CCR;
		
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
			String[] memory,
			String hexStartValue
		) {
			this.memoryLength = memory;
			this.hexStartValue = hexStartValue;
			this.programCounter = 
				interpreterUtility.decodeEntireMemoryLocation(hexStartValue);
			this.parserOfInstructions = new instructionParser();
		}
		
		public void ExecuteAllInstructions() {
			while (this.memoryLength[this.programCounter].charAt(0) != 'F' && 
					this.memoryLength[this.programCounter].charAt(2) != '2' && 
					this.memoryLength[this.programCounter].charAt(3) != '5') {
				String instruction = this.memoryLength[this.programCounter];
				this.programCounter++;
				this.parserOfInstructions.parse(
						instruction, 
						this.programCounter, 
						this.memoryLength, 
						this.CCR, 
						this.gpRegisters);
			}
		}
	}