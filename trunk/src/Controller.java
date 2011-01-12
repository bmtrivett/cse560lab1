import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Provides the functionality to the Wi-11 Machine's components by sending and
 * receiving information between the view and the interpreter, loader, and
 * model.
 * 
 * @author Ben Trivett
 */
public class Controller {
	// Contains event listeners for view GUI and the methods that control
	// sending/receiving info from view to/from interpreter/loader.
	/**
	 * Constructor for the Wi-11 Machine controller.
	 */
	public Controller() {
		MachineMain.machineView.setListener(null,
				new PutInputDirectlyToOutput());
	}

	/**
	 * An action listener that takes the user input in the text field of the
	 * View and outputs it into the text area of the View. Used as a preliminary
	 * GUI test function but may prove useful when echoing commands.
	 * 
	 * @author Ben Trivett
	 * 
	 */
	private class PutInputDirectlyToOutput implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String text = MachineMain.machineView.getInput();
			MachineMain.machineView.outputText(text + '\n');
			MachineMain.machineView.clearInputField();
		}
	}
}
