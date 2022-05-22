package gui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;

import businessLogic.BLFacade;
import domain.Chat;
import domain.Message;
import domain.User;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Label;
import java.awt.Color;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class CreateChatGUI extends JFrame {
	private User user;
	private JTextField textMessages;
	private JTextField textUser;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CreateChatGUI(User ownUser) {
		getContentPane().setForeground(Color.RED);
		getContentPane().setFont(new Font("Dialog", Font.PLAIN, 16));
		 
		user = ownUser;
		this.setSize(new Dimension(552, 837));
		
		
		BLFacade facade = MainGUI.getBusinessLogic();
		DefaultListModel DLM = new DefaultListModel();
		String s;
		ArrayList<User> Difusers = facade.getUsers();
		Difusers.remove(ownUser);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(38, 72, 432, 377);
		getContentPane().add(scrollPane_1);
		JList Users = new JList();
		Users.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane_1.setViewportView(Users);
		if(Difusers!=null) {
		for(User u:Difusers) {
			if(!u.getUserName().equals(ownUser.getUserName())) {
				s=u.getUserName();
				DLM.addElement(s);
			}
		}
		}
		Users.setModel(DLM);
		
		JLabel ErrorLbl = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		ErrorLbl.setFont(new Font("Candara", Font.PLAIN, 16));
		ErrorLbl.setForeground(Color.RED);
		ErrorLbl.setBounds(28, 711, 541, 60);
		getContentPane().add(ErrorLbl);
		
		JButton CreateNewChat = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateNewChat")); //$NON-NLS-1$ //$NON-NLS-2$
		CreateNewChat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		CreateNewChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ErrorLbl.setText("");
				boolean b = true;
				boolean l = true;
				User u1 = null ;
				ArrayList<User> partekideak = new ArrayList<User>();
				if(!ownUser.getUserName().equals(Users.getSelectedValue())) {
				if(Users.getSelectedValue()!=null) {
					partekideak.add(ownUser);
					for(User u :Difusers) {
						if(u.getUserName()==Users.getSelectedValue()) {
							u1 = u;
							break;
						}
					}
					partekideak.add(u1);
					String s = new String(u1.getUserName() +" eta " +ownUser.getUserName() );
					String s1 = new String(ownUser.getUserName() +" eta " +u1.getUserName() );
					for(Chat c :user.getTxatak()) {
						if(s.equals(c.getChatIzena())||s1.equals(c.getChatIzena())){
							b=false;
							break;
						}
					}
					if(b)
					user.addChat(facade.CreateChat(s,partekideak));	
					else 
						ErrorLbl.setText(ResourceBundle.getBundle("Etiquetas").getString("ChatAlreadyCreated"));
					Users.clearSelection();
					}else
					 ErrorLbl.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectChat"));
				}else 
					ErrorLbl.setText("Can't Create a chat with yourself");
				Users.clearSelection();
			}
		});
		getContentPane().setLayout(null);
		CreateNewChat.setBounds(38, 599, 219, 48);
		getContentPane().add(CreateNewChat);
		
		JLabel lblUsers = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AllUsers")); //$NON-NLS-1$ //$NON-NLS-2$
		lblUsers.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUsers.setBounds(28, 26, 229, 35);
		getContentPane().add(lblUsers);
		
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SendMessageGUI gui =new SendMessageGUI(user);
				gui.setVisible(true);
				jButtonClose_actionPerformed(e);
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnClose.setBounds(188, 676, 140, 46);
		getContentPane().add(btnClose);
		
		textUser = new JTextField();
		textUser.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textUser.setText(/*ResourceBundle.getBundle("Etiquetas").getString("CreateChatGUI.textField.text")*/ " "); //$NON-NLS-1$ //$NON-NLS-2$
		textUser.setBounds(38, 528, 432, 60);
		getContentPane().add(textUser);
		textUser.setColumns(10);
		
		JLabel lbluserr = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SearchByUsername")); //$NON-NLS-1$ //$NON-NLS-2$
		lbluserr.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbluserr.setBounds(38, 476, 390, 35);
		getContentPane().add(lbluserr);
		
		JButton btnUsername = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Search")); //$NON-NLS-1$ //$NON-NLS-2$
		btnUsername.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Users.clearSelection();
				DefaultListModel DLM1 = new DefaultListModel(); 
				Users.setModel(DLM1);	
				String user = textUser.getText();
				for(User u : Difusers) {
					if(user==null || user==" ") {
						DLM1.addElement(u.getUserName());
					}else if (u.getUserName().contains(user)){
						DLM1.addElement(u.getUserName());
					}
				
				}
				Users.setModel(DLM1);	
			}
		});
		btnUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUsername.setBounds(267, 599, 203, 48);
		getContentPane().add(btnUsername);

	}
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
