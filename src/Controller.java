import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import org.w3c.dom.css.Counter;

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
	private String execInst = "Press enter to start ";

	// Step mode requires a field to keep track of the number of instructions
	// that have already been executed.
	private Integer stepExecInst;
	
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
	 * Outputs all the memory that has been altered and all of the registers
	 * into the text area of the View.
	 */
	private void DisplayAllMemoryAndRegisters() {
		// Memory header
		MachineMain.machineView
				.outputText("\tMemory Address\tMemory Contents\n");
		MachineMain.machineView
				.outputText("\t--------------\t\t---------------\n");

		// Memory contents that have been altered are displayed
		Integer counter = 0;
		while (counter < MachineMain.machineModel.memoryArray.length) {
			if (MachineMain.machineModel.memoryArray[counter] != null) {
				MachineMain.machineView.outputText('\t'
						+ Utility.DecimalValueToHex(counter).toUpperCase() + "          \t\t"
						+ MachineMain.machineModel.memoryArray[counter] + '\n');
			}
			counter++;
		}

		// General purpose registers header
		MachineMain.machineView
				.outputText("\nGeneral Purpose Registers:\tR0  \tR1  \tR2  \tR3  "
						+ "\tR4  \tR5  \tR6  \tR7  \n");
		MachineMain.machineView
				.outputText("\t\t----\t----\t----\t----"
						+ "\t----\t----\t----\t----\n");
		MachineMain.machineView.outputText("\t                          ");

		// General purpose register contents display
		counter = 0;
		while (counter <= 7) {
			MachineMain.machineView
					.outputText('\t' + MachineMain.machineModel.registerMap
							.get(counter));
			counter++;
		}

		// Program counter and CCR header
		MachineMain.machineView
				.outputText("\n\nProgram Counter\tCondition Code Registers:\tN\tZ\tP\n");
		MachineMain.machineView
				.outputText("---------------\t\t\t\t-\t-\t-\n");

		// Program counter contents and CCR contents display
		MachineMain.machineView
				.outputText(MachineMain.machineModel.programCounter
						+ "           \t                         \t\t\t"
						+ Utility
								.BooleanToString(MachineMain.machineModel.conditionCodeRegisters
										.get('N'))
						+ '\t'
						+ Utility
								.BooleanToString(MachineMain.machineModel.conditionCodeRegisters
										.get('Z'))
						+ '\t'
						+ Utility
								.BooleanToString(MachineMain.machineModel.conditionCodeRegisters
										.get('P')) + '\n');
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

			// Check if input was -1, if so set instruction limit to default
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
							&& newInstructionLimit <= Integer.MAX_VALUE) {
						// Set new instruction limit, display instructions and
						// change action listener to run or options.
						MachineMain.machineModel.instructionLimit = newInstructionLimit;
						MachineMain.machineView.outputText(runOrOptionsInst);
						MachineMain.machineView.setListener(options,
								runOrOptions);
					} else {
						// Display error and instructions again.
						MachineMain.machineView
								.showError("Invalid response. Valid responses: 1 to 2,147,483,647, -1");
						MachineMain.machineView.outputText("CURRENT: "
								+ MachineMain.machineModel.instructionLimit
								+ '\n' + optionsInst);
					}
				} else {
					// Display error and instructions again.
					MachineMain.machineView
							.showError("Invalid response. Valid responses: 1 to 2,147,483,647, -1");
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
				MachineMain.machineView.outputText(execInst
						+ MachineMain.machineModel.programName + ".\n\n");
				MachineMain.machineView.setListener(modeSelect, quiet);
			} else if (text.equals("b") || text.equals("B")) {
				// Display instructions and change action listener to trace
				MachineMain.machineView.outputText(execInst
						+ MachineMain.machineModel.programName + ".\n\n");
				MachineMain.machineView.setListener(modeSelect, trace);
			} else if (text.equals("c") || text.equals("C")) {
				// Set step instruction counter to 0.
				stepExecInst = 0;
				// Display instructions and change action listener to step
				MachineMain.machineView.outputText(execInst
						+ MachineMain.machineModel.programName + ".\n\n");
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
			String executeError;
			String instName;
			Integer[] memAltered = new Integer[5];
			Integer[] regAltered = new Integer[5];
			int instructionCount = 0;
			while (instructionCount < MachineMain.machineModel.instructionLimit) {
				executeError = null;// ExecuteAnInstruction(instName,
									// memAltered[],
									// regAltered[]);

				// Check if the loader returned an error finding the file.
				if (executeError != null) {
					MachineMain.machineView.outputText(executeError + '\n');
				}
				// End execution when the halt instruction occurs.
				// if (instName.equals("HALT")){
				break;
				// }
				// instructionCount++;
			}
			// Output new instructions and change action listener to end.
			MachineMain.machineView.outputText('\n' + endInst);
			MachineMain.machineView.setListener(quiet, end);

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
			// Output initial memory contents
			MachineMain.machineView.outputText("Initial memory state ======================================================\n");
			DisplayAllMemoryAndRegisters();
			MachineMain.machineView.outputText("===========================================================================\n");
			
/*
			String executeError;
			String instName;
			Integer[] memAltered = new Integer[5];
			Integer[] regAltered = new Integer[5];
			int instructionCount = 0;
			while (instructionCount < MachineMain.machineModel.instructionLimit) {
				// Display current program counter contents
				MachineMain.machineView.outputText("Program Counter: "
						+ MachineMain.machineModel.programCounter + '\n');

				executeError = null;// ExecuteAnInstruction(instName,
									// memAltered[],
									// regAltered[]);

				// Check if the interpreter had an error executing the instruction.
				if (executeError != null) {
					MachineMain.machineView.outputText(executeError + '\n');
				}
				// End execution when the halt instruction occurs.
				else if (instName.equals("HALT")) {
					// break;
				} else {
					// Display the instruction being executed
					MachineMain.machineView.outputText("Instruction: "
							+ instName + '\n');

					// If memory was altered, display it
					if (memAltered[0] != null) {
						// Memory header
						MachineMain.machineView
								.outputText("\n\tMemory Address\tNew Memory Contents\n");
						MachineMain.machineView
								.outputText("\t--------------\t-------------------\n");

						// Memory contents that have been altered are displayed
						Integer counter = 0;
						while (memAltered[counter] != null) {
							MachineMain.machineView
									.outputText('\t'
											+ Utility
													.DecimalValueToHex(memAltered[counter])
											+ "          \t"
											+ MachineMain.machineModel.memoryArray[memAltered[counter]]
											+ '\n');
						}
						counter++;
					}

					// If registers were altered, display them
					if (regAltered[0] != null) {
						// Register header
						MachineMain.machineView
								.outputText("\n\tRegister Number\tNew Register Contents\n");
						MachineMain.machineView
								.outputText("\t---------------\t\t---------------------\n");

						// Register contents that have been altered are
						// displayed
						Integer counter = 0;
						while (regAltered[counter] != null) {
							MachineMain.machineView
									.outputText("\tR"
											+ Utility
													.DecimalValueToHex(regAltered[counter])
											+ "             \t"
											+ MachineMain.machineModel.registerMap
													.get(memAltered[counter])
											+ '\n');

							counter++;
						}

						// Display condition code registers regardless of
						// whether they have changed or not
						MachineMain.machineView
								.outputText("\n\n\tCondition Code Registers:\tN\tZ\tP\n");
						MachineMain.machineView
								.outputText("\t-------------------------\t-\t-\t-\n");
						MachineMain.machineView
								.outputText('\t'
										+ MachineMain.machineModel.programCounter
										+ "\t                         \t"
										+ Utility
												.BooleanToString(MachineMain.machineModel.conditionCodeRegisters
														.get('N'))
										+ '\t'
										+ Utility
												.BooleanToString(MachineMain.machineModel.conditionCodeRegisters
														.get('Z'))
										+ '\t'
										+ Utility
												.BooleanToString(MachineMain.machineModel.conditionCodeRegisters
														.get('P')) + '\n');
					}
				}
				instructionCount++;
			}

			// If the instruction limit was met, display error
			if (instructionCount == MachineMain.machineModel.instructionLimit){
				MachineMain.machineView.showError("Instruction Limit Reached.");
			}
*/
			// Output final memory contents
			MachineMain.machineView.outputText("Final memory state ========================================================\n");
			DisplayAllMemoryAndRegisters();
			MachineMain.machineView.outputText("===========================================================================\n");
			
			// Output new instructions and change action listener to end.
			MachineMain.machineView.outputText('\n' + endInst);
			MachineMain.machineView.setListener(trace, end);

		}
	}

	/**
	 * An action listener that displays the memory and registers as they are 
	 * altered during execution by the interpreter. Only one instruction is executed at a time.
	 *  The action listener switches to end when the HALT instruction is executed. Has to use
	 *  a private field to keep track of the number of instructions executed.
	 * 
	 * @author Ben Trivett
	 */
	private class StepMode implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Output initial memory contents if the first instruction has not been executed
			if (stepExecInst == 0){
				MachineMain.machineView.outputText("Initial memory state ======================================================\n");
				DisplayAllMemoryAndRegisters();
				MachineMain.machineView.outputText("===========================================================================\n");
			}
			
			// Identifies when the program is done executing
			Boolean isEnd = false;
			
			if (stepExecInst != MachineMain.machineModel.instructionLimit) {	
/*
			String executeError;
			String instName;
			Integer[] memAltered = new Integer[5];
			Integer[] regAltered = new Integer[5];
	
				// Display current program counter contents
				MachineMain.machineView.outputText("Program Counter: "
						+ MachineMain.machineModel.programCounter + '\n');

				executeError = ExecuteAnInstruction(instName,
									 memAltered[],
									 regAltered[]);

                
			
				// Check if the interpreter had an error executing the instruction.
				if (executeError != null) {
					MachineMain.machineView.outputText(executeError + '\n');
				}
				 else {
					// End execution when the halt instruction occurs.
				    if (instName.equals("HALT")) {
						isEnd = true;
					}
					
					// Display the instruction being executed
					MachineMain.machineView.outputText("Instruction: "
							+ instName + '\n');

					// If memory was altered, display it
					if (memAltered[0] != null) {
						// Memory header
						MachineMain.machineView
								.outputText("\n\tMemory Address\tNew Memory Contents\n");
						MachineMain.machineView
								.outputText("\t--------------\t\t-------------------\n");

						// Memory contents that have been altered are displayed
						Integer counter = 0;
						while (memAltered[counter] != null) {
							MachineMain.machineView
									.outputText('\t'
											+ Utility
													.DecimalValueToHex(memAltered[counter])
											+ "          \t"
											+ MachineMain.machineModel.memoryArray[memAltered[counter]]
											+ '\n');
						}
						counter++;
					}

					// If registers were altered, display them
					if (regAltered[0] != null) {
						// Register header
						MachineMain.machineView
								.outputText("\n\tRegister Number\tNew Register Contents\n");
						MachineMain.machineView
								.outputText("\t---------------\t\t---------------------\n");

						// Register contents that have been altered are
						// displayed
						Integer counter = 0;
						while (regAltered[counter] != null) {
							MachineMain.machineView
									.outputText("\tR"
											+ Utility
													.DecimalValueToHex(regAltered[counter])
											+ "             \t"
											+ MachineMain.machineModel.registerMap
													.get(memAltered[counter])
											+ '\n');

							counter++;
						}

						// Display condition code registers regardless of
						// whether they have changed or not
						MachineMain.machineView
								.outputText("\n\n\tCondition Code Registers:\tN\tZ\tP\n");
						MachineMain.machineView
								.outputText("\t\t\t\t-\t-\t-\n");
						MachineMain.machineView
								.outputText('\t'
										+ MachineMain.machineModel.programCounter
										+ "\t                         \t"
										+ Utility
												.BooleanToString(MachineMain.machineModel.conditionCodeRegisters
														.get('N'))
										+ '\t'
										+ Utility
												.BooleanToString(MachineMain.machineModel.conditionCodeRegisters
														.get('Z'))
										+ '\t'
										+ Utility
												.BooleanToString(MachineMain.machineModel.conditionCodeRegisters
														.get('P')) + '\n');
					}
				}
			}
*/
				
			} else {
				MachineMain.machineView.showError("Instruction Limit Reached.");
				isEnd = true;
			}
			
			if (isEnd){
			// Output final memory contents

				MachineMain.machineView.outputText("Final memory state ========================================================\n");
				DisplayAllMemoryAndRegisters();
				MachineMain.machineView.outputText("===========================================================================\n");
			
			// Output new instructions and change action listener to end.
			MachineMain.machineView.outputText('\n' + endInst);
			MachineMain.machineView.setListener(step, end);
			}else{
				stepExecInst++;
				MachineMain.machineView.outputText("\nPress enter to continue execution.\n\n");
			}
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
				MachineMain.machineView.dispose();
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
