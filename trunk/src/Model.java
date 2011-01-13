import java.util.HashMap;
import java.util.Map;


/**
 * @author #1Group
 *
 */
public class Model {
	// Use this class to store all the memory and register info in one spot
	// since the loader, interpreter, and simulator all need access to it.
	/**
	 * The map will store an integer and the string of hex for the registers.
	 */
	public static final Map<Integer, String> registerMap = new HashMap<Integer, String>();
	String[] memoryLength = new String[65536];
}
