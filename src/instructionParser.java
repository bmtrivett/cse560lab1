import java.util.Map;

/** 
 * This class will parse through the given Instructions
 * @author GerardLouis
 *
 */
public class instructionParser {
	
	/**
	 * This method parses the instruction given by the hex string
	 * instruction. It also increments the programCounter. There are 
	 * five possible return values for this function:
	 * <br /> 
	 * 1.It can return a blank string, which signifies a successful execution. 
	 * <br /> 
	 * 2. It can return a string of hex characters (upto 4); this
	 * signifies a successful operation that has also affected the
	 * programCounter. 
	 * <br /> 
	 * 3. It can return an error message; this will have some indication 
	 * as to the nature of the error (Trying to access a register not there, 
	 * accessing an operand not on the same page as the instruction, etc).
	 * <br />
	 * 4. It can return a string containing "TRAP" plus 2 Hex digits; in 
	 * this case, according to the value of the hex digits, a system call 
	 * will be executed.
	 * <br />
	 * 5. It can return a string containing "DBUG"; in this case the GUI will 
	 * display the contents of the machine registers (PC, general purpose 
	 * registers, CCRs).
	 * @param instruction The hex value of the instruction to be parsed.
	 * @param memoryChanges This array will be written to with the hex value of
	 * the memory location(s) modified.
	 * @param registerChanges This array will be written to with the register
	 * number that was modified.
	 * @return
	 */
	public String parse(
			String instruction,
			String[] memoryChanges,
			Integer[] registerChanges
	) {
		// Create string error and set to a blank string. Also set
		// binaryRep to the binary representation of the last three hex
		// digits of instruction string.
		String error = null;
		String binaryRep = interpreterUtility.decodeLastThreeHexDigits(instruction);
		
		// Have a switch statement to decide which function to use.
		// In each case assign the return value of the function to error.
		// In the case of BRx, JSR, JSRR, and RET, also set programCounter
		// to value of error if in the proper format.
		switch (instruction.charAt(0)) {
			case '0':
			{
				Interpreter.instruction = "BRx";
				error = instructionCases.BRx(
						MachineMain.machineModel.conditionCodeRegisters, 
						MachineMain.machineModel.programCounter, binaryRep);
				if (error.length() <= 4) {
					MachineMain.machineModel.programCounter = error;
				}
				break;
			}	
			case '1':
			{
				Interpreter.instruction = "ADD";
				error = instructionCases.ADD(
						MachineMain.machineModel.registerMap, 
						MachineMain.machineModel.conditionCodeRegisters, binaryRep,
						registerChanges);
				break;
			}
			case '2':
			{
				Interpreter.instruction = "LD";
				error = instructionCases.LD(
						MachineMain.machineModel.registerMap, 
						MachineMain.machineModel.memoryArray, 
						MachineMain.machineModel.conditionCodeRegisters, 
						MachineMain.machineModel.programCounter, binaryRep,
						registerChanges);
				break;
			}
			case '3':
			{
				Interpreter.instruction = "ST";
				error = instructionCases.ST(
						MachineMain.machineModel.registerMap, 
						MachineMain.machineModel.memoryArray, 
						MachineMain.machineModel.conditionCodeRegisters, 
						MachineMain.machineModel.programCounter, binaryRep,
						memoryChanges);
				break;
			}
			case '4':
			{
				Interpreter.instruction = "JSR";
				error = instructionCases.JSR(
						MachineMain.machineModel.registerMap, 
						MachineMain.machineModel.programCounter, binaryRep,
						registerChanges);
				if (error.length() <= 4) {
					MachineMain.machineModel.programCounter = error;
				}
				break;
			}
			case '5':
			{
				Interpreter.instruction = "AND";
				error = instructionCases.AND(
						MachineMain.machineModel.registerMap, 
						MachineMain.machineModel.conditionCodeRegisters, binaryRep,
						registerChanges);
				break;
			}
			case '6':
			{
				Interpreter.instruction = "LDR";
				error = instructionCases.LDR(
						MachineMain.machineModel.registerMap, 
						MachineMain.machineModel.conditionCodeRegisters, binaryRep,
						registerChanges);
				break;
			}
			case '7':
			{
				Interpreter.instruction = "STR";
				error = instructionCases.STR(
						MachineMain.machineModel.registerMap, 
						MachineMain.machineModel.memoryArray, 
						MachineMain.machineModel.conditionCodeRegisters, binaryRep,
						memoryChanges);
				break;
			}
			case '8':
			{
				Interpreter.instruction = "DBUG";
				error = "DBUG";
				break;
			}
			case '9':
			{
				Interpreter.instruction = "NOT";
				error = instructionCases.NOT(
						MachineMain.machineModel.registerMap, 
						MachineMain.machineModel.conditionCodeRegisters, binaryRep,
						registerChanges);
				break;
			}
			case 'A':
			{
				Interpreter.instruction = "LDI";
				error = instructionCases.LDI(
						MachineMain.machineModel.registerMap, 
						MachineMain.machineModel.memoryArray, 
						MachineMain.machineModel.conditionCodeRegisters, 
						MachineMain.machineModel.programCounter, binaryRep,
						registerChanges);
				break;
			}
			case 'B':
			{
				Interpreter.instruction = "STI";
				error = instructionCases.STI(
						MachineMain.machineModel.registerMap, 
						MachineMain.machineModel.memoryArray, 
						MachineMain.machineModel.conditionCodeRegisters, 
						MachineMain.machineModel.programCounter, binaryRep,
						memoryChanges);
				break;
			}
			case 'C':
			{
				Interpreter.instruction = "JSRR";
				error = instructionCases.JSRR(
						MachineMain.machineModel.registerMap, 
						MachineMain.machineModel.programCounter, binaryRep,
						registerChanges);
				if (error.length() <= 4) {
					MachineMain.machineModel.programCounter = error;
				}
				break;
			}
			case 'D':
			{
				Interpreter.instruction = "RET";
				error = instructionCases.RET(
						MachineMain.machineModel.registerMap);
				if (error.length() <= 4) {
					MachineMain.machineModel.programCounter = error;
				}
				break;
			}
			case 'E':
			{
				Interpreter.instruction = "LEA";
				error = instructionCases.LEA(
						MachineMain.machineModel.registerMap, 
						MachineMain.machineModel.conditionCodeRegisters, 
						MachineMain.machineModel.programCounter, binaryRep,
						registerChanges);
				break;
			}
			case 'F':
			{
				Interpreter.instruction = "TRAP";
				error = instructionCases.TRAP(binaryRep);
				break;
			}
			default:
			{
				break;
			}
		}
		// Finally return error string.
		return error;
	}
}