/**
 * A class that contains utility methods that all the other classes can use.
 * 
 * @author Ben Trivett
 */
public class Utility {

	/**
	 * Converts from a hex string to a binary string.
	 * 
	 * @param hex
	 *            The hexadecimal string that will be converted to binary.
	 * @return A string of binary that represents the hexadecimal parameter.
	 */
	public static String ConvertHexToBinary(String hex) {
		int i = Integer.parseInt(hex);
		String binary = Integer.toBinaryString(i);
		return binary;
	}
	
	/**
	 * Converts from a binary string to a hex string.
	 * 
	 * @param binary
	 *            The binary string that will be converted to hexadecimal.
	 * @return A string of hecidecimal that represents the binary parameter.
	 */
	public static String ConvertBinaryToHex(String binary) {
		int i = Integer.parseInt(binary);
		String hex = Integer.toHexString(i);
		return hex;
	}

}
