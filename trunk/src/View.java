import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

/**
 * Contains the GUI for the Wileven Machine. Displays a frame with a text field
 * for input and a text area for output.
 * 
 * @author Ben Trivett
 */
public class View extends JFrame {
	private static final long serialVersionUID = 1L;

	private static final Integer DEFAULT_CONSOLE_WIDTH = 80;
	private static final Integer DEFAULT_CONSOLE_HEIGHT = 40;
	private static JTextField inputField;
	private static JTextArea outputField;

	/**
	 * Constructor for the Wileven Machine GUI.
	 */
	public View() {
		this.resetView();
	}

	/**
	 * Creates a new GUI with an empty text field and text area.
	 */
	public void resetView() {
		// Set up display objects.
		inputField = new JTextField(DEFAULT_CONSOLE_WIDTH);
		outputField = new JTextArea(DEFAULT_CONSOLE_HEIGHT,
				DEFAULT_CONSOLE_WIDTH);
		outputField.setEditable(false); // Default is editable
		JScrollPane consolePane = new JScrollPane(outputField);
		

		// Put display objects in a container and lay them out.
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		content.add(inputField, BorderLayout.SOUTH);
		content.add(consolePane, BorderLayout.NORTH);

		// Finalize layout by adding content and title.
		this.setContentPane(content);
		this.pack();
		this.setTitle("Wileven Machine");

		// Set the window closing event.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Retrieves the input inside the text field and returns it as a string.
	 * 
	 * @return The user inputed text as a String.
	 */
	public String getInput() {
		return new String(inputField.getText());
	}

	/**
	 * Pops up a message dialog that displays the text from the parameter.
	 * 
	 * @param errMessage
	 *            The text to be displayed.
	 */
	public void showError(String errMessage) {
		JOptionPane.showMessageDialog(this, errMessage);
	}

	/**
	 * Adds the text from the parameter to the end of the text area.
	 * 
	 * @param out
	 *            The text to be added to the end of the text area.
	 */
	public void outputText(String out) {
		outputField.append(out);
		outputField.setCaretPosition(outputField.getDocument().getLength());
	}

	/**
	 * Replaces the action listener for the text field.
	 * 
	 * @param old
	 *            The action listener to be removed.
	 * @param current
	 *            The action listener to be added.
	 */
	public void setListener(ActionListener old, ActionListener current) {
		inputField.removeActionListener(old);
		inputField.addActionListener(current);
	}

	/**
	 * Clears the text from the text field.
	 */
	public void clearInputField() {
		inputField.setText(null);
	}
}
