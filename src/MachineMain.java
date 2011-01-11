public class MachineMain {

	/**
	 * The main method for the Wi-11 Machine; initiates and controls the model,
	 * view, and controller.
	 * 
	 * @param args
	 */
	public static View machineView;
	public static Controller machineController;
	
	public static void main(String[] args) {
		// The main method for the Wi-11 Machine. Initiates and controls MVC.
		//Boolean runProgram = true;
		
		//while (runProgram){
			// Initialize view
			machineView = new View();
			machineView.setVisible(true);
			
			// Initialize controller
			machineController = new Controller();
			
		//}
	}

}
