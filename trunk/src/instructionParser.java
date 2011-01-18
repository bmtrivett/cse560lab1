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
	 * Private helper function to decode the instruction from the first
	 * hex digit. It returns the English pseudo-code of the instruction
	 * @param word The 4 digit hex String passed in from the interpreter.
	 * @return English pseudo-code of the instruction in the word passed in.
	 */
	private String decodeFirstHexDigit(
			String word
	) {
		// A character firstDigit is assigned to the first character of
		// the String word passed in. Also a String instruction is initialized
		// to null.
		char firstDigit = word.charAt(0);
		String instruction = null;
		
		// Then a case select is used to determine which 
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
		return instruction;
	}
	
	/**
	 * Private helper function to decode the last 3 digits (pos[0:2]) into
	 * a String of binary numbers.
	 * @param word The four character instruction passed in.
	 * @return A String of binary numbers of length 12.
	 */
	private String decodeLastThreeHexDigits(
			String word
	) {
		// Sets baseTen to integer value of hex digit at the end of the
		// String word, and then adds each subsequent digit to baseTen.
		// Finally returns binary String representation of baseTen int.
		int baseTen = this.decodeDigitMemoryLocation(word.charAt(3), 0);
		baseTen += this.decodeDigitMemoryLocation(word.charAt(2), 1);
		baseTen += this.decodeDigitMemoryLocation(word.charAt(1), 2);
		return Integer.toBinaryString(baseTen);
	}
	
	/**
	 * Private helper function that returns the base 10 equivalent of 
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
	private int decodeDigitMemoryLocation(
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
		
		// Then, memoryLocation is multiplied by the product of
		// of 16 and the position integer passed in to result in
		// the final base 10 representation of the hex digit passed
		// in. This result is returned.
		return (16*position)*memoryLocation;
	}
	
	public void parse(
			String instruction
	) {
		this.englishInstruction = this.decodeFirstHexDigit(instruction);
		this.binaryRepOfLastThreeHexDigits = this.decodeLastThreeHexDigits(instruction);
	}
}
