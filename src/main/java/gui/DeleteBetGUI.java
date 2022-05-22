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
import domain.Bet;
import domain.NormalUser;
import domain.Transaction;
import domain.User;
import exceptions.IsEmpty;
import exceptions.NoValidMoneyException;
import exceptions.NonPositiveNum;
import exceptions.UserNameAlreadyUsed;
import exceptions.UserNotExist;
import exceptions.WrongPassword;
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

public class DeleteBetGUI extends JFrame {
	private JLabel lblSartuDatuakBerriro=new JLabel();
	private JScrollPane scrollPane;
	private JList<Bet> transactionList = null;
	private DefaultListModel<Bet> transactionInfo = new DefaultListModel<Bet>();
	private JScrollPane scrollPane_1;
	private ArrayList<Bet> transactions;
	private User userdb;
	public DeleteBetGUI(NormalUser user) {
		
		getContentPane().setLayout(null);
		this.setSize(new Dimension(1000, 400));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("BetDelete"));
		BLFacade facade = MainGUI.getBusinessLogic();
		
		JLabel titleBet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BetDelete"));
		titleBet.setFont(new Font("Tahoma", Font.BOLD, 18));
		titleBet.setBounds(410, 11, 165, 27);
		getContentPane().add(titleBet);
		lblSartuDatuakBerriro.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		//lblSartuDatuakBerriro = new JLabel("");
		lblSartuDatuakBerriro.setBounds(87, 250, 305, 40);
		getContentPane().add(lblSartuDatuakBerriro);
		//userdb = facade.getUserFromDB(user); 
		transactions = user.getBetList();
		for(Bet d : transactions) {
			transactionInfo.addElement(d);
		}
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(new Rectangle(34, 49, 922, 241));
		scrollPane_1.setViewportView(transactionList);
		getContentPane().add(scrollPane_1);
		
		transactionList = new JList<Bet>();
		transactionList.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPane_1.setViewportView(transactionList);
		transactionList.setModel(transactionInfo);
		JButton addMoneyButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Confirm")); //$NON-NLS-1$ //$NON-NLS-2$
		addMoneyButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
        addMoneyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int betnum= transactionList.getSelectedValue().getBetNumber();
                //transactionInfo.clear();
                transactions = user.getBetList();
                refreshData(transactions);
                /*for(Bet d : transactions) {
        			transactionInfo.addElement(d);
        		}*/
                try{
                    NormalUser u = (NormalUser) facade.deleteBet(betnum, user);
                    user.setBetList(u.getBetList());
                    user.setMoney(u.getMoney());
                    user.setMoneyTransactions(u.getMoneyTransactions());
                    transactionInfo.clear();
                    transactions = user.getBetList();
            		for(Bet d : transactions) 
            			transactionInfo.addElement(d);
                }catch(NonPositiveNum a) {
                    System.out.println("Sartutako zenbakia ez da onargarria");
                }catch(IsEmpty a) {
                    System.out.println("Sartu apostuaren zenbakia");
                }
            }
        });
        addMoneyButton.setBounds(410, 311, 194, 39);
        getContentPane().add(addMoneyButton);
	}
	public void close() {
		this.setVisible(false);
	}
	private void refreshData(ArrayList<Bet> bets) {
		 transactionInfo.clear();
		 for(Bet d : bets) {
 			transactionInfo.addElement(d);
 		}
	}
}