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
import domain.Group;
import domain.Message;
import domain.User;
import exceptions.UserAlreadyInTheGroup;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Point;

@SuppressWarnings("serial")
public class ManageGroupNotAdminGUI extends JFrame {

	private User user;
	JList<String> ChatsList;
	DefaultListModel<String> usersListModel = new DefaultListModel<String>();
	DefaultListModel<String> groupMembersListModel = new DefaultListModel<String>();
	ArrayList<Chat> txatak ;
	private JList<String> usersList = new JList<String>();
	private JButton btnClose;
	private JScrollPane scrollPane;
	private JButton leaveGroupButton;
	private JList<String> groupMembersList = new JList<String>();
	private JScrollPane scrollPane_1;
	private JLabel groupMembers;
	private JLabel manageGroupLabel;
	
	public ManageGroupNotAdminGUI(User ownUser, Group group) {
		getContentPane().setForeground(Color.RED);
		getContentPane().setFont(new Font("Dialog", Font.PLAIN, 16));
		 
		user = ownUser;
		this.setSize(new Dimension(500, 700));
		
		BLFacade facade = MainGUI.getBusinessLogic();
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("ManageGroup"));
		getContentPane().setLayout(null);
		
		
		//Ekarri user guztiak
		ArrayList<User> users = new ArrayList<User>();
		users = facade.getUsers();
		//gehitu modelera user guztiak
		for(User u:users) {
			usersListModel.addElement(u.getUserName());
		}
		usersListModel.removeElement(ownUser);
		//Taldeko erabiltzaileen lista bete
		ArrayList<User> groupUsers = new ArrayList<User>();
		groupUsers = group.getPartaideak();
		for(User u : groupUsers) 
		{
			System.out.println(u.getUserName()); 
			groupMembersListModel.addElement(u.getUserName());
		}
		groupMembersListModel.removeElement(ownUser);
		
		groupMembers = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("GroupMembers"));
		groupMembers.setLocation(new Point(6, 0));
		groupMembers.setFont(new Font("Tahoma", Font.PLAIN, 18));
		groupMembers.setBounds(102, 116, 229, 35);
		getContentPane().add(groupMembers);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(102, 162, 200, 289);
		getContentPane().add(scrollPane_1);
		scrollPane_1.setViewportView(groupMembersList);
		groupMembersList.setBackground(Color.WHITE);
		groupMembersList.setFont(new Font("Tahoma", Font.PLAIN, 18));
		//groupMembersList.setModel(groupMembersListModel);
		groupMembersList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			}
		});
		groupMembersList.setModel(groupMembersListModel);
	

		
		leaveGroupButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LeaveGroup"));
		leaveGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				facade.leaveGroup(user, group);
				close();
			}
		});
		leaveGroupButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		leaveGroupButton.setBounds(35, 486, 172, 46);
		getContentPane().add(leaveGroupButton);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(593, 382, -174, -230);
		getContentPane().add(scrollPane);
		
		btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnClose.setBounds(217, 486, 190, 46);
		getContentPane().add(btnClose);
		
		manageGroupLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ManageGroup")); //$NON-NLS-1$ //$NON-NLS-2$
		manageGroupLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		manageGroupLabel.setBounds(113, 11, 218, 50);
		getContentPane().add(manageGroupLabel);
	}
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	private void refreshList(Group g) {
		this.groupMembersListModel.clear();
		for(User u : g.getPartaideak()) {
			this.groupMembersListModel.addElement(u.getUserName());
		}
	}
	private void close() {
		this.setVisible(false);
	}
}