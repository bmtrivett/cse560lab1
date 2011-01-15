import java.util.HashMap;
import java.util.Map;

/**
 * Use this class to store all the memory and register info in one spot since
 * the loader, interpreter, and simulator all need access to it.
 * 
 * @author #1Group
 * 
 */
public class Model {
	/**
	 * The map will store an integer denoting the register number and the string
	 * of hex for the registers.
	 */
	public static Map<Integer, String> registerMap;
	/**
	 * The entire abstract machine's memory. Each string in the array will have
	 * a length of 4, representing the hex value stored there.
	 */
	public static String[] memoryArray;

	/**
	 * Constructor for the Wi-11 machine model.
	 */
	public Model() {
		registerMap = new HashMap<Integer, String>();
		memoryArray = new String[65536];
	}

}
