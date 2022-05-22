package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Admin;
import domain.NormalUser;
import domain.User;
import exceptions.IsEmpty;
import exceptions.UserNameAlreadyUsed;
import exceptions.UserNotExist;
import exceptions.WrongPassword;
import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.FlowLayout;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class LoginGUI extends JFrame {

	private JPasswordField passwordField;
	private JTextField userNameField;
	private JLabel lblNewLabel=new JLabel(ResourceBundle.getBundle("Etiquetas").getString("UserName"));
	private JLabel lblNewLabel_1=new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password"));
	private JLabel lblNewLabel_2=new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Login"));
	private JButton loginButton=new JButton(ResourceBundle.getBundle("Etiquetas").getString("Login"));
	private JLabel lblSartuDatuakBerriro=new JLabel();
	private final JButton closeButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	public LoginGUI() {
		
		getContentPane().setLayout(null);
		this.setSize(new Dimension(600, 450));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Login"));
		BLFacade facade = MainGUI.getBusinessLogic();
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Calisto MT", Font.PLAIN, 18));
		passwordField.setBounds(276, 156, 285, 46);
		getContentPane().add(passwordField);
		
		userNameField = new JTextField();
		userNameField.setBackground(Color.WHITE);
		userNameField.setFont(new Font("Calisto MT", Font.PLAIN, 18));
		userNameField.setBounds(276, 88, 285, 46);
		getContentPane().add(userNameField);
		userNameField.setColumns(10);
		lblNewLabel.setFont(new Font("Calisto MT", Font.PLAIN, 18));
		
		//lblNewLabel = new JLabel("User Name:");
		lblNewLabel.setBounds(33, 86, 225, 46);
		getContentPane().add(lblNewLabel);
		lblNewLabel_1.setFont(new Font("Calisto MT", Font.PLAIN, 18));
		
		//lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(33, 156, 225, 46);
		getContentPane().add(lblNewLabel_1);
		lblNewLabel_2.setBackground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Calisto MT", Font.BOLD, 18));
		
		lblNewLabel_2.setBounds(197, 11, 276, 52);
		getContentPane().add(lblNewLabel_2);
		loginButton.setForeground(Color.BLACK);
		loginButton.setBackground(Color.WHITE);
		loginButton.setFont(new Font("Calisto MT", Font.PLAIN, 18));
		
		//loginButton = new JButton("Login");
		loginButton.setBounds(33, 263, 248, 52);
		getContentPane().add(loginButton);
		lblSartuDatuakBerriro.setForeground(Color.RED);
		lblSartuDatuakBerriro.setFont(new Font("Calisto MT", Font.PLAIN, 18));
		
		//lblSartuDatuakBerriro = new JLabel("");
		lblSartuDatuakBerriro.setBounds(33, 337, 522, 51);
		getContentPane().add(lblSartuDatuakBerriro);
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		closeButton.setForeground(Color.BLACK);
		closeButton.setFont(new Font("Calisto MT", Font.PLAIN, 18));
		closeButton.setBackground(Color.WHITE);
		closeButton.setBounds(291, 263, 248, 52);
		
		getContentPane().add(closeButton);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try{String u = userNameField.getText();
				String p = passwordField.getText();
				User e = facade.login(u, p);
				//if(e.getAdmin()) {
				System.out.println(e.getClass().getTypeName());
				if(e.getClass().getTypeName().equals("domain.Admin")) {
					Admin user = (Admin) e;
					AdminBetGUI a = new AdminBetGUI (user);
					a.setVisible(true);
				}
				else {
					UserBetGUI a = new UserBetGUI((NormalUser) e);
					a.setVisible(true);
				}
				}catch(IsEmpty e1) {
					lblSartuDatuakBerriro.setText(ResourceBundle.getBundle("Etiquetas").getString("IsEmpty"));
				}
				catch(UserNameAlreadyUsed e2) {
					lblSartuDatuakBerriro.setText(ResourceBundle.getBundle("Etiquetas").getString("UserNameNotExist"));
				}
				catch(WrongPassword e3) {
					lblSartuDatuakBerriro.setText(ResourceBundle.getBundle("Etiquetas").getString("WrongPassword"));
				}
				catch(UserNotExist e4) {
					lblSartuDatuakBerriro.setText(ResourceBundle.getBundle("Etiquetas").getString("UserNameNotExist"));
				}
			}
		});
	}
	private void close() {
		this.setVisible(false);
	}
}
