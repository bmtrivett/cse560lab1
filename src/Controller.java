import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Provides the functionality to the Wileven Machine's components by sending and
 * receiving information between the view and the interpreter, loader, and
 * model. Progress the machine's run process is accomplished by successful
 * completion of the action listeners in this order: GetFileLocation,
 * RunOrSetOptions (if "OPTIONS" is chosen: SetOptions then back to
 * RunOrSetOptions), RunModeSelect, (QuietMode, TraceMode, or StepMode), then
 * finally EndOrRestart.
 * 
 * @author Ben Trivett
 */
public class Controller {
	// Contains event listeners for view GUI and the methods that control
	// sending/receiving info from view to/from interpreter/loader.

	// Instantiate action listeners.
	private GetFileLocation getFile;
	private RunOrSetOptions runOrOptions;
	private SetOptions options;
	private RunModeSelect modeSelect;
	private QuietMode quiet;
	private TraceMode trace;
	private StepMode step;
	private EndOrRestart end;

	// Consolidate all instruction text.
	private String getFileInst = "Enter location of file you wish to load:\n";
	private String runOrOptionsInst = "Select an option:\n"
			+ "A) Choose run mode.\nB) Set instruction limit.\n";
	private String modeSelectInst = "Select run mode:\n"
			+ "A) Quiet mode.\nB) Trace mode.\nC) Step mode.\n";
	private String optionsInst = "New instruction limit (1 to 2,147,483,647 "
			+ "or -1 for DEFAULT):\n";
	private String endInst = "Execution over, please choose an option:\n"
			+ "A) Load another file.\n" + "B) Reset Wileven Machine.\n"
			+ "C) Quit.\n";

	// Add this before to options instructions for dynamic behavior:
	// "CURRENT: " + MachineMain.machineModel.instructionLimit + '\n' +

	/**
	 * Constructor for the Wileven Machine controller.
	 */
	public Controller() {
		// Designate first action listener.
		getFile = new GetFileLocation();
		MachineMain.machineView.setListener(null, getFile);

		// Create the rest of the action listeners.
		runOrOptions = new RunOrSetOptions();
		options = new SetOptions();
		modeSelect = new RunModeSelect();
		quiet = new QuietMode();
		trace = new TraceMode();
		step = new StepMode();
		end = new EndOrRestart();

		// Output first instruction to user.
		MachineMain.machineView.outputText(getFileInst);
	}

	/**
	 * Takes the user input in the text field of the View, clears the field, and
	 * outputs it into the text area of the View.
	 * 
	 * @author Ben Trivett
	 */
	private void echoInput() {
		String text = MachineMain.machineView.getInput();
		MachineMain.machineView.outputText(text + "\n\n");
		MachineMain.machineView.clearInputField();
	}

	/**
	 * An action listener that takes the user input in the text field of the
	 * View and sends it to the loader. If the loader doesn't return an error,
	 * then the action listener is changed to the next step in the program
	 * process, RunOrSetOptions.
	 * 
	 * @author Ben Trivett
	 */
	private class GetFileLocation implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Take in the input.
			String text = MachineMain.machineView.getInput();

			// Send the input to the loader.
			String getFileError;
			try {
				getFileError = InputInstructions.FindFile(text);
			} catch (IOException e1) {
				getFileError = "An error has occurred while loading this file.";
			}

			// Check if the loader returned an error finding the file.
			if (getFileError != null) {
				echoInput();
				MachineMain.machineView.showError(getFileError);
				MachineMain.machineView.outputText(getFileInst);
			} else {
				// If no error output new instructions and change
				// action listener to next.
				MachineMain.machineView.outputText("Loading: ");
				echoInput();
				MachineMain.machineView.outputText(runOrOptionsInst);
				MachineMain.machineView.setListener(getFile, runOrOptions);
			}
		}
	}

	/**
	 * An action listener that takes the user input in the text field of the
	 * View and decides whether to change to run mode select or set instruction
	 * limit.
	 * 
	 * @author Ben Trivett
	 */
	private class RunOrSetOptions implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Take in the input.
			String text = MachineMain.machineView.getInput();
			echoInput();

			if (text.equals("a") || text.equals("A")) {
				// Display instructions and change action listener to run mode
				// select
				MachineMain.machineView.outputText(modeSelectInst);
				MachineMain.machineView.setListener(runOrOptions, modeSelect);
			} else if (text.equals("b") || text.equals("B")) {
				// Display instructions and change action listener to options
				MachineMain.machineView.outputText("CURRENT: "
						+ MachineMain.machineModel.instructionLimit + '\n'
						+ optionsInst);
				MachineMain.machineView.setListener(runOrOptions, options);
			} else {
				// Display error and instructions again.
				MachineMain.machineView
						.showError("Invalid response. Valid responses: A, B");
				MachineMain.machineView.outputText(runOrOptionsInst);
			}
		}
	}

	/**
	 * An action listener that takes the user input in the text field of the
	 * View, sets that as the new instruction limit, then goes back to the run
	 * or options action listener.
	 * 
	 * @author Ben Trivett
	 */
	private class SetOptions implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Take in the input.
			String text = MachineMain.machineView.getInput();
			echoInput();

			// Check if input was blank, if so set instruction limit to default
			// and return to previous action listener.
			if (text.equals("-1")) {
				MachineMain.machineModel.instructionLimit = Model.DEFAULT_INSTRUCTION_LIMIT;
				MachineMain.machineView.outputText(runOrOptionsInst);
				MachineMain.machineView.setListener(options, runOrOptions);
			} else {
				// Check for possible exception
				Boolean errorExists = false;
				try {
					Integer.parseInt(text);
				} catch (NumberFormatException err) {
					errorExists = true;
				}

				// If there was no exception, convert text to integer
				if (!errorExists) {
					int newInstructionLimit = Integer.parseInt(text);
					if (newInstructionLimit >= 1
							&& newInstructionLimit <= 65536) {
						// Set new instruction limit, display instructions and
						// change action listener to run or options.
						MachineMain.machineModel.instructionLimit = newInstructionLimit;
						MachineMain.machineView.outputText(runOrOptionsInst);
						MachineMain.machineView.setListener(options,
								runOrOptions);
					} else {
						// Display error and instructions again.
						MachineMain.machineView
								.showError("Invalid response. Valid responses: 1 to 65536, -1");
						MachineMain.machineView.outputText("CURRENT: "
								+ MachineMain.machineModel.instructionLimit
								+ '\n' + optionsInst);
					}
				} else {
					// Display error and instructions again.
					MachineMain.machineView
							.showError("Invalid response. Valid responses: 1 to 65536, -1");
					MachineMain.machineView.outputText("CURRENT: "
							+ MachineMain.machineModel.instructionLimit + '\n'
							+ optionsInst);
				}
			}
		}
	}

	/**
	 * An action listener that takes the user input in the text field of the
	 * View and decides what mode to run the program in.
	 * 
	 * @author Ben Trivett
	 */
	private class RunModeSelect implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Take in the input.
			String text = MachineMain.machineView.getInput();
			echoInput();

			if (text.equals("a") || text.equals("A")) {
				// Display instructions and change action listener to quiet
				// MachineMain.machineView.outputText(quietInst);
				MachineMain.machineView.setListener(modeSelect, quiet);
			} else if (text.equals("b") || text.equals("B")) {
				// Display instructions and change action listener to trace
				// MachineMain.machineView.outputText(traceInst);
				MachineMain.machineView.setListener(modeSelect, trace);
			} else if (text.equals("c") || text.equals("C")) {
				// Display instructions and change action listener to step
				// MachineMain.machineView.outputText(stepInst);
				MachineMain.machineView.setListener(modeSelect, step);
			} else {
				// Display error and instructions again.
				MachineMain.machineView
						.showError("Invalid response. Valid responses: A, B, C");
				MachineMain.machineView.outputText(modeSelectInst);
			}
		}
	}

	/**
	 * An action listener that takes the user input in the text field of the
	 * View and outputs it into the text area of the View.
	 * 
	 * @author Ben Trivett
	 */
	private class QuietMode implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MachineMain.machineView.outputText("Executing...\n");
			// Tell the interpreter to run in quiet mode.
			String executeError;
			String instName;
			Integer[] memAltered = new Integer[5];
			Integer[] regAltered = new Integer[5];
			// try {
			executeError = null;// ExecuteAnInstruction(instName, memAltered[], regAltered[]);
			// } catch (IOException e1) {
			// getFileError = "An error has occurred while loading this file.";
			// }

			// Check if the loader returned an error finding the file.
			if (executeError != null) {
				MachineMain.machineView.showError(executeError);
				MachineMain.machineView.outputText(executeError);
			} else {
				// If no error output new instructions and change
				// action listener to next.
				MachineMain.machineView.outputText("Success!\n");
				MachineMain.machineView.setListener(quiet, end);
			}
		}
	}

	/**
	 * An action listener that takes the user input in the text field of the
	 * View and outputs it into the text area of the View.
	 * 
	 * @author Ben Trivett
	 */
	private class TraceMode implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String text = MachineMain.machineView.getInput();
			MachineMain.machineView.outputText(text + '\n');
			MachineMain.machineView.clearInputField();
		}
	}

	/**
	 * An action listener that takes the user input in the text field of the
	 * View and outputs it into the text area of the View.
	 * 
	 * @author Ben Trivett
	 */
	private class StepMode implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String text = MachineMain.machineView.getInput();
			MachineMain.machineView.outputText(text + '\n');
			MachineMain.machineView.clearInputField();
		}
	}

	/**
	 * An action listener that takes the user input in the text field of the
	 * View and either changes the action listener to GetFileLocation, restarts
	 * the Wileven Machine, or exits.
	 * 
	 * @author Ben Trivett
	 */
	private class EndOrRestart implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Take in the input.
			String text = MachineMain.machineView.getInput();
			echoInput();

			if (text.equals("a") || text.equals("A")) {
				// Display instructions and change action listener to getFile
				MachineMain.machineView.outputText(getFileInst);
				MachineMain.machineView.setListener(end, getFile);
			} else if (text.equals("b") || text.equals("B")) {
				// Restart Wileven Machine
				MachineMain.Reset();
			} else if (text.equals("c") || text.equals("C")) {
				// Close Wileven Machine
				System.exit(0);
			} else {
				// Display error and instructions again.
				MachineMain.machineView
						.showError("Invalid response. Valid responses: A, B, C");
				MachineMain.machineView.outputText(endInst);
			}
		}
	}
}
