package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Quote;
import domain.Event;
import domain.NormalUser;
import domain.Question;
import exceptions.BetAlreadyCreatedException;
import exceptions.CuoteAlreadyCreated;
import exceptions.DatePassed;
import exceptions.EventFinished;
import exceptions.IsEmpty;
import exceptions.NotEnoughMoneyException;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CreateBetGUI2 extends JFrame{
	private static final long serialVersionUID = 1L;

	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	BLFacade facade = MainGUI.getBusinessLogic();
	
	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents"));
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton QuitButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QuitCuote"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelError = new JLabel();
	
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JComboBox<Question> comboBoxQuestion = new JComboBox<Question>();
	DefaultComboBoxModel<Question> modelQuestions = new DefaultComboBoxModel<Question>();
	
	private Event event;
	private Question q;
	private Quote c;
	private Quote cuoteBet;
	
	private JComboBox<Quote> comboBoxCuotes = new JComboBox<Quote>();
	DefaultComboBoxModel<Quote> modelCuotes = new DefaultComboBoxModel<Quote>();
	private  JLabel lblCuotesLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListCuotes")); //$NON-NLS-1$ //$NON-NLS-2$
	private  JButton SelectButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SelectCuote")); //$NON-NLS-1$ //$NON-NLS-2$
	private  JButton FinishButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("FinishBet")); //$NON-NLS-1$ //$NON-NLS-2$
	
	private  JComboBox<Quote> betedCuotes = new JComboBox<Quote>();
	DefaultComboBoxModel<Quote> modelSelectedCuotes = new DefaultComboBoxModel<Quote>();
	private JTextField amountToBet;


	public CreateBetGUI2(NormalUser user) {
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(800, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateCuote"));
		jComboBoxEvents.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jComboBoxEvents.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				event = (Event)jComboBoxEvents.getSelectedItem();
				modelQuestions.removeAllElements();
				modelCuotes.removeAllElements();
				for(Question q: event.getQuestions()) { //gertaerari dagozkion galderak lortzeko
					modelQuestions.addElement(q); //comboBoxa osatu galderekin
				}
				if(modelQuestions.getSize()==0) {
					SelectButton.setEnabled(false);
				}
				if(modelSelectedCuotes.getSize()==0) {
					QuitButton.setEnabled(false);
				}
			}
		});

		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setBounds(new Rectangle(298, 47, 450, 30));
		jLabelListOfEvents.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jLabelListOfEvents.setBounds(new Rectangle(298, 16, 347, 20));
		jCalendar.getYearChooser().getSpinner().setFont(new Font("Tahoma", Font.PLAIN, 18));
		jCalendar.getMonthChooser().getComboBox().setFont(new Font("Tahoma", Font.PLAIN, 11));
		jCalendar.getDayChooser().getDayPanel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));
		QuitButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		QuitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				betedCuotes.remove(betedCuotes.getSelectedIndex());
				modelSelectedCuotes.removeElementAt(betedCuotes.getSelectedIndex());
				if(modelSelectedCuotes.getSize()==0) { 
					QuitButton.setEnabled(false);
					FinishButton.setEnabled(false);
					SelectButton.setEnabled(false);
				}
			}
		});

		QuitButton.setBounds(new Rectangle(193, 394, 174, 45));
		QuitButton.setEnabled(false);
		jButtonClose.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		jButtonClose.setBounds(new Rectangle(23, 394, 130, 45));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		jLabelMsg.setBounds(new Rectangle(50, 353, 595, 30));
		jLabelMsg.setForeground(Color.red);
		// jLabelMsg.setSize(new Dimension(305, 20));

		jLabelError.setBounds(new Rectangle(62, 339, 305, 20));
		jLabelError.setForeground(Color.red);

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelError, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(QuitButton, null);
		this.getContentPane().add(jLabelListOfEvents, null);
		this.getContentPane().add(jComboBoxEvents, null);

		this.getContentPane().add(jCalendar, null);
		
		
		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar.getDate());
		paintDaysWithEvents(jCalendar,datesWithEventsCurrentMonth);
		jLabelEventDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);
		
		JLabel lblListOfQueston = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListOfQuestions")); //$NON-NLS-1$ //$NON-NLS-2$
		lblListOfQueston.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblListOfQueston.setBounds(298, 98, 347, 25);
		getContentPane().add(lblListOfQueston);
		comboBoxQuestion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		//comboBoxQuestion = new JComboBox();
		comboBoxQuestion.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				q = (Question)comboBoxQuestion.getSelectedItem();
				modelCuotes.removeAllElements();
				if(q!=null) {
					modelCuotes.removeAllElements();
					for(Quote c: q.getCuotes()) { //gertaerari dagozkion galderak lortzeko
						modelCuotes.addElement(c); //comboBoxa osatu galderekin
					}
				}
				if(modelCuotes.getSize()==0) {
					SelectButton.setEnabled(false);
				}
				if(betedCuotes.getWidth()==0) {
					QuitButton.setEnabled(false);
				}
				if(modelSelectedCuotes.getSize()==0) {
					QuitButton.setEnabled(false);
				}
			}
		});
		comboBoxQuestion.setBounds(298, 134, 450, 30);
		comboBoxQuestion.setModel(modelQuestions);
		getContentPane().add(comboBoxQuestion);
		comboBoxCuotes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxCuotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c= (Quote)comboBoxCuotes.getSelectedItem();
				if(c!=null) {
					SelectButton.setEnabled(true);
				}
				if(modelSelectedCuotes.getSize()==0) {
					QuitButton.setEnabled(false);
				}
			}
		});
		comboBoxCuotes.setBounds(298, 212, 450, 30);
		comboBoxCuotes.setModel(modelCuotes);
		getContentPane().add(comboBoxCuotes);
		lblCuotesLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCuotesLabel.setBounds(298, 186, 347, 25);
		
		getContentPane().add(lblCuotesLabel);
		SelectButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		SelectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean badago = false;
				for(int i = 0; i<betedCuotes.getItemCount(); i++) {
					if(betedCuotes.getItemAt(i).getCuoteNumber()==c.getCuoteNumber()) badago=true; //MEZU BAT IPINI BEHAR DA KUOTA BAT EZSIN DELA ERREPIKATU APUSTU BEREAN ADIERAZTEKO
				}
				if(badago==false && comboBoxCuotes.getSelectedItem()!=null)
					modelSelectedCuotes.addElement(c);
				if(modelSelectedCuotes.getSize()>0) {
					betedCuotes.setEnabled(true);
					FinishButton.setEnabled(true);
				}
			}
		});
		betedCuotes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		betedCuotes.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(modelSelectedCuotes.getSize()>0) {
				FinishButton.setEnabled(true);
				QuitButton.setEnabled(true);
				}
			}
		});
		betedCuotes.setModel(modelSelectedCuotes);
		SelectButton.setBounds(395, 394, 169, 45);
		SelectButton.setEnabled(false);
		getContentPane().add(SelectButton);
		FinishButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		FinishButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Quote> cuotesToBet = new ArrayList<Quote>();
				for(int i = 0; i<modelSelectedCuotes.getSize(); i++) {
					cuotesToBet.add(modelSelectedCuotes.getElementAt(i));
				}
				String moneyAmountString = amountToBet.getText();
				double moneyAmount=0;
				if(moneyAmountString!=null)
					try {
						moneyAmount = Integer.parseInt(moneyAmountString);
					}catch (NumberFormatException nfe){
						jLabelMsg.setText("Diru kopuruak zenbaki bat izan behar du");
					}
				Date d = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));
				if(cuotesToBet.size()==1) {
					try {
						NormalUser u = facade.createBet(user, c, moneyAmount,d);
						user.setBetList(u.getBetList());
						user.setMoney(u.getMoney());
						user.setMoneyTransactions(u.getMoneyTransactions());
					} catch (NotEnoughMoneyException e1) {
						// TODO Auto-generated catch block
						jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("MinimumBet") + " " + c.getQuestion().getBetMinimum());
					} catch(BetAlreadyCreatedException e2) {
						
					} catch(IsEmpty e3){
						
					} catch(DatePassed e4) {
						jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("DatePassed"));
					}
				}
				else {
					try {
						NormalUser u = facade.createMultiBet(user, cuotesToBet, moneyAmount,d);
						user.setBetList(u.getBetList());
						user.setMoney(u.getMoney());
						user.setMoneyTransactions(u.getMoneyTransactions());
					} catch (NotEnoughMoneyException e1) {
						// TODO Auto-generated catch block
						jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("MinimumBet") + " " + c.getQuestion().getBetMinimum());
					} catch(BetAlreadyCreatedException e2) {
						
					} catch(IsEmpty e3){
						
					} catch(DatePassed e4) {
						jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("DatePassed"));
					}
				}
			}
		});
		FinishButton.setBounds(591, 394, 157, 45);
		FinishButton.setEnabled(false);
		getContentPane().add(FinishButton);
		betedCuotes.setBounds(298, 288, 450, 30);
		betedCuotes.setEnabled(false);
		getContentPane().add(betedCuotes);
		
		amountToBet = new JTextField();
		amountToBet.setFont(new Font("Tahoma", Font.PLAIN, 14));
		//amountToBet.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateBetGUI2.textField.text")); //$NON-NLS-1$ //$NON-NLS-2$
		amountToBet.setBounds(23, 288, 259, 30);
		getContentPane().add(amountToBet);
		amountToBet.setColumns(10);
		
		JLabel moneyToBetLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AmountToBet")); //$NON-NLS-1$ //$NON-NLS-2$
		moneyToBetLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		moneyToBetLabel.setBounds(23, 257, 259, 30);
		getContentPane().add(moneyToBetLabel);
		
		JLabel selectedCuotesLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectedCuotes")); //$NON-NLS-1$ //$NON-NLS-2$
		selectedCuotesLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		selectedCuotesLabel.setBounds(298, 257, 315, 30);
		getContentPane().add(selectedCuotesLabel);
		
		// Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
//				this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
//					public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					System.out.println("calendarAnt: "+calendarAnt.getTime());
					System.out.println("calendarAct: "+calendarAct.getTime());
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());
					
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) { 
							// Si en JCalendar estÃ¡ 30 de enero y se avanza al mes siguiente, devolverÃ­a 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este cÃ³digo se dejarÃ¡ como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}
						
						jCalendar.setCalendar(calendarAct);
						
						BLFacade facade = MainGUI.getBusinessLogic();

						datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar.getDate());
					}



					paintDaysWithEvents(jCalendar,datesWithEventsCurrentMonth);

					//	Date firstDay = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));
					Date firstDay = UtilDate.trim(calendarAct.getTime());

					try {
						BLFacade facade = MainGUI.getBusinessLogic();

						Vector<domain.Event> events = facade.getEvents(firstDay);

						if (events.isEmpty())
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")
									+ ": " + dateformat1.format(calendarAct.getTime()));
						else
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						jComboBoxEvents.removeAllItems();
						System.out.println("Events " + events);
						
						modelEvents.removeAllElements();
						
						for (domain.Event ev : events)
							modelEvents.addElement(ev);
						jComboBoxEvents.repaint();
						
						

						if (events.size() == 0)
							QuitButton.setEnabled(false);
						/*else
							QuitButton.setEnabled(true);*/

					} catch (Exception e1) {

						jLabelError.setText(e1.getMessage());
					}

				}
			}
		});
		
		
	}
	public static void paintDaysWithEvents(JCalendar jCalendar,Vector<Date> datesWithEventsCurrentMonth) {
		// For each day with events in current month, the background color for that day is changed.

		
		Calendar calendar = jCalendar.getCalendar();
		
		int month = calendar.get(Calendar.MONTH);
		int today=calendar.get(Calendar.DAY_OF_MONTH);
		int year=calendar.get(Calendar.YEAR);
		
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;
		
		
	 	for (Date d:datesWithEventsCurrentMonth){

	 		calendar.setTime(d);
	 		System.out.println(d);
	 		

			
			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
//			    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
	 	}
	 	
 			calendar.set(Calendar.DAY_OF_MONTH, today);
	 		calendar.set(Calendar.MONTH, month);
	 		calendar.set(Calendar.YEAR, year);

	 	
	}
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}