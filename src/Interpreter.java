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
		 * This integer will have the start value of the instructions
		 * in hex (although it will actually be an integer)
		 */
		public int hexStartValue;
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
			int hexStartValue
		) {
			this.memoryLength = memory;
			this.hexStartValue = hexStartValue;
			this.programCounter = this.hexStartValue;
			this.parserOfInstructions = new instructionParser();
		}
		
		public void ExecuteAllInstructions() {
			while (this.memoryLength[this.programCounter].charAt(0) != 'F' && 
					this.memoryLength[this.programCounter].charAt(2) != '2' && 
					this.memoryLength[this.programCounter].charAt(3) != '5') {
				String instruction = this.memoryLength[this.programCounter];
				// The line below should be uncommented once the parse method
				// has been written.
				//this.parserOfInstructions.parse(instruction);
			}
		}
	}