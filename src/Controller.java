/**
 * @author Ben Trivett
 *
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
	// Contains event listeners for view GUI and the methods that control
	// sending/receiving info from view to/from interpreter/loader.
		public Controller(){
			MachineMain.machineView.setListener(null, new InputFieldListener());
		}
	private class InputFieldListener implements ActionListener {		
		public void actionPerformed(ActionEvent e) {
        	String text = MachineMain.machineView.getInput();
        	MachineMain.machineView.outputText(text + '\n');
        	MachineMain.machineView.clearInputField();
		}
	}
}
