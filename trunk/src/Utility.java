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
         * @return A string of hexidecimal that represents the binary parameter.
         */
        public static String BinaryToHex(String binary) {
                return Integer.toHexString(Integer.parseInt(binary, 2)).toUpperCase();
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
         * Converts from a boolean to a single character string. 0 for false or 1
         * for true.
         * 
         * @param b
         *            The boolean that will be converted to 0 or 1.
         * @return A string that represents the boolean.
         */
        public static String BooleanToString(Boolean b) {
                if (b){
                        return "1";
                } else {
                        return "0";
                }
        }

}