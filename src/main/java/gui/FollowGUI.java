package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Bet;
import domain.Follow;
import domain.NormalUser;
import domain.Transaction;
import domain.User;
import exceptions.IsEmpty;
import exceptions.NoValidMoneyException;
import exceptions.NonPositiveNum;
import exceptions.NotEnoughMoneyException;
import exceptions.UserNameAlreadyUsed;
import exceptions.UserNotExist;
import exceptions.WrongPassword;
import exceptions.invalidPercentage;
import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import java.awt.Font;

public class FollowGUI extends JFrame {
	private JLabel lblSartuDatuakBerriro=new JLabel();
	private JScrollPane scrollPane;
	private JList<NormalUser> transactionList = null;
	private DefaultListModel<NormalUser> transactionInfo = new DefaultListModel<NormalUser>();
	private JScrollPane scrollPane_1;
	private List<NormalUser> transactions;
	private JTextField days;
	private JTextField money;
	private JTextField percentage;
	private List<NormalUser> follows;
	private DefaultListModel<NormalUser> followsInfo = new DefaultListModel<NormalUser>();
	private JTextArea textArea=new JTextArea("");
	private JList<NormalUser> followList;
	private DefaultListModel<String> moneyModel = new DefaultListModel<String>();
	private JList<String> moneyList = new JList<String>();
	
	public FollowGUI(NormalUser user) {
		
		getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Follows"));
		BLFacade facade = MainGUI.getBusinessLogic();
		
		JLabel titleBet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Follows"));
		titleBet.setFont(new Font("Tahoma", Font.PLAIN, 16));
		titleBet.setBounds(189, 11, 285, 27);
		getContentPane().add(titleBet);
		
		//lblSartuDatuakBerriro = new JLabel("");
		lblSartuDatuakBerriro.setBounds(146, 387, 165, 16);
		getContentPane().add(lblSartuDatuakBerriro);
		//userdb = facade.getUserFromDB(user); 
		transactions = facade.getNormalUsersRankings(user);
		for(NormalUser u : transactions) {
			if(!u.getUserName().equals(user.getUserName())) {
			transactionInfo.addElement(u);
			}
			
		}
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(new Rectangle(34, 49, 178, 231));
		scrollPane_1.setViewportView(transactionList);
		getContentPane().add(scrollPane_1);
		
		transactionList = new JList<NormalUser>();
		scrollPane_1.setViewportView(transactionList);
		transactionList.setModel(transactionInfo);
		JButton addMoneyButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Confirm")); //$NON-NLS-1$ //$NON-NLS-2$
        addMoneyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username= transactionList.getSelectedValue().getUserName();
				
                
				
				String u2 = money.getText();
				double moneyforbet=0;
				if(!u2.isEmpty())
					moneyforbet = Double.parseDouble(u2);
				
				
				String u3 = percentage.getText();
				double percentageforbet=0;
				if(!u3.isEmpty())
					percentageforbet = Double.parseDouble(u3);
                
                
                try {
                NormalUser us =	facade.follow(username, user,   moneyforbet,  percentageforbet);
                
                System.out.println(us.getUserName());
                
                transactionInfo.clear(); 
                transactions = facade.getNormalUsersRankings(us);
            	for(NormalUser u : transactions) {
            		if(!u.getUserName().equals(user.getUserName()) && !us.jarraitzenDu(u)) {
            		transactionInfo.addElement(u);
            		}
            	}
            	moneyModel.clear();
            	for(Follow f : us.getFollows()) {
        			moneyModel.addElement("Money: " + f.getMoney() + " Percentage: " + f.getBetPercentage());
        		}
            	
                followsInfo.clear(); 
            	follows = facade.userFollows(us);
        		for(NormalUser u : follows) {
        			if(!u.getUserName().equals(user.getUserName())) {
        			followsInfo.addElement(u);
        			}
        		}
        		textArea.setText("");
				textArea.setVisible(true);
				textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("followedWell"));
				} catch (NotEnoughMoneyException e1) {
						// TODO Auto-generated catch block
					textArea.setText("");
					textArea.setVisible(true);
					textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("NotEnoughMoneyException"));
				} catch(invalidPercentage e2) {
					
					textArea.setText("");
					textArea.setVisible(true);
					textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("invalidPercentage"));
					
				} catch(NonPositiveNum e3) {
					textArea.setText("");
					textArea.setVisible(true);
					textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("NonPositiveNum"));
				}
               
                
            }
            
        });
        addMoneyButton.setBounds(484, 400, 158, 32);
        getContentPane().add(addMoneyButton);
        
        
        
        JLabel lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("money")); //$NON-NLS-1$ //$NON-NLS-2$
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_1.setBounds(34, 357, 127, 32);
        getContentPane().add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("percentage")); //$NON-NLS-1$ //$NON-NLS-2$
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_2.setBounds(171, 363, 147, 26);
        getContentPane().add(lblNewLabel_2);
        
       
        
        money = new JTextField();
        money.setBounds(34, 400, 127, 32);
        getContentPane().add(money);
        money.setColumns(10);
        
        percentage = new JTextField();
        percentage.setBounds(171, 399, 140, 33);
        getContentPane().add(percentage);
        percentage.setColumns(10);
        
        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(222, 49, 222, 231);
        getContentPane().add(scrollPane_2);
        
        followList = new JList<NormalUser>();
        scrollPane_2.setViewportView(followList);
        
        followList.setModel(followsInfo);
        
        follows = facade.userFollows(user);
		for(NormalUser u : follows) {
			if(!u.getUserName().equals(user.getUserName())) {
			followsInfo.addElement(u);
			}
		}
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Unfollow")); //$NON-NLS-1$ //$NON-NLS-2$
		btnNewButton.setBounds(321, 399, 153, 33);
		getContentPane().add(btnNewButton);
		
		
		
		textArea.setBounds(82, 291, 229, 40);
		getContentPane().add(textArea);
		
		JLabel userPointLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("yourPoint") + ":" + user.getPoints()); //$NON-NLS-1$ //$NON-NLS-2$
		userPointLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		userPointLabel.setBounds(360, 291, 255, 40);
		getContentPane().add(userPointLabel);
		
		JScrollPane scrollPane_2_1 = new JScrollPane();
		scrollPane_2_1.setBounds(454, 49, 190, 231);
		getContentPane().add(scrollPane_2_1);
		
		moneyList = new JList();
		scrollPane_2_1.setViewportView(moneyList);
		
		for(Follow f : user.getFollows()) {
			moneyModel.addElement("Money: " + f.getMoney() + " Percentage: " + f.getBetPercentage());
		}
		moneyList.setModel(moneyModel);
		btnNewButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	               
	            	String name = followList.getSelectedValue().getUserName();
	                
	          		NormalUser us = facade.unfollow(name, user);
	          		
	          		
	          		System.out.println(us.getUserName());
	          		
	          		followsInfo.clear();
	          		transactionInfo.clear();
	          		moneyModel.clear();
	          		
	          		
	          		
	            	follows = facade.userFollows(us);
	          		for(NormalUser u : follows) {
	          			if(!u.getUserName().equals(user.getUserName()) ) {
	          				followsInfo.addElement(u);
	          				}
	          		}
	          		
	          		
	                transactions = facade.getNormalUsersRankings(us);
	         		for(NormalUser u : transactions) {
	         			if(!u.getUserName().equals(user.getUserName())&& !us.jarraitzenDu(u)) {
	         				transactionInfo.addElement(u);
	         				}
	         		}
	         		for(Follow f : us.getFollows()) {
	        			moneyModel.addElement("Money: " + f.getMoney() + " Percentage: " + f.getBetPercentage());
	        		}
	         		textArea.setText("");
					textArea.setVisible(true);
					textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("unFollowedWell")); 
	          		
	            }
	            
	            
	        });
	}
}
