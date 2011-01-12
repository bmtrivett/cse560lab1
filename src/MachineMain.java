/**
 * The main method for the Wi-11 Machine; instantiates and controls the model,
 * view, and controller.
 * 
 * @author Ben Trivett
 * @param args
 */
public class MachineMain {

	public static View machineView;
	public static Controller machineController;

	public static void main(String[] args) {
		// The main method for the Wi-11 Machine. Initiates and controls MVC.
		// Boolean runProgram = true;

		// while (runProgram){ // Used for resetting the program... maybe.
		// Initialize view
		machineView = new View();
		machineView.setVisible(true);

		// Initialize controller
		machineController = new Controller();

		// }
	}

}
