/**
 * The main method for the Wi-11 Machine that creates instances of a model,
 * view, and controller.
 * 
 * @author Ben Trivett
 * @param args
 */
public class MachineMain {

	public static View machineView;
	public static Controller machineController;
	public static Model machineModel;

	public static void main(String[] args) {
		// Initialize view
		machineView = new View();
		machineView.setVisible(true);

		// Initialize controller
		machineController = new Controller();

		// Initialize model
		machineModel = new Model();
	}

}
