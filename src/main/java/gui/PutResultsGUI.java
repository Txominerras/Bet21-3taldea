package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.*;
import javax.xml.crypto.Data;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Quote;
import domain.Event;
import domain.Question;
import exceptions.CuoteAlreadyCreated;
import exceptions.CuoteHasNotQuestion;
import exceptions.EventFinished;
import exceptions.QuestionHasResult;

import java.awt.Label;
import java.awt.Font;

public class PutResultsGUI extends JFrame{

	private static final long serialVersionUID = 1L;

	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	BLFacade facade = MainGUI.getBusinessLogic();
	
	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents"));
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private static Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton jButtonPutResult = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PutResults"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelQuotes = new JLabel();
	
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JComboBox<Question> comboBoxQuestion = new JComboBox<Question>();
	DefaultComboBoxModel<Question> modelQuestions = new DefaultComboBoxModel<Question>();
	
	private JComboBox<Quote> JComboBoxCuotes = new JComboBox<Quote>();
	DefaultComboBoxModel<Quote> modelCuotes = new DefaultComboBoxModel<Quote>();
	
	private Event event;
	private Question q;
	private JLabel ErrorLabel = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$

	
	@SuppressWarnings("rawtypes")
	public PutResultsGUI() {
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(850, 480));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("PutResults"));
		jComboBoxEvents.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jComboBoxEvents.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				event = (Event)jComboBoxEvents.getSelectedItem();
				ErrorLabel.setText("");
				modelQuestions.removeAllElements();
				modelCuotes.removeAllElements();
				for(Question q: event.getQuestions()) { //gertaerari dagozkion galderak lortzeko
					modelQuestions.addElement(q); //comboBoxa osatu galderekin
				}
				ErrorLabel.setText("");
				jButtonPutResult.disable();
			}
		});

		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setBounds(new Rectangle(324, 80, 466, 29));
		jLabelListOfEvents.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jLabelListOfEvents.setBounds(new Rectangle(324, 39, 337, 30));

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));
		jButtonPutResult.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jButtonPutResult.setEnabled(false);

		jButtonPutResult.setBounds(new Rectangle(158, 375, 250, 55));

		jButtonPutResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Quote WinnerCuote = (Quote) JComboBoxCuotes.getSelectedItem();
				ErrorLabel.setText("");
				try {
					facade.PutResults(WinnerCuote);
				} catch (CuoteHasNotQuestion e1) {
					ErrorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("CuoteNoQuestions"));
				}catch(QuestionHasResult e2) {
					ErrorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("AlreadyAResult"));
				}	
				}
			});
		jButtonClose.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jButtonClose.setBounds(new Rectangle(599, 375, 191, 55));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		jLabelMsg.setFont(new Font("Tahoma", Font.PLAIN, 18));

		jLabelMsg.setBounds(new Rectangle(68, 290, 516, 37));
		jLabelMsg.setForeground(Color.red);
		jLabelQuotes.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jLabelQuotes.setText(ResourceBundle.getBundle("Etiquetas").getString("ListCuotes")); //$NON-NLS-1$ //$NON-NLS-2$
		// jLabelMsg.setSize(new Dimension(305, 20));

		jLabelQuotes.setBounds(new Rectangle(324, 207, 337, 37));
		jLabelQuotes.setForeground(Color.BLACK);

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelQuotes, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonPutResult, null);
		this.getContentPane().add(jLabelListOfEvents, null);
		this.getContentPane().add(jComboBoxEvents, null);

		this.getContentPane().add(jCalendar, null);
		
		
		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar.getDate());
		paintDaysWithEvents(jCalendar,datesWithEventsCurrentMonth);
		
		

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 18, 180, 25);
		getContentPane().add(jLabelEventDate);
		
		JLabel lblListOfQueston = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListOfQuestions")); //$NON-NLS-1$ //$NON-NLS-2$
		lblListOfQueston.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblListOfQueston.setBounds(324, 120, 351, 30);
		getContentPane().add(lblListOfQueston);
		comboBoxQuestion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		comboBoxQuestion.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				q = (Question)comboBoxQuestion.getSelectedItem();
				ErrorLabel.setText("");
				if(q!=null) {
				modelCuotes.removeAllElements();
				for(Quote c: q.getCuotes()) { //gertaerari dagozkion galderak lortzeko
					modelCuotes.addElement(c); //comboBoxa osatu galderekin
				}
				jButtonPutResult.disable();
				}
			}
		});
		comboBoxQuestion.setBounds(324, 156, 466, 30);
		comboBoxQuestion.setModel(modelQuestions);
		getContentPane().add(comboBoxQuestion);
		
		JComboBoxCuotes = new JComboBox(modelCuotes);
		JComboBoxCuotes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JComboBoxCuotes.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent arg0) {
				ErrorLabel.setText("");
				jButtonPutResult.enable();
			}
		});
		JComboBoxCuotes.setBounds(324, 248, 466, 31);
		getContentPane().add(JComboBoxCuotes);
		ErrorLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ErrorLabel.setBackground(Color.BLACK);
		ErrorLabel.setForeground(Color.RED);
		ErrorLabel.setBounds(68, 338, 605, 30);
		
		getContentPane().add(ErrorLabel);
		
		// Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
//				this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
//					public void propertyChange(PropertyChangeEvent propertychangeevent) {
				ErrorLabel.setText("");
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
							// Si en JCalendar est???? 30 de enero y se avanza al mes siguiente, devolver????a 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este c????digo se dejar???? como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}
						
						jCalendar.setCalendar(calendarAct);
						
						BLFacade facade = MainGUI.getBusinessLogic();
						
						Vector<Date> EventDays =facade.getEventsMonth(jCalendar.getDate());
						
						/*for(Date d:EventDays) {
							if(!HasDatePassed(d)) 
								EventDays.remove(d);	
	
						}*/
					
						datesWithEventsCurrentMonth=EventDays;
					}



					paintDaysWithEvents(jCalendar,datesWithEventsCurrentMonth);

					//	Date firstDay = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));
					Date firstDay = UtilDate.trim(calendarAct.getTime());

					try {
						Vector<domain.Event> events = new Vector<domain.Event>();
						BLFacade facade = MainGUI.getBusinessLogic();
						//if(HasDatePassed(firstDay)) {
						 events = facade.getEvents(firstDay);
						//}
						if (events.isEmpty()) {
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")
									+ ": " + dateformat1.format(calendarAct.getTime()));}
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
							jButtonPutResult.setEnabled(false);
						else
							jButtonPutResult.setEnabled(true);

					} catch (Exception e1) {
						
					}

				}
			}
		});
		
		
	}
	@SuppressWarnings("deprecation")
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
	 		//if(d.getMonth()==Calendar.MONTH) {
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
	 	//	}
	 	
	 	}
	 	
 			calendar.set(Calendar.DAY_OF_MONTH, today);
	 		calendar.set(Calendar.MONTH, month);
	 		calendar.set(Calendar.YEAR, year);

	 	
	}
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	
	public static boolean HasDatePassed(Date date) {
		
		Date actualdate = new Date();
		return date.before(actualdate);

	}
	
	
}