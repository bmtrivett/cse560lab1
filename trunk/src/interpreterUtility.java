/**
 * This utility class provides methods that can be used for
 * commonly performed operations.
 * @author GerardLouis
 *
 */
public class interpreterUtility {
	/**
	 * Public static helper function to decode the instruction from the first
	 * hex digit. It returns the English pseudo-code of the instruction
	 * @param word The 4 digit hex String passed in from the interpreter.
	 * @return English pseudo-code of the instruction in the word passed in.
	 */
	public static String decodeFirstHexDigit(
			String word
	) {
		// A character firstDigit is assigned to the first character of
		// the String word passed in. Also a String instruction is initialized
		// to null.
		char firstDigit = word.charAt(0);
		String instruction = null;
		
		// Then a case select is used to determine which English instruction
		// is to be used.
		switch (firstDigit) {
			case '0': instruction = "BRx";
				break;
			case '1': instruction = "ADD";
				break;
			case '2': instruction = "LD";
				break;
			case '3': instruction = "ST";
				break;
			case '4': instruction = "JSR";
				break;
			case '5': instruction = "AND";
				break;
			case '6': instruction = "LDR";
				break;
			case '7': instruction = "STR";
				break;
			case '8': instruction = "DBUG";
				break;
			case '9': instruction = "NOT";
				break;
			case 'A': instruction = "LDI";
				break;
			case 'B': instruction = "STI";
				break;
			case 'C': instruction = "JSRR";
				break;
			case 'D': instruction = "RET";
				break;
			case 'E': instruction = "LEA";
				break;
			case 'F': instruction = "TRAP";
				break;
			default: break;
		}
		// Finally English instruction is returned.
		return instruction;
	}
	
	/**
	 * Public static function that returns the base 10 equivalent of 
	 * the digit specified, using the position specified in the integer
	 * passed in.
	 * @param word A String of four characters representing the hex 
	 * value to be decoded.
	 * @param hexCharacter A character which represents the digit in hex
	 * @param position The position in the hex string the single digit
	 * is in. For example if the entered digit is E and position 2, the
	 * resultant integer will be 448.
	 * @return An integer which is base 10 conversion of the passed in hex
	 * digit, using the int position to calculate the power the final result
	 * has to be multiplied by.
	 */
	public static int decodeDigitMemoryLocation(
			char hexCharacter,
			int position
	) {
		// Sets int memoryLocation to zero.
		int memoryLocation = 0;
		
		// If-else to check if the character passed in is a digit.
		// If it is a digit, memoryLocation is set to the base-10
		// version of that digit.
		if (Character.isDigit(hexCharacter)) {
			memoryLocation = Character.digit(hexCharacter, 10);
		}
		// Else, memoryLocation is set to 10, 11, 12, 13, 14 or 15,
		// depending if the character is A, B, C, D, E, or F.
		else {
			if (hexCharacter == 'A') {
				memoryLocation = 10;
			}
			else if (hexCharacter == 'B') {
				memoryLocation = 11;
			}
			else if (hexCharacter == 'C') {
				memoryLocation = 12;
			}
			else if (hexCharacter == 'D') {
				memoryLocation = 13;
			}
			else if (hexCharacter == 'E') {
				memoryLocation = 14;
			}
			else {
				memoryLocation = 15;
			}
		}
		
		// Then, memoryLocation is multiplied by the exponent
		// of 16 raised to the position integer passed in. That results in
		// the final base 10 representation of the hex digit passed
		// in. This result is returned.
		return ((int)Math.pow(16, position))*memoryLocation;
	}
	
	/**
	 * This public static function decodes a String of hex characters
	 * into a integer of base 10 value. While this function will work on
	 * strings of length greater than four, the integer returned will point
	 * to a location out of memory range.
	 * @param word The string of hex characters to be converted to a base
	 * 10 integer. The maximum length allowed is four.
	 * @return An integer base 10 conversion of the hex characters in
	 * the string that is passed in.
	 */
	public static int decodeEntireMemoryLocation(
			String word
	) {
		int counter = word.length();
		int result = 0;
		while (counter > 0) {
			result += interpreterUtility.decodeDigitMemoryLocation(
					word.charAt(counter-1), (word.length()-counter));
			counter--;
		}
		return result;
	}
	
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
		String substring = word.substring(1);
		int baseTen = interpreterUtility.decodeEntireMemoryLocation(substring);
		return Integer.toBinaryString(baseTen);
	}
}
