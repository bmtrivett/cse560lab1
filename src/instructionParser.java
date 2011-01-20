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
	
	/**
	 * 
	 * @param registerMap
	 * @param conditionCodeRegisters
	 * @param binaryRep
	 * @return
	 */
	private static boolean ADD(
			Map<Integer, String> registerMap,
			Map<Character, Boolean> conditionCodeRegisters,
			String binaryRep
			
	) {
		String DR = binaryRep.substring(0, 2);
		String SR1 = binaryRep.substring(3, 5);
		int SR1Contents = Utility.HexToDecimalValue(
				registerMap.get(Integer.parseInt(SR1, 2)));
		int temp2 = 0;
		if (binaryRep.charAt(6) == '0') {
			String SR2 = binaryRep.substring(9);
			temp2 = Utility.HexToDecimalValue(
					registerMap.get(Integer.parseInt(SR2, 2)));
		}
		else {
			String imm5 = binaryRep.substring(7);
			temp2 = interpreterUtility.signExtendBinaryString(imm5);
		}
		int result = SR1Contents+temp2;
		if (result < 0) {
			conditionCodeRegisters.put('N', true);
			conditionCodeRegisters.put('Z', false);
			conditionCodeRegisters.put('P', false);
		}
		else if (result > 0) {
			conditionCodeRegisters.put('N', false);
			conditionCodeRegisters.put('Z', false);
			conditionCodeRegisters.put('P', true);
		}
		else {
			conditionCodeRegisters.put('Z', true);
		}
		registerMap.put((Integer.parseInt(DR, 2)), (Integer.toString(result)));
		// Boolean value to indicate success?
		return true;
	}
	
	private static boolean AND(
		Map<Integer, String> registerMap,
		Map<Character, Boolean> conditionCodeRegisters,
		String binaryRep
	) {
		String DR = binaryRep.substring(0, 2);
		String SR1 = binaryRep.substring(3, 5);
		String SR1Contents = Utility.HexToBinary(registerMap.get(Integer.parseInt(SR1, 2)));
		String temp2 = "";
		if (binaryRep.charAt(6) == '0') {
			String SR2 = binaryRep.substring(9);
			temp2 = Utility.HexToBinary(
					registerMap.get(Integer.parseInt(SR2, 2)));
		}
		else {
			String imm5 = binaryRep.substring(7);
			temp2 = interpreterUtility.signExtendBinaryString(imm5);
		}
		return true;
	}
	
	/**
	 * 
	 * @param registerMap
	 * @param memoryArray
	 * @param conditionCodeRegisters
	 * @param programCounter
	 * @param instruction
	 */
	public void parse(
			Map<Integer, String> registerMap,
			String[] memoryArray,
			Map<Character, Boolean> conditionCodeRegisters,
			int programCounter,
			String instruction
	) {
		this.englishInstruction = 
			interpreterUtility.decodeFirstHexDigit(instruction);
		this.binaryRepOfLastThreeHexDigits = 
			interpreterUtility.decodeLastThreeHexDigits(instruction);
	}
}
