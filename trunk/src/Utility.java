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
	public static String HexToBinary(String hex) {
		return Integer.toBinaryString(Integer.parseInt(hex, 16));
	}

	/**
	 * Converts from a binary string to a hex string.
	 * 
	 * @param binary
	 *            The binary string that will be converted to hexadecimal.
	 * @return A string of hexidecimal of length 4 that represents the binary
	 *         parameter.
	 */
	public static String BinaryToHex(String binary) {
		String hexString = Integer.toHexString(Integer.parseInt(binary, 2))
				.toUpperCase();
		while (hexString.length() < 4) {
			hexString = "0" + hexString;
		}
		return hexString;
	}

	/**
	 * Converts from a hex string to a decimal value.
	 * 
	 * @param hex
	 *            The hexadecimal string that will be converted to base 10.
	 * @return An integer that represents the hexadecimal parameter.
	 */
	public static Integer HexToDecimalValue(String hex) {
		return Integer.parseInt(hex, 16);
	}

	/**
	 * Converts from a decimal value to a hexadecimal string.
	 * 
	 * @param value
	 *            The decimal value that will be converted to hexadecimal.
	 * @return A string that represents the decimal value parameter.
	 */
	public static String DecimalValueToHex(Integer value) {
		return Integer.toHexString(value);
	}

	/**
	 * Tests whether parameter value is a hexadecimal string or not.
	 * 
	 * @param value
	 *            The string that is going to be tested.
	 * @return A boolean true if the string is in all hex, or a false if the
	 *         string is not.
	 */
	public static Boolean isHexString(String value) {
		try {
			Integer.parseInt(value, 16);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Converts from a boolean to a single character string. 0 for false or 1
	 * for true.
	 * 
	 * @param b
	 *            The boolean that will be converted to 0 or 1.
	 * @return A string that represents the boolean.
	 */
	public static String BooleanToString(Boolean b) {
		if (b) {
			return "1";
		} else {
			return "0";
		}
	}

}