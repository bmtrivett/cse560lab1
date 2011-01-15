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
	public Map<Integer, String> registerMap;
	/**
	 * The entire abstract machine's memory. Each string in the array will have
	 * a length of 4, representing the hex value stored there.
	 */
	public String[] memoryArray;
	/**
	 * The default limit of instructions allowed to be executed.
	 */
	public static final int DEFAULT_INSTRUCTION_LIMIT = 1000;
	/**
	 * The potentially user-defined instance of the maximum number of
	 * instructions allowed to be executed. Defined in the controller to only be
	 * 1 to 65536 or -1.
	 */
	public Integer instructionLimit;

	/**
	 * Constructor for the Wi-11 machine model.
	 */
	public Model() {
		registerMap = new HashMap<Integer, String>();
		memoryArray = new String[65536];
		instructionLimit = DEFAULT_INSTRUCTION_LIMIT;
	}

}
