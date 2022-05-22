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
import domain.Event;
import domain.Question;
import exceptions.CuoteAlreadyCreated;
import exceptions.EventFinished;
import exceptions.NoValidMoneyException;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Font;

public class CreateQuoteGUI extends JFrame{
	private static final long serialVersionUID = 1L;

	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	BLFacade facade = MainGUI.getBusinessLogic();
	
	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents"));
	private JLabel jLabelCuote = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Cuote"));
	private JLabel jLabelPrize = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Prize"));
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));

	private JTextField jTextFieldCuote = new JTextField();
	private JTextField jTextFieldPrize = new JTextField();
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton jButtonCreate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateCuote"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelError = new JLabel();
	
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JComboBox<Question> comboBoxQuestion = new JComboBox<Question>();
	DefaultComboBoxModel<Question> modelQuestions = new DefaultComboBoxModel<Question>();
	
	private Event event;
	private Question q;

	
	public CreateQuoteGUI() {
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 18));
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateCuote"));
		jComboBoxEvents.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jComboBoxEvents.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				event = (Event)jComboBoxEvents.getSelectedItem();
				modelQuestions.removeAllElements();
				for(Question q: event.getQuestions()) { //gertaerari dagozkion galderak lortzeko
					modelQuestions.addElement(q); //comboBoxa osatu galderekin
				}
			}
		});

		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setBounds(new Rectangle(289, 77, 362, 28));
		jLabelListOfEvents.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jLabelListOfEvents.setBounds(new Rectangle(289, 46, 362, 20));
		jLabelCuote.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jLabelCuote.setBounds(new Rectangle(46, 254, 183, 40));
		jTextFieldCuote.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jTextFieldCuote.setBounds(new Rectangle(239, 254, 375, 40));
		jLabelPrize.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jLabelPrize.setBounds(new Rectangle(46, 319, 183, 39));
		jTextFieldPrize.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jTextFieldPrize.setBounds(new Rectangle(239, 319, 130, 39));

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));
		jButtonCreate.setFont(new Font("Tahoma", Font.PLAIN, 18));

		jButtonCreate.setBounds(new Rectangle(106, 375, 174, 53));
		jButtonCreate.setEnabled(false);

		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String option = jTextFieldCuote.getText();
				float prize=0;
				try {
					prize = Float.parseFloat(jTextFieldPrize.getText());
				}catch(NumberFormatException e3) {
					jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("IsEmpty"));
				}
					if(e!=null && q!=null) {
						try {
							facade.createCuote(event, q, option, prize);
							jLabelMsg.setText("");
							jLabelError.setText("");
							jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("CuoteCreated"));
						}catch(EventFinished e1) {
							jLabelMsg.setText("");
							jLabelError.setText("");
							jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("DatePassed"));
						}catch(CuoteAlreadyCreated e2){
							jLabelMsg.setText("");
							jLabelError.setText("");
							jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("CuoteAlreadyCreated"));
						}catch(NoValidMoneyException e3){
							jLabelMsg.setText("");
							jLabelError.setText("");
							jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("MinimumPrize"));
						}
					}
				}
		});
		jButtonClose.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jButtonClose.setBounds(new Rectangle(390, 375, 162, 53));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		jLabelMsg.setFont(new Font("Tahoma", Font.PLAIN, 18));

		jLabelMsg.setBounds(new Rectangle(371, 336, 305, 28));
		jLabelMsg.setForeground(Color.red);
		jLabelError.setFont(new Font("Tahoma", Font.PLAIN, 18));
		// jLabelMsg.setSize(new Dimension(305, 20));

		jLabelError.setBounds(new Rectangle(361, 295, 305, 30));
		jLabelError.setForeground(Color.red);

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelError, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(jTextFieldCuote, null);
		this.getContentPane().add(jLabelCuote, null);
		this.getContentPane().add(jTextFieldPrize, null);

		this.getContentPane().add(jLabelPrize, null);
		this.getContentPane().add(jLabelListOfEvents, null);
		this.getContentPane().add(jComboBoxEvents, null);

		this.getContentPane().add(jCalendar, null);
		
		
		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar.getDate());
		paintDaysWithEvents(jCalendar,datesWithEventsCurrentMonth);
		
		

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);
		
		JLabel lblListOfQueston = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListOfQuestions")); //$NON-NLS-1$ //$NON-NLS-2$
		lblListOfQueston.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblListOfQueston.setBounds(289, 144, 362, 28);
		getContentPane().add(lblListOfQueston);
		
		comboBoxQuestion = new JComboBox();
		comboBoxQuestion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxQuestion.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				q = (Question)comboBoxQuestion.getSelectedItem();
			}
		});
		comboBoxQuestion.setBounds(289, 183, 362, 28);
		comboBoxQuestion.setModel(modelQuestions);
		getContentPane().add(comboBoxQuestion);
		
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
							jButtonCreate.setEnabled(false);
						else
							jButtonCreate.setEnabled(true);

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
