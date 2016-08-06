
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Main {
	static float preValue, curValue, result,x;
	public static void main(String s[]) {
		JFrame frame = new JFrame("Aktieprogram åt leo");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel panel2 = new JPanel();

		JLabel lname = new JLabel("Name");
		JTextField name = new JTextField(24);
		
		JLabel lantal = new JLabel("Antal aktier");
		JTextField antal = new JTextField(24);
		
		JLabel lppa = new JLabel("Pris per aktie");
		JTextField ppa = new JTextField(24);
		
		JLabel lcourt = new JLabel("Courtage");
		String[] choices = { "0.069% - 69SEK            ","0.034% - 49 SEK            ", "0.15% - 39 SEK            ","0% - 0SEK            ","0.25% - 1 SEK            "};
		final JComboBox<String> court = new JComboBox<String>(choices);
		
		JLabel lworth = new JLabel("Nuvarande värde");
		JTextField worth = new JTextField(24);
		JTextArea resultbox = new JTextArea(12,40);
		worth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try {
					preValue = Integer.parseInt(antal.getText()) * Float.parseFloat(ppa.getText());
					curValue = Integer.parseInt(antal.getText()) * Float.parseFloat(worth.getText());
					float[] courtage = getCourtage(court.getSelectedIndex());
					x = (preValue + 2*courtage[0]) / Integer.parseInt(antal.getText());
					result = curValue - preValue - courtage[0] - courtage[1];
					
					StringBuilder sb = new StringBuilder();
					sb.append("Change: " + result + "\n");
					sb.append("Sell point to break even: ~" + x + "\n");
					resultbox.setText(sb.toString());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		name.setText("PDX");
		antal.setText("2200");
		ppa.setText("44.6");
		worth.setText("65");

		JButton button = new JButton();
		button.setText("Räkna ut $$$");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					preValue = Integer.parseInt(antal.getText()) * Float.parseFloat(ppa.getText());
					curValue = Integer.parseInt(antal.getText()) * Float.parseFloat(worth.getText());
					float[] courtage = getCourtage(court.getSelectedIndex());
					x = (preValue + 2*courtage[0]) / Integer.parseInt(antal.getText());
					result = curValue - preValue - courtage[0] - courtage[1];
					
					StringBuilder sb = new StringBuilder();
					sb.append("Change: " + result + "\n");
					sb.append("Sell point to break even: ~" + x + "\n");
					resultbox.setText(sb.toString());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		JButton button2 = new JButton();
		button2.setText("Copy to clipboard");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringSelection selection = new StringSelection("I bought " + antal.getText() + " " + name.getText() + " stocks at " + ppa.getText() + " each, Now worth approx. " + Float.parseFloat(worth.getText())*Integer.parseInt(antal.getText()) + "(" + Float.parseFloat(worth.getText()) + "). \n Profit: " + result );
			    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			    clipboard.setContents(selection, selection);
			}
		});
		
		panel2.add(name);
		panel2.add(lname);
		panel2.add(antal);
		panel2.add(lantal);
		panel2.add(ppa);
		panel2.add(lppa);
		panel2.add(court);
		panel2.add(lcourt);
		panel2.add(worth);
		panel2.add(lworth);
		panel2.add(resultbox);
		panel2.add(button2);
		mainPanel.add(panel2, BorderLayout.CENTER);
		mainPanel.add(button, BorderLayout.SOUTH);
		
		frame.add(mainPanel);
		frame.setSize(500, 430);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	//Returns two different courtage values. One[0] for when you buy, and one[1] for when you sell.
	private static float[] getCourtage(int index){
		float[] returnvalue = {0,0};
		switch(index){
		case 0:
			returnvalue[0] = (float) Math.max(69, preValue*0.00069);
			returnvalue[1] = (float) Math.max(69, curValue*0.00069);
			break;
		case 1:
			returnvalue[0] = (float) Math.max(49, preValue*0.00034);
			returnvalue[1] = (float) Math.max(49, curValue*0.00034);
			break;
		case 2:
			returnvalue[0] = (float) Math.max(39, preValue*0.0015);
			returnvalue[1] = (float) Math.max(39, curValue*0.0015);
			break;
		case 3:
			break;
		case 4:
			returnvalue[0] = (float) Math.max(1, preValue*0.0025);
			returnvalue[1] = (float) Math.max(1, curValue*0.0025);
			break;
		default:
			//Should never happen
			System.out.println(("No courtage selection"));
		}
		return returnvalue;
	}
}