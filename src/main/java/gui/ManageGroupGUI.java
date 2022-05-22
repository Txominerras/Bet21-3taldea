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
public class ManageGroupGUI extends JFrame {

	private User user;
	JList<String> ChatsList;
	DefaultListModel<String> usersListModel = new DefaultListModel<String>();
	DefaultListModel<String> groupMembersListModel = new DefaultListModel<String>();
	ArrayList<Chat> txatak ;
	private JList<String> usersList = new JList<String>();
	private JButton addButton;
	private JScrollPane scrollPane_1_1;
	private JLabel peopleToAddLabel;
	private JButton expelButton;
	private JButton btnClose;
	private JScrollPane scrollPane;
	private JButton leaveGroupButton;
	private JList<String> groupMembersList = new JList<String>();
	private JScrollPane scrollPane_1;
	private JButton deleteGroupButton;
	private JLabel groupMembers;
	private JLabel title= new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ManageGroup"));
	private JLabel lblNewLabel;
	
	public ManageGroupGUI(User ownUser, Group group) {
		getContentPane().setForeground(Color.RED);
		getContentPane().setFont(new Font("Dialog", Font.PLAIN, 16));
		 
		user = ownUser;
		this.setSize(new Dimension(700, 800));
		
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
		usersListModel.removeElement(ownUser.getUserName());
		
		//Taldeko erabiltzaileen lista bete
		ArrayList<User> groupUsers = new ArrayList<User>();
		groupUsers = group.getPartaideak();
		for(User u : groupUsers) 
		{
			System.out.println(u.getUserName()); 
			groupMembersListModel.addElement(u.getUserName());
		}
		groupMembersListModel.removeElement(ownUser.getUserName());

		groupMembers = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("GroupMembers"));
		groupMembers.setLocation(new Point(6, 0));
		groupMembers.setFont(new Font("Tahoma", Font.PLAIN, 18));
		groupMembers.setBounds(413, 140, 200, 35);
		getContentPane().add(groupMembers);
		
		deleteGroupButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DeleteGroup"));
		deleteGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User u = facade.deleteGroup(user, group);
				ownUser.setTxatak(u.getTxatak());
				close();
			}
		});
		deleteGroupButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		deleteGroupButton.setBounds(48, 673, 172, 46);
		getContentPane().add(deleteGroupButton);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(413, 186, 200, 289);
		getContentPane().add(scrollPane_1);
		//groupMembersListModel = new DefaultListModel();
		
		//groupMembersList = new JList();
		scrollPane_1.setViewportView(groupMembersList);
		groupMembersList.setBackground(Color.WHITE);
		groupMembersList.setFont(new Font("Tahoma", Font.PLAIN, 18));
		//groupMembersList.setModel(groupMembersListModel);
		groupMembersList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				
			}
		});
			
		leaveGroupButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LeaveGroup"));
		leaveGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Group g = facade.leaveGroup(user, group);
				group.setPartaideak(g.getPartaideak());
				ownUser.removeGroup(group);
				SendMessageGUI sm = new SendMessageGUI(ownUser);
				sm.setVisible(true);
				close();
			}
		});
		leaveGroupButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		leaveGroupButton.setBounds(230, 673, 172, 46);
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
		btnClose.setBounds(423, 673, 190, 46);
		getContentPane().add(btnClose);
		
		expelButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Expel"));
		expelButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		expelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String userName = groupMembersList.getSelectedValue();
				Group g=null;
				if(userName!=null) {
					g = facade.quickFromTheGroup(userName,group);
				}
				refreshList(g,ownUser);
				group.setPartaideak(g.getPartaideak());
			}
		});
		expelButton.setBounds(413, 486, 200, 46);
		getContentPane().add(expelButton);
		
		peopleToAddLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AllUsers"));
		peopleToAddLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		peopleToAddLabel.setBounds(60, 140, 253, 35);
		getContentPane().add(peopleToAddLabel);
		
		scrollPane_1_1 = new JScrollPane();
		scrollPane_1_1.setBounds(48, 186, 200, 289);
		getContentPane().add(scrollPane_1_1);
		
		usersList = new JList();
		usersList.setFont(new Font("Tahoma", Font.PLAIN, 18));
		usersList.setBackground(Color.WHITE);
		scrollPane_1_1.setViewportView(usersList);
		
		addButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Add"));
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String userName = usersList.getSelectedValue();
				if(userName!=null) {
					try {
						Group g = facade.addUserToGroup(userName, group);
						groupMembersListModel.addElement(userName);
						group.setPartaideak(g.getPartaideak());
					} catch (UserAlreadyInTheGroup e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		addButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addButton.setBounds(48, 486, 200, 46);
		getContentPane().add(addButton);
		
		usersList.setModel(usersListModel);
		groupMembersList.setModel(groupMembersListModel);
		
		title = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ManageGroup")); //$NON-NLS-1$ //$NON-NLS-2$
		title.setFont(new Font("Tahoma", Font.BOLD, 18));
		title.setBounds(253, 23, 166, 61);
		getContentPane().add(title);
		
		lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ManageGroupGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(48, 583, 565, 35);
		getContentPane().add(lblNewLabel);
	}
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	private void refreshList(Group g, User ownUser) {
		this.groupMembersListModel.clear();
		for(User u : g.getPartaideak()) {
			this.groupMembersListModel.addElement(u.getUserName());
		}
		this.groupMembersListModel.removeElement(ownUser.getUserName());
	}
	private void close() {
		this.setVisible(false);
	}
}
