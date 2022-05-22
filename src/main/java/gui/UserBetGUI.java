package gui;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

import domain.NormalUser;
import domain.User;

import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;

public class UserBetGUI extends JFrame  {
	private JLabel lblUserBet=new JLabel(ResourceBundle.getBundle("Etiquetas").getString("UserBet"));
	private JButton quit=new JButton(ResourceBundle.getBundle("Etiquetas").getString("Quit"));
	private final JButton btnMoneymovements = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MoneyMovements")); //$NON-NLS-1$ //$NON-NLS-2$
	private JButton btnQueryQuestions;
	private JButton btnNewButton=new JButton(ResourceBundle.getBundle("Etiquetas").getString("DeleteBets"));
	private JButton SendMessageButton=new JButton(ResourceBundle.getBundle("Etiquetas").getString("ChatsGroups"));
	private JButton createBetButton= new JButton();
	private JButton folllow=new JButton(ResourceBundle.getBundle("Etiquetas").getString("Follow"));

	public UserBetGUI(NormalUser u) {
		getContentPane().setLayout(null);
		this.setSize(new Dimension(900, 550));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("UserBet"));
		btnQueryQuestions = new JButton((ResourceBundle.getBundle("Etiquetas").getString("QueryQueries")));
		btnQueryQuestions.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnQueryQuestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FindQuestionsGUI f = new FindQuestionsGUI(u);
				f.setVisible(true);
			}
		});
		btnQueryQuestions.setBounds(47, 231, 365, 56);
		getContentPane().add(btnQueryQuestions);
		lblUserBet.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		//lblUserBet = new JLabel("User - BET21");
		lblUserBet.setBounds(359, 42, 321, 63);
		getContentPane().add(lblUserBet);
		quit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		//quit = new JButton(ResourceBundle.getBundle("Etiquetas").getString("UserBetGUI.btnNewButton_1.text"));
		quit.setBounds(295, 413, 250, 57);
		getContentPane().add(quit);
		//quit.setBounds(125, 275, 149, 23);
		getContentPane().add(quit);
		btnMoneymovements.setFont(new Font("Tahoma", Font.PLAIN, 18));
		//quit.setBounds(new Rectangle(275, 275, 130, 30));
		btnMoneymovements.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new MoneyMovementsGUI(u);
				a.setVisible(true);
			}
		});
		btnMoneymovements.setBounds(47, 318, 365, 56);
		
		getContentPane().add(btnMoneymovements);
		
		btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DeleteBets")); //$NON-NLS-1$ //$NON-NLS-2$
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DeleteBetGUI d = new DeleteBetGUI(u);
				d.setVisible(true);
			}
		});
		btnNewButton.setBounds(47, 141, 365, 56);
		getContentPane().add(btnNewButton);
		
		createBetButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Bet"));
		createBetButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		createBetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CreateBetGUI2(u);
				a.setVisible(true);
			}
		});
		createBetButton.setBounds(485, 231, 365, 56);
		getContentPane().add(createBetButton);
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		//createBetButton.setBounds(28, 225, 365, 56);
        getContentPane().add(createBetButton);
        
        //SendMessageButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("UserBetGUI.SendMessageButton.arg0")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-2$
        SendMessageButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
        //SendMessageButton.setBounds(40, 368, 365, -8);
        getContentPane().add(SendMessageButton);
        SendMessageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame a = new SendMessageGUI(u);
                a.setVisible(true);
            }
        });
        SendMessageButton.setBounds(485, 141, 365, 56);
        
        folllow.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
                 JFrame a = new FollowGUI(u);
                 a.setVisible(true);
        	}
        });
        folllow.setFont(new Font("Tahoma", Font.PLAIN, 18));
        folllow.setBounds(485, 318, 365, 56);
        getContentPane().add(folllow);

	}
	
	
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
