package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import businessLogic.BLFacade;
import domain.User;
import exceptions.AgeException;
import exceptions.EmailUsed;
import exceptions.IsEmpty;
import exceptions.PhoneNumberUsed;
import exceptions.UserNameAlreadyUsed;
import exceptions.WrongPassword;

import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;

public class RegisterGUI extends JFrame {
	private JTextField userNameField;
	private JTextField passField;
	private JTextField ageField;
	private JTextField emailField;
	private JTextField phoneField;
	private JTextField realNameField;
	private JTextField surnameField;
	private JLabel lblNewLabel=new JLabel(ResourceBundle.getBundle("Etiquetas").getString("UserName"));
	private JLabel lblPassword=new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password"));
	private JLabel lblAge=new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Age"));
	private JLabel lblEmail=new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Email"));
	private JLabel lblRealName=new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Name"));
	private JLabel lblSurname=new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Surname"));
	private JLabel lblPhoneNumber=new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PhoneNumber"));
	private JTextArea textArea=new JTextArea("");
	private final JLabel userNameErrorLabel = new JLabel();
	private final JLabel passErrorLabel = new JLabel(); 
	private final JLabel ageErrorLabel = new JLabel(); 
	private final JLabel emailErrorLabel = new JLabel(); 
	private final JLabel phoneErrorLabel = new JLabel(); 
	private final JLabel nameErrorLabel = new JLabel();
	private final JLabel surnameErrorLabel = new JLabel();
	private JButton btnCreateAccount;
	private final JLabel resgisterTitle = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Register")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JButton closeButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	
	public RegisterGUI() {
		getContentPane().setLayout(null);
		this.setSize(new Dimension(800, 700));
		
		BLFacade facade = MainGUI.getBusinessLogic();
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Register"));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		//lblNewLabel = new JLabel("User Name");
		lblNewLabel.setBounds(34, 73, 259, 38);
		getContentPane().add(lblNewLabel);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		//lblPassword = new JLabel("Password");
		lblPassword.setBounds(34, 135, 259, 42);
		getContentPane().add(lblPassword);
		
		userNameField = new JTextField();
		userNameField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		userNameField.setBounds(327, 69, 421, 42);
		getContentPane().add(userNameField);
		userNameField.setColumns(10);
		
		passField = new JTextField();
		passField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		passField.setBounds(327, 135, 421, 42);
		getContentPane().add(passField);
		passField.setColumns(10);
		lblAge.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		//lblAge = new JLabel("Age");
		lblAge.setBounds(34, 198, 259, 42);
		getContentPane().add(lblAge);
		
		ageField = new JTextField();
		ageField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ageField.setBounds(327, 198, 421, 42);
		getContentPane().add(ageField);
		ageField.setColumns(10);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		//lblEmail = new JLabel("Email");
		lblEmail.setBounds(34, 256, 259, 42);
		getContentPane().add(lblEmail);
		
		emailField = new JTextField();
		emailField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		emailField.setBounds(327, 256, 421, 42);
		getContentPane().add(emailField);
		emailField.setColumns(10);
		lblRealName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		//lblRealName = new JLabel("Real Name");
		lblRealName.setBounds(34, 362, 259, 38);
		getContentPane().add(lblRealName);
		lblSurname.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		//lblSurname = new JLabel("Surname");
		lblSurname.setBounds(34, 411, 259, 38);
		getContentPane().add(lblSurname);
		lblPhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		//lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setBounds(34, 313, 259, 38);
		getContentPane().add(lblPhoneNumber);
		
		phoneField = new JTextField();
		phoneField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		phoneField.setBounds(327, 309, 421, 38);
		getContentPane().add(phoneField);
		phoneField.setColumns(10);
		
		realNameField = new JTextField();
		realNameField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		realNameField.setBounds(327, 362, 421, 38);
		getContentPane().add(realNameField);
		realNameField.setColumns(10);
		
		surnameField = new JTextField();
		surnameField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		surnameField.setBounds(327, 411, 421, 38);
		getContentPane().add(surnameField);
		surnameField.setColumns(10);
		
		btnCreateAccount = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateAccount"));
		btnCreateAccount.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
				String userName = userNameField.getText();
				
				String pass = passField.getText();
				String age = ageField.getText();
				int a=-1;
				if(!age.equals(""))
					a = Integer.parseInt(age);
				
				String email = emailField.getText();
				String phone = phoneField.getText();
				int p=-1;
				if(!phone.equals(""))
					p = Integer.parseInt(phone);
				
				String realName = realNameField.getText();
				String surname = surnameField.getText();
				
				User r = facade.register(userName,pass,email,p,a,realName,surname);
				textArea.setText("");
				textArea.setVisible(true);
				textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("Created"));
			
				}catch(IsEmpty e1) {
					textArea.setText("");
					textArea.setVisible(true);
					textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("IsEmpty"));
				}catch(UserNameAlreadyUsed e2) {
					textArea.setText("");
					textArea.setVisible(true);
					textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("IncorrectUserName"));
				}catch(WrongPassword e3) {
					textArea.setText("");
					textArea.setVisible(true);
					textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("NotValidPassword"));
				}
				catch(PhoneNumberUsed e4) {
					textArea.setText("");
					textArea.setVisible(true);
					textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("PhoneNumberAlreadyUsed"));
				}
				catch(EmailUsed e5) {
					textArea.setText("");
					textArea.setVisible(true);
					textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("EmailAlreadyUsed"));
				}
				catch(AgeException e6) {
					textArea.setText("");
					textArea.setVisible(true);
					textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("NotValidAge"));
				}
			}
		});
		btnCreateAccount.setBounds(34, 498, 259, 53);
		getContentPane().add(btnCreateAccount);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 34));
		
		//textArea = new JTextArea();
		textArea.setVisible(false);
	//	textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.textArea.text")); //$NON-NLS-1$ //$NON-NLS-2$
		textArea.setBounds(327, 494, 402, 126);
		getContentPane().add(textArea);
		userNameErrorLabel.setBounds(414, 73, 46, 14);
		
		getContentPane().add(userNameErrorLabel);
		passErrorLabel.setBounds(414, 113, 46, 14);
		
		getContentPane().add(passErrorLabel);
		ageErrorLabel.setBounds(414, 153, 46, 14);
		
		getContentPane().add(ageErrorLabel);
		emailErrorLabel.setBounds(414, 192, 46, 14);
		
		getContentPane().add(emailErrorLabel);
		phoneErrorLabel.setBounds(414, 231, 46, 14);
		
		getContentPane().add(phoneErrorLabel);
		nameErrorLabel.setBounds(414, 271, 46, 14);
		
		getContentPane().add(nameErrorLabel);
		surnameErrorLabel.setBounds(414, 313, 46, 14);
		
		getContentPane().add(surnameErrorLabel);
		resgisterTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
		resgisterTitle.setBounds(274, 11, 272, 51);
		
		getContentPane().add(resgisterTitle);
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		closeButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		closeButton.setBounds(34, 567, 259, 53);
		
		getContentPane().add(closeButton);
	}
	private void close() {
		this.setVisible(false);
	}
}
