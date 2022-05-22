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
import exceptions.GroupAlreadyCreated;
import exceptions.NotEnoughUsers;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Label;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class CreateGroupGUI extends JFrame {
	private User user;
	private JTextField textMessages;
	private JTextField textUser;
	private JTextField groupNameField;
	DefaultListModel DLM2 = new DefaultListModel();
	private JButton quitButton;
	private JButton selectButton;
	private JList selectedUsers;
	private JButton searchButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Search"));
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CreateGroupGUI(User ownUser) {
		getContentPane().setForeground(Color.RED);
		getContentPane().setFont(new Font("Dialog", Font.PLAIN, 16));
		 
		user = ownUser;
		this.setSize(new Dimension(552, 1000));
		
		
		BLFacade facade = MainGUI.getBusinessLogic();
		DefaultListModel DLM = new DefaultListModel();
		String s;
		ArrayList<User> difusers = facade.getUsers();
		difusers.remove(ownUser);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(47, 215, 187, 360);
		getContentPane().add(scrollPane_1);
		JList users = new JList();
		users.setFont(new Font("Tahoma", Font.PLAIN, 18));
		users.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String userName = (String)users.getSelectedValue();
			}
		});
		scrollPane_1.setViewportView(users);
		users.setModel(DLM);
		if(difusers!=null) {
			for(User u:difusers) {
				if(!u.getUserName().equals(ownUser.getUserName())) {
					s=u.getUserName();
					DLM.addElement(s);
				}
			}
		}
		JLabel ErrorLbl = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		ErrorLbl.setFont(new Font("Candara", Font.PLAIN, 18));
		ErrorLbl.setForeground(Color.RED);
		ErrorLbl.setBounds(10, 901, 541, 60);
		getContentPane().add(ErrorLbl);
		
		JButton CreateNewGroup = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateNewGroup")); //$NON-NLS-1$ //$NON-NLS-2$
		CreateNewGroup.setFont(new Font("Tahoma", Font.PLAIN, 18));
		CreateNewGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ErrorLbl.setText("");
				ArrayList<String> usersToGroup=new ArrayList<String>();
				String groupName = groupNameField.getText();
				for(int i = 0; i<DLM2.getSize(); i++) {
					usersToGroup.add((String) DLM2.get(i));
				}
				usersToGroup.add(ownUser.getUserName());
				
				try {
					User user = facade.createGroup(ownUser.getUserName(), usersToGroup, groupName);
					ownUser.setTxatak(user.getTxatak());
					SendMessageGUI sm = new SendMessageGUI(ownUser);
					sm.setVisible(true);
					close();
				}catch(NotEnoughUsers e2) {
					ErrorLbl.setText(ResourceBundle.getBundle("Etiquetas").getString("NotEnoughUsersForGroup"));
				}catch(GroupAlreadyCreated e3) {
					ErrorLbl.setText(ResourceBundle.getBundle("Etiquetas").getString("NotEnoughUsersForGroup"));
				}
			}
		});
		getContentPane().setLayout(null);
		CreateNewGroup.setBounds(39, 767, 248, 48);
		getContentPane().add(CreateNewGroup);
		
		JLabel lblUsers = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AllUsers")); //$NON-NLS-1$ //$NON-NLS-2$
		lblUsers.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUsers.setBounds(47, 169, 229, 35);
		getContentPane().add(lblUsers);
		
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SendMessageGUI gui =new SendMessageGUI(user);
				gui.setVisible(true);
				jButtonClose_actionPerformed(e);
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnClose.setBounds(173, 844, 140, 46);
		getContentPane().add(btnClose);
		
		textUser = new JTextField();
		textUser.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textUser.setText(/*ResourceBundle.getBundle("Etiquetas").getString("CreateChatGUI.textField.text")*/ " "); //$NON-NLS-1$ //$NON-NLS-2$
		textUser.setBounds(61, 682, 407, 60);
		getContentPane().add(textUser);
		textUser.setColumns(10);
		
		JLabel lbluserr = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SearchByUsername")); //$NON-NLS-1$ //$NON-NLS-2$
		lbluserr.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbluserr.setBounds(47, 636, 390, 35);
		getContentPane().add(lbluserr);
		
		
		JScrollPane scrollPane_1_1 = new JScrollPane();
		scrollPane_1_1.setBounds(323, 215, 186, 360);
		getContentPane().add(scrollPane_1_1);
		
		selectedUsers = new JList();
		selectedUsers.setFont(new Font("Tahoma", Font.PLAIN, 18));
		selectedUsers.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				
			}
		});
		scrollPane_1_1.setViewportView(selectedUsers);
		
		selectButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Select"));
		selectButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		selectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userName = (String) users.getSelectedValue();
				if(userName!=null && !DLM2.contains(userName)) {
					DLM2.addElement(userName);
				}
				selectedUsers.setModel(DLM2);
			}
		});
		selectButton.setBounds(75, 586, 134, 39);
		getContentPane().add(selectButton);
		
		groupNameField = new JTextField();
		groupNameField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		groupNameField.setBounds(202, 112, 307, 46);
		getContentPane().add(groupNameField);
		groupNameField.setColumns(10);

		
		
		quitButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Quit"));
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		quitButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		quitButton.setBounds(351, 586, 117, 39);
		getContentPane().add(quitButton);
		
		JLabel groupNameLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("GroupName"));
		groupNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		groupNameLabel.setBounds(47, 125, 187, 21);
		getContentPane().add(groupNameLabel);
		
		JLabel cerateGroupLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateGroup"));
		cerateGroupLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		cerateGroupLabel.setBounds(193, 11, 219, 46);
		getContentPane().add(cerateGroupLabel);
		
		JLabel membersLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("GroupMembers"));
		membersLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		membersLabel.setBounds(297, 169, 229, 35);
		getContentPane().add(membersLabel);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				users.clearSelection();
				DefaultListModel DLM1 = new DefaultListModel(); 
				users.setModel(DLM1);	
				String user = textUser.getText();
				for(User u : difusers) {
					if(user==null || user==" ") {
						DLM1.addElement(u.getUserName());
					}else if (u.getUserName().contains(user)){
						DLM1.addElement(u.getUserName());
					}
				}
				users.setModel(DLM1);
			}
		});
		
		searchButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		searchButton.setBounds(297, 767, 170, 48);
		getContentPane().add(searchButton);
	}
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	private void close() {
		this.setVisible(false);
	}
}
