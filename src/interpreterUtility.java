/**
 * This utility class provides methods that can be used for
 * commonly performed operations.
 * @author GerardLouis
 *
 */
public class interpreterUtility {
	
	/**
	 * Public static function to decode the last 3 digits (pos[0:2]) into
	 * a String of binary numbers.
	 * @param word The four character instruction passed in.
	 * @return A String of binary numbers of length 12.
	 */
	public static String decodeLastThreeHexDigits(
			String word
	) {
		// Sets baseTen to integer value of hex digit at the end of the
		// String word, and then adds each subsequent digit to baseTen.
		// Finally returns binary String representation of baseTen integer.
		//String substring = word.substring(1);
		//int baseTen = interpreterUtility.decodeEntireMemoryLocation(substring);
		//return Integer.toBinaryString(baseTen);
		return Utility.HexToBinary(word.substring((word.length()-3)));
	}
	
	/**
	 * Public static function to return an integer that is a base 10
	 * conversion of the signed binary string passed in.
	 * @param binaryString A string consisting of 0s and 1s, representing
	 * a signed binary number.
	 * @return An integer that represents the base 10 representation
	 * of the signed binary string passed in.
	 */
	public static int signExtendBinaryString(
			String binaryString
	) {
		int result = 0;
		if ((binaryString.charAt(0) == '1')) {
			StringBuffer tempString = new StringBuffer();
			int counter = binaryString.length();
			while (counter > 1) {
				while ((binaryString.charAt(counter-1)) != '1') {
					tempString.insert(0, (binaryString.charAt(counter-1)));
					counter--;
				}
				if (binaryString.charAt(counter-1) == '0') {
					tempString.insert(0, '1');
				}
				else {
					tempString.insert(0, '0');
				}
				counter--;
			}
			result = 0 - Integer.parseInt((tempString.toString()), 2);
		}
		else {
			result = Integer.parseInt(binaryString, 2);
		}
		return result;
	}
	
	/**
	 * This function will check if the binary string passed is a valid
	 * value for a register. It must parse to a value between 0 and 7, 
	 * and so must have a maximum length of 3.
	 * @param binaryRep The binary representation of the register number
	 * being passed in.
	 * @return True if the binary string passed in represents a decimal
	 * number between 0 and 7 (inclusive).
	 */
	public static boolean isRegisterValue(
			String binaryRep
	) {
		return (Utility.HexToDecimalValue(Utility.BinaryToHex(binaryRep)) < 8 &&
				Utility.HexToDecimalValue(Utility.BinaryToHex(binaryRep)) >= 0);
	}
	
	/**
	 * Increments the given hex string by 1 and returns a new string
	 * with the incremented value.
	 * @param hex The hexadecimal string that is to be incremented.
	 * @return A hex string that represents the hex string passed in,
	 * incremented by 1.
	 */
	public static String IncrementHexValue(String hex) {
		Integer result = Utility.HexToDecimalValue(hex);
		result++;
		return Integer.toHexString(result).toUpperCase();
	}
	
	/**
	 * Decrements the given hex string by 1 and returns a new string
	 * with the decremented value.
	 * @param hex The hexadecimal string that is to be decremented.
	 * @return A hex string that represents the hex string passed in,
	 * decremented by 1.
	 */
	public static String DecrementHexValue(String hex) {
		Integer result = Utility.HexToDecimalValue(hex);
		result--;
		return Integer.toHexString(result).toUpperCase();
	}
	
	public static boolean isOnSamePage(String operand, String programCounter) {
		operand = Utility.HexToBinary(operand);
		programCounter = Utility.HexToBinary(programCounter);
		operand = operand.substring(0, 6);
		programCounter = programCounter.substring(0, 6);
		
		return (Integer.parseInt(operand, 2) == Integer.parseInt(programCounter, 2));
	}
}
