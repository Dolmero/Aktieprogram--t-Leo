import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
okay
public class Main {
	static float preValue, curValue, result, x;
	static JTextArea resultbox = new JTextArea(12,35);
	static JTextField name = new JTextField(24);
	static JTextField antal = new JTextField();
	static JTextField ppa = new JTextField(24);
	static JComboBox<String> court;
	static JTextField worth = new JTextField(24);
	public static void main(String s[]) {
		JFrame frame = new JFrame("Aktieprogram åt leo");
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		JLabel lname = new JLabel("Name");
		mainPanel.add(lname, constraints);
		constraints.gridx = 1;
		mainPanel.add(name, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		JLabel lantal = new JLabel("Antal aktier");
		mainPanel.add(lantal, constraints);
		constraints.gridx = 1;
		mainPanel.add(antal, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		JLabel lppa = new JLabel("Pris per aktie");
		mainPanel.add(lppa, constraints);
		constraints.gridx = 1;
		mainPanel.add(ppa, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		JLabel lcourt = new JLabel("Courtage");
		mainPanel.add(lcourt, constraints);
		String[] choices = { "0.069% - 69SEK            ","0.034% - 49 SEK            ", "0.15% - 39 SEK            ","0% - 0SEK            ","0.25% - 1 SEK            "};
		court = new JComboBox<String>(choices);
		constraints.gridx = 1;
		mainPanel.add(court, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 4;
		JLabel lworth = new JLabel("Nuvarande värde");
		mainPanel.add(lworth, constraints);
		constraints.gridx = 1;
		mainPanel.add(worth, constraints);
		
		constraints.gridwidth = 2;
		constraints.gridx = 0;
		constraints.gridy = 5;
		mainPanel.add(resultbox,constraints);

		constraints.gridx = 0;
		constraints.gridy = 6;
		JButton button2 = new JButton();
		button2.setText("Copy to clipboard");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringSelection selection = new StringSelection("I bought " + antal.getText() + " " + name.getText() + " stocks at " + ppa.getText() + " each, Now worth approx. " + Float.parseFloat(worth.getText())*Integer.parseInt(antal.getText()) + "(" + Float.parseFloat(worth.getText()) + "). \n Profit: " + result );
			    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			    clipboard.setContents(selection, selection);
			}
		});
		constraints.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(button2, constraints);
		name.setText("PDX");
		antal.setText("2200");
		ppa.setText("44.6");
		worth.setText("65");
		frame.add(mainPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		UpdateThread updates = new UpdateThread();
		updates.start();
	}
	
	/**
	 * Gets the user input.
	 * @return
	 * [0] number of stocks
	 * [1] price per stock
	 * [2] courtage selection index
	 * [3] current price
	 */
	public static returnValues getvalues(){
		returnValues ret = null;
		try{
			ret = new returnValues(Integer.parseInt(antal.getText()), Float.parseFloat(ppa.getText()), court.getSelectedIndex(), Float.parseFloat(worth.getText()));
		}catch(Exception e){
			System.out.println("Can't convert one of the strings, but hey lets not crash");
		}
		return ret;
	}
	
	public static void setText(String txt, float profit){
		result = profit;
		resultbox.setText(txt);
	}
}