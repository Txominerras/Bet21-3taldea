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

@SuppressWarnings("serial")
public class SendMessageGUI extends JFrame {

	private User user;
	private JTextField textField;
	JList<String> ChatsList;
	DefaultListModel DLM = new DefaultListModel();
	DefaultListModel DLM1 = new DefaultListModel();
	ArrayList<Chat> txatak ;
	
	public SendMessageGUI(User ownUser) {
		getContentPane().setForeground(Color.RED);
		getContentPane().setFont(new Font("Dialog", Font.PLAIN, 16));
		 
		user = ownUser;
		this.setSize(new Dimension(1018, 850));
		
		BLFacade facade = MainGUI.getBusinessLogic();
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("SendMessageGUI"));
		getContentPane().setLayout(null);
		String s;
		txatak = user.getTxatak();
		if(!txatak.isEmpty()) {
			for(Chat c:txatak) {
				s= c.getChatIzena();
				DLM.addElement(s);
			}
		} 

		JLabel chatsLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Chats")); //$NON-NLS-1$ //$NON-NLS-2$
		chatsLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		chatsLabel.setBounds(52, 44, 229, 35);
		getContentPane().add(chatsLabel);
		
		JButton CreateChat = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateChat")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-2$
		CreateChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateChatGUI gui =new CreateChatGUI(user);
				gui.setVisible(true);
				jButtonClose_actionPerformed(e);
			}
		});
		CreateChat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		CreateChat.setBounds(107, 664, 229, 46);
		getContentPane().add(CreateChat);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(52, 90, 395, 378);
		getContentPane().add(scrollPane_1);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(532, 90, 402, 378);
		getContentPane().add(scrollPane_2);
		

		JList MessagesList = new JList();
		MessagesList.setFont(new Font("Tahoma", Font.PLAIN, 14));
		DLM1 = new DefaultListModel();
		MessagesList.setModel(DLM1);
		scrollPane_2.setViewportView(MessagesList);
		
		JList<String> chatsList = new JList();
		scrollPane_1.setViewportView(chatsList);
		chatsList.setBackground(Color.WHITE);
		chatsList.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chatsList.setModel(DLM);
		chatsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				Chat SelectedChat= new Chat();
				DLM1 = new DefaultListModel();
				MessagesList.setModel(DLM1);
				for(Chat c : txatak) {
					if(c.getChatIzena().equals(chatsList.getSelectedValue())) {
						SelectedChat = c;
						break;
					}
				}
				if(SelectedChat!=null) {
					ArrayList<Message> Mezuak =SelectedChat.getMezuak();
					if(Mezuak!=null) {
						for (Message m : Mezuak)
							DLM1.addElement(m.getIgorlea().getUserName() + " : " + m.getTextua()+ "    " +m.getData());
					}
				}
				MessagesList.setModel(DLM1);
			}
		});
		
		JLabel lblError = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblError.setForeground(Color.RED);
		lblError.setBounds(381, 508, 482, 35);
		getContentPane().add(lblError);
	

		
		JButton SendMessage = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SendMessage")); //$NON-NLS-1$ //$NON-NLS-2$
		SendMessage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		SendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int posizioa =0;
				Date actualdate = new Date();
				if(chatsList.getSelectedValue()!=null) {
				if(textField.getText()!=" " || textField.getText()!="") {
					Chat Txat = null ;
					for(Chat c : user.getTxatak()) {
						if(chatsList.getSelectedValue() == c.getChatIzena()) {
							Txat=c;
							break;
						}
					}
					facade.SendMessage(ownUser,Txat,textField.getText(), actualdate);
					Message Mezua = new Message(ownUser,actualdate,textField.getText(), Txat);							
					user.getTxatak().get(chatsList.getSelectedIndex()).AddMessage(Mezua);
					DLM1.addElement(Mezua.getIgorlea().getUserName() + " : " + Mezua.getTextua()+ "    " +Mezua.getData());
					MessagesList.setModel(DLM1);
					}else {
						lblError.setText("Message is empty");
					}
				}else {
					lblError.setText("Need to select a Chat");
				}
			}
		});
		
		SendMessage.setBounds(610, 664, 229, 46);
		getContentPane().add(SendMessage);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(593, 382, -174, -230);
		getContentPane().add(scrollPane);
		
		textField = new JTextField();
		textField.setText(""); //$NON-NLS-1$ //$NON-NLS-2$
		textField.setBounds(107, 554, 732, 79);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel messageTextLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Write")); //$NON-NLS-1$ //$NON-NLS-2$
		messageTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		messageTextLabel.setBounds(107, 516, 625, 27);
		getContentPane().add(messageTextLabel);
		
		JLabel chatMessagesLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Messages")); //$NON-NLS-1$ //$NON-NLS-2$
		chatMessagesLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		chatMessagesLabel.setBounds(545, 44, 229, 35);
		getContentPane().add(chatMessagesLabel);
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnClose.setBounds(380, 666, 190, 46);
		getContentPane().add(btnClose);
		
		JButton createGroupButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateGroup")); //$NON-NLS-1$ //$NON-NLS-2$
		createGroupButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		createGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateGroupGUI cg = new CreateGroupGUI(user);
				cg.setVisible(true);
				close();
			}
		});
		createGroupButton.setBounds(166, 745, 312, 46);
		getContentPane().add(createGroupButton);
		
		JButton managerGroupButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ManageGroup")); //$NON-NLS-1$ //$NON-NLS-2$
		managerGroupButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		managerGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String chatIzena = chatsList.getSelectedValue();
				
				if(DLM.getSize()>0) {
				Chat chat = null;
				
				for(Chat c : txatak) {
					if(chatIzena.equals(c.getChatIzena())) {
						chat = c;
					}
				}
				System.out.println(chat.getClass().getSimpleName());
				if(chat!=null && chat.getClass().getSimpleName().equals("Group")) 
				{
					Group g = (Group) chat;
					if(g.getGroupAdmin().equals(user.getUserName()))
					{
						ManageGroupGUI mg = new ManageGroupGUI(user,g);
						mg.setVisible(true);
						close();
					}
					else {
						ManageGroupNotAdminGUI mg = new ManageGroupNotAdminGUI(user,g);
						mg.setVisible(true);
						close();
					}
				}
				else {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectAGroup"));

				}
			}
				else {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectAGroup"));
				}
			}
		});
		managerGroupButton.setBounds(499, 745, 312, 46);
		getContentPane().add(managerGroupButton);
		//managerGroupButton.setVisible(false);
		
		
	}
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	private void close() {
		this.setVisible(false);
	}
}
