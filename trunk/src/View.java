/**
 * @author Ben Trivett
 *
 */

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

public class View extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private static final Integer DEFAULT_CONSOLE_WIDTH = 80;
	private static final Integer DEFAULT_CONSOLE_HEIGHT = 40;
	private static JTextField inputField;
	private static JTextArea outputField;
	
	public View(){
		this.resetView();
	}
	
	public void resetView(){
		// Set up display objects.
		inputField = new JTextField(DEFAULT_CONSOLE_WIDTH);
		outputField = new JTextArea(DEFAULT_CONSOLE_HEIGHT, DEFAULT_CONSOLE_WIDTH);
		outputField.setEditable(false); // Default is editable
		JScrollPane consolePane = new JScrollPane(outputField);
		
		// Put display objects in a container and lay them out.
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		content.add(inputField, BorderLayout.NORTH);
		content.add(consolePane, BorderLayout.SOUTH);

		// Finalize layout by adding content and title.
		this.setContentPane(content);
		this.pack();
		this.setTitle("Wi-11 Machine");

		// Set the window closing event.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public String getInput() {
		return new String(inputField.getText());
	}
	
	public void showError(String errMessage) {
		JOptionPane.showMessageDialog(this, errMessage);
	}
	
	public void outputText(String out){
		outputField.append(out);
	}
	
	public void setListener(ActionListener old, ActionListener current) {
		inputField.removeActionListener(old);
		inputField.addActionListener(current);
	}
	
	public void clearInputField(){
		inputField.setText(null);
	}
}
