import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Component.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.InputMethodListener;
import java.util.ArrayList;
import java.awt.event.InputMethodEvent;

public class ViewGUI extends JFrame {

	private JPanel contentPane;


	public ViewGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 470, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		
		JFormattedTextField formattedTextField_1 = new JFormattedTextField();
		formattedTextField_1.setText("1");
		formattedTextField_1.setName("R1C1");
		formattedTextField_1.setBounds(27, 28, 22, 19);
		contentPane.add(formattedTextField_1);
		
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setText("2");
		formattedTextField.setName("R1C2");
		formattedTextField.setBounds(50, 28, 22, 19);
		contentPane.add(formattedTextField);
		
		JFormattedTextField formattedTextField_2 = new JFormattedTextField();
		formattedTextField_2.setText("3");
		formattedTextField_2.setName("R1C3");
		formattedTextField_2.setBounds(71, 28, 22, 19);
		contentPane.add(formattedTextField_2);
	}
}
