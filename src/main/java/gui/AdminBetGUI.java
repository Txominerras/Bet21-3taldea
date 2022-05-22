package gui;

import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import domain.Admin;
import domain.Event;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class AdminBetGUI extends JFrame {
	private JButton btnNewButton=new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));
	private JButton btnNewButton_1=new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
	private JLabel lblAdminGui=new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AdminBet"));
	private JButton btnCreateCuote=new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateCuote"));
	private JButton btnNewButton_2= new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));
	private JButton quit=new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JButton deleteEventButton=new JButton(ResourceBundle.getBundle("Etiquetas").getString("DeleteEvent"));
	private final JButton btnNewButton_3 = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PutResults"));
	private final JButton duplicateEventButton_1 = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DuplicateEvent"));
//tonteri bat
	public AdminBetGUI(Admin u) {
		getContentPane().setLayout(null);
		this.setSize(new Dimension(1000, 700));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("AdminBet"));
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame c = new CreateQuestionGUI(new Vector<Event>());
				c.setVisible(true);
			}
		});
		btnNewButton.setBounds(42, 161, 409, 61);
		getContentPane().add(btnNewButton);
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame c = new FindQuestionsGUI(null);
				c.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(42, 272, 409, 61);
		getContentPane().add(btnNewButton_1);
		btnCreateCuote.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		btnCreateCuote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame c = new CreateQuoteGUI();
				c.setVisible(true);
			}
		});
		btnCreateCuote.setBounds(42, 388, 409, 61);
		getContentPane().add(btnCreateCuote);
		lblAdminGui.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		lblAdminGui.setBounds(389, 35, 366, 74);
		getContentPane().add(lblAdminGui);
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame c = new CreateEventGUI();
				c.setVisible(true);
			}
		});
		btnNewButton_2.setBounds(502, 161, 409, 61);
		getContentPane().add(btnNewButton_2);
		quit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		//quit = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AdminBetGUI.btnNewButton_3.text"));
		quit.setBounds(366, 596, 218, 41);
		getContentPane().add(quit);
		//quit.setBounds(new Rectangle(275, 275, 130, 30));
		
		deleteEventButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AdminBetGUI.btnNewButton_3.text"));
		deleteEventButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		deleteEventButton.setText(ResourceBundle.getBundle("Etiquetas").getString("DeleteEvent"));
		deleteEventButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame c = new DeleteEventGUI();
				c.setVisible(true);
			}
		});
		deleteEventButton.setBounds(502, 388, 409, 60);
		getContentPane().add(deleteEventButton);
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame c = new PutResultsGUI();
				c.setVisible(true);
			}
		});
		
		btnNewButton_3.setBounds(502, 272, 409, 61);
		getContentPane().add(btnNewButton_3);
		duplicateEventButton_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		duplicateEventButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame c = new DuplicateEventGUI();
				c.setVisible(true);
			}
		});
		duplicateEventButton_1.setBounds(42, 504, 409, 61);
		
		getContentPane().add(duplicateEventButton_1);
		
		JButton SendMessage = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ChatsGroups"));
		SendMessage.setFont(new Font("Tahoma", Font.PLAIN, 18));
        SendMessage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame c = new SendMessageGUI(u);
                c.setVisible(true);
            }
        });
        SendMessage.setBounds(502, 504, 409, 61);
        getContentPane().add(SendMessage);
	}
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
