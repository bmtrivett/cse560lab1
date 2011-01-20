import java.util.Map;

/** 
 * This class will parse through the given Instructions
 * @author GerardLouis
 *
 */
public class instructionParser {
	
	/**
	 * This public field represents the englishInstruction that
	 * is currently being executed.
	 */
	public String englishInstruction = null;
	
	/**
	 * This public field represents the binary representation of the
	 * last three hex digits of the word being parsed through.
	 */
	public String binaryRepOfLastThreeHexDigits = null;
		
	private static boolean ADD(
			String binaryRep,
			int[] CCR,
			String[] gpRegisters
	) {
		String DR = binaryRep.substring(0, 2);
		String SR1 = binaryRep.substring(3, 5);
		int temp1 = Utility.HexToDecimalValue(
				gpRegisters[Integer.parseInt(SR1, 2)]);
		int temp2 = 0;
		if (binaryRep.charAt(6) == '0') {
			String SR2 = binaryRep.substring(9);
			temp2 = Utility.HexToDecimalValue(
					gpRegisters[Integer.parseInt(SR2, 2)]);
		}
		// Working on sign extension
		else {
			String imm5 = binaryRep.substring(7);
			temp2 = interpreterUtility.signExtendBinaryString(imm5);
		}
		gpRegisters[Integer.parseInt(DR, 2)] = Integer.toString(temp1+temp2);
		// Boolean value to indicate success?
		return true;
	}
	
	/**
	 * This method will parse through the given instruction, executing the
	 * @param instruction
	 */
	public void parse(
			Map<Integer, String> registerMap,
			String[] memoryArray,
			Integer[] conditionCodeRegisters,
			int programCounter,
			String instruction
	) {
		this.englishInstruction = 
			interpreterUtility.decodeFirstHexDigit(instruction);
		this.binaryRepOfLastThreeHexDigits = 
			interpreterUtility.decodeLastThreeHexDigits(instruction);
	}
}
