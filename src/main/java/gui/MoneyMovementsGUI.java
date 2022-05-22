package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.NormalUser;
import domain.Transaction;
import domain.User;
import exceptions.IsEmpty;
import exceptions.NoValidMoneyException;
import exceptions.UserNameAlreadyUsed;
import exceptions.UserNotExist;
import exceptions.WrongPassword;
import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
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

public class MoneyMovementsGUI extends JFrame {
	private JLabel showMoneyLabel=new JLabel(ResourceBundle.getBundle("Etiquetas").getString("UserName"));
	private JLabel titleMoney;
	private JLabel lblSartuDatuakBerriro=new JLabel();
	private JLabel moneyLabel=new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AddMoneyToAccount"));
	private JButton showTransactionsButton;
	private JTextField add_removeMoney;
	private JScrollPane scrollPane;
	private JList<Transaction> transactionList = null;
	private DefaultListModel<Transaction> transactionInfo = new DefaultListModel<Transaction>();
	private JScrollPane scrollPane_1;
	private JTextPane textPane = new JTextPane();
	public MoneyMovementsGUI(NormalUser user) {
		
		getContentPane().setLayout(null);
		this.setSize(new Dimension(800, 400));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MoneyMovements"));
		BLFacade facade = MainGUI.getBusinessLogic();
		showMoneyLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		//lblNewLabel = new JLabel("User Name:");
		showMoneyLabel.setBounds(78, 105, 128, 29);
		getContentPane().add(showMoneyLabel);
		double userMoney = user.getMoney();
		String userMoneyString = String.valueOf(userMoney);
		showMoneyLabel.setText(userMoneyString);
		
		titleMoney = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MoneyMovements"));
		titleMoney.setFont(new Font("Tahoma", Font.BOLD, 18));
		titleMoney.setBounds(292, 11, 172, 40);
		getContentPane().add(titleMoney);
		lblSartuDatuakBerriro.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		//lblSartuDatuakBerriro = new JLabel("");
		lblSartuDatuakBerriro.setBounds(62, 258, 165, 16);
		getContentPane().add(lblSartuDatuakBerriro);
		
		JButton addMoneyButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Confirm")); //$NON-NLS-1$ //$NON-NLS-2$
		addMoneyButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addMoneyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String u = add_removeMoney.getText();
				double money=0;
				if(!u.isEmpty())
					money = Double.parseDouble(u);
				try{
				textPane.setText("");
				NormalUser user2 = facade.addMoney(money,user);
				user.setMoney(user2.getMoney());
				user.setMoneyTransactions(user2.getMoneyTransactions());
				showMoneyLabel.setText(String.valueOf(user2.getMoney()));
				}catch(NoValidMoneyException a) {
					textPane.setVisible(true);
					textPane.setText(ResourceBundle.getBundle("Etiquetas").getString("MoneyPositive"));
				}catch(IsEmpty a) {
					
				}
				add_removeMoney.setText("");
			}
		});
		addMoneyButton.setBounds(78, 285, 146, 49);
		getContentPane().add(addMoneyButton);
		moneyLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		moneyLabel.setBounds(24, 62, 255, 32);
		getContentPane().add(moneyLabel);
		
		showTransactionsButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SeeTransaction")); //$NON-NLS-1$ //$NON-NLS-2$
		showTransactionsButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		showTransactionsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//show money movements
				//transactionInfo.clear();
				ArrayList<Transaction> transactions = facade.seeMoneyMovements(user);
				refreshTransactions(transactions);
				/*for(Transaction d : transactions) {
					transactionInfo.addElement(d);
				}*/
			}
		});
		showTransactionsButton.setBounds(419, 285, 165, 49);
		getContentPane().add(showTransactionsButton);
	
		
		add_removeMoney = new JTextField();
		add_removeMoney.setFont(new Font("Tahoma", Font.PLAIN, 18));
		//add_removeMoney.setText(ResourceBundle.getBundle("Etiquetas").getString("MoneyMovementsGUI.textField.text")); //$NON-NLS-1$ //$NON-NLS-2$
		add_removeMoney.setBounds(62, 147, 132, 49);
		getContentPane().add(add_removeMoney);
		add_removeMoney.setColumns(10);
		
	
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(new Rectangle(289, 78, 453, 182));
		scrollPane_1.setViewportView(transactionList);
		getContentPane().add(scrollPane_1);
		
		transactionList = new JList<Transaction>();
		transactionList.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPane_1.setViewportView(transactionList);
		transactionList.setModel(transactionInfo);
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		
		 //$NON-NLS-1$ //$NON-NLS-2$
		textPane.setBounds(25, 228, 235, 46);
		getContentPane().add(textPane);
		
		
	}
	private void close() {
		this.setVisible(false);
	}
	private void refreshTransactions(ArrayList<Transaction> transactions) {
		transactionInfo.clear();
		for(Transaction d : transactions) {
			transactionInfo.addElement(d);
		}
	}
}