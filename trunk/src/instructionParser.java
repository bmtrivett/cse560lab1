/** 
 * This class will parse through the given Instructions
 * @author GerardLouis
 *
 */
public class instructionParser {
	
	/**
	 * Private helper class to decode the instruction from the first
	 * hex digit
	 * @param word The 4 digit hex String passed in from the interpreter.
	 * @return
	 */
	private String decodeFirstHexDigit(
			String word
	) {
		char firstDigit = word.charAt(0);
		String instruction = null;
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
}
