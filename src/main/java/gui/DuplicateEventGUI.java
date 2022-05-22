package gui;

import java.text.DateFormat;
import java.util.*;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Event;
import exceptions.DatePassed;
import exceptions.EventAlreadyCreated;
import exceptions.EventFinished;
import exceptions.IsEmpty;
import exceptions.NoValidDate;
import exceptions.QuestionAlreadyExist;

public class DuplicateEventGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	BLFacade facade = MainGUI.getBusinessLogic();

	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents"));
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;
	private JLabel warningLabel;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton jButtonDuplicate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DuplicateEvent"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelError = new JLabel();
	
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();
	private JTextField YearTextField;
	private JTextField monthTextField;
	private JTextField dayTextField;
	private final JLabel jLabelMonth = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Month")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel jLabelDay = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Day")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel lblNewLabel_3 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Year")); //$NON-NLS-1$ //$NON-NLS-2$

	public DuplicateEventGUI() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("DuplicateEvent"));
		jButtonDuplicate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		jButtonDuplicate.setEnabled(false);
		jComboBoxEvents.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setBounds(new Rectangle(314, 121, 360, 40));
		jLabelListOfEvents.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jLabelListOfEvents.setBounds(new Rectangle(314, 69, 277, 30));
		jCalendar.getDayChooser().getDayPanel().addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				jButtonDuplicate.setEnabled(true);
				
			}
		});

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jButtonDuplicate.setBounds(new Rectangle(68, 377, 231, 57));
		jButtonDuplicate.setEnabled(false);

		jButtonDuplicate.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				//jButtonCreate_actionPerformed(e); 
				try {
					Date d = new Date();
					int urt = Integer.parseInt(YearTextField.getText()) - 1900;
					d.setYear(urt);
					d.setMonth(Integer.parseInt(monthTextField.getText()));
					d.setDate(Integer.parseInt(dayTextField.getText()));
					d.setHours(0);
					d.setMinutes(0);
					d.setSeconds(0);
					//Date d = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));
					Date date = UtilDate.trim(d);
					Event event = (Event) jComboBoxEvents.getSelectedItem();
					jButtonDuplicate.setEnabled(true);
					facade.duplicateEvent(event, date);
					jComboBoxEvents.removeItem((Event)event);;
					jLabelError.setText("");
					jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("EventCreated"));
				}catch(IsEmpty e2) {
					jLabelError.setVisible(true);
					jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("EventNotSelected"));
				}catch(java.lang.NumberFormatException e2) {
					jLabelError.setVisible(true);
					jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("NotValidDate"));
				}
			}
		});
		jButtonClose.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jButtonClose.setBounds(new Rectangle(421, 377, 178, 57));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		jLabelMsg.setFont(new Font("Tahoma", Font.PLAIN, 18));

		jLabelMsg.setBounds(new Rectangle(68, 269, 531, 30));
		jLabelMsg.setForeground(Color.red);
		jLabelError.setFont(new Font("Tahoma", Font.PLAIN, 18));
		// jLabelMsg.setSize(new Dimension(305, 20));

		jLabelError.setBounds(new Rectangle(76, 326, 523, 40));
		jLabelError.setForeground(Color.red);

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelError, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonDuplicate, null);
		this.getContentPane().add(jLabelListOfEvents, null);
		this.getContentPane().add(jComboBoxEvents, null);

		this.getContentPane().add(jCalendar, null);
		
		
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar.getDate());
		paintDaysWithEvents(jCalendar,datesWithEventsCurrentMonth);
		jLabelEventDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);
		
		YearTextField = new JTextField();
		YearTextField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		YearTextField.setText(""); //$NON-NLS-1$ //$NON-NLS-2$
		YearTextField.setBounds(165, 227, 87, 31);
		getContentPane().add(YearTextField);
		YearTextField.setColumns(10);
		
		monthTextField = new JTextField();
		monthTextField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		monthTextField.setText(""); //$NON-NLS-1$ //$NON-NLS-2$
		monthTextField.setBounds(353, 227, 90, 31);
		getContentPane().add(monthTextField);
		monthTextField.setColumns(10);
		
		dayTextField = new JTextField();
		dayTextField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		dayTextField.setText(""); //$NON-NLS-1$ //$NON-NLS-2$
		dayTextField.setBounds(550, 227, 90, 31);
		getContentPane().add(dayTextField);
		dayTextField.setColumns(10);
		
		JLabel jLabelYear = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		jLabelYear.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jLabelYear.setBounds(68, 227, 87, 31);
		getContentPane().add(jLabelYear);
		jLabelMonth.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jLabelMonth.setBounds(279, 228, 64, 30);
		
		getContentPane().add(jLabelMonth);
		jLabelDay.setFont(new Font("Tahoma", Font.PLAIN, 18));
		jLabelDay.setBounds(465, 227, 64, 31);
		
		getContentPane().add(jLabelDay);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(68, 227, 87, 31);
		
		getContentPane().add(lblNewLabel_3);
		
		warningLabel = new JLabel("");
		warningLabel.setBounds(32, 265, 392, 14);
		warningLabel.setVisible(false);

		
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

						for (domain.Event ev : events)
							modelEvents.addElement(ev);
						jComboBoxEvents.repaint();

						//if (events.size() == 0)
							//jButtonCreate.setEnabled(false);
						//else
							//jButtonCreate.setEnabled(true);

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

